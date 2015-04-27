package cz.muni.fi.fits.gui.view.controllers;

import cz.muni.fi.fits.gui.MainApp;
import cz.muni.fi.fits.gui.models.Language;
import cz.muni.fi.fits.gui.services.PreferencesService;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Modality;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * TODO description
 */
public class RootLayoutController implements Initializable {

    public ToggleGroup languages;
    public Menu langMenu;

    private MainApp _mainApp;
    private boolean _firstToggleEvent = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
