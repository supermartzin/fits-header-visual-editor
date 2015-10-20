package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.ChainRecordGroup;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ChainRecordsTabController extends OperationTabController {

    public GridPane gridPane;
    public Button addValueButton;

    private int _currentIndex;
    private final SortedMap<Integer, ChainRecordGroup> _chainRecordGroups;

    public ChainRecordsTabController() {
        _chainRecordGroups = new TreeMap<>();
        _currentIndex = 3;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        _tabName = resources.getString("tab.chain");

        ChainRecordGroup requiredGroup = new ChainRecordGroup(resources, true);
        addNewRecordGroup(requiredGroup);
    }

    @Override
    public String getInputDataString() {
        throw new UnsupportedOperationException("not implemented yet");
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
}
