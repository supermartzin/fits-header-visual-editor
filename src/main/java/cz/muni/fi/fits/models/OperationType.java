package cz.muni.fi.fits.models;

/**
 * Enumeration of main editing operations
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public enum OperationType {
    ADD_NEW_RECORD_TO_END,
    ADD_NEW_RECORD_TO_INDEX,
    REMOVE_RECORD_BY_KEYWORD,
    REMOVE_RECORD_BY_INDEX,
    CHANGE_KEYWORD,
    CHANGE_VALUE_BY_KEYWORD,
    CHAIN_RECORDS,
}
