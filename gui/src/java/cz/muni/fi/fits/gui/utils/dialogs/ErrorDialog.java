package cz.muni.fi.fits.gui.utils.dialogs;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public final class ErrorDialog {

    /**
     *
     * @param title
     * @param header
     * @param content
     */
    public static void show(String title, String header, String content) {
        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
        errorDialog.setTitle(title);
        errorDialog.setHeaderText(header);
        errorDialog.setContentText(content);

        errorDialog.initModality(Modality.APPLICATION_MODAL);

        errorDialog.showAndWait();
    }
}
