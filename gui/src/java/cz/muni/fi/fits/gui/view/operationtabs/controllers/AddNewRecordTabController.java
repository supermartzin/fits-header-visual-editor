package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.inputdata.InputData;
import cz.muni.fi.fits.gui.models.operationenums.RecordPlacement;
import cz.muni.fi.fits.gui.utils.Constants;
import cz.muni.fi.fits.gui.utils.Constrainer;
import cz.muni.fi.fits.gui.utils.combobox.ComboBoxItem;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * TODO description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class AddNewRecordTabController extends ValueOperationTabController {

    public TextField keywordField;
    public CheckBox updateSwitchField;
    public CheckBox removeSwitchField;
    public TextField commentField;
    public ComboBox recordPlacementField;
    public TextField indexNumberField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        _tabName = resources.getString("tab.add");

        setFieldsConstraints();
        loadRecordPlacementField(resources);
    }

    @Override
    public InputData getInputData() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    private void setFieldsConstraints() {
        Constrainer.constrainTextFieldWithRegex(valueTimeField, Constants.TIME_PATTERN);
        Constrainer.constrainTextFieldWithRegex(valueDateTimeTimeField, Constants.TIME_PATTERN);
        Constrainer.constrainTextFieldWithRegex(valueNumberField, Constants.DECIMAL_NUMBER_PATTERN);
        Constrainer.constrainTextFieldWithRegex(indexNumberField, Constants.INTEGRAL_NUMBER_PATTERN);
        Constrainer.constrainTextFieldWithRegex(keywordField, Constants.KEYWORD_PATTERN);
    }

    private void loadRecordPlacementField(ResourceBundle resources) {
        if (resources != null) {
            recordPlacementField.getItems().add(new ComboBoxItem<>(RecordPlacement.END,
                    resources.getString(RecordPlacement.END.getPropertyName())));
            recordPlacementField.getItems().add(new ComboBoxItem<>(RecordPlacement.INDEX,
                    resources.getString(RecordPlacement.INDEX.getPropertyName())));
        }

        recordPlacementField.setCellFactory(param -> new ComboBoxListCell<>());

        recordPlacementField.valueProperty().addListener(
                (observable, oldValue, newValue) -> onRecordPlacementFieldSelectionChanged((ComboBoxItem) newValue));
    }

    private void onRecordPlacementFieldSelectionChanged(ComboBoxItem comboBoxItem) {
        if (comboBoxItem != null) {
            switch ((RecordPlacement) comboBoxItem.getType()) {
                case END:
                    setRecordPlacementDependentFieldsVisibility(true, false, false);
                    break;
                case INDEX:
                    setRecordPlacementDependentFieldsVisibility(false, true, true);
                    break;
            }
        }
    }

    private void setRecordPlacementDependentFieldsVisibility(
            boolean updateSwitchField,
            boolean removeSwitchField,
            boolean indexNumberField) {
        this.updateSwitchField.setVisible(updateSwitchField);
        this.removeSwitchField.setVisible(removeSwitchField);
        this.indexNumberField.setVisible(indexNumberField);
    }
}
