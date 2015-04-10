package cz.muni.fi.fits.engine;

import cz.muni.fi.fits.exceptions.EditingEngineException;
import cz.muni.fi.fits.exceptions.FitsHeaderException;
import nom.tam.fits.*;
import nom.tam.util.BufferedFile;
import nom.tam.util.Cursor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * TODO description
 */
public class NomTamFitsEditingEngine implements HeaderEditingEngine {

    private static final List<String> MANDATORY_KEYWORDS_REGEX = Arrays.asList("^NAXIS[0-9]{0,3}$", "^SIMPLE$", "^BITPIX$", "^EXTEND$", "^XTENSION$");

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
                Cursor<String, HeaderCard> iterator = header.iterator();

                // move cursor to the end of header
                iterator.end();

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
            boolean inRange = index <= header.getNumberOfCards() - 1;

            Cursor<String, HeaderCard> iterator = header.iterator();

            if (inRange) {
                // iterate to specified index
                if (index > 1)
                    iterator.next(index - 1);

                // check for mandatory keywords at this index
                String indexKey = iterator.next().getKey();
                for (String mandatoryKwRegex : MANDATORY_KEYWORDS_REGEX) {
                    if (indexKey.matches(mandatoryKwRegex))
                        throw new FitsHeaderException("Record cannot be inserted to index " + index + " because of mandatory keyword '" + indexKey + "'");
                }

                iterator.prev();
            } else {
                // iterate to the end of header
                iterator.end();
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

    @Override
    public void removeRecordByKeyword(String keyword, File fitsFile) throws EditingEngineException {
        if (keyword == null)
            throw new IllegalArgumentException("keyword is null");
        if (fitsFile == null)
            throw new IllegalArgumentException("fitsFile is null");

        try {
            Fits fits = new Fits(fitsFile);

            // get header of first HDU unit
            BasicHDU hdu = fits.getHDU(0);
            Header header = hdu.getHeader();

            /// check if keyword does already exists
            boolean keywordExists = header.containsKey(keyword);

            if (!keywordExists)
                throw new FitsHeaderException("Header does not contain keyword '" + keyword + "'");

            // check for mandatory keywords
            for (String mandatoryKwRegex : MANDATORY_KEYWORDS_REGEX) {
                if (keyword.matches(mandatoryKwRegex))
                    throw new FitsHeaderException("Keyword '" + keyword + "' is mandatory hence it cannot be removed");
            }

            // remove card with specified keyword
            header.removeCard(keyword);

            // write changes back to file
            BufferedFile bf = new BufferedFile(fitsFile, "rw");
            fits.write(bf);
        } catch (FitsException | IOException ex) {
            throw new EditingEngineException("Error in editing engine: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void removeRecordFromIndex(int index, File fitsFile) throws EditingEngineException {
        if (index < 0)
            throw new IllegalArgumentException("invalid index");
        if (fitsFile == null)
            throw new IllegalArgumentException("fitsFile is null");

        try {
            Fits fits = new Fits(fitsFile);

            // get header of first HDU unit
            BasicHDU hdu = fits.getHDU(0);
            Header header = hdu.getHeader();

            // check if index is in range
            boolean inRange = index <= header.getNumberOfCards() - 1;

            if (!inRange)
                throw new FitsHeaderException("Index " + index + " is not in range of header size");

            // move iterator to specified index
            Cursor<String, HeaderCard> iterator;
            if (index > 1)
                iterator = header.iterator(index - 1);
            else
                iterator = header.iterator();

            String indexKey = iterator.next().getKey();
            for (String mandatoryKwRegex : MANDATORY_KEYWORDS_REGEX) {
                if (indexKey.matches(mandatoryKwRegex))
                    throw new FitsHeaderException("Keyword '" + indexKey + "' on index " + index + " is mandatory hence it cannot be removed");
            }

            // remove record on the index
            iterator.remove();

            // write changes back to file
            BufferedFile bf = new BufferedFile(fitsFile, "rw");
            fits.write(bf);
        } catch (FitsException | IOException ex) {
            throw new EditingEngineException("Error in editing engine: " + ex.getMessage(), ex);
        }
    }
}
