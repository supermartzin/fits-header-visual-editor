package cz.muni.fi.fits.gui.utils.dialogs;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public final class InfoDialog {

    /**
     *
     * @param title
     * @param header
     * @param content
     */
    public static void show(String title, String header, String content) {
        Alert infoDialog = new Alert(Alert.AlertType.INFORMATION);
        infoDialog.setTitle(title);
        infoDialog.setHeaderText(header);
        infoDialog.setContentText(content);

        infoDialog.initModality(Modality.APPLICATION_MODAL);

        infoDialog.showAndWait();
    }
}
