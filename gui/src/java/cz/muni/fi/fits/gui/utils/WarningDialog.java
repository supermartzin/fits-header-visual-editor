package cz.muni.fi.fits.gui.utils;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public final class WarningDialog {

    public static void show(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait();
    }
}
