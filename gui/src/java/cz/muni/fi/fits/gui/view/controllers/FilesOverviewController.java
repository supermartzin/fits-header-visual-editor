package cz.muni.fi.fits.gui.view.controllers;

import cz.muni.fi.fits.gui.MainApp;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * TODO description
 */
public class FilesOverviewController implements Initializable {

    public ListView<String> filesList;

    private MainApp _mainApp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setMainApp(MainApp mainApp) {
        _mainApp = mainApp;
    }

    public void onSelectNone(ActionEvent actionEvent) {

    }

    public void onSelectAll(ActionEvent actionEvent) {

    }

    public void onBrowseFiles(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        List<File> files = fileChooser.showOpenMultipleDialog(_mainApp.getPrimaryStage());
        List<String> fileItems = new ArrayList<>();
        files.forEach(file -> fileItems.add(file.getAbsolutePath()));
        filesList.setItems(FXCollections.observableArrayList(fileItems));
    }
}
