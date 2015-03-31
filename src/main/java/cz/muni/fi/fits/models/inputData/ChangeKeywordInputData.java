package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * TODO description
 */
public class ChangeKeywordInputData extends OneSwitchInputData {

    private final String _oldKeyword;
    private final String _newKeyword;

    public ChangeKeywordInputData(String oldKeyword, String newKeyword, boolean removeValueOfNewIfExists) {
        this(oldKeyword, newKeyword, removeValueOfNewIfExists, new HashSet<>());
    }

    public ChangeKeywordInputData(String oldKeyword, String newKeyword, boolean removeValueOfNewIfExists, Collection<File> fitsFiles) {
        super(OperationType.CHANGE_KEYWORD, fitsFiles, removeValueOfNewIfExists);
        this._oldKeyword = oldKeyword.toUpperCase();
        this._newKeyword = newKeyword.toUpperCase();
    }

    public String getOldKeyword() {
        return _oldKeyword;
    }

    public String getNewKeyword() {
        return _newKeyword;
    }

    public boolean removeValueOfNewIfExists() {
        return _hasSwitch;
    }
}
