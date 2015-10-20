package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.operationenums.ValueType;
import cz.muni.fi.fits.gui.utils.*;
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
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public abstract class BasicRecordBasedOperationTabController extends OperationTabController {

    public ComboBox<ComboBoxItem<ValueType>> valueTypeField;

    public TextField keywordField;
    public TextField commentField;

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
        super.initialize(location, resources);

        loadValueTypeField(resources);
    }

    protected void setFieldsConstraints() {
        Constrainer.constrainTextFieldWithRegex(valueTimeField, Constants.TIME_PATTERN);
        Constrainer.constrainTextFieldWithRegex(valueDateTimeTimeField, Constants.TIME_PATTERN);
        Constrainer.constrainTextFieldWithRegex(valueNumberField, Constants.DECIMAL_NUMBER_PATTERN);
        Constrainer.constrainTextFieldWithRegex(keywordField, Constants.KEYWORD_PATTERN);
    }

    protected void loadValueTypeField(ResourceBundle resources) {
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

        valueTypeField.valueProperty().addListener(
                (observable, oldValue, newValue) -> onValueTypeFieldSelectionChanged((ComboBoxItem) newValue));
    }

    protected void onValueTypeFieldSelectionChanged(ComboBoxItem comboBoxItem) {
        if (comboBoxItem != null) {
            switch ((ValueType) comboBoxItem.getType()) {
                case STRING:
                    setValueTypeDependentFieldsVisibility(true, false, false, false, false, false);
                    break;
                case NUMBER:
                    setValueTypeDependentFieldsVisibility(false, true, false, false, false, false);
                    break;
                case DATETIME:
                    setValueTypeDependentFieldsVisibility(false, false, true, false, false, false);
                    break;
                case DATE:
                    setValueTypeDependentFieldsVisibility(false, false, false, true, false, false);
                    break;
                case TIME:
                    setValueTypeDependentFieldsVisibility(false, false, false, false, true, false);
                    break;
                case BOOLEAN:
                    setValueTypeDependentFieldsVisibility(false, false, false, false, false, true);
                    break;
            }
        }
    }

    protected void setValueTypeDependentFieldsVisibility(
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


    /**
     *
     */
    class Validator {

        /**
         *
         * @throws ValidationException
         */
        void validateKeywordField() throws ValidationException {
            if (keywordField.getText().isEmpty()) {
                WarningDialog.show(
                        _resources.getString("oper.common.alert.title"),
                        _resources.getString("oper.common.alert.header"),
                        _resources.getString("oper.common.alert.content.keyword.empty"));

                throw new ValidationException();
            }
        }

        /**
         * @throws ValidationException
         */
        void validateValueTypeField() throws ValidationException {
            if (valueTypeField.getValue() == null) {
                WarningDialog.show(
                        _resources.getString("oper.common.alert.title"),
                        _resources.getString("oper.common.alert.header"),
                        _resources.getString("oper.common.alert.content.value.type"));

                throw new ValidationException();
            }
        }

        /**
         * @throws ValidationException
         */
        void validateValueField() throws ValidationException {
            ValueType valueType = valueTypeField.getValue().getType();

            switch (valueType) {
                case DATE:
                    if (valueDateField.getValue() == null)
                        valueEmpty();

                    break;

                case TIME:
                    String timeString = valueTimeField.getText();
                    if (timeString.isEmpty())
                        valueEmpty();
                    if (!Parsers.Time.tryParse(timeString))
                        valueInvalid();

                    break;

                case DATETIME:
                    String timeText = valueDateTimeTimeField.getText();

                    if (valueDateTimeDateField.getValue() == null
                            && timeText.isEmpty()) {
                        valueEmpty();
                    }
                    if (!timeText.isEmpty()
                            && !Parsers.Time.tryParse(timeText)) {
                        valueInvalid();
                    }

                    break;

                case NUMBER:
                    String numberText =  valueNumberField.getText();
                    if (numberText.isEmpty())
                        valueEmpty();
                    if (!Parsers.Integer.tryParse(numberText)
                            && !Parsers.Long.tryParse(numberText)
                            && !Parsers.Double.tryParse(numberText))
                        valueInvalid();

                    break;

                case STRING:
                    if (valueStringField.getText().isEmpty())
                        valueEmpty();

                    break;
            }
        }


        private void valueEmpty() throws ValidationException {
            WarningDialog.show(
                    _resources.getString("oper.common.alert.title"),
                    _resources.getString("oper.common.alert.header"),
                    _resources.getString("oper.common.alert.content.value.empty"));

            throw new ValidationException();
        }

        private void valueInvalid() throws ValidationException {
            WarningDialog.show(
                    _resources.getString("oper.common.alert.title"),
                    _resources.getString("oper.common.alert.header"),
                    _resources.getString("oper.common.alert.content.value.invalid"));

            throw new ValidationException();
        }
    }
}
