package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.inputdata.ChangeValueByKeywordInputData;
import cz.muni.fi.fits.gui.models.inputdata.InputData;
import cz.muni.fi.fits.gui.utils.ValidationException;
import javafx.scene.control.CheckBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ChangeValueTabController extends BasicRecordBasedOperationTabController {

    public CheckBox addSwitchField;

    private Validator _validator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        _tabName = resources.getString("tab.change.value");
        _validator = new Validator();

        setFieldsConstraints();
    }

    @Override
    public InputData getInputData() {
        try {
            // validate fields
            _validator.validateKeywordField();
            _validator.validateValueTypeField();
            _validator.validateValueField();

            // get keyword
            String keyword = keywordField.getText();
            // get value
            String newValue = getRecordValue();
            // get comment
            String newComment = commentField.getText();
            // get switch
            boolean addNewIfNotExist = addSwitchField.isSelected();

            return new ChangeValueByKeywordInputData(keyword, newValue, newComment, addNewIfNotExist);
        } catch (ValidationException vEx) {
            // validation errors
            return null;
        }
    }


    /**
     *
     */
    class Validator extends BasicRecordBasedOperationTabController.Validator { }
}
