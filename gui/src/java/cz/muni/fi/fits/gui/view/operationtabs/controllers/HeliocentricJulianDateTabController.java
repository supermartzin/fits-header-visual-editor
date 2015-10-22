package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.inputdata.InputData;
import cz.muni.fi.fits.gui.models.operationenums.HJDRecordType;
import cz.muni.fi.fits.gui.utils.Constants;
import cz.muni.fi.fits.gui.utils.Constrainer;
import cz.muni.fi.fits.gui.utils.combobox.ComboBoxItem;
import javafx.scene.control.ComboBox;
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
public class HeliocentricJulianDateTabController extends JulianDateBaseOperationTabController {

    // RIGHT ASCENSION
    public ComboBox rightAscensionRecordTypeField;
    public TextField rightAscensionKeywordField;
    public TextField rightAscensionDecimalValueField;
    public HBox rightAscensionFullValueContainer;
    public TextField rightAscensionHoursField;
    public TextField rightAscensionMinutesField;
    public TextField rightAscensionSecondsField;

    // DECLINATION
    public ComboBox declinationRecordTypeField;
    public TextField declinationKeywordField;
    public TextField declinationDecimalValueField;
    public HBox declinationFullValueContainer;
    public TextField declinationDegreesField;
    public TextField declinationMinutesField;
    public TextField declinationSecondsField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        _tabName = resources.getString("tab.hjd");

        loadRightAscensionRecordTypeField(resources);
        loadDeclinationRecordTypeField(resources);
    }

    @Override
    public InputData getInputData() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    protected void setFieldsConstraints() {
        super.setFieldsConstraints();

        Constrainer.constrainTextFieldWithRegex(rightAscensionKeywordField, Constants.KEYWORD_PATTERN);
        Constrainer.constrainTextFieldWithRegex(rightAscensionDecimalValueField, Constants.RIGHT_ASCENSION_PATTERN);
        Constrainer.constrainTextFieldWithRegex(rightAscensionHoursField, Constants.HOURS_PATTERN);
        Constrainer.constrainTextFieldWithRegex(rightAscensionMinutesField, Constants.MINUTES_PATTERN);
        Constrainer.constrainTextFieldWithRegex(rightAscensionSecondsField, Constants.SECONDS_PATTERN);

        Constrainer.constrainTextFieldWithRegex(declinationKeywordField, Constants.KEYWORD_PATTERN);
        Constrainer.constrainTextFieldWithRegex(declinationDecimalValueField, Constants.DECLINATION_PATTERN);
        Constrainer.constrainTextFieldWithRegex(declinationDegreesField, Constants.DEGREES_PATTERN);
        Constrainer.constrainTextFieldWithRegex(declinationMinutesField, Constants.MINUTES_PATTERN);
        Constrainer.constrainTextFieldWithRegex(declinationSecondsField, Constants.SECONDS_PATTERN);
    }

    private void loadRightAscensionRecordTypeField(ResourceBundle resources) {
        loadHJDRecordTypeItems(resources, rightAscensionRecordTypeField);

        rightAscensionRecordTypeField.setCellFactory(param -> new ComboBoxListCell<>());

        rightAscensionRecordTypeField.valueProperty().addListener(
                (observable, oldValue, newValue) -> onRightAscensionRecordTypeFieldSelectionChanged((ComboBoxItem) newValue));
    }

    private void loadDeclinationRecordTypeField(ResourceBundle resources) {
        loadHJDRecordTypeItems(resources, declinationRecordTypeField);

        declinationRecordTypeField.setCellFactory(param -> new ComboBoxListCell<>());

        declinationRecordTypeField.valueProperty().addListener(
                (observable, oldValue, newValue) -> onDeclinationRecordTypeFieldSelectionChanged((ComboBoxItem) newValue));
    }

    private void loadHJDRecordTypeItems(ResourceBundle resources, ComboBox comboBox) {
        if (resources != null && comboBox != null) {
            comboBox.getItems().add(new ComboBoxItem<>(HJDRecordType.KEYWORD,
                    resources.getString(HJDRecordType.KEYWORD.getPropertyName())));
            comboBox.getItems().add(new ComboBoxItem<>(HJDRecordType.DECIMAL_VALUE,
                    resources.getString(HJDRecordType.DECIMAL_VALUE.getPropertyName())));
            comboBox.getItems().add(new ComboBoxItem<>(HJDRecordType.FULL_VALUE,
                    resources.getString(HJDRecordType.FULL_VALUE.getPropertyName())));
        }
    }

    private void onRightAscensionRecordTypeFieldSelectionChanged(ComboBoxItem comboBoxItem) {
        if (comboBoxItem != null) {
            switch ((HJDRecordType) comboBoxItem.getType()) {
                case KEYWORD:
                    setRightAscensionRecordTypeDependentFieldsVisibility(true, false, false);
                    break;
                case DECIMAL_VALUE:
                    setRightAscensionRecordTypeDependentFieldsVisibility(false, true, false);
                    break;
                case FULL_VALUE:
                    setRightAscensionRecordTypeDependentFieldsVisibility(false, false, true);
                    break;
            }
        }
    }

    private void onDeclinationRecordTypeFieldSelectionChanged(ComboBoxItem comboBoxItem) {
        if (comboBoxItem != null) {
            switch ((HJDRecordType) comboBoxItem.getType()) {
                case KEYWORD:
                    setDeclinationRecordTypeDependentFieldsVisibility(true, false, false);
                    break;
                case DECIMAL_VALUE:
                    setDeclinationRecordTypeDependentFieldsVisibility(false, true, false);
                    break;
                case FULL_VALUE:
                    setDeclinationRecordTypeDependentFieldsVisibility(false, false, true);
                    break;
            }
        }
    }

    private void setRightAscensionRecordTypeDependentFieldsVisibility(
            boolean rightAscenionKeywordField,
            boolean rightAscensionDecimalValueField,
            boolean rightAscensionFullValueContainer) {
        this.rightAscensionKeywordField.setVisible(rightAscenionKeywordField);
        this.rightAscensionDecimalValueField.setVisible(rightAscensionDecimalValueField);
        this.rightAscensionFullValueContainer.setVisible(rightAscensionFullValueContainer);
    }

    private void setDeclinationRecordTypeDependentFieldsVisibility(
            boolean declinationKeywordField,
            boolean declinationDecimalValueField,
            boolean declinationFullValueContainer) {
        this.declinationKeywordField.setVisible(declinationKeywordField);
        this.declinationDecimalValueField.setVisible(declinationDecimalValueField);
        this.declinationFullValueContainer.setVisible(declinationFullValueContainer);
    }
}
