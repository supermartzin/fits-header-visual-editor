package cz.muni.fi.fits.gui;

import cz.muni.fi.fits.gui.models.FitsFile;
import cz.muni.fi.fits.gui.services.ResourceBundleService;
import cz.muni.fi.fits.gui.view.controllers.FilesOverviewController;
import cz.muni.fi.fits.gui.view.controllers.RootLayoutController;
import cz.muni.fi.fits.gui.view.operationtabs.utils.OperationTabsLoader;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;

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

    private ObservableList<FitsFile> _fitsFiles;

    public Stage getPrimaryStage() {
        return _primaryStage;
    }

    public ObservableList<FitsFile> getFitsFiles() {
        return _fitsFiles;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        _primaryStage = primaryStage;
        _primaryStage.setTitle("FITS Header Visual Editor Tool");
        // TODO set icon

        _fitsFiles = FXCollections.observableArrayList();

        // initialize layouts
        initRootLayout();
        initFilesOverview();
        initOperationTabsView();
        initOutputView();
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
        } catch (IOException e) {
            // TODO handle exception
            e.printStackTrace();
        }
    }

    private void initFilesOverview() {
        try {
            FXMLLoader filesOverviewFile = new FXMLLoader(MainApp.class.getResource("view/FilesOverview.fxml"));
            ResourceBundleService.setResourceBundle(filesOverviewFile);
            ScrollPane filesList = filesOverviewFile.load();

            FilesOverviewController controller = filesOverviewFile.getController();
            controller.setMainApp(this);
            controller.setTableItemsCollection(_fitsFiles);

            _rootLayout.setLeft(filesList);
        } catch (IOException e) {
            // TODO handle exception
            e.printStackTrace();
        }
    }

    private void initOperationTabsView() {
        try {
            FXMLLoader operationTabsViewFile = new FXMLLoader(MainApp.class.getResource("view/OperationTabsView.fxml"));
            ResourceBundleService.setResourceBundle(operationTabsViewFile);
            ScrollPane tabView = operationTabsViewFile.load();

            // load Tabs into TabPane
            TabPane tabPane = extractTabPane(tabView);
            Collection<Tab> tabs = loadOperationTabs();
            if (tabPane != null) {
                tabPane.getTabs().addAll(tabs);
            }

            // load TabPane into view
            _centralLayout.getItems().add(tabView);
        } catch (IOException e) {
            // TODO handle exception
            e.printStackTrace();
        }
    }
    
    private void initOutputView() {
        try {
            FXMLLoader outputViewFile = new FXMLLoader(MainApp.class.getResource("view/OutputView.fxml"));
            ResourceBundleService.setResourceBundle(outputViewFile);
            AnchorPane outputTextFlow = outputViewFile.load();

            ScrollPane scrollPane = new ScrollPane(outputTextFlow);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            _centralLayout.getItems().add(scrollPane);
        } catch (IOException e) {
            // TODO handle exception
            e.printStackTrace();
        }
    }

    private Collection<Tab> loadOperationTabs()
            throws IOException {
        Collection<Tab> tabs = new LinkedHashSet<>();

        // load Tabs
        tabs.add(OperationTabsLoader.loadOperationTab("view/operationtabs/AddNewRecordTab.fxml"));
        tabs.add(OperationTabsLoader.loadOperationTab("view/operationtabs/RemoveRecordTab.fxml"));
        tabs.add(OperationTabsLoader.loadOperationTab("view/operationtabs/ChangeKeywordTab.fxml"));
        tabs.add(OperationTabsLoader.loadOperationTab("view/operationtabs/ChangeValueTab.fxml"));
        tabs.add(OperationTabsLoader.loadOperationTab("view/operationtabs/ShiftTimeTab.fxml"));
        // TODO load other tabs

        return tabs;
    }

    private TabPane extractTabPane(ScrollPane scrollPane) {
        AnchorPane anchorPane = (AnchorPane) scrollPane.getContent();

        if (anchorPane != null) {
            for (Node node : anchorPane.getChildren()) {
                if (node instanceof TabPane)
                    return (TabPane) node;
            }
        }

        return null;
    }
}
