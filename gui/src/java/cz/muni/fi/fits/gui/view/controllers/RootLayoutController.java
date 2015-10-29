package cz.muni.fi.fits.gui.view.controllers;

import cz.muni.fi.fits.gui.MainApp;
import cz.muni.fi.fits.gui.models.Language;
import cz.muni.fi.fits.gui.services.PreferencesService;
import cz.muni.fi.fits.gui.services.ResourceBundleService;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * TODO description
 */
public class RootLayoutController implements Initializable {

    public ToggleGroup languages;
    public Menu langMenu;

    private MainApp _mainApp;
    private ResourceBundle _resources;
    private boolean _firstToggleEvent = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _resources = resources;

        // add listener for language changed
        languages.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !_firstToggleEvent) {
                RadioMenuItem langItem = (RadioMenuItem) newValue;
                // get new language
                Language newLanguage = Language.getLanguageByCode(langItem.getId());
                // save new language
                PreferencesService.saveLanguage(newLanguage, _mainApp.getClass());
                // alert user about restart
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(resources.getString("restart.alert.title"));
                alert.setHeaderText(resources.getString("restart.alert.header"));
                alert.setContentText(resources.getString("restart.alert.text.lang"));
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.initOwner(_mainApp.getPrimaryStage());
                alert.showAndWait();
            }

            if (_firstToggleEvent)
                _firstToggleEvent = false;
        });

        // set selected language menu item
        Language language = PreferencesService.getLanguage(MainApp.class);
        selectLanguageItem(language);
    }

    public void setMainApp(MainApp mainApp) {
        _mainApp = mainApp;
    }

    public void onPreferences() {
        try {
            FXMLLoader userPreferencesFile = new FXMLLoader(MainApp.class.getResource("view/UserPreferences.fxml"));
            ResourceBundleService.setResourceBundle(userPreferencesFile);
            AnchorPane anchorPane = userPreferencesFile.load();

            Stage stage = new Stage();
            stage.setTitle(_resources.getString("prefs"));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(_mainApp.getPrimaryStage());

            stage.setScene(new Scene(anchorPane));

            UserPreferencesController controller = userPreferencesFile.getController();
            controller.setOwner(stage);
            controller.setMainApp(_mainApp);

            stage.showAndWait();
        } catch (IOException ioEx) {
            // TODO handle exception
        }

    }

    public void onExit() {
        System.exit(0);
    }

    public void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("TODO about dialog"); // TODO about dialog

        alert.initOwner(_mainApp.getPrimaryStage());
        alert.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait();
    }

    private void selectLanguageItem(Language language) {
        for (MenuItem menuItem : langMenu.getItems()) {
            RadioMenuItem item = (RadioMenuItem) menuItem;
            if (item.getId().equals(language.getCode())) {
                item.setSelected(true);
                return;
            }
        }
    }
}
