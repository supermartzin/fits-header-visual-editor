package cz.muni.fi.fits.input.converters;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * TODO description
 */
public class ValueTypeConverter implements TypeConverter {
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

    @Override
    public int parseInt(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        return Integer.parseInt(value);
    }

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

    @Override
    public long parseLong(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        return Long.parseLong(value);
    }

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

    @Override
    public double parseDouble(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        return Double.parseDouble(value);
    }

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

    @Override
    public BigInteger parseBigInteger(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        return new BigInteger(value);
    }

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

    @Override
    public BigDecimal parseBigDecimal(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");

        return new BigDecimal(value);
    }
}
