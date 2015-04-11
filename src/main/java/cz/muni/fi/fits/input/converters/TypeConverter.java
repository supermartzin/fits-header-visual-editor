package cz.muni.fi.fits.input.converters;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * TODO description
 */
public interface TypeConverter {

    /**
     *
     * @param value
     * @return
     */
    boolean tryParseInt(String value);

    /**
     *
     * @param value
     * @return
     */
    int parseInt(String value);

    /**
     *
     * @param value
     * @return
     */
    boolean tryParseLong(String value);

    /**
     *
     * @param value
     * @return
     */
    long parseLong(String value);

    /**
     *
     * @param value
     * @return
     */
    boolean tryParseDouble(String value);

    /**
     *
     * @param value
     * @return
     */
    double parseDouble(String value);

    /**
     *
     * @param value
     * @return
     */
    boolean tryParseBoolean(String value);

    /**
     *
     * @param value
     * @return
     */
    boolean parseBoolean(String value);

    /**
     *
     * @param value
     * @return
     */
    boolean tryParseBigInteger(String value);

    /**
     *
     * @param value
     * @return
     */
    BigInteger parseBigInteger(String value);

    /**
     *
     * @param value
     * @return
     */
    boolean tryParseBigDecimal(String value);

    /**
     *
     * @param value
     * @return
     */
    BigDecimal parseBigDecimal(String value);
}
