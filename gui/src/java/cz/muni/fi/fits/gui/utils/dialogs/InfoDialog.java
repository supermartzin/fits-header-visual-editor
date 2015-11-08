package cz.muni.fi.fits.gui.utils.dialogs;

import cz.muni.fi.fits.gui.MainApp;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
     * @param mainApp
     */
    public static void show(String title, String header, String content, MainApp mainApp) {
        Alert infoDialog = new Alert(Alert.AlertType.INFORMATION);
        infoDialog.setTitle(title);
        infoDialog.setHeaderText(header);
        infoDialog.setContentText(content);

        infoDialog.initOwner(mainApp.getPrimaryStage());
        infoDialog.initModality(Modality.APPLICATION_MODAL);

        // set icon
        Stage stage = (Stage) infoDialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(mainApp.getApplicationIcon());

        infoDialog.showAndWait();
    }
}
