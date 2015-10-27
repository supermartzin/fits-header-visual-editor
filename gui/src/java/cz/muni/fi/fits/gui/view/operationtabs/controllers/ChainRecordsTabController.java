package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.ChainRecordGroup;
import cz.muni.fi.fits.gui.models.inputdata.ChainRecordsInputData;
import cz.muni.fi.fits.gui.models.inputdata.InputData;
import cz.muni.fi.fits.gui.utils.ValidationException;
import cz.muni.fi.fits.gui.utils.WarningDialog;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.*;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ChainRecordsTabController extends OperationTabController {

    public GridPane gridPane;
    public Button addValueButton;

    public TextField keywordField;
    public CheckBox updateSwitchField;
    public CheckBox longstringsSwitchField;
    public TextField commentField;

    private Validator _validator;
    private int _currentIndex;
    private final SortedMap<Integer, ChainRecordGroup> _chainRecordGroups;

    public ChainRecordsTabController() {
        _chainRecordGroups = new TreeMap<>();
        _currentIndex = 4;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        _tabName = resources.getString("tab.chain");
        _validator = new Validator();

        ChainRecordGroup requiredGroup = new ChainRecordGroup(resources, true);
        addNewRecordGroup(requiredGroup);
    }

    @Override
    public InputData getInputData() {
        try {
            // validate fields
            _validator.validateKeywordField();
            _validator.validateChainRecordGroupFields();

            // get keyword
            String keyword = keywordField.getText();

            // get switches
            boolean updateIfExists = updateSwitchField.isSelected();
            boolean longstringsSupport = longstringsSwitchField.isSelected();

            // get values
            List<ChainRecordsInputData.ChainTuple> chainValues = new LinkedList<>();
            _chainRecordGroups.forEach((index, chainRecordGroup) ->
                    chainValues.add(new ChainRecordsInputData.ChainTuple(chainRecordGroup.getValueType(),
                                                                         chainRecordGroup.getValue())));

            // get comment
            String comment = commentField.getText();

            return new ChainRecordsInputData(keyword, updateIfExists, longstringsSupport, chainValues, comment);
        } catch (ValidationException vEx) {
            // validation errors
            return null;
        }
    }

    public void onAddRecordButtonClicked() {
        ChainRecordGroup chainRecordGroup = new ChainRecordGroup(_resources, false);
        chainRecordGroup.setRemoveListener(this::onRemove);

        addNewRecordGroup(chainRecordGroup);
    }

    private void addNewRecordGroup(ChainRecordGroup chainRecordGroup) {
        if (chainRecordGroup != null) {
            _chainRecordGroups.put(_currentIndex, chainRecordGroup);
            chainRecordGroup.addToGrid(gridPane, _currentIndex);

            moveAddValueButton(++_currentIndex);
        }
    }

    private void onRemove(EventObject eventObject) {
        if (eventObject != null) {
            ChainRecordGroup group = (ChainRecordGroup) eventObject.getSource();
            _chainRecordGroups.remove(group.getRowIndex());

            // move all following value groups
            moveFollowingValueGroups(group.getRowIndex());

            // move addRecord button
            int index = GridPane.getRowIndex(addValueButton);
            moveAddValueButton(--index);

            // decrease current row index
            _currentIndex--;
        }
    }

    private void moveFollowingValueGroups(int startIndex) {
        // iterate over copied map to be able to modify original map
        new TreeMap<>(_chainRecordGroups).forEach(
                (index, chainRecordGroup) -> {
                    if (index > startIndex) {
                        // remove from actual index
                        _chainRecordGroups.remove(index);
                        // put to index lower by one
                        _chainRecordGroups.put(index - 1, chainRecordGroup);
                        // set lower index to group
                        chainRecordGroup.setRowIndex(index - 1);
                    }
                });
    }

    private void moveAddValueButton(int index) {
        GridPane.setRowIndex(addValueButton, index);
    }


    /**
     *
     */
    class Validator {

        /**
         * @throws ValidationException
         */
        void validateKeywordField()
                throws ValidationException {
            if (keywordField.getText().isEmpty()) {
                WarningDialog.show(
                        _resources.getString("oper.common.alert.title"),
                        _resources.getString("oper.common.alert.header"),
                        _resources.getString("oper.common.alert.content.keyword.empty"));

                throw new ValidationException("Keyword field is not set");
            }
        }

        /**
         * @throws ValidationException
         */
        void validateChainRecordGroupFields()
                throws ValidationException {
            for (ChainRecordGroup chainRecordGroup : _chainRecordGroups.values()) {
                // validate value type
                if (chainRecordGroup.getValueType() == null) {
                    WarningDialog.show(
                            _resources.getString("oper.common.alert.title"),
                            _resources.getString("oper.common.alert.header"),
                            _resources.getString("oper.chain.alert.content.value.type.empty"));

                    throw new ValidationException("Keyword field is not set");
                }

                // validate value
                switch (chainRecordGroup.getValueType()) {
                    case KEYWORD:
                        if (chainRecordGroup.getValue().isEmpty()) {
                            WarningDialog.show(
                                    _resources.getString("oper.common.alert.title"),
                                    _resources.getString("oper.common.alert.header"),
                                    _resources.getString("oper.chain.alert.content.value.keyword.empty"));

                            throw new ValidationException("Keyword value of chained record is not set");
                        }
                        break;

                    case CONSTANT:
                        if (chainRecordGroup.getValue().isEmpty()) {
                            WarningDialog.show(
                                    _resources.getString("oper.common.alert.title"),
                                    _resources.getString("oper.common.alert.header"),
                                    _resources.getString("oper.chain.alert.content.value.constant.empty"));

                            throw new ValidationException("Constant value of chained record is not set");
                        }
                        break;
                }
            }
        }
    }
}
