package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.operationenums.RemoveType;
import cz.muni.fi.fits.gui.utils.Constants;
import cz.muni.fi.fits.gui.utils.Constrainer;
import cz.muni.fi.fits.gui.utils.combobox.ComboBoxItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
public class RemoveRecordController extends OperationTabController {

    public ComboBox removeTypeField;

    // remove BY KEYWORD fields
    public Label keywordLabel;
    public TextField keywordField;
    // remove BY INDEX fields
    public Label indexLabel;
    public TextField indexField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        _tabName = resources.getString("tab.remove");

        setFieldsConstraints();
        loadRemoveTypeField(resources);
    }

    @Override
    public String getInputDataString() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    private void setFieldsConstraints() {
        Constrainer.constrainTextFieldWithRegex(keywordField, Constants.KEYWORD_PATTERN);
        Constrainer.constrainTextFieldWithRegex(indexField, Constants.INTEGRAL_NUMBER_PATTERN);
    }

    private void loadRemoveTypeField(ResourceBundle resources) {
        if (resources != null) {
            removeTypeField.getItems().add(new ComboBoxItem<>(RemoveType.BY_KEYWORD,
                    resources.getString(RemoveType.BY_KEYWORD.getPropertyName())));
            removeTypeField.getItems().add(new ComboBoxItem<>(RemoveType.FROM_INDEX,
                    resources.getString(RemoveType.FROM_INDEX.getPropertyName())));
        }

        removeTypeField.setCellFactory(param -> new ComboBoxListCell<>());

        removeTypeField.valueProperty().addListener(
                (observable, oldValue, newValue) -> onRemoveTypeFieldSelectionChanged((ComboBoxItem) newValue));
    }

    private void onRemoveTypeFieldSelectionChanged(ComboBoxItem comboBoxItem) {
        if (comboBoxItem != null) {
            switch ((RemoveType) comboBoxItem.getType()) {
                case BY_KEYWORD:
                    setRemoveTypeDependentFieldsVisibility(true, true, false, false);
                    break;
                case FROM_INDEX:
                    setRemoveTypeDependentFieldsVisibility(false, false, true, true);
            }
        }
    }

    private void setRemoveTypeDependentFieldsVisibility(boolean keywordLabel,
                                                        boolean keywordField,
                                                        boolean indexLabel,
                                                        boolean indexField) {
        this.keywordLabel.setVisible(keywordLabel);
        this.keywordField.setVisible(keywordField);
        this.indexLabel.setVisible(indexLabel);
        this.indexField.setVisible(indexField);
    }
}
