package cz.muni.fi.fits.engine;

import cz.muni.fi.fits.exceptions.EditingEngineException;
import cz.muni.fi.fits.utils.Tuple;

import java.io.File;
import java.util.LinkedList;

/**
 *
 * TODO description
 */
public interface HeaderEditingEngine {

    void addNewRecord(String keyword, Object value, String comment, boolean updateIfExists, File fitsFile) throws EditingEngineException;

    void addNewRecordToIndex(int index, String keyword, Object value, String comment, boolean removeOldIfExists, File fitsFile) throws EditingEngineException;

    void removeRecordByKeyword(String keyword, File fitsFile) throws EditingEngineException;

    void removeRecordFromIndex(int index, File fitsFile) throws EditingEngineException;

    void changeKeywordOfRecord(String oldKeyword, String newKeyword, boolean removeValueOfNewIfExists, File fitsFile) throws EditingEngineException;

    void changeValueOfRecord(String keyword, Object newValue, String newComment, boolean addNewIfNotExists, File fitsFile) throws EditingEngineException;

    void chainMultipleRecords(String keyword, LinkedList<Tuple> chainParameters, String comment, boolean updateIfExists, boolean skipIfChainKwNotExists, File fitsFile) throws EditingEngineException;
}
