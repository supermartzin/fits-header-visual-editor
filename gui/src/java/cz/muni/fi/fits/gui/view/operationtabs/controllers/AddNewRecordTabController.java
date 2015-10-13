package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.inputdata.InputData;
import cz.muni.fi.fits.gui.models.operations.ValueType;
import cz.muni.fi.fits.gui.models.operations.add.AddRecordToPlace;
import cz.muni.fi.fits.gui.utils.Constants;
import cz.muni.fi.fits.gui.utils.Constrainer;
import cz.muni.fi.fits.gui.utils.combobox.ComboBoxItem;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * TODO description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class AddNewRecordTabController extends OperationTabController {

    public TextField keywordField;
    public CheckBox updateSwitchField;
    public CheckBox removeSwitchField;
    public TextField commentField;
    public ComboBox valueTypeField;
    public ComboBox insertToPlaceField;
    public TextField indexNumberField;

    // STRING
    public TextField valueStringField;
    // NUMBER
    public TextField valueNumberField;
    // DATETIME
    public HBox valueDateTimeContainer;
    public DatePicker valueDateTimeDateField;
    public TextField valueDateTimeTimeField;
    // DATE
    public DatePicker valueDateField;
    // TIME
    public TextField valueTimeField;
    // BOOLEAN
    public CheckBox valueBooleanField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _tabName = resources.getString("tab.add");

        setFieldsConstraints();
        loadValueTypeField(resources);
        loadInsertToPlaceField(resources);
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

    private void loadValueTypeField(ResourceBundle resources) {
        if (resources != null) {
            valueTypeField.getItems().add(new ComboBoxItem<>(ValueType.STRING,
                    resources.getString(ValueType.STRING.getPropertyName())));
            valueTypeField.getItems().add(new ComboBoxItem<>(ValueType.NUMBER,
                    resources.getString(ValueType.NUMBER.getPropertyName())));
            valueTypeField.getItems().add(new ComboBoxItem<>(ValueType.DATETIME,
                    resources.getString(ValueType.DATETIME.getPropertyName())));
            valueTypeField.getItems().add(new ComboBoxItem<>(ValueType.DATE,
                    resources.getString(ValueType.DATE.getPropertyName())));
            valueTypeField.getItems().add(new ComboBoxItem<>(ValueType.TIME,
                    resources.getString(ValueType.TIME.getPropertyName())));
            valueTypeField.getItems().add(new ComboBoxItem<>(ValueType.BOOLEAN,
                    resources.getString(ValueType.BOOLEAN.getPropertyName())));
        }

        valueTypeField.setCellFactory(param -> new ComboBoxListCell<>());

        valueTypeField.valueProperty().addListener((observable, oldValue, newValue) -> onValueTypeSelectionChanged((ComboBoxItem) newValue));
    }

    private void loadInsertToPlaceField(ResourceBundle resources) {
        if (resources != null) {
            insertToPlaceField.getItems().add(new ComboBoxItem<>(AddRecordToPlace.END,
                    resources.getString(AddRecordToPlace.END.getPropertyName())));
            insertToPlaceField.getItems().add(new ComboBoxItem<>(AddRecordToPlace.INDEX,
                    resources.getString(AddRecordToPlace.INDEX.getPropertyName())));
        }

        insertToPlaceField.setCellFactory(param -> new ComboBoxListCell<>());

        insertToPlaceField.valueProperty().addListener((observable, oldValue, newValue) -> onInsertToPlaceSelectionChanged((ComboBoxItem) newValue));
    }

    private void onValueTypeSelectionChanged(ComboBoxItem comboBoxItem) {
        if (comboBoxItem != null) {
            switch ((ValueType) comboBoxItem.getType()) {
                case STRING:
                    setValueFieldVisibility(true, false, false, false, false, false);
                    break;
                case NUMBER:
                    setValueFieldVisibility(false, true, false, false, false, false);
                    break;
                case DATETIME:
                    setValueFieldVisibility(false, false, true, false, false, false);
                    break;
                case DATE:
                    setValueFieldVisibility(false, false, false, true, false, false);
                    break;
                case TIME:
                    setValueFieldVisibility(false, false, false, false, true, false);
                    break;
                case BOOLEAN:
                    setValueFieldVisibility(false, false, false, false, false, true);
                    break;
            }
        }
    }

    private void onInsertToPlaceSelectionChanged(ComboBoxItem comboBoxItem) {
        if (comboBoxItem != null) {
            switch ((AddRecordToPlace) comboBoxItem.getType()) {
                case END:
                    setRecordPlacingFieldsVisibility(true, false, false);
                    break;
                case INDEX:
                    setRecordPlacingFieldsVisibility(false, true, true);
                    break;
            }
        }
    }

    private void setValueFieldVisibility(
            boolean stringField,
            boolean numberField,
            boolean datetimeField,
            boolean dateField,
            boolean timeField,
            boolean booleanField) {
        valueStringField.setVisible(stringField);
        valueNumberField.setVisible(numberField);
        valueDateTimeContainer.setVisible(datetimeField);
        valueDateField.setVisible(dateField);
        valueTimeField.setVisible(timeField);
        valueBooleanField.setVisible(booleanField);
    }

    private void setRecordPlacingFieldsVisibility(
            boolean updateSwitchField,
            boolean removeSwitchField,
            boolean indexNumberField) {
        this.updateSwitchField.setVisible(updateSwitchField);
        this.removeSwitchField.setVisible(removeSwitchField);
        this.indexNumberField.setVisible(indexNumberField);
    }
}
