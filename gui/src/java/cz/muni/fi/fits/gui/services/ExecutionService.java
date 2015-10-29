package cz.muni.fi.fits.gui.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * TODO insert description
 *
 * @author Martin Vrábel - © 2015 FITS-HeaderVisualEditor
 * @version 1.0
 */
public class ExecutionService extends Service {

    private final Task _task;

    public ExecutionService(Task task) {
        _task = task;
    }

    @Override
    protected Task createTask() {
        return _task;
    }
}
