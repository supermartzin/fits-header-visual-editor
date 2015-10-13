package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.inputdata.InputData;
import cz.muni.fi.fits.gui.utils.Constants;
import cz.muni.fi.fits.gui.utils.Constrainer;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _tabName = resources.getString("tab.change.keyword");

        setFieldsConstraints();
    }

    @Override
    public InputData getInputData() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    private void setFieldsConstraints() {
        Constrainer.constrainTextFieldWithRegex(oldKeywordField, Constants.KEYWORD_PATTERN);
        Constrainer.constrainTextFieldWithRegex(newKeywordField, Constants.KEYWORD_PATTERN);
    }
}
