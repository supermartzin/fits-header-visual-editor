package cz.muni.fi.fits.engine;

import cz.muni.fi.fits.exceptions.EditingEngineException;
import cz.muni.fi.fits.exceptions.FitsHeaderException;
import nom.tam.fits.*;
import nom.tam.util.BufferedFile;
import nom.tam.util.Cursor;

import java.io.File;
import java.io.IOException;

/**
 *
 * TODO description
 */
public class NomTamFitsEditingEngine implements HeaderEditingEngine {

    @Override
    public void addNewRecord(String keyword, Object value, String comment, boolean updateIfExists, File fitsFile) throws EditingEngineException {
        if (keyword == null)
            throw new IllegalArgumentException("keyword is null");
        if (value == null)
            throw new IllegalArgumentException("value is null");
        if (fitsFile == null)
            throw new IllegalArgumentException("fitsFile is null");

        try {
            Fits fits = new Fits(fitsFile);

            // get header of first HDU unit
            BasicHDU hdu = fits.getHDU(0);
            Header header = hdu.getHeader();

            // create new HeaderCard based on value type
            HeaderCard card;
            if (value instanceof Integer) {
                card = new HeaderCard(keyword, (Integer)value, comment);
            } else if (value instanceof Long) {
                card = new HeaderCard(keyword, (Long)value, comment);
            } else if (value instanceof Double) {
                card = new HeaderCard(keyword, (Double)value, comment);
            } else if (value instanceof Boolean) {
                card = new HeaderCard(keyword, (Boolean)value, comment);
            } else if (value instanceof String) {
                card = new HeaderCard(keyword, (String)value, comment);
            } else {
                throw new EditingEngineException("Unknown type for value object");
            }

            // check if keyword does already exists
            boolean keywordExists = header.containsKey(keyword);

            if (keywordExists) {
                if (updateIfExists) {
                    header.updateLine(keyword, card);
                } else {
                    throw new FitsHeaderException("Header already contains '" + keyword + "' keyword");
                }
            } else {
                // iterate to the end of header
                Cursor iterator = header.iterator();
                while (iterator.hasNext()) {
                    iterator.next();
                }
                // insert new card
                iterator.add(keyword, card);
            }

            // write changes back to file
            BufferedFile bf = new BufferedFile(fitsFile, "rw");
            fits.write(bf);
        } catch (FitsException | IOException ex) {
            throw new EditingEngineException("Error in editing engine: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void addNewRecordToIndex(int index, String keyword, Object value, String comment, boolean removeOldIfExists, File fitsFile) throws EditingEngineException {
        if (index < 0)
            throw new IllegalArgumentException("invalid index");
        if (keyword == null)
            throw new IllegalArgumentException("keyword is null");
        if (value == null)
            throw new IllegalArgumentException("value is null");
        if (fitsFile == null)
            throw new IllegalArgumentException("fitsFile is null");

        if (index == 1 && !value.equals("SIMPLE"))
            throw new EditingEngineException("Index 1 is invalid for keyword '" + keyword + "'");

        try {
            Fits fits = new Fits(fitsFile);

            // get header of first HDU unit
            BasicHDU hdu = fits.getHDU(0);
            Header header = hdu.getHeader();

            // check if keyword does already exists
            boolean keywordExists = header.containsKey(keyword);

            if (keywordExists) {
                if (removeOldIfExists) {
                    header.removeCard(keyword);
                } else {
                    throw new FitsHeaderException("Header already contains '" + keyword + "' keyword");
                }
            }

            // create new HeaderCard based on value type
            HeaderCard card;
            if (value instanceof Integer) {
                card = new HeaderCard(keyword, (Integer) value, comment);
            } else if (value instanceof Long) {
                card = new HeaderCard(keyword, (Long) value, comment);
            } else if (value instanceof Double) {
                card = new HeaderCard(keyword, (Double) value, comment);
            } else if (value instanceof Boolean) {
                card = new HeaderCard(keyword, (Boolean) value, comment);
            } else if (value instanceof String) {
                card = new HeaderCard(keyword, (String) value, comment);
            } else {
                throw new EditingEngineException("Unknown type for value object");
            }

            // check if index is in range of header size
            boolean inRange = index <= header.getNumberOfCards();

            Cursor iterator = header.iterator();

            if (inRange) {
                // iterate to specified index
                for (int i = 1; i < index; i++) {
                    iterator.next();
                }
            } else {
                // iterate to the end of header
                while (iterator.hasNext()) {
                    iterator.next();
                }
            }

            // insert new card
            iterator.add(keyword, card);

            // write changes back to file
            BufferedFile bf = new BufferedFile(fitsFile, "rw");
            fits.write(bf);
        } catch (FitsException | IOException ex) {
            throw new EditingEngineException("Error in editing engine: " + ex.getMessage(), ex);
        }
    }
}
