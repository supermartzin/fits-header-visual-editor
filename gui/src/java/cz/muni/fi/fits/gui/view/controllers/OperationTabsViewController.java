package cz.muni.fi.fits.gui.view.controllers;

import cz.muni.fi.fits.gui.MainApp;
import cz.muni.fi.fits.gui.models.FitsFile;
import cz.muni.fi.fits.gui.models.inputdata.InputData;
import cz.muni.fi.fits.gui.services.ExecutionService;
import cz.muni.fi.fits.gui.utils.dialogs.InfoDialog;
import cz.muni.fi.fits.gui.utils.dialogs.WarningDialog;
import cz.muni.fi.fits.gui.view.operationtabs.controllers.TabController;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * TODO description
 */
public class OperationTabsViewController extends Controller {

    public Button startButton;
    public ProgressBar progressBar;

    private final Collection<TabController> _tabs;
    private MainApp _mainApp;

    public OperationTabsViewController() {
        _tabs = new HashSet<>();
    }

    @Override
    public void init() {
    }

    public void onStartEditor() {
        // get currently selected operation tab
        Optional<TabController> optional = _tabs.stream()
                .filter(TabController::selected)
                .findFirst();

        if (optional.isPresent()) {
            TabController tab = optional.get();

            // input data
            InputData inputData = tab.getInputData();
            if (inputData != null) {
                // FITS files
                Collection<FitsFile> fitsFiles = _mainApp.getFitsFiles();
                if (checkFitsFiles(fitsFiles)) {
                    try {
                        // create input file with FITS filepaths
                        Path filesIn = createInputFile(fitsFiles);

                        inputData.setInputFilePath(filesIn.toAbsolutePath().toString());

                        ExecutionService service =
                                new ExecutionService(_mainApp.getEngineFilepath(),
                                        inputData.getInputDataArguments(),
                                        fitsFiles.size());

                        progressBar.progressProperty().unbind();
                        progressBar.progressProperty().bind(service.getTask().progressProperty());

                        // on operation succeeded
                        service.setOnSucceeded(event -> {
                            // delete temp input file
                            deleteInputFile(filesIn);

                            // show success dialog
                            InfoDialog.show(
                                    ""/*_resources.getString("")*/,
                                    null,
                                    ""/*_resources.getString("")*/);

                            progressBar.progressProperty().unbind();
                            progressBar.setProgress(0);
                        });

                        service.setOnFailed(event -> {
                            // delete temp input file
                            deleteInputFile(filesIn);

                            // TODO show error dialog

                            progressBar.progressProperty().unbind();
                            progressBar.setProgress(0.0);
                        });

                        service.start();
                    } catch (IOException ioEx) {
                        // TODO handle exception
                    }
                }
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

    private boolean checkFitsFiles(Collection<FitsFile> fitsFiles) {
        if (fitsFiles == null || fitsFiles.isEmpty()) {
            WarningDialog.show(
                    _resources.getString("oper.common.alert.title"),
                    _resources.getString("oper.common.alert.header"),
                    _resources.getString("oper.common.alert.content.files.empty"));

            return false;
        } else {
            return true;
        }
    }

    private Path createInputFile(Collection<FitsFile> fitsFiles)
            throws IOException {
        List<String> filepaths = new LinkedList<>();
        fitsFiles.forEach(fitsFile -> filepaths.add(fitsFile.getFilepath()));

        Path filesIn = Files.createTempFile("files", "in");
        Files.write(filesIn, filepaths);

        return filesIn;
    }

    private void deleteInputFile(Path inputFile) {
        try {
            Files.deleteIfExists(inputFile);
        } catch (IOException ignored) {
        }
    }
}
