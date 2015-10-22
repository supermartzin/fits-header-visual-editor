package cz.muni.fi.fits.gui.view.controllers;

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

            InputData inputData = tab.getInputData();
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
}
