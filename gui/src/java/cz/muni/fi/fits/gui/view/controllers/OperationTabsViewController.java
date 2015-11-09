package cz.muni.fi.fits.gui.view.controllers;

import cz.muni.fi.fits.gui.models.FitsFile;
import cz.muni.fi.fits.gui.models.Preferences;
import cz.muni.fi.fits.gui.models.inputdata.InputData;
import cz.muni.fi.fits.gui.services.ExecutionService;
import cz.muni.fi.fits.gui.utils.Constants;
import cz.muni.fi.fits.gui.utils.Parsers;
import cz.muni.fi.fits.gui.utils.dialogs.ErrorDialog;
import cz.muni.fi.fits.gui.utils.dialogs.ExceptionDialog;
import cz.muni.fi.fits.gui.utils.dialogs.InfoDialog;
import cz.muni.fi.fits.gui.utils.dialogs.WarningDialog;
import cz.muni.fi.fits.gui.view.operationtabs.controllers.TabController;
import javafx.concurrent.Worker;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * TODO description
 */
public class OperationTabsViewController extends Controller {

    public Button startButton;
    public ProgressBar progressBar;

    private final Collection<TabController> _tabs;

    public OperationTabsViewController() {
        _tabs = new HashSet<>();
    }

    @Override
    public void init() { }

    public void onStartEditor() {
        // get currently selected operation tab
        Optional<TabController> optional = _tabs.stream()
                .filter(TabController::selected)
                .findFirst();

        if (optional.isPresent()) {
            TabController tab = optional.get();
            Preferences preferences = _mainApp.getPreferences();

            // check filepath to engine JAR file
            String engineFilepath = preferences.getEngineFilepath();
            if (!checkEngineFilepath(engineFilepath))
                return;

            // check correct java version
            if (!checkJavaVersion())
                return;

            // input data
            InputData inputData = tab.getInputData();
            if (inputData != null) {
                try {
                    // check input FITS files
                    Collection<FitsFile> fitsFiles = _mainApp.getFitsFiles();
                    if (!checkFitsFiles(fitsFiles)) return;

                    // create engine properties file
                    Path engineProperties = buildEnginePropertiesFile(preferences);

                    // create input file with FITS filepaths
                    Path filesIn = createInputFile(fitsFiles);

                    inputData.setInputFilePath(filesIn.toAbsolutePath().toString());

                    ExecutionService service = new ExecutionService(inputData.getInputDataArguments(),
                                                                    engineFilepath,
                                                                    fitsFiles.size());

                    // send service to MainApp
                    _mainApp.setExecutionService(service);

                    progressBar.progressProperty().unbind();
                    progressBar.progressProperty().bind(service.getTask().progressProperty());
                    startButton.setDisable(true);

                    // on operation succeeded
                    service.setOnSucceeded(event -> {
                        // delete temp input file
                        deleteFileIfExists(filesIn);
                        // delete engine properties file
                        deleteFileIfExists(engineProperties);

                        // get results
                        boolean noErrors = (boolean) ((Worker) event.getSource()).getValue();

                        if (noErrors) {
                            // show success dialog
                            InfoDialog.show(
                                    _resources.getString("oper.success.dialog.title"),
                                    null,
                                    _resources.getString("oper.success.dialog.content"),
                                    _mainApp);
                        } else {
                            // show half-success dialog
                            InfoDialog.show(
                                    _resources.getString("oper.success.dialog.title"),
                                    null,
                                    _resources.getString("oper.success.half.dialog.content"),
                                    _mainApp);
                        }

                        progressBar.progressProperty().unbind();
                        progressBar.setProgress(0.0);
                        startButton.setDisable(false);
                    });

                    // on operation failed
                    service.setOnFailed(event -> {
                        // delete temp input file
                        deleteFileIfExists(filesIn);
                        // delete engine properties file
                        deleteFileIfExists(engineProperties);

                        // show exception dialog
                        Throwable exception = ((Worker)event.getSource()).getException();
                        ExceptionDialog.show(
                                _resources.getString("oper.fail.dialog.title"),
                                _resources.getString("oper.fail.dialog.header"),
                                _resources.getString("oper.fail.dialog.content")
                                        + Constants.NEWLINE + "   " + exception.getMessage(),
                                exception,
                                _mainApp);

                        progressBar.progressProperty().unbind();
                        progressBar.setProgress(0.0);
                        startButton.setDisable(false);
                    });

                    // on operation cancelled
                    service.setOnCancelled(event -> {
                        // delete temp input file
                        deleteFileIfExists(filesIn);
                        // delete engine properties file
                        deleteFileIfExists(engineProperties);

                        // show error dialog
                        ErrorDialog.show(
                                _resources.getString("app.error.dialog.title"),
                                _resources.getString("app.error.dialog.header"),
                                _resources.getString("oper.cancelled.dialog.content"),
                                _mainApp);

                        progressBar.progressProperty().unbind();
                        progressBar.setProgress(0.0);
                        startButton.setDisable(false);
                    });

                    service.start();
                } catch (IOException
                        | IllegalArgumentException ex) {
                    ExceptionDialog.show(
                            _resources.getString("app.error.dialog.title"),
                            _resources.getString("app.error.dialog.header"),
                            _resources.getString("app.error.dialog.content.editor.start"),
                            ex,
                            _mainApp);
                }

            }
        }
    }

