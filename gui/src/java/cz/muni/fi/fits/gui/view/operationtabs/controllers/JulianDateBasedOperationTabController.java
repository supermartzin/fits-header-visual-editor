package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.operationenums.JDRecordType;
import cz.muni.fi.fits.gui.utils.*;
import cz.muni.fi.fits.gui.utils.combobox.ComboBoxItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public abstract class JulianDateBasedOperationTabController extends OperationTabController {

    // DATETIME
    public ComboBox<ComboBoxItem<JDRecordType>> datetimeRecordTypeField;
    public TextField datetimeKeywordField;
    public HBox datetimeValueContainer;
    public DatePicker datetimeValueDateField;
    public TextField datetimeValueTimeField;

    // EXPOSURE
    public ComboBox<ComboBoxItem<JDRecordType>> exposureRecordTypeField;
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

    protected String getDatetimeFieldValue() {
        ComboBoxItem<JDRecordType> recordTypeItem = datetimeRecordTypeField.getValue();

        if (recordTypeItem != null) {
            switch (recordTypeItem.getType()){
                case KEYWORD:
                    return datetimeKeywordField.getText();

                case VALUE:
                    LocalDate date = datetimeValueDateField.getValue();
                    LocalTime time = Parsers.Time.parse(datetimeValueTimeField.getText());

                    if (date == null)
                        date = LocalDate.of(0, 1, 1);
                    if (time == null)
                        time = LocalTime.of(0, 0, 0);

                    return LocalDateTime.of(date, time).toString();
            }
        }

        return null;
    }

    protected String getExposureFieldValue() {
        ComboBoxItem<JDRecordType> recordTypeItem = exposureRecordTypeField.getValue();

        if (recordTypeItem != null) {
            switch (recordTypeItem.getType()) {
                case KEYWORD:
                    return exposureKeywordField.getText();

                case VALUE:
                    double exposureValue = Parsers.Double.parse(exposureValueField.getText());
                    return Double.toString(exposureValue);
            }
        }

        return null;
    }


    /**
     *
     */
    abstract class Validator {

        /**
         *
         */
        void validateDatetimeRecordTypeField()
                throws ValidationException {
            if (datetimeRecordTypeField.getValue() == null) {
                WarningDialog.show(
                        _resources.getString("oper.common.alert.title"),
                        _resources.getString("oper.common.alert.header"),
                        _resources.getString("oper.jd.alert.content.datetime.type"));

                throw new ValidationException("Datetime value type is not selected");
            }
        }

        /**
         *
         * @throws ValidationException
         */
        void validateDatetimeValueField()
                throws ValidationException {
            JDRecordType recordType = datetimeRecordTypeField.getValue().getType();

            switch (recordType) {
                case KEYWORD:
                    if (datetimeKeywordField.getText().isEmpty()) {
                        WarningDialog.show(
                                _resources.getString("oper.common.alert.title"),
                                _resources.getString("oper.common.alert.header"),
                                _resources.getString("oper.jd.alert.content.datetime.keyword.empty"));

                        throw new ValidationException("Keyword of the datetime record is not set");
                    }
                    break;

                case VALUE:
                    String timeText = datetimeValueTimeField.getText();

                    if (datetimeValueDateField.getValue() == null
                            && timeText.isEmpty()) {
                        WarningDialog.show(
                                _resources.getString("oper.common.alert.title"),
                                _resources.getString("oper.common.alert.header"),
                                _resources.getString("oper.jd.alert.content.datetime.value.empty"));

                        throw new ValidationException("Datetime value is empty");
                    }
                    if (!timeText.isEmpty()
                            && !Parsers.Time.tryParse(timeText)) {
                        WarningDialog.show(
                                _resources.getString("oper.common.alert.title"),
                                _resources.getString("oper.common.alert.header"),
                                _resources.getString("oper.jd.alert.content.datetime.value.invalid"));

                        throw new ValidationException("Datetime value is in invalid format");
                    }
                    break;
            }
        }

        /**
         *
         * @throws ValidationException
         */
        void validateExposureRecordTypeField()
                throws ValidationException {
            if (exposureRecordTypeField.getValue() == null) {
                WarningDialog.show(
                        _resources.getString("oper.common.alert.title"),
                        _resources.getString("oper.common.alert.header"),
                        _resources.getString("oper.jd.alert.content.exposure.type"));

                throw new ValidationException("Exposure value type is not selected");
            }
        }

        /**
         *
         * @throws ValidationException
         */
        void validateExposureValueField()
                throws ValidationException {
            JDRecordType recordType = exposureRecordTypeField.getValue().getType();

            switch (recordType) {
                case KEYWORD:
                    if (exposureKeywordField.getText().isEmpty()) {
                        WarningDialog.show(
                                _resources.getString("oper.common.alert.title"),
                                _resources.getString("oper.common.alert.header"),
                                _resources.getString("oper.jd.alert.content.exposure.keyword.empty"));

                        throw new ValidationException("Keyword of the exposure record is empty");
                    }
                    break;

                case VALUE:
                    String exposureText = exposureValueField.getText();

                    if (exposureText.isEmpty()) {
                        WarningDialog.show(
                                _resources.getString("oper.common.alert.title"),
                                _resources.getString("oper.common.alert.header"),
                                _resources.getString("oper.jd.alert.content.exposure.value.empty"));

                        throw new ValidationException("Exposure value is empty");
                    }
                    if (!Parsers.Double.tryParse(exposureText)) {
                        WarningDialog.show(
                                _resources.getString("oper.common.alert.title"),
                                _resources.getString("oper.common.alert.header"),
                                _resources.getString("oper.jd.alert.content.exposure.value.invalid"));

                        throw new ValidationException("Exposure value is in invalid format");
                    }
                    break;
            }

        }
    }
}
