package cz.muni.fi.fits.gui.services;

import cz.muni.fi.fits.gui.models.FilterType;
import cz.muni.fi.fits.gui.models.FitsFile;
import eap.fits.FitsHDU;
import eap.fits.FitsHeader;
import eap.fits.RandomAccessFitsFile;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedHashSet;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class FilteringTask extends Task<Void> {

    private final ObservableList<FitsFile> _fitsFiles;
    private final FilterType _filterType;
    private final String _keyword;
    private final String _value;

    public FilteringTask(ObservableList<FitsFile> fitsFiles, FilterType filterType,
                         String keyword, String value) {
        if (fitsFiles == null)
            throw new IllegalArgumentException("FitsFiles collection is null");
        if (filterType == null)
            throw new IllegalArgumentException("Filter type is null");
        if (keyword == null)
            throw new IllegalArgumentException("Keyword is null");

        _fitsFiles = fitsFiles;
        _filterType = filterType;
        _keyword = keyword.toUpperCase();
        _value = value;
    }

    @Override
    protected Void call() throws Exception {
        new LinkedHashSet<>(_fitsFiles).forEach(
                fitsFile -> {
                    try {
                        boolean matchesFilter = false;

                        // read FITS file
                        RandomAccessFile file = new RandomAccessFile(fitsFile.getFilepath(), "r");
                        RandomAccessFitsFile fits = new RandomAccessFitsFile(file);

                        // get header of first HDU unit
                        FitsHDU hdu = fits.getHDU(0);
                        FitsHeader header = hdu.getHeader();

                        // find out if matches filter
                        if (_value != null) {
                            matchesFilter = header.hasCard(_keyword)
                                                && header.card(_keyword).stringValue().equals(_value);
                        } else {
                            matchesFilter = header.hasCard(_keyword);
                        }

                        // remove or keep matching cards
                        switch (_filterType) {
                            case KEEP:
                                if (!matchesFilter)
                                    _fitsFiles.remove(fitsFile);
                                break;

                            case REMOVE:
                                if (matchesFilter)
                                    _fitsFiles.remove(fitsFile);
                                break;
                        }

                        file.close();
                    } catch (IOException ignored) {
                        ignored.printStackTrace();
                    }
                });

        return null;
    }
}