    public void addTabController(TabController tabController) {
        if (tabController != null) {
            _tabs.add(tabController);
        }
    }


    private boolean checkEngineFilepath(String engineFilepath) {
        if (!new File(engineFilepath).exists()) {
            ErrorDialog.show(
                    _resources.getString("app.error.dialog.title"),
                    _resources.getString("app.error.dialog.header"),
                    _resources.getString("app.error.dialog.content.engine.not_exist"),
                    _mainApp);

            return false;
        } else {
            return true;
        }
    }

    private boolean checkJavaVersion() {
        String versionString = Runtime.class.getPackage().getSpecificationVersion();
        if (Parsers.Double.tryParse(versionString)) {
            Double version = Parsers.Double.parse(versionString);

            if (version < 1.8) {
                ErrorDialog.show(
                        _resources.getString("app.error.dialog.title"),
                        _resources.getString("app.error.dialog.header"),
                        _resources.getString("app.error.dialog.content.java.bad_version"),
                        _mainApp);
                return false;
            }
        }

        return true;
    }

    private boolean checkFitsFiles(Collection<FitsFile> fitsFiles) {
        if (fitsFiles == null || fitsFiles.isEmpty()) {
            WarningDialog.show(
                    _resources.getString("oper.common.alert.title"),
                    _resources.getString("oper.common.alert.header"),
                    _resources.getString("oper.common.alert.content.files.empty"),
                    _mainApp);

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

    private void deleteFileIfExists(Path inputFile) {
        try {
            Files.deleteIfExists(inputFile);
        } catch (IOException ignored) { }
    }

    private Path buildEnginePropertiesFile(Preferences preferences) throws IOException {
        File engineFile = new File(preferences.getEngineFilepath());

        String engineDirectory = engineFile.getParent();
        if (engineDirectory != null) {
            Path fitsProperties = Paths.get(engineDirectory + Constants.PATH_SEPARATOR +  Constants.ENGINE_PROPERTIES_FILENAME);

            // create properties file if it does not exist
            if (!Files.exists(fitsProperties)) {
                Files.createFile(fitsProperties);
            }

            // construct properties
            List<String> properties = new LinkedList<>();
            // output.writer
            String outputWriter = "console";
            if (preferences.saveOutputToFile())
                outputWriter += ", file";
            properties.add("output.writer=" + outputWriter);
            // output.file
            if (preferences.saveOutputToFile()) {
                String outputFilepath = preferences.getOutputFilepath();
                // escape Windows path backslashes if present
                outputFilepath = outputFilepath.replaceAll("\\\\", "\\\\\\\\");
                properties.add("output.file=" + outputFilepath);
            }

            // write properties
            Files.write(fitsProperties, properties);

            return fitsProperties;
        }

        return Paths.get("." + Constants.PATH_SEPARATOR + preferences.getEngineFilepath());
    }
}
