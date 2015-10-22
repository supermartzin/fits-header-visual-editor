package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.inputdata.AddNewRecordInputData;
import cz.muni.fi.fits.gui.models.inputdata.AddNewToIndexInputData;
import cz.muni.fi.fits.gui.models.inputdata.InputData;
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
    public InputData getInputData() {
        try {
            // validate fields
            _validator.validateRecordPlacementField();
            _validator.validateKeywordField();
            _validator.validateValueTypeField();
            _validator.validateValueField();

            InputData inputData = null;

            // get keyword
            String keyword = keywordField.getText();
            // get value
            String value = getRecordValue();
            // get comment
            String comment = commentField.getText();

            // create InputData based on placement in header
            switch (recordPlacementField.getValue().getType()) {
                case END:
                    // get switch
                    boolean updateIfExists = updateSwitchField.isSelected();

                    inputData = new AddNewRecordInputData(keyword, value, comment, updateIfExists);
                    break;

                case INDEX:
                    // validate
                    _validator.validateIndexField();

                    // get index
                    int index = Parsers.Integer.parse(indexNumberField.getText());
                    //get switch
                    boolean removeOldIfExists = removeSwitchField.isSelected();

                    inputData = new AddNewToIndexInputData(keyword, value, comment, index, removeOldIfExists);
                    break;
            }

            return inputData;
        } catch (ValidationException vEx) {
            // validation errors
            return null;
        }
    }

    @Override
    protected void setFieldsConstraints() {
        super.setFieldsConstraints();

        Constrainer.constrainTextFieldWithRegex(indexNumberField, Constants.NONNEG_INTEGRAL_NUMBER_PATTERN);
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
         * @throws ValidationException
         */
        void validateRecordPlacementField()
                throws ValidationException {
            if (recordPlacementField.getValue() == null) {
                WarningDialog.show(
                        _resources.getString("oper.common.alert.title"),
                        _resources.getString("oper.common.alert.header"),
                        _resources.getString("oper.add.alert.content.rec_place"));

                throw new ValidationException("Record placement field is not set");
            }
        }

        /**
         * @throws ValidationException
         */
        void validateIndexField()
                throws ValidationException {
            String indexText = indexNumberField.getText();

            if (indexText.isEmpty()) {
                WarningDialog.show(
                        _resources.getString("oper.common.alert.title"),
                        _resources.getString("oper.common.alert.header"),
                        _resources.getString("oper.common.alert.content.index.empty"));

                throw new ValidationException("Index field is not set");
            }
            if (!Parsers.Integer.tryParse(indexText)) {
                WarningDialog.show(
                        _resources.getString("oper.common.alert.title"),
                        _resources.getString("oper.common.alert.header"),
                        _resources.getString("oper.common.alert.content.index.invalid"));

                throw new ValidationException("Index field value is in invalid format");
            }
        }
    }
}
