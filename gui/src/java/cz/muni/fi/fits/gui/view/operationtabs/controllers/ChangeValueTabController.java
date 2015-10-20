package cz.muni.fi.fits.gui.view.operationtabs.controllers;

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
    public String getInputDataString() {
        try {
            // validate fields
            _validator.validateKeywordField();
            _validator.validateValueTypeField();
            _validator.validateValueField();

            return "";
        } catch (ValidationException vEx) {
            return null;
        }
    }


    /**
     *
     */
    class Validator extends BasicRecordBasedOperationTabController.Validator { }
}
