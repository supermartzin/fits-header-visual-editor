package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.inputdata.ChangeKeywordInputData;
import cz.muni.fi.fits.gui.models.inputdata.InputData;
import cz.muni.fi.fits.gui.utils.Constants;
import cz.muni.fi.fits.gui.utils.Constrainer;
import cz.muni.fi.fits.gui.utils.ValidationException;
import cz.muni.fi.fits.gui.utils.WarningDialog;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ChangeKeywordTabController extends OperationTabController {

    public CheckBox removeSwitchField;
    public TextField oldKeywordField;
    public TextField newKeywordField;

    private Validator _validator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        _tabName = resources.getString("tab.change.keyword");
        _validator = new Validator();

        setFieldsConstraints();
    }

    @Override
    public InputData getInputData() {
        try {
            // validate fields
            _validator.validateOldKeywordField();
            _validator.validateNewKeywordField();

            // get keywords
            String oldKeyword = oldKeywordField.getText();
            String newKeyword = newKeywordField.getText();
            // get switch
            boolean removeValueOfNewIfExists = removeSwitchField.isSelected();

            return new ChangeKeywordInputData(oldKeyword, newKeyword, removeValueOfNewIfExists);
        } catch (ValidationException vEx) {
            // validation errors
            return null;
        }
    }

    private void setFieldsConstraints() {
        Constrainer.constrainTextFieldWithRegex(oldKeywordField, Constants.KEYWORD_PATTERN);
        Constrainer.constrainTextFieldWithRegex(newKeywordField, Constants.KEYWORD_PATTERN);
    }


    /**
     *
     */
    class Validator {

        /**
         *
         * @throws ValidationException
         */
        void validateOldKeywordField()
                throws ValidationException {
            if (oldKeywordField.getText().isEmpty()) {
                WarningDialog.show(
                        _resources.getString("oper.common.alert.title"),
                        _resources.getString("oper.common.alert.header"),
                        _resources.getString("oper.change.alert.content.keyword.old.empty"));

                throw new ValidationException("Old keyword field is not set");
            }
        }

        /**
         *
         * @throws ValidationException
         */
        void validateNewKeywordField()
                throws ValidationException {
            if (newKeywordField.getText().isEmpty()) {
                WarningDialog.show(
                        _resources.getString("oper.common.alert.title"),
                        _resources.getString("oper.common.alert.header"),
                        _resources.getString("oper.change.alert.content.keyword.new.empty"));

                throw new ValidationException("New keyword field is not set");
            }
        }
    }
}
