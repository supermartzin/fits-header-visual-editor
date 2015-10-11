package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.ValueType;
import cz.muni.fi.fits.gui.models.ValueTypeItem;
import cz.muni.fi.fits.gui.models.inputdata.InputData;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class AddNewRecordTabController implements TabController {

    private static final Pattern TIME_PATTERN = Pattern.compile("^[0-9:.]{1,12}$");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9.,]*$");

    public TextField keywordTextField;
    public CheckBox updateIfExistsCheckBox;
    public TextField commentTextField;
    public ComboBox valueTypeComboBox;

    // STRING
    public TextField valueTextField;
    // NUMBER
    public TextField valueNumberField;
    // DATETIME
    public HBox valueDateTimeHBox;
    public DatePicker valueDateTimeDatePicker;
    public TextField valueDateTimeTimeField;
    // DATE
    public DatePicker valueDatePicker;
    // TIME
    public TextField valueTimeTextField;
    // BOOLEAN
    public CheckBox valueCheckBox;


    private String _tabName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _tabName = resources.getString("tab.add");

        setFieldsConstraints();
        loadComboBox(resources);
    }

    @Override
    public boolean called() {
        return false;
    }

    @Override
    public void setCalled(boolean called) {

    }

    @Override
    public InputData getInputData() {
        return null;
    }

    @Override
    public String getTabName() {
        return _tabName;
    }


    private void setFieldsConstraints() {
        // set time fields value constraints
        valueTimeTextField.textProperty().addListener((observable, oldValue, newValue) ->
                onTextValueChanged(oldValue, newValue, valueTimeTextField, TIME_PATTERN));
        valueDateTimeTimeField.textProperty().addListener((observable1, oldValue1, newValue1) ->
                onTextValueChanged(oldValue1, newValue1, valueDateTimeTimeField, TIME_PATTERN));

        // set number field value validator
        valueNumberField.textProperty().addListener((observable2, oldValue2, newValue2) ->
                onTextValueChanged(oldValue2, newValue2, valueNumberField, NUMBER_PATTERN));
    }

    private void onTextValueChanged(String oldValue, String newValue, TextField field, Pattern regex) {
        if (!newValue.isEmpty()) {
            Matcher matcher = regex.matcher(newValue);
            if (!matcher.matches())
                field.setText(oldValue);
        }
    }

    private void loadComboBox(ResourceBundle resources) {
        if (resources != null) {
            valueTypeComboBox.getItems().add(new ValueTypeItem(ValueType.STRING, resources.getString(ValueType.STRING.getPropertyName())));
            valueTypeComboBox.getItems().add(new ValueTypeItem(ValueType.NUMBER, resources.getString(ValueType.NUMBER.getPropertyName())));
            valueTypeComboBox.getItems().add(new ValueTypeItem(ValueType.DATETIME, resources.getString(ValueType.DATETIME.getPropertyName())));
            valueTypeComboBox.getItems().add(new ValueTypeItem(ValueType.DATE, resources.getString(ValueType.DATE.getPropertyName())));
            valueTypeComboBox.getItems().add(new ValueTypeItem(ValueType.TIME, resources.getString(ValueType.TIME.getPropertyName())));
            valueTypeComboBox.getItems().add(new ValueTypeItem(ValueType.BOOLEAN, resources.getString(ValueType.BOOLEAN.getPropertyName())));
        }

        valueTypeComboBox.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new ListCell<ValueTypeItem>() {
                    @Override
                    protected void updateItem(ValueTypeItem item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null)
                            setText(item.getName());
                    }
                };
            }
        });

        valueTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> onComboBoxItemChanged((ValueTypeItem)newValue));
    }

    private void onComboBoxItemChanged(ValueTypeItem valueTypeItem) {
        switch (valueTypeItem.getValueType()) {
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

    private void setValueFieldVisibility(
            boolean stringField,
            boolean numberField,
            boolean datetimeField,
            boolean dateField,
            boolean timeField,
            boolean booleanField) {
        valueTextField.setVisible(stringField);
        valueNumberField.setVisible(numberField);
        valueDateTimeHBox.setVisible(datetimeField);
        valueDatePicker.setVisible(dateField);
        valueTimeTextField.setVisible(timeField);
        valueCheckBox.setVisible(booleanField);
    }
}
