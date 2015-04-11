package cz.muni.fi.fits.engine;

import cz.muni.fi.fits.exceptions.EditingEngineException;
import cz.muni.fi.fits.exceptions.FitsHeaderException;
import cz.muni.fi.fits.utils.Constants;
import cz.muni.fi.fits.utils.Tuple;
import nom.tam.fits.*;
import nom.tam.util.BufferedFile;
import nom.tam.util.Cursor;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
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

            // create new header card based on value type
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
            } else if (value instanceof BigInteger) {
                card = new HeaderCard(keyword, (BigInteger) value, comment);
            } else if (value instanceof BigDecimal) {
                card = new HeaderCard(keyword,(BigDecimal) value, comment);
            } else {
                throw new EditingEngineException("Unknown type for value object");
            }

            // check if keyword does already exist
            boolean keywordExists = header.containsKey(keyword);

            if (keywordExists) {
                if (updateIfExists) {
                    // check for mandatory keyword
                    for (String mandatoryKwRegex : MANDATORY_KEYWORDS_REGEX) {
                        if (keyword.matches(mandatoryKwRegex))
                            throw new FitsHeaderException("Keyword '" + keyword + "' already exists in header and is mandatory hence it cannot be changed");
                    }
                    // update existing header card
                    header.updateLine(keyword, card);
                } else {
                    throw new FitsHeaderException("Header already contains '" + keyword + "' keyword");
                }
            } else {
                Cursor<String, HeaderCard> iterator = header.iterator();

                // move cursor to the end of header
                iterator.end();

                // insert new header card
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

            // check if keyword does already exist
            boolean keywordExists = header.containsKey(keyword);

            if (keywordExists) {
                if (removeOldIfExists) {
                    header.removeCard(keyword);
                } else {
                    throw new FitsHeaderException("Header already contains '" + keyword + "' keyword");
                }
            }

            // create new header card based on value type
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
            } else if (value instanceof BigInteger) {
                card = new HeaderCard(keyword, (BigInteger) value, comment);
            } else if (value instanceof BigDecimal) {
                card = new HeaderCard(keyword, (BigDecimal) value, comment);
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

                // check for mandatory keyword at this index
                String indexKey = iterator.next().getKey();
                for (String mandatoryKwRegex : MANDATORY_KEYWORDS_REGEX) {
                    if (indexKey.matches(mandatoryKwRegex))
                        throw new FitsHeaderException("Record cannot be inserted to index " + index + " because of mandatory keyword '" + indexKey + "'");
                }

                iterator.prev();
            } else {
                // move cursor to the end of header
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

            /// check if keyword does already exist
            boolean keywordExists = header.containsKey(keyword);

            if (!keywordExists)
                throw new FitsHeaderException("Header does not contain keyword '" + keyword + "'");

            // check for mandatory keyword
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

            // check if index is in range of header size
            boolean inRange = index <= header.getNumberOfCards() - 1;

            if (!inRange)
                throw new FitsHeaderException("Index " + index + " is not in range of header size");

            // move cursor to specified index
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

    @Override
    public void changeKeywordOfRecord(String oldKeyword, String newKeyword, boolean removeValueOfNewIfExists, File fitsFile) throws EditingEngineException {
        if (oldKeyword == null)
            throw new IllegalArgumentException("oldKeyword is null");
        if (newKeyword == null)
            throw new IllegalArgumentException("newKeyword is null");
        if (fitsFile == null)
            throw new IllegalArgumentException("fitsFile is null");

        try {
            Fits fits = new Fits(fitsFile);

            // get header of first HDU unit
            BasicHDU hdu = fits.getHDU(0);
            Header header = hdu.getHeader();

            // check if old keyword does already exist
            boolean oldExists = header.containsKey(oldKeyword);

            if (!oldExists)
                throw new FitsHeaderException("Header does not contain '" + oldKeyword + "' keyword");

            // check if new keyword does already exist
            boolean newExists = header.containsKey(newKeyword);

            if (newExists) {
                if (removeValueOfNewIfExists) {
                    // check for mandatory keyword
                    for (String mandatoryKwRegex : MANDATORY_KEYWORDS_REGEX) {
                        if (newKeyword.matches(mandatoryKwRegex))
                            throw new FitsHeaderException("Header already contains '" + newKeyword + "' keyword but it is mandatory hence it cannot be removed");
                    }
                    // remove already existing header card
                    header.removeCard(newKeyword);
                } else {
                    throw new FitsHeaderException("Header already contains '" + newKeyword + "' keyword");
                }
            }

            // check for mandatory keyword
            for (String mandatoryKwRegex : MANDATORY_KEYWORDS_REGEX) {
                if (oldKeyword.matches(mandatoryKwRegex))
                    throw new FitsHeaderException("Keyword '" + oldKeyword + "' is mandatory hence it cannot be changed");
            }

            // get old header card and create updated one based on type of value
            HeaderCard newCard;
            HeaderCard oldCard = header.findCard(oldKeyword);
            if (oldCard.valueType() == String.class) {
                String value = header.getStringValue(oldKeyword);
                newCard = new HeaderCard(newKeyword, value, oldCard.getComment());
            } else if (oldCard.valueType() == Double.class) {
                Double value = header.getDoubleValue(oldKeyword);
                newCard = new HeaderCard(newKeyword, value, oldCard.getComment());
            } else if (oldCard.valueType() == Boolean.class) {
                boolean value = header.getBooleanValue(oldKeyword);
                newCard = new HeaderCard(newKeyword, value, oldCard.getComment());
            } else if (oldCard.valueType() == Integer.class) {
                int value = header.getIntValue(oldKeyword);
                newCard = new HeaderCard(newKeyword, value, oldCard.getComment());
            } else if (oldCard.valueType() == Long.class) {
                long value = header.getLongValue(oldKeyword);
                newCard = new HeaderCard(newKeyword, value, oldCard.getComment());
            } else if (oldCard.valueType() == BigInteger.class) {
                BigInteger value = header.getBigIntegerValue(oldKeyword);
                newCard = new HeaderCard(newKeyword, value, oldCard.getComment());
            } else if (oldCard.valueType() == BigDecimal.class) {
                BigDecimal value = header.getBigDecimalValue(oldKeyword);
                newCard = new HeaderCard(newKeyword, value, oldCard.getComment());
            } else {
                newCard = new HeaderCard(newKeyword, oldCard.getValue(), oldCard.getComment());
            }

            // update old header card with new one
            header.updateLine(oldKeyword, newCard);

            // write changes back to file
            BufferedFile bf = new BufferedFile(fitsFile, "rw");
            fits.write(bf);
        } catch (FitsException | IOException ex) {
            throw new EditingEngineException("Error in editing engine: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void changeValueOfRecord(String keyword, Object newValue, String newComment, boolean addNewIfNotExists, File fitsFile) throws EditingEngineException {
        if (keyword == null)
            throw new IllegalArgumentException("keyword is null");
        if (newValue == null)
            throw new IllegalArgumentException("newValue is null");
        if (fitsFile == null)
            throw new IllegalArgumentException("fitsFile is null");

        try {
            Fits fits = new Fits(fitsFile);

            // get header of first HDU unit
            BasicHDU hdu = fits.getHDU(0);
            Header header = hdu.getHeader();

            // check if keyword does already exist
            boolean keywordExists = header.containsKey(keyword);

            // create new header card based on value type
            HeaderCard card;
            if (newValue instanceof Integer) {
                card = new HeaderCard(keyword, (Integer) newValue, newComment);
            } else if (newValue instanceof Long) {
                card = new HeaderCard(keyword, (Long) newValue, newComment);
            } else if (newValue instanceof Double) {
                card = new HeaderCard(keyword, (Double) newValue, newComment);
            } else if (newValue instanceof Boolean) {
                card = new HeaderCard(keyword, (Boolean) newValue, newComment);
            } else if (newValue instanceof String) {
                card = new HeaderCard(keyword, (String) newValue, newComment);
            } else if (newValue instanceof BigInteger) {
                card = new HeaderCard(keyword, (BigInteger) newValue, newComment);
            } else if (newValue instanceof BigDecimal) {
                card = new HeaderCard(keyword, (BigDecimal) newValue, newComment);
            } else {
                throw new EditingEngineException("Unknown type for value object");
            }

            if (keywordExists) {
                // check for mandatory keyword
                for (String mandatoryKwRegex : MANDATORY_KEYWORDS_REGEX) {
                    if (keyword.matches(mandatoryKwRegex))
                        throw new EditingEngineException("Keyword '" + keyword + "' is mandatory hence it cannot be changed");
                }

                // update existing header card
                header.updateLine(keyword, card);
            } else {
                if (!addNewIfNotExists) {
                    throw new FitsHeaderException("Header does not contain '" + keyword + "' keyword");
                } else {
                    Cursor<String, HeaderCard> iterator = header.iterator();

                    // move cursor to the end of header
                    iterator.end();

                    // insert new header card
                    iterator.add(keyword, card);
                }
            }

            // write changes back to file
            BufferedFile bf = new BufferedFile(fitsFile, "rw");
            fits.write(bf);
        } catch (FitsException | IOException ex) {
            throw new EditingEngineException("Error in editing engine: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void chainMultipleRecords(String keyword, LinkedList<Tuple> chainParameters, String comment, boolean updateIfExists, boolean skipIfChainKwNotExists, File fitsFile) throws EditingEngineException {
        if (keyword == null)
            throw new IllegalArgumentException("keyword is null");
        if (chainParameters == null)
            throw new IllegalArgumentException("chainParamaters are null");
        if (fitsFile == null)
            throw new IllegalArgumentException("fitsFile is null");

        try {
            Fits fits = new Fits(fitsFile);

            // get header of first HDU unit
            BasicHDU hdu = fits.getHDU(0);
            Header header = hdu.getHeader();

            // iterate over parameters and create new value
            String value = "";
            for (Tuple chainParameter : chainParameters) {
                switch ((String) chainParameter.getFirst()) {
                    case "constant":
                        value += (String) chainParameter.getSecond();
                        break;
                    case "keyword":
                        String key = (String) chainParameter.getSecond();
                        // check if header contains key
                        boolean keyExists = header.containsKey(key);

                        if (!keyExists) {
                            if (skipIfChainKwNotExists)
                                break;
                            else
                                throw new FitsHeaderException("Header does not contain '" + key + "' keyword");
                        } else {
                            // add to value
                            value += header.findCard(key).getValue();
                        }
                        break;
                }
            }

            // check for validity of value
            if (value.isEmpty())
                throw new EditingEngineException("Value of chained records cannot empty");
            if (value.length() > Constants.MAX_STRING_VALUE_LENGTH)
                throw new EditingEngineException("Value of chained records is too long");
            if (comment != null
                    && value.length() + comment.length() > Constants.MAX_STRING_VALUE_COMMENT_LENGTH)
                throw new EditingEngineException("Value along with comment are too long. Try to shorten the comment");

            // check if keyword does already exist
            boolean keywordExists = header.containsKey(keyword);

            if (keywordExists) {
                if (!updateIfExists) {
                    throw new FitsHeaderException("Header already contains '" + keyword + "' keyword");
                } else {
                    // check for mandatory keyword
                    for (String mandatoryKwRegex : MANDATORY_KEYWORDS_REGEX) {
                        if (keyword.matches(mandatoryKwRegex))
                            throw new FitsHeaderException("Header already contains '" + keyword + "' keyword but it is mandatory hence it cannot be changed");
                    }

                    // update header card with new chained value
                    header.updateLine(keyword, new HeaderCard(keyword, value, comment));
                }
            } else {
                Cursor<String, HeaderCard> iterator = header.iterator();

                // move cursor to the end of header
                iterator.end();

                // insert new header card
                iterator.add(keyword, new HeaderCard(keyword, value, comment));
            }

            // write changes back to file
            BufferedFile bf = new BufferedFile(fitsFile, "rw");
            fits.write(bf);
        } catch (FitsException | IOException ex) {
            throw new EditingEngineException("Error in editing engine: " + ex.getMessage(), ex);
        }
    }
}
