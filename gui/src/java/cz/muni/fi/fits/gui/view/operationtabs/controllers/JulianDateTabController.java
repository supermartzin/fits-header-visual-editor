package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.inputdata.InputData;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class JulianDateTabController extends JulianDateBaseOperationTabController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        _tabName = resources.getString("tab.jd");
    }

    @Override
    public InputData getInputData() {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
