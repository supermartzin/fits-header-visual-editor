package cz.muni.fi.fits.input.converters;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Interface used for converting object types
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public interface TypeConverter {

    /**
     * Tries to parse an <code>Integer</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse an <code>Integer</code>
     * @return      <code>true</code> when value contains parsable <code>Integer</code> value
     *              <code>false</code> when value does not contain parsable <code>Integer</code> value
     */
    boolean tryParseInt(String value);

    /**
     * Parses an <code>Integer</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse an <code>Integer</code>
     * @return      parsed <code>Integer</code> value
     */
    int parseInt(String value);

    /**
     * Tries to parse a <code>Long</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>Long</code>
     * @return      <code>true</code> when value contains parsable <code>Long</code> value
     *              <code>false</code> when value does not contain parsable <code>Long</code> value
     */
    boolean tryParseLong(String value);

    /**
     * Parses a <code>Long</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>Long</code>
     * @return      parsed <code>Long</code> value
     */
    long parseLong(String value);

    /**
     * Tries to parse a <code>Double</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>Double</code>
     * @return      <code>true</code> when value contains parsable <code>Double</code> value
     *              <code>false</code> when value does not contain parsable <code>Double</code> value
     */
    boolean tryParseDouble(String value);

    /**
     * Parses a <code>Double</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>Double</code>
     * @return      parsed <code>Double</code> value
     */
    double parseDouble(String value);

    /**
     * Tries to parse a <code>Boolean</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>Boolean</code>
     * @return      <code>true</code> when value contains parsable <code>Boolean</code> value
     *              <code>false</code> when value does not contain parsable <code>Boolean</code> value
     */
    boolean tryParseBoolean(String value);

    /**
     * Parses a <code>Boolean</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>Boolean</code>
     * @return      parsed <code>Boolean</code> value
     */
    boolean parseBoolean(String value);

    /**
     * Tries to parse a <code>BigInteger</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>BigInteger</code>
     * @return      <code>true</code> when value contains parsable <code>BigInteger</code> value
     *              <code>false</code> when value does not contain parsable <code>BigInteger</code> value
     */
    boolean tryParseBigInteger(String value);

    /**
     * Parses a <code>BigInteger</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>BigInteger</code>
     * @return      parsed <code>BigInteger</code> value
     */
    BigInteger parseBigInteger(String value);

    /**
     * Tries to parse a <code>BigDecimal</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>BigDecimal</code>
     * @return      <code>true</code> when value contains parsable <code>BigDecimal</code> value
     *              <code>false</code> when value does not contain parsable <code>BigDecimal</code> value
     */
    boolean tryParseBigDecimal(String value);

    /**
     * Parses a <code>BigDecimal</code> value from given <code>String</code> value
     *
     * @param value <code>String</code> value from which to parse a <code>BigDecimal</code>
     * @return      parsed <code>BigDecimal</code> value
     */
    BigDecimal parseBigDecimal(String value);
}
