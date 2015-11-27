package cz.muni.fi.fits.gui.view.controllers;

import cz.muni.fi.fits.gui.exceptions.InvalidEnginePathException;
import cz.muni.fi.fits.gui.models.FitsFile;
import cz.muni.fi.fits.gui.models.inputdata.InputData;
import cz.muni.fi.fits.gui.FITSHeaderEditor;
import cz.muni.fi.fits.gui.tasks.EditingTask;
import cz.muni.fi.fits.gui.utils.Constants;
import cz.muni.fi.fits.gui.utils.Parsers;
import cz.muni.fi.fits.gui.utils.dialogs.ErrorDialog;
import cz.muni.fi.fits.gui.utils.dialogs.ExceptionDialog;
import cz.muni.fi.fits.gui.utils.dialogs.InfoDialog;
import cz.muni.fi.fits.gui.utils.dialogs.WarningDialog;
import cz.muni.fi.fits.gui.view.operationtabs.controllers.TabController;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
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

            // check correct java version
            if (!checkJavaVersion())
                return;

            // check input FITS files
            Collection<FitsFile> fitsFiles = _mainApp.getFitsFiles();
            if (!checkFitsFiles(fitsFiles)) return;

            // input data
            InputData inputData = tab.getInputData();
            if (inputData != null) {
                    try {
                        // create input file with FITS filepaths
                        Path filesInFile = createInputFile(_mainApp.getFitsFiles());

                        // add input file to input data
                        inputData.setInputFilePath(filesInFile.toAbsolutePath().toString());

                        EditingTask task = new EditingTask(new FITSHeaderEditor(_mainApp.getPreferences()),
                                                            inputData.getInputDataArguments(),
                                                            fitsFiles.size());

                        // send service to MainApp for adding listeners
                        _mainApp.setEditingTask(task);

                        progressBar.progressProperty().unbind();
                        progressBar.progressProperty().bind(task.progressProperty());
                        startButton.setDisable(true);

                        Service service = new Service() {
                            @Override
                            protected Task createTask() {
                                return task;
                            }
                        };

                        // on operation succeeded
                        service.setOnSucceeded(event -> {
                            // delete temp input file
                            deleteFileIfExists(filesInFile);

                            // get results
                            boolean endedWithErrors = task.endedWithErrors();

                            if (!endedWithErrors) {
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
                            deleteFileIfExists(filesInFile);

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
                            deleteFileIfExists(filesInFile);

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
                    } catch (InvalidEnginePathException iepEx) {
                        // invalid path to editor engine
                        ErrorDialog.show(
                                _resources.getString("app.error.dialog.title"),
                                _resources.getString("app.error.dialog.header"),
                                _resources.getString("app.error.dialog.content.engine.not_exist"),
                                _mainApp);
                    } catch (IOException ioEx) {
                        // error during execution of editor
                        ExceptionDialog.show(
                                _resources.getString("app.error.dialog.title"),
                                _resources.getString("app.error.dialog.header"),
                                _resources.getString("app.error.dialog.content.editor.start"),
                                ioEx,
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
}
