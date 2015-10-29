package cz.muni.fi.fits.gui.view.controllers;

import cz.muni.fi.fits.gui.MainApp;
import cz.muni.fi.fits.gui.models.FitsFile;
import cz.muni.fi.fits.gui.models.inputdata.InputData;
import cz.muni.fi.fits.gui.services.ExecutionService;
import cz.muni.fi.fits.gui.services.ExecutionTask;
import cz.muni.fi.fits.gui.view.operationtabs.controllers.TabController;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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

            try {
                List<String> filepaths = new LinkedList<>();
                fitsFiles.forEach(fitsFile -> filepaths.add(fitsFile.getFilepath()));

                Path filesIn = Files.createTempFile("files", "in");
                Files.write(filesIn, filepaths);

                if (inputData != null) {
                    inputData.setInputFilePath(filesIn.toAbsolutePath().toString());

                    ExecutionTask executionTask = new ExecutionTask(
                            inputData.getInputDataArguments(), fitsFiles.size(), _mainApp.getEngineFilepath());
                    ExecutionService service = new ExecutionService(executionTask);

                    progressBar.progressProperty().unbind();
                    progressBar.progressProperty().bind(executionTask.progressProperty());

                    service.setOnSucceeded(event -> {
                        try {
                            Files.deleteIfExists(filesIn);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    service.setOnFailed(event -> System.err.println(event.getSource()));

                    service.start();
                }
            } catch (IOException ioEx) {
                // TODO handle exception
                ioEx.printStackTrace();
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
