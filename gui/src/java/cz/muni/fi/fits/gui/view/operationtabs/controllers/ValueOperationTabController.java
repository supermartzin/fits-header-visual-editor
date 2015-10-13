package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.operations.ValueType;
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
public abstract class ValueOperationTabController extends OperationTabController {

    public ComboBox valueTypeField;

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
        loadValueTypeField(resources);
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

        valueTypeField.valueProperty().addListener((observable, oldValue, newValue) -> onValueTypeSelectionChanged((ComboBoxItem) newValue));
    }

    protected void onValueTypeSelectionChanged(ComboBoxItem comboBoxItem) {
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

    protected void setValueFieldVisibility(
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
}
