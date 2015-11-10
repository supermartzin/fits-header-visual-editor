package cz.muni.fi.fits.gui.view.controllers;

import cz.muni.fi.fits.gui.MainApp;
import cz.muni.fi.fits.gui.models.FitsFile;
import cz.muni.fi.fits.gui.services.ResourceBundleService;
import cz.muni.fi.fits.gui.utils.dialogs.ExceptionDialog;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * TODO description
 */
public class FilesOverviewController extends Controller {

    public TableView<FitsFile> tableView;
    public TableColumn<FitsFile, Boolean> selectColumn;
    public TableColumn<FitsFile, String> filenameColumn;
    public TableColumn<FitsFile, String> filepathColumn;
    public Label numberOfSelectedFilesLabel;

    public MenuItem deleteMenuItem;
    public MenuItem selectMenuItem;
    public MenuItem invertMenuItem;
    public MenuItem deselectMenuItem;

    public Button invertSelectionButton;
    public Button filterFilesButton;

    private CheckBox selectFilesCheckBox;
    private IntegerProperty _numberOfSelectedFiles;

    @Override
    public void init() {
        // initialize number of selected files
        _numberOfSelectedFiles = new SimpleIntegerProperty(0);
        _numberOfSelectedFiles.addListener((observable, oldValue, newValue) -> {
            numberOfSelectedFilesLabel.setText(newValue.toString());
        });

        // allow multiple rows selection
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // listener for items selection change
        tableView.getSelectionModel().getSelectedIndices().addListener((ListChangeListener<Integer>) change -> {
            if (change.getList().size() > 0) {
                setContextMenuState(true, true, true, true);
            } else {
                setContextMenuState(false, false, false, false);
            }
        });

        // prevent table columns reordering
        tableView.getColumns().addListener((ListChangeListener<TableColumn<FitsFile, ?>>) change -> {
            change.next();
            if (change.wasReplaced()) {
                tableView.getColumns().clear();
                tableView.getColumns().addAll(selectColumn, filenameColumn, filepathColumn);
            }
        });

        // set column as a CheckBox column
        selectColumn.setCellFactory(cellData -> {
            CheckBoxTableCell<FitsFile, Boolean> checkBoxTableCell = new CheckBoxTableCell<>();
            // set value changed listener
            checkBoxTableCell.setSelectedStateCallback(index -> {
                int count = tableView.getItems().stream().filter(FitsFile::isSelected).toArray().length;
                _numberOfSelectedFiles.setValue(count);

                return tableView.getItems().get(index).selectedProperty();
            });
            return checkBoxTableCell;
        });

        // add checkbox to column heading
        selectFilesCheckBox = new CheckBox();
        selectFilesCheckBox.setDisable(true);
        selectColumn.setGraphic(selectFilesCheckBox);
        selectFilesCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            tableView.getItems().forEach(fitsFile -> fitsFile.setSelected(newValue));
        });

        // set value of cells in column
        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        filenameColumn.setCellValueFactory(cellData -> cellData.getValue().filenameProperty());
        filepathColumn.setCellValueFactory(cellData -> cellData.getValue().filepathProperty());
    }

    public void setTableItemsCollection(ObservableList<FitsFile> observableList) {
        if (observableList == null)
            throw new IllegalArgumentException("observableList is null");

        // set table items
        tableView.setItems(observableList);

        // listener for number of items in table
        tableView.getItems().addListener((ListChangeListener<FitsFile>) change -> {
            if (change.getList().size() > 0) {
                setSelectButtonsState(true, true, true);
            } else {
                setSelectButtonsState(false, false, false);
            }
        });
    }

    //region Buttons methods

    public void onInvertSelection() {
        tableView.getItems().forEach(fitsFile -> fitsFile.setSelected(!fitsFile.isSelected()));
    }

    public void onFilterActivated() {
        try {
            FXMLLoader filterDialogFile = new FXMLLoader(MainApp.class.getResource("view/FilterDialog.fxml"));
            ResourceBundleService.setResourceBundle(filterDialogFile);
            AnchorPane anchorPane = filterDialogFile.load();

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(_resources.getString("filter.dialog.title"));
            stage.getIcons().add(_mainApp.getApplicationIcon());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(_mainApp.getPrimaryStage());

            stage.setScene(new Scene(anchorPane));

            FilterDialogController controller = filterDialogFile.getController();
            controller.setMainApp(_mainApp);
            controller.setOwner(stage);

            stage.showAndWait();
        } catch (IOException ioEx) {
            ExceptionDialog.show(
                    _resources.getString("app.error.dialog.title"),
                    _resources.getString("app.error.dialog.header"),
                    _resources.getString("app.error.dialog.content.filter"),
                    ioEx,
                    _mainApp);
        }
    }

    public void onAddFiles() {
        // set Extension filter for FITS files
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("FITS files (*.fit, *.fits, *.fts)", "*.fit", "*.fits", "*.fts");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().add(extensionFilter);
        List<File> files = fileChooser.showOpenMultipleDialog(_mainApp.getPrimaryStage());
        if (files != null) {
            files.forEach(file -> {
                FitsFile newFile = new FitsFile(file.getName(), file.getAbsolutePath());
                // check for file uniqueness
                if (!tableView.getItems().contains(newFile))
                    tableView.getItems().add(newFile);
            });
        }
    }

    private void setSelectButtonsState(boolean selectAllState, boolean invertSelectionState, boolean filterState) {
        selectFilesCheckBox.setDisable(!selectAllState);
        invertSelectionButton.setDisable(!invertSelectionState);
        filterFilesButton.setDisable(!filterState);
    }

    //endregion

    //region Context Menu methods

    public void onContextMenuShowing() {
        ObservableList<FitsFile> selectedItems = tableView.getSelectionModel().getSelectedItems();
        if (areAllItemsSelected(selectedItems)) {
            selectMenuItem.setDisable(true);
        }
        if (areAllItemsDeselected(selectedItems)) {
            deselectMenuItem.setDisable(true);
        }
    }

    public void onContextMenuHidden() {
        selectMenuItem.setDisable(false);
        deselectMenuItem.setDisable(false);
    }

    public void onDeleteContextMenu() {
        ObservableList<FitsFile> selected = tableView.getSelectionModel().getSelectedItems();
        if (selected.size() > 0) tableView.getItems().removeAll(selected);
    }

    public void onSelectContextMenu() {
        ObservableList<FitsFile> selected = tableView.getSelectionModel().getSelectedItems();
        selected.forEach(fitsFile -> fitsFile.setSelected(true));
    }

    public void onDeselectContextMenu() {
        ObservableList<FitsFile> selected = tableView.getSelectionModel().getSelectedItems();
        selected.forEach(fitsFile -> fitsFile.setSelected(false));
    }

    public void onInvertContextMenu() {
        ObservableList<FitsFile> selected = tableView.getSelectionModel().getSelectedItems();
        selected.forEach(fitsFile -> fitsFile.setSelected(!fitsFile.isSelected()));
    }

    private void setContextMenuState(boolean deleteState, boolean selectState, boolean deselectState, boolean invertState) {
        deleteMenuItem.setDisable(!deleteState);
        selectMenuItem.setDisable(!selectState);
        deselectMenuItem.setDisable(!deselectState);
        invertMenuItem.setDisable(!invertState);
    }

    private boolean areAllItemsSelected(Collection<FitsFile> fitsFiles) {
        if (fitsFiles == null)
            return false;

        // if at least one FitsFile is deselected > return false
        for (FitsFile fitsFile : fitsFiles) {
            if (!fitsFile.isSelected())
                return false;
        }

        return true;
    }

    private boolean areAllItemsDeselected(Collection<FitsFile> fitsFiles) {
        if (fitsFiles == null)
            return false;

        // if at least one FitsFile is selected > return false
        for (FitsFile fitsFile : fitsFiles) {
            if (fitsFile.isSelected())
                return false;
        }

        return true;
    }

    //endregion
}
