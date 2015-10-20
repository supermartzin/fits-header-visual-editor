package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import javafx.scene.control.Tab;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public abstract class OperationTabController implements TabController {

    protected String _tabName;
    protected ResourceBundle _resources;

    private boolean _selected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (resources != null)
            _resources = resources;
    }

    @Override
    public void setTab(final Tab tab) {
        if (tab != null) {
            // add listener for Tab selection
            tab.selectedProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        _selected = newValue;
                    });
        }
    }

    @Override
    public boolean selected() {
        return _selected;
    }

    @Override
    public String getTabName() {
        return _tabName;
    }
}
