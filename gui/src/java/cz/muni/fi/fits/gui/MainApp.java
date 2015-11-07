package cz.muni.fi.fits.gui;

import cz.muni.fi.fits.gui.listeners.OutputListener;
import cz.muni.fi.fits.gui.models.FitsFile;
import cz.muni.fi.fits.gui.models.Preferences;
import cz.muni.fi.fits.gui.services.ExecutionService;
import cz.muni.fi.fits.gui.services.ResourceBundleService;
import cz.muni.fi.fits.gui.utils.dialogs.ExceptionDialog;
import cz.muni.fi.fits.gui.view.controllers.FilesOverviewController;
import cz.muni.fi.fits.gui.view.controllers.OperationTabsViewController;
import cz.muni.fi.fits.gui.view.controllers.OutputViewController;
import cz.muni.fi.fits.gui.view.controllers.RootLayoutController;
import cz.muni.fi.fits.gui.view.operationtabs.utils.OperationTabsLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

/**
 * TODO description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class MainApp extends Application {

    private Stage _primaryStage;
    private BorderPane _rootLayout;
    private SplitPane _centralLayout;

    private OutputViewController _outputViewController;

    private ObservableList<FitsFile> _fitsFiles;
    private Preferences _preferences;
    private ResourceBundle _resources;

    public MainApp() {
        _fitsFiles = FXCollections.observableArrayList();
        _preferences = new Preferences();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        _primaryStage = primaryStage;
        _primaryStage.setTitle("FITS Header Visual Editor");
        // TODO set icon

        _resources = ResourceBundleService.getBundle();

        // initialize layouts
        initRootLayout();
        initFilesOverview();
        initOperationTabsView();
        initOutputView();

        // set unhandled exception handler
        Thread.currentThread().setUncaughtExceptionHandler((thread, exception) ->
                Platform.runLater(() ->
                        ExceptionDialog.show(
                                _resources.getString("oper.common.error.title"),
                                _resources.getString("oper.common.error.header"),
                                _resources.getString("oper.common.error.content.unhandled_excp"),
                                exception)));
    }

    public Stage getPrimaryStage() {
        return _primaryStage;
    }

    public Collection<FitsFile> getFitsFiles() {
        Set<FitsFile> selectedFiles = new HashSet<>();

        // filter only selected files
        _fitsFiles.stream()
                .filter(FitsFile::isSelected)
                .forEach(selectedFiles::add);

        return selectedFiles;
    }

    public void setPreferences(Preferences preferences) {
        _preferences = preferences;
    }

    public Preferences getPreferences() {
        return _preferences;
    }

    public void setExecutionService(ExecutionService executionService) {
        if (executionService != null) {
            executionService.setOutputListener(new OutputListener() {
                @Override
                public void onInfo(String infoMessage) {
                    Platform.runLater(() -> _outputViewController.onInfoMessage(infoMessage));
                }

                @Override
                public void onError(String errorMessage) {
                    Platform.runLater(() -> _outputViewController.onErrorMessage(errorMessage));
                }

                @Override
                public void onException(String exceptionMessage) {
                    Platform.runLater(() -> _outputViewController.onExceptionMessage(exceptionMessage));
                }
            });
        }
    }


    private void initRootLayout() {
        try {
            FXMLLoader rootLayoutFile = new FXMLLoader(MainApp.class.getResource("view/RootLayout.fxml"));
            ResourceBundleService.setResourceBundle(rootLayoutFile);
            _rootLayout = rootLayoutFile.load();

            RootLayoutController controller = rootLayoutFile.getController();
            controller.setMainApp(this);

            _centralLayout = new SplitPane();
            _centralLayout.setOrientation(Orientation.VERTICAL);
            _rootLayout.setCenter(_centralLayout);

            Scene scene = new Scene(_rootLayout);
            _primaryStage.setScene(scene);

            _primaryStage.show();
        } catch (IOException ioEx) {
            ExceptionDialog.show(
                    _resources.getString("oper.common.error.title"),
                    _resources.getString("oper.common.error.header"),
                    _resources.getString("app.error.dialog.content.fatal"),
                    ioEx);

            // exit application
            System.exit(0);
        }
    }

    private void initFilesOverview() {
        try {
            FXMLLoader filesOverviewFile = new FXMLLoader(MainApp.class.getResource("view/FilesOverview.fxml"));
            ResourceBundleService.setResourceBundle(filesOverviewFile);
            _rootLayout.setLeft(filesOverviewFile.load());

            FilesOverviewController controller = filesOverviewFile.getController();
            controller.setMainApp(this);
            controller.setTableItemsCollection(_fitsFiles);
        } catch (IOException e) {
            // TODO handle exception
            e.printStackTrace();
        }
    }

    private void initOperationTabsView() {
        try {
            FXMLLoader operationTabsViewFile = new FXMLLoader(MainApp.class.getResource("view/OperationTabsView.fxml"));
            ResourceBundleService.setResourceBundle(operationTabsViewFile);
            AnchorPane anchorPane = operationTabsViewFile.load();

            OperationTabsViewController controller = operationTabsViewFile.getController();
            controller.setMainApp(this);

            // load Tabs into TabPane
            Collection<Tab> tabs = loadOperationTabs(controller);
            TabPane tabPane = extractTabPane(anchorPane);
            if (tabPane != null)
                tabPane.getTabs().addAll(tabs);

            // load Tabs into view
            _centralLayout.getItems().add(anchorPane);
        } catch (IOException e) {
            // TODO handle exception
            e.printStackTrace();
        }
    }

    private void initOutputView() {
        try {
            FXMLLoader outputViewFile = new FXMLLoader(MainApp.class.getResource("view/OutputView.fxml"));
            ResourceBundleService.setResourceBundle(outputViewFile);

            _centralLayout.getItems().add(outputViewFile.load());
            _outputViewController = outputViewFile.getController();
        } catch (IOException e) {
            // TODO handle exception
            e.printStackTrace();
        }
    }

    private Collection<Tab> loadOperationTabs(OperationTabsViewController parentController)
            throws IOException {
        Collection<Tab> tabs = new LinkedHashSet<>();

        // load Tabs
        tabs.add(OperationTabsLoader.loadTab("view/operationtabs/AddNewRecordTab.fxml", parentController));
        tabs.add(OperationTabsLoader.loadTab("view/operationtabs/RemoveRecordTab.fxml", parentController));
        tabs.add(OperationTabsLoader.loadTab("view/operationtabs/ChangeKeywordTab.fxml", parentController));
        tabs.add(OperationTabsLoader.loadTab("view/operationtabs/ChangeValueTab.fxml", parentController));
        tabs.add(OperationTabsLoader.loadTab("view/operationtabs/ChainRecordsTab.fxml", parentController));
        tabs.add(OperationTabsLoader.loadTab("view/operationtabs/ShiftTimeTab.fxml", parentController));
        tabs.add(OperationTabsLoader.loadTab("view/operationtabs/JulianDateTab.fxml", parentController));
        tabs.add(OperationTabsLoader.loadTab("view/operationtabs/HeliocentricJulianDateTab.fxml", parentController));

        return tabs;
    }

    private TabPane extractTabPane(AnchorPane anchorPane) {
        if (anchorPane != null) {
            for (Node node : anchorPane.getChildren()) {
                if (node instanceof TabPane)
                    return (TabPane) node;
            }
        }

        return null;
    }
}
