package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.inputdata.InputData;
import cz.muni.fi.fits.gui.models.operationenums.ShiftDirection;
import cz.muni.fi.fits.gui.utils.Constants;
import cz.muni.fi.fits.gui.utils.Constrainer;
import cz.muni.fi.fits.gui.utils.combobox.ComboBoxItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ShiftTimeTabController extends OperationTabController {

    public TextField keywordField;

    // shift value fields
    public TextField yearsField;
    public TextField monthsField;
    public TextField daysField;
    public TextField hoursField;
    public TextField minutesField;
    public TextField secondsField;
    public TextField millisecondsField;

    // shift direction fields
    public ComboBox yearsDirectionField;
    public ComboBox monthsDirectionField;
    public ComboBox daysDirectionField;
    public ComboBox hoursDirectionField;
    public ComboBox minutesDirectionField;
    public ComboBox secondsDirectionField;
    public ComboBox millisecondsDirectionField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        _tabName = resources.getString("tab.shift");

        setFieldsConstraints();
        loadShiftDirectionFields(resources);
    }

    @Override
    public InputData getInputData() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    private void setFieldsConstraints() {
        Constrainer.constrainTextFieldWithRegex(keywordField, Constants.KEYWORD_PATTERN);

        Constrainer.constrainTextFieldWithRegex(yearsField, Constants.NONNEG_INTEGRAL_NUMBER_PATTERN);
        Constrainer.constrainTextFieldWithRegex(monthsField, Constants.NONNEG_INTEGRAL_NUMBER_PATTERN);
        Constrainer.constrainTextFieldWithRegex(daysField, Constants.NONNEG_INTEGRAL_NUMBER_PATTERN);
        Constrainer.constrainTextFieldWithRegex(hoursField, Constants.NONNEG_INTEGRAL_NUMBER_PATTERN);
        Constrainer.constrainTextFieldWithRegex(minutesField, Constants.NONNEG_INTEGRAL_NUMBER_PATTERN);
        Constrainer.constrainTextFieldWithRegex(secondsField, Constants.NONNEG_INTEGRAL_NUMBER_PATTERN);
        Constrainer.constrainTextFieldWithRegex(millisecondsField, Constants.NONNEG_INTEGRAL_NUMBER_PATTERN);
    }

    private void loadShiftDirectionFields(ResourceBundle resources) {
        if (resources != null) {
            ComboBoxItem<ShiftDirection> forwardDirection = new ComboBoxItem<>(
                    ShiftDirection.FORWARD, resources.getString(ShiftDirection.FORWARD.getPropertyName()));
            ComboBoxItem<ShiftDirection> backwardDirection = new ComboBoxItem<>(
                    ShiftDirection.BACKWARD, resources.getString(ShiftDirection.BACKWARD.getPropertyName()));

            yearsDirectionField.getItems().addAll(forwardDirection, backwardDirection);
            monthsDirectionField.getItems().addAll(forwardDirection, backwardDirection);
            daysDirectionField.getItems().addAll(forwardDirection, backwardDirection);
            hoursDirectionField.getItems().addAll(forwardDirection, backwardDirection);
            minutesDirectionField.getItems().addAll(forwardDirection, backwardDirection);
            secondsDirectionField.getItems().addAll(forwardDirection, backwardDirection);
            millisecondsDirectionField.getItems().addAll(forwardDirection, backwardDirection);
        }

        yearsDirectionField.setCellFactory(param -> new ComboBoxListCell<>());
        monthsDirectionField.setCellFactory(param -> new ComboBoxListCell<>());
        daysDirectionField.setCellFactory(param -> new ComboBoxListCell<>());
        hoursDirectionField.setCellFactory(param -> new ComboBoxListCell<>());
        minutesDirectionField.setCellFactory(param -> new ComboBoxListCell<>());
        secondsDirectionField.setCellFactory(param -> new ComboBoxListCell<>());
        millisecondsDirectionField.setCellFactory(param -> new ComboBoxListCell<>());
    }
}
