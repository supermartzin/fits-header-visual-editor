package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.operationenums.JDRecordType;
import cz.muni.fi.fits.gui.utils.Constants;
import cz.muni.fi.fits.gui.utils.Constrainer;
import cz.muni.fi.fits.gui.utils.combobox.ComboBoxItem;
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
public abstract class JulianDateBaseOperationTabController extends OperationTabController {

    // DATETIME
    public ComboBox datetimeRecordTypeField;
    public TextField datetimeKeywordField;
    public HBox datetimeValueContainer;
    public DatePicker datetimeValueDateField;
    public TextField datetimeValueTimeField;

    // EXPOSURE
    public ComboBox exposureRecordTypeField;
    public TextField exposureKeywordField;
    public TextField exposureValueField;

    public TextField commentField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        setFieldsConstraints();
        loadDatetimeRecordTypeField(resources);
        loadExposureRecordTypeField(resources);
    }

    protected void setFieldsConstraints() {
        Constrainer.constrainTextFieldWithRegex(datetimeKeywordField, Constants.KEYWORD_PATTERN);
        Constrainer.constrainTextFieldWithRegex(datetimeValueTimeField, Constants.TIME_PATTERN);
        Constrainer.constrainTextFieldWithRegex(exposureKeywordField, Constants.KEYWORD_PATTERN);
        Constrainer.constrainTextFieldWithRegex(exposureValueField, Constants.DECIMAL_NUMBER_PATTERN);
    }

    protected void loadDatetimeRecordTypeField(ResourceBundle resources) {
        loadJDRecordTypeItems(resources, datetimeRecordTypeField);

        datetimeRecordTypeField.setCellFactory(param -> new ComboBoxListCell<>());

        datetimeRecordTypeField.valueProperty().addListener(
                (observable, oldValue, newValue) -> onDatetimeRecordTypeFieldSelectionChanged((ComboBoxItem) newValue));
    }

    protected void loadExposureRecordTypeField(ResourceBundle resources) {
        loadJDRecordTypeItems(resources, exposureRecordTypeField);

        exposureRecordTypeField.setCellFactory(param -> new ComboBoxListCell<>());

        exposureRecordTypeField.valueProperty().addListener(
                (observable, oldValue, newValue) -> onExposureRecordTypeFieldSelectionChanged((ComboBoxItem) newValue));
    }

    protected void loadJDRecordTypeItems(ResourceBundle resources, ComboBox comboBox) {
        if (resources != null && comboBox != null) {
            comboBox.getItems().add(new ComboBoxItem<>(JDRecordType.KEYWORD,
                    resources.getString(JDRecordType.KEYWORD.getPropertyName())));
            comboBox.getItems().add(new ComboBoxItem<>(JDRecordType.VALUE,
                    resources.getString(JDRecordType.VALUE.getPropertyName())));
        }
    }

    protected void onDatetimeRecordTypeFieldSelectionChanged(ComboBoxItem comboBoxItem) {
        if (comboBoxItem != null) {
            switch ((JDRecordType) comboBoxItem.getType()) {
                case KEYWORD:
                    setDatetimeRecordTypeDependentFieldsVisibility(true, false);
                    break;
                case VALUE:
                    setDatetimeRecordTypeDependentFieldsVisibility(false, true);
                    break;
            }
        }
    }

    protected void onExposureRecordTypeFieldSelectionChanged(ComboBoxItem comboBoxItem) {
        if (comboBoxItem != null) {
            switch ((JDRecordType) comboBoxItem.getType()) {
                case KEYWORD:
                    setExposureRecordTypeDependentFieldsVisibility(true, false);
                    break;
                case VALUE:
                    setExposureRecordTypeDependentFieldsVisibility(false, true);
                    break;
            }
        }
    }

    protected void setDatetimeRecordTypeDependentFieldsVisibility(
            boolean datetimeKeywordField,
            boolean datetimeValueContainer) {
        this.datetimeKeywordField.setVisible(datetimeKeywordField);
        this.datetimeValueContainer.setVisible(datetimeValueContainer);
    }

    protected void setExposureRecordTypeDependentFieldsVisibility(
            boolean exposureKeywordField,
            boolean exposureValueField) {
        this.exposureKeywordField.setVisible(exposureKeywordField);
        this.exposureValueField.setVisible(exposureValueField);
    }
}
