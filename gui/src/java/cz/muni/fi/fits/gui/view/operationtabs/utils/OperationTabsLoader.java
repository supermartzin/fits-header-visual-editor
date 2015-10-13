package cz.muni.fi.fits.gui.view.operationtabs.utils;

import cz.muni.fi.fits.gui.MainApp;
import cz.muni.fi.fits.gui.services.ResourceBundleService;
import cz.muni.fi.fits.gui.view.operationtabs.controllers.TabController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * TODO description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class OperationTabsLoader {

    public static Tab loadOperationTab(String resourcePath)
            throws IOException {

        FXMLLoader tabFile = new FXMLLoader(MainApp.class.getResource(resourcePath));
        ResourceBundleService.setResourceBundle(tabFile);
        AnchorPane anchorPane = tabFile.load();

        TabController controller = tabFile.getController();

        return new Tab(controller.getTabName(), anchorPane);
    }
}
