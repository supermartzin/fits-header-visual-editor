package cz.muni.fi.fits.gui.view.controllers;

import cz.muni.fi.fits.gui.MainApp;
import cz.muni.fi.fits.gui.services.PreferencesService;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * TODO insert description
 *
 * @author Martin Vrábel - © 2015 FITS-HeaderVisualEditor
 * @version 1.0
 */
public class UserPreferencesController implements Initializable {

    private Stage _ownerStage;
    private MainApp _mainApp;

    public TextField enginePathField;

    public Button okButton;
    public Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        enginePathField.setText(PreferencesService.loadEngineFilePath(this.getClass()));
    }

    public void setOwner(Stage ownerStage) {
        _ownerStage = ownerStage;
    }

    public void setMainApp(MainApp mainApp) {
        if (mainApp == null)
            throw new IllegalArgumentException("mainApp object is null");

        _mainApp = mainApp;
    }

    public void onChooseEngineFile() {
        // set extension filter for .jar file
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("FITS Header Engine JAR file (*.jar)", "*.jar");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().add(extensionFilter);
        File jarFile = fileChooser.showOpenDialog(_ownerStage);
        if (jarFile != null) {
            enginePathField.setText(jarFile.getAbsolutePath());
        }
    }

    public void onOK() {
        // get engine filepath
        String filepath = enginePathField.getText();
        // save engine filepath
        PreferencesService.saveEngineFilePath(filepath, this.getClass());
        _mainApp.setEngineFilepath(filepath);

        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public void onCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
