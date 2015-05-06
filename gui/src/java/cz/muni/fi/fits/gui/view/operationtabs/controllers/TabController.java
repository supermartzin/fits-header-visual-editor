package cz.muni.fi.fits.gui.view.operationtabs.controllers;

import cz.muni.fi.fits.gui.models.inputdata.InputData;
import javafx.fxml.Initializable;

/**
 * TODO description
 */
public interface TabController extends Initializable {

    boolean called();

    void setCalled(boolean called);

    InputData getInputData();
}
