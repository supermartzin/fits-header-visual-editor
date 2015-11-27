package cz.muni.fi.fits.gui.tasks;

import cz.muni.fi.fits.gui.models.FilterType;
import cz.muni.fi.fits.gui.models.FitsFile;
import cz.muni.fi.fits.gui.NomTamFitsReader;
import javafx.concurrent.Task;
import nom.tam.fits.FitsFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class FilteringTask extends Task<Set<FitsFile>> {

    private final Collection<FitsFile> _fitsFiles;
    private final FilterType _filterType;
    private final String _keyword;
    private final String _value;

    public FilteringTask(Collection<FitsFile> fitsFiles, FilterType filterType,
                         String keyword, String value) {
        if (fitsFiles == null)
            throw new IllegalArgumentException("FitsFiles collection is null");
        if (filterType == null)
            throw new IllegalArgumentException("Filter type is null");
        if (keyword == null)
            throw new IllegalArgumentException("Keyword is null");

        _fitsFiles = new HashSet<>(fitsFiles);
        _filterType = filterType;
        _keyword = keyword.toUpperCase();
        _value = value;
    }

    @Override
    protected Set<FitsFile> call() throws Exception {
        // allow longstrings
        FitsFactory.setLongStringsEnabled(true);

        Set<FitsFile> filesToRemove = new HashSet<>();

        _fitsFiles.forEach(
                fitsFile -> {
                    try (NomTamFitsReader fits = new NomTamFitsReader(fitsFile.getFilepath())) {
                        boolean matchesFilter = false;

                        // find out if matches filter
                        if (_value != null)
                            matchesFilter = fits.containsRecordWithValue(_keyword, _value);
                        else
                            matchesFilter = fits.containsRecord(_keyword);

                        // remove or keep files with matching keyword/value
                        switch (_filterType) {
                            case KEEP:
                                if (!matchesFilter)
                                    filesToRemove.add(fitsFile);
                                break;

                            case REMOVE:
                                if (matchesFilter)
                                    filesToRemove.add(fitsFile);
                                break;
                        }
                    } catch (IOException ignored) { }
                });

        // return files to remove
        return filesToRemove;
    }
}
