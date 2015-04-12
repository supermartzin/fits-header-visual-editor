package cz.muni.fi.fits.engine;

import cz.muni.fi.fits.models.Result;
import cz.muni.fi.fits.utils.Tuple;

import java.io.File;
import java.util.LinkedList;

/**
 *
 * TODO description
 */
public interface HeaderEditingEngine {

    /**
     *
     * @param keyword
     * @param value
     * @param comment
     * @param updateIfExists
     * @param fitsFile
     * @return
     */
    Result addNewRecord(String keyword, Object value, String comment, boolean updateIfExists, File fitsFile);

    /**
     *
     * @param index
     * @param keyword
     * @param value
     * @param comment
     * @param removeOldIfExists
     * @param fitsFile
     * @return
     */
    Result addNewRecordToIndex(int index, String keyword, Object value, String comment, boolean removeOldIfExists, File fitsFile);

    /**
     *
     * @param keyword
     * @param fitsFile
     * @return
     */
    Result removeRecordByKeyword(String keyword, File fitsFile);

    /**
     *
     * @param index
     * @param fitsFile
     * @return
     */
    Result removeRecordFromIndex(int index, File fitsFile);

    /**
     *
     * @param oldKeyword
     * @param newKeyword
     * @param removeValueOfNewIfExists
     * @param fitsFile
     * @return
     */
    Result changeKeywordOfRecord(String oldKeyword, String newKeyword, boolean removeValueOfNewIfExists, File fitsFile);

    /**
     *
     * @param keyword
     * @param newValue
     * @param newComment
     * @param addNewIfNotExists
     * @param fitsFile
     * @return
     */
    Result changeValueOfRecord(String keyword, Object newValue, String newComment, boolean addNewIfNotExists, File fitsFile);

    /**
     *
     * @param keyword
     * @param chainParameters
     * @param comment
     * @param updateIfExists
     * @param skipIfChainKwNotExists
     * @param fitsFile
     * @return
     */
    Result chainMultipleRecords(String keyword, LinkedList<Tuple> chainParameters, String comment, boolean updateIfExists, boolean skipIfChainKwNotExists, File fitsFile);
}
