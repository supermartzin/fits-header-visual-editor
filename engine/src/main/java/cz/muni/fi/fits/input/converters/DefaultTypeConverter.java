package cz.muni.fi.fits.input.converters;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Default converter class for converting different object types,
 * implments {@link TypeConverter} interface
 *
 * @author Martin Vrábel
 * @version 1.1
 */
public class DefaultTypeConverter implements TypeConverter {

    /**
     * Tries to parse an <code>Integer</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse an <code>Integer</code>
     * @return      {@inheritDoc}
     */
    @Override
    public boolean tryParseInt(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException nfEx) {
            return false;
        }
    }

    /**
     * Parses an <code>Integer</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse an <code>Integer</code>
     * @return      {@inheritDoc}
     */
    @Override
    public int parseInt(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        return Integer.parseInt(value);
    }

    /**
     * Tries to parse a <code>Long</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>Long</code>
     * @return      {@inheritDoc}
     */
    @Override
    public boolean tryParseLong(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException nfEx) {
            return false;
        }
    }

    /**
     * Parses a <code>Long</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>Long</code>
     * @return      {@inheritDoc}
     */
    @Override
    public long parseLong(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        return Long.parseLong(value);
    }

    /**
     * Tries to parse a <code>Double</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>Double</code>
     * @return      {@inheritDoc}
     */
    @Override
    public boolean tryParseDouble(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException nfEx) {
            return false;
        }
    }

    /**
     * Parses a <code>Double</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>Double</code>
     * @return      {@inheritDoc}
     */
    @Override
    public double parseDouble(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        return Double.parseDouble(value);
    }

    /**
     * Tries to parse a <code>Boolean</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>Boolean</code>
     * @return      {@inheritDoc}
     */
    @Override
    public boolean tryParseBoolean(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        value = value.toUpperCase().trim();
        return value.equals("TRUE")
                || value.equals("T")
                || value.equals("FALSE")
                || value.equals("F");
    }

    /**
     * Parses a <code>Boolean</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>Boolean</code>
     * @return      {@inheritDoc}
     */
    @Override
    public boolean parseBoolean(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        value = value.toUpperCase().trim();
        if (value.equals("TRUE") || value.equals("T"))
            return true;

        if (value.equals("FALSE") || value.equals("F"))
            return false;

        throw new IllegalArgumentException("string does not contain valid boolean value");
    }

    /**
     * Tries to parse a <code>BigInteger</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>BigInteger</code>
     * @return      {@inheritDoc}
     */
    @Override
    public boolean tryParseBigInteger(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        try {
            new BigInteger(value);
            return true;
        } catch (NumberFormatException nfEx) {
            return false;
        }
    }

    /**
     * Parses a <code>BigInteger</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>BigInteger</code>
     * @return      {@inheritDoc}
     */
    @Override
    public BigInteger parseBigInteger(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        return new BigInteger(value);
    }

    /**
     * Tries to parse a <code>BigDecimal</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>BigDecimal</code>
     * @return      {@inheritDoc}
     */
    @Override
    public boolean tryParseBigDecimal(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        try {
            new BigDecimal(value);
            return true;
        } catch (NumberFormatException nfEx) {
            return false;
        }
    }

    /**
     * Parses a <code>BigDecimal</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>BigDecimal</code>
     * @return      {@inheritDoc}
     */
    @Override
    public BigDecimal parseBigDecimal(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        return new BigDecimal(value);
    }
}
