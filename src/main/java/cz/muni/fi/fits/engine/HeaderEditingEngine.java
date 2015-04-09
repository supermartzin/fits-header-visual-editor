package cz.muni.fi.fits.engine;

import cz.muni.fi.fits.exceptions.EditingEngineException;

import java.io.File;

/**
 *
 * TODO description
 */
public interface HeaderEditingEngine {

    void addNewRecord(String keyword, Object value, String comment, boolean updateIfExists, File fitsFile) throws EditingEngineException;

    void addNewRecordToIndex(int index, String keyword, Object value, String comment, boolean removeOldIfExists, File fitsFile) throws EditingEngineException;
}
