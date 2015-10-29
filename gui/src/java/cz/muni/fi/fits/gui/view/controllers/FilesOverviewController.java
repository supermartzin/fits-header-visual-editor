package cz.muni.fi.fits.gui.view.controllers;

import cz.muni.fi.fits.gui.MainApp;
import cz.muni.fi.fits.gui.models.FitsFile;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * TODO description
 */
public class FilesOverviewController implements Initializable {

    public TableView<FitsFile> tableView;
    public TableColumn<FitsFile, Boolean> selectColumn;
    public TableColumn<FitsFile, String> filenameColumn;
    public TableColumn<FitsFile, String> filepathColumn;
    public Button selectNoneButton;
    public Button selectAllButton;
    public Button invertSelectionButton;
    public MenuItem deleteMenuItem;
    public MenuItem selectMenuItem;
    public MenuItem invertMenuItem;
    public MenuItem deselectMenuItem;
    public Label numberOfSelectedFilesLabel;

    private MainApp _mainApp;
    private IntegerProperty _numberOfSelectedFiles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
                int count = tableView.getItems().stream().filter(FitsFile::getSelected).toArray().length;
                _numberOfSelectedFiles.setValue(count);

                return tableView.getItems().get(index).selectedProperty();
            });
            return checkBoxTableCell;
        });

        // set value of cells in column
        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        filenameColumn.setCellValueFactory(cellData -> cellData.getValue().filenameProperty());
        filepathColumn.setCellValueFactory(cellData -> cellData.getValue().filepathProperty());
    }

    public void setMainApp(MainApp mainApp) {
        _mainApp = mainApp;
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

    public void onSelectNone() {
        tableView.getItems().forEach(fitsFile -> fitsFile.setSelected(false));
    }

    public void onSelectAll() {
        tableView.getItems().forEach(fitsFile -> fitsFile.setSelected(true));
    }

    public void onInvertSelection() {
        tableView.getItems().forEach(fitsFile -> fitsFile.setSelected(!fitsFile.getSelected()));
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

    private void setSelectButtonsState(boolean selectAllState, boolean selectNoneState, boolean invertSelectionState) {
        selectAllButton.setDisable(!selectAllState);
        selectNoneButton.setDisable(!selectNoneState);
        invertSelectionButton.setDisable(!invertSelectionState);
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
        selected.forEach(fitsFile -> fitsFile.setSelected(!fitsFile.getSelected()));
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
            if (!fitsFile.getSelected())
                return false;
        }

        return true;
    }

    private boolean areAllItemsDeselected(Collection<FitsFile> fitsFiles) {
        if (fitsFiles == null)
            return false;

        // if at least one FitsFile is selected > return false
        for (FitsFile fitsFile : fitsFiles) {
            if (fitsFile.getSelected())
                return false;
        }

        return true;
    }

    //endregion
}
