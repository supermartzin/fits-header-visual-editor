package cz.muni.fi.fits.engine;

import cz.muni.fi.fits.exceptions.EditingEngineException;

import java.io.File;

/**
 *
 * TODO description
 */
public interface HeaderEditingEngine {

    void addNewRecord(String keyword, String value, String comment, boolean updateIfExists, File fitsFile) throws EditingEngineException;
}
