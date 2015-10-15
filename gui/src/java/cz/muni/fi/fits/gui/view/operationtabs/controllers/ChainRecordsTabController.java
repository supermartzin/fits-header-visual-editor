package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.inputdata.InputData;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ChainRecordsTabController extends OperationTabController {

    public GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _tabName = resources.getString("tab.chain");

        addNewGroup(4);
        addNewGroup(5);
        addNewGroup(6);
        addNewGroup(7);
        addNewGroup(8);
        addNewGroup(9);
    }

    private void addNewGroup(int rowIndex) {
        Button btn = new Button("Add");
        GridPane.setRowIndex(btn, rowIndex);
        GridPane.setColumnIndex(btn, 0);

        TextField txtField = new TextField();
        GridPane.setRowIndex(txtField, rowIndex);
        GridPane.setColumnIndex(txtField, 1);

        ComboBox comboBox = new ComboBox();
        GridPane.setRowIndex(comboBox, rowIndex);
        GridPane.setColumnIndex(comboBox, 2);

        Button btn2 = new Button("X");
        GridPane.setRowIndex(btn2, rowIndex);
        GridPane.setColumnIndex(btn2, 3);

        gridPane.addRow(rowIndex, btn, txtField, comboBox, btn2);

        RowConstraints rowConstraints = new RowConstraints(
                Region.USE_PREF_SIZE,
                30.0,
                Region.USE_PREF_SIZE);
        rowConstraints.setVgrow(Priority.NEVER);
        gridPane.getRowConstraints().add(rowConstraints);
    }

    @Override
    public InputData getInputData() {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
