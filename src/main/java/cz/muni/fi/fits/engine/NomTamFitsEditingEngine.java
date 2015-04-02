package cz.muni.fi.fits.engine;

import cz.muni.fi.fits.exceptions.EditingEngineException;
import cz.muni.fi.fits.exceptions.FitsFileException;
import cz.muni.fi.fits.exceptions.FitsHeaderException;
import nom.tam.fits.*;
import nom.tam.util.Cursor;

import java.io.File;
import java.io.IOException;

/**
 *
 * TODO description
 */
public class NomTamFitsEditingEngine implements HeaderEditingEngine {

    @Override
    public void addNewRecord(String keyword, String value, String comment, boolean updateIfExists, File fitsFile) throws EditingEngineException {
        try {
            Fits fits = new Fits(fitsFile);

            fits.read();

            // check for number of HDUs in file
            if (fits.getNumberOfHDUs() == 0)
                throw new FitsFileException(fitsFile.getName(), "FITS file does no contain any HDU");

            BasicHDU hdu = fits.getHDU(0);
            Header header = hdu.getHeader();

            HeaderCard card = new HeaderCard(keyword, value, comment);

            // check if keyword does not already exists
            boolean alreadyExists = header.containsKey(keyword);

            if (alreadyExists) {
                if (updateIfExists) {
                    header.updateLine(keyword, card);
                } else {
                    throw new FitsHeaderException(fitsFile.getName(), "FITS Header already contains '" + keyword + "' keyword");
                }
            } else {
                Cursor cursor = header.iterator();
                while (cursor.hasNext()) {
                    cursor.next();
                }
                cursor.add(card);
            }

            System.out.println("ok");
        } catch (FitsException | IOException ex) {
            throw new EditingEngineException("Error in editing engine: " + ex.getMessage(), ex);
        }
    }
}
