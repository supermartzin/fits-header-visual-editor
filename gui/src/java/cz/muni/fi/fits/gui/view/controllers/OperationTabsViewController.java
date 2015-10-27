package cz.muni.fi.fits.gui.view.controllers;

import cz.muni.fi.fits.gui.MainApp;
import cz.muni.fi.fits.gui.models.FitsFile;
import cz.muni.fi.fits.gui.models.inputdata.InputData;
import cz.muni.fi.fits.gui.view.operationtabs.controllers.TabController;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * TODO description
 */
public class OperationTabsViewController implements Initializable {

    public Button startButton;
    public ProgressBar progressBar;

    private final Collection<TabController> _tabs;
    private MainApp _mainApp;

    public OperationTabsViewController() {
        _tabs = new HashSet<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onStartEditor() {
        Optional<TabController> optional = _tabs.stream().filter(TabController::selected).findFirst();

        if (optional.isPresent()) {
            TabController tab = optional.get();

            // input data
            InputData inputData = tab.getInputData();

            // FITS files
            Collection<FitsFile> fitsFiles = _mainApp.getFitsFiles();



            if (inputData != null) {
                inputData.setInputFilePath("files.in");
                System.out.println(inputData.getInputDataString()); // TODO delete
            }
        }
    }
    
    public void addTabController(TabController tabController) {
        if (tabController != null) {
            _tabs.add(tabController);
        }
    }

    public void setMainApp(MainApp mainApp) {
        if (mainApp == null)
            throw new IllegalArgumentException("mainApp object is null");

        _mainApp = mainApp;
    }
}
