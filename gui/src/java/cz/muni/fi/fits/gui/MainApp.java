package cz.muni.fi.fits.gui;

import cz.muni.fi.fits.gui.services.ResourceBundleService;
import cz.muni.fi.fits.gui.view.controllers.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * TODO description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class MainApp extends Application {

    private Stage _primaryStage;
    private BorderPane _rootLayout;

    public Stage getPrimaryStage() {
        return _primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        _primaryStage = primaryStage;
        this._primaryStage.setTitle("FITS Header Visual Editor Tool");
        // TODO set icon

        initRootLayout();
    }

    private void initRootLayout() {
        try {
            FXMLLoader rootLayoutFile = new FXMLLoader(MainApp.class.getResource("view/RootLayout.fxml"));
            ResourceBundleService.setResourceBundle(rootLayoutFile);
            _rootLayout = rootLayoutFile.load();

            RootLayoutController controller = rootLayoutFile.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(_rootLayout);
            _primaryStage.setScene(scene);

            _primaryStage.show();
        } catch (IOException e) {
            // TODO handle exception
            e.printStackTrace();
        }
    }
}
