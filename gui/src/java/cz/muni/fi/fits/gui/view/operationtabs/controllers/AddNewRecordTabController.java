package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.operationenums.RecordPlacement;
import cz.muni.fi.fits.gui.utils.*;
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
public class AddNewRecordTabController extends BasicRecordBasedOperationTabController {

    public CheckBox updateSwitchField;
    public CheckBox removeSwitchField;
    public ComboBox<ComboBoxItem<RecordPlacement>> recordPlacementField;
    public TextField indexNumberField;

    private Validator _validator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        _tabName = resources.getString("tab.add");
        _validator = new Validator();

        setFieldsConstraints();
        loadRecordPlacementField(resources);
    }

    @Override
    public String getInputDataString() {
        try {
            // validate fields
            _validator.validateRecordPlacement();
            _validator.validateIndexField();
            _validator.validateKeywordField();
            _validator.validateValueTypeField();
            _validator.validateValueField();

            String inputDataString = "";

            switch (recordPlacementField.getValue().getType()) {
                case END:
                    // operation name
                    inputDataString += "ADD" + Constants.EXPRESSIONS_DELIMITER;
                    // set switch
                    if (updateSwitchField.isSelected())
                        inputDataString += "-u" + Constants.EXPRESSIONS_DELIMITER;
                    // set files placeholder
                    inputDataString += Constants.INPUT_FILES_PLACEHOLDER + Constants.EXPRESSIONS_DELIMITER;
                    // set
            }

            return "";
        } catch (ValidationException vEx) {
            return null;
        }
    }

    @Override
    protected void setFieldsConstraints() {
        super.setFieldsConstraints();

        Constrainer.constrainTextFieldWithRegex(indexNumberField, Constants.INTEGRAL_NUMBER_PATTERN);
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


    /**
     *
     */
    class Validator extends BasicRecordBasedOperationTabController.Validator {

        /**
         *
         * @throws ValidationException
         */
        void validateRecordPlacement() throws ValidationException {
            if (recordPlacementField.getValue() == null) {
                WarningDialog.show(
                        _resources.getString("oper.common.alert.title"),
                        _resources.getString("oper.common.alert.header"),
                        _resources.getString("oper.add.alert.content.rec_place"));

                throw new ValidationException();
            }
        }

        /**
         *
         * @throws ValidationException
         */
        void validateIndexField() throws ValidationException {
            RecordPlacement recordPlacement = recordPlacementField.getValue().getType();
            if (recordPlacement.equals(RecordPlacement.INDEX)) {
                String indexText = indexNumberField.getText();

                if (indexText.isEmpty()) {
                    WarningDialog.show(
                            _resources.getString("oper.common.alert.title"),
                            _resources.getString("oper.common.alert.header"),
                            _resources.getString("oper.common.alert.content.index.empty"));

                    throw new ValidationException();
                }
                if (!Parsers.Integer.tryParse(indexText)) {
                    WarningDialog.show(
                            _resources.getString("oper.common.alert.title"),
                            _resources.getString("oper.common.alert.header"),
                            _resources.getString("oper.common.alert.content.index.invalid"));

                    throw new ValidationException();
                }
            }
        }
    }
}
