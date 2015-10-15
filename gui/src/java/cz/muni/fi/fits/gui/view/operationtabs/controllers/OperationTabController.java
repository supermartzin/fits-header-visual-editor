package cz.muni.fi.fits.gui.view.operationtabs.controllers;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public abstract class OperationTabController implements TabController {

    protected String _tabName;

    private boolean _operationCalled;

    @Override
    public boolean called() {
        return _operationCalled;
    }

    @Override
    public void setCalled(boolean called) {
        _operationCalled = called;
    }

    @Override
    public String getTabName() {
        return _tabName;
    }
}
