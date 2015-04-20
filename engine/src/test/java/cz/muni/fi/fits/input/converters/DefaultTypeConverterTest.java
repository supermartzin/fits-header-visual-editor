package cz.muni.fi.fits.input.converters;

import cz.muni.fi.fits.exceptions.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Tests for {@link DefaultTypeConverter} class
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class DefaultTypeConverterTest {

    private DefaultTypeConverter _converter;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        _converter = new DefaultTypeConverter();
    }

    @After
    public void tearDown() throws Exception {
        _converter = null;
    }

    // Integer
    @Test
    public void testTryParseInt_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.tryParseInt(value);
    }

    @Test
    public void testParseInt_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.parseInt(value);
    }

    @Test
    public void testTryParseInt_EmptyValue() throws Exception {
        String value = "";

        assertFalse(_converter.tryParseInt(value));
    }

    @Test
    public void testParseInt_EmptyValue() throws Exception {
        String value = "";

        exception.expect(ParseException.class);
        _converter.parseInt(value);
    }

    @Test
    public void testTryParseInt_IncorrectValues() throws Exception {
        String value1 = "258749654511";
        String value2 = "258749654.511";
        String value3 = "258749654511abd";
        String value4 = "true";

        assertFalse(_converter.tryParseInt(value1));
        assertFalse(_converter.tryParseInt(value2));
        assertFalse(_converter.tryParseInt(value3));
        assertFalse(_converter.tryParseInt(value4));
    }

    @Test
    public void testParseInt_IncorrectValue() throws Exception {
        String value = "258749654511";

        exception.expect(ParseException.class);
        _converter.parseInt(value);
    }

    @Test
    public void testTryParseInt_CorectValues() throws Exception {
        String value1 = "12453";
        String value2 = "0";
        String value3 = "-1254";
        String value4 = "+6589";

        assertTrue(_converter.tryParseInt(value1));
        assertTrue(_converter.tryParseInt(value2));
        assertTrue(_converter.tryParseInt(value3));
        assertTrue(_converter.tryParseInt(value4));
    }

    @Test
    public void testParseInt_CorectValues() throws Exception {
        String value1 = "12453";
        String value2 = "0";
        String value3 = "-1254";
        String value4 = "+6589";

        assertNotNull(_converter.parseInt(value1));
        assertNotNull(_converter.parseInt(value2));
        assertNotNull(_converter.parseInt(value3));
        assertNotNull(_converter.parseInt(value4));
    }


    // Long
    @Test
    public void testTryParseLong_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.tryParseLong(value);
    }

    @Test
    public void testParseLong_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.parseLong(value);
    }

    @Test
    public void testTryParseLong_EmptyValue() throws Exception {
        String value = "";

        assertFalse(_converter.tryParseLong(value));
    }

    @Test
    public void testParseLong_EmptyValue() throws Exception {
        String value = "";

        exception.expect(ParseException.class);
        _converter.parseLong(value);
    }

    @Test
    public void testTryParseLong_IncorrectValues() throws Exception {
        String value1 = "+-258749654511";
        String value2 = "258749654.511";
        String value3 = "258749654511abd";
        String value4 = "true";

        assertFalse(_converter.tryParseLong(value1));
        assertFalse(_converter.tryParseLong(value2));
        assertFalse(_converter.tryParseLong(value3));
        assertFalse(_converter.tryParseLong(value4));
    }

    @Test
    public void testParseLong_IncorrectValue() throws Exception {
        String value = "+-258749654511";

        exception.expect(ParseException.class);
        _converter.parseLong(value);
    }

    @Test
    public void testTryParseLong_CorrectValues() throws Exception {
        String value1 = "258749654511";
        String value2 = "+258749654511";
        String value3 = "-258749654511";
        String value4 = "6985";

        assertTrue(_converter.tryParseLong(value1));
        assertTrue(_converter.tryParseLong(value2));
        assertTrue(_converter.tryParseLong(value3));
        assertTrue(_converter.tryParseLong(value4));
    }

    @Test
    public void testParseLong_CorrectValues() throws Exception {
        String value1 = "258749654511";
        String value2 = "+258749654511";
        String value3 = "-258749654511";
        String value4 = "6985";

        assertNotNull(_converter.parseLong(value1));
        assertNotNull(_converter.parseLong(value2));
        assertNotNull(_converter.parseLong(value3));
        assertNotNull(_converter.parseLong(value4));
    }


    // Double
    @Test
    public void testTryParseDouble_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.tryParseDouble(value);
    }

    @Test
    public void testParseDouble_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.parseDouble(value);
    }

    @Test
    public void testTryParseDouble_EmptyValue() throws Exception {
        String value = "";

        assertFalse(_converter.tryParseDouble(value));
    }

    @Test
    public void testParseDouble_EmptyValue() throws Exception {
        String value = "";

        exception.expect(ParseException.class);
        _converter.parseDouble(value);
    }

    @Test
    public void testTryParseDouble_IncorrectValues() throws Exception {
        String value1 = "+-258749654511";
        String value2 = "258749654.511.2";
        String value3 = "258749654511abd";
        String value4 = "true";

        assertFalse(_converter.tryParseDouble(value1));
        assertFalse(_converter.tryParseDouble(value2));
        assertFalse(_converter.tryParseDouble(value3));
        assertFalse(_converter.tryParseDouble(value4));
    }

    @Test
    public void testParseDouble_IncorrectValue() throws Exception {
        String value = "+-258749654511";

        exception.expect(ParseException.class);
        _converter.parseDouble(value);
    }

    @Test
    public void testTryParseDouble_CorrectValues() throws Exception {
        String value1 = "+258749654511";
        String value2 = "-258749654.511";
        String value3 = "2.58749654511E25";
        String value4 = "45.";

        assertTrue(_converter.tryParseDouble(value1));
        assertTrue(_converter.tryParseDouble(value2));
        assertTrue(_converter.tryParseDouble(value3));
        assertTrue(_converter.tryParseDouble(value4));
    }

    @Test
    public void testParseDouble_CorrectValues() throws Exception {
        String value1 = "+258749654511";
        String value2 = "-258749654.511";
        String value3 = "2.58749654511E25";
        String value4 = "45.";

        assertNotNull(_converter.parseDouble(value1));
        assertNotNull(_converter.parseDouble(value2));
        assertNotNull(_converter.parseDouble(value3));
        assertNotNull(_converter.parseDouble(value4));
    }


    // Boolean
    @Test
    public void testTryParseBoolean_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.tryParseBoolean(value);
    }

    @Test
    public void testParseBoolean_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.parseBoolean(value);
    }

    @Test
    public void testTryParseBoolean_EmptyValue() throws Exception {
        String value = "";

        assertFalse(_converter.tryParseBoolean(value));
    }

    @Test
    public void testParseBoolean_EmptyValue() throws Exception {
        String value = "";

        exception.expect(ParseException.class);
        _converter.parseBoolean(value);
    }

    @Test
    public void testTryParseBoolean_IncorrectValues() throws Exception {
        String value1 = "tt";
        String value2 = "1";
        String value3 = "truel";

        assertFalse(_converter.tryParseBoolean(value1));
        assertFalse(_converter.tryParseBoolean(value2));
        assertFalse(_converter.tryParseBoolean(value3));
    }

    @Test
    public void testParseBoolean_IncorrectValue() throws Exception {
        String value = "tt";

        exception.expect(ParseException.class);
        _converter.parseBoolean(value);
    }

    @Test
    public void testTryParseBoolean_CorrectValues() throws Exception {
        String value1 = "true";
        String value2 = "t";
        String value3 = "FALSE";
        String value4 = "FaLsE";
        String value5 = "F";

        assertTrue(_converter.tryParseBoolean(value1));
        assertTrue(_converter.tryParseBoolean(value2));
        assertTrue(_converter.tryParseBoolean(value3));
        assertTrue(_converter.tryParseBoolean(value4));
        assertTrue(_converter.tryParseBoolean(value5));
    }

    @Test
    public void testParseBoolean_CorrectValues() throws Exception {
        String value1 = "true";
        String value2 = "t";
        String value3 = "FALSE";
        String value4 = "FaLsE";
        String value5 = "F";

        assertNotNull(_converter.parseBoolean(value1));
        assertNotNull(_converter.parseBoolean(value2));
        assertNotNull(_converter.parseBoolean(value3));
        assertNotNull(_converter.parseBoolean(value4));
        assertNotNull(_converter.parseBoolean(value5));
    }


    // BigInteger
    @Test
     public void testTryParseBigInteger_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.tryParseBigInteger(value);
    }

    @Test
    public void testParseBigInteger_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.parseBigInteger(value);
    }

    @Test
    public void testTryParseBigInteger_EmptyValue() throws Exception {
        String value = "";

        assertFalse(_converter.tryParseBigInteger(value));
    }

    @Test
    public void testParseBigInteger_EmptyValue() throws Exception {
        String value = "";

        exception.expect(ParseException.class);
        _converter.parseBigInteger(value);
    }

    @Test
     public void testTryParseBigInteger_IncorrectValues() throws Exception {
        String value1 = "--12365889452123624789541";
        String value2 = "1234567890123456789.";
        String value3 = ".02365T";

        assertFalse(_converter.tryParseBigInteger(value1));
        assertFalse(_converter.tryParseBigInteger(value2));
        assertFalse(_converter.tryParseBigInteger(value3));
    }

    @Test
    public void testParseBigInteger_IncorrectValue() throws Exception {
        String value = "--12365889452123624789541";

        exception.expect(ParseException.class);
        _converter.parseBigInteger(value);
    }

    @Test
    public void testTryParseBigInteger_CorrectValues() throws Exception {
        String value1 = "12345678901234567890";
        String value2 = "-56324";
        String value3 = "+6658999898989784512154121";

        assertTrue(_converter.tryParseBigInteger(value1));
        assertTrue(_converter.tryParseBigInteger(value2));
        assertTrue(_converter.tryParseBigInteger(value3));
    }

    @Test
    public void testParseBigInteger_CorrectValues() throws Exception {
        String value1 = "12345678901234567890";
        String value2 = "-56324";
        String value3 = "+6658999898989784512154121";

        assertNotNull(_converter.parseBigInteger(value1));
        assertNotNull(_converter.parseBigInteger(value2));
        assertNotNull(_converter.parseBigInteger(value3));
    }


    // BigDecimal
    @Test
    public void testTryParseBigDecimal_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.tryParseBigDecimal(value);
    }

    @Test
    public void testParseBigDecimal_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.parseBigDecimal(value);
    }

    @Test
    public void testTryParseBigDecimal_EmptyValue() throws Exception {
        String value = "";

        assertFalse(_converter.tryParseBigDecimal(value));
    }

    @Test
    public void testParseBigDecimal_EmptyValue() throws Exception {
        String value = "";

        exception.expect(ParseException.class);
        _converter.parseBigDecimal(value);
    }

    @Test
    public void testTryParseBigDecimal_IncorrectValues() throws Exception {
        String value1 = "12354778996..1254";
        String value2 = "4.58963256x10^-659";
        String value3 = "-+5632.1254";

        assertFalse(_converter.tryParseBigDecimal(value1));
        assertFalse(_converter.tryParseBigDecimal(value2));
        assertFalse(_converter.tryParseBigDecimal(value3));
    }

    @Test
    public void testParseBigDecimal_IncorrectValue() throws Exception {
        String value = "12354778996..1254";

        exception.expect(ParseException.class);
        _converter.parseBigDecimal(value);
    }

    @Test
    public void testTryParseBigDecimal_CorrectValues() throws Exception {
        String value1 = "123.457896541232E12345";
        String value2 = "-8965874513269115.11445458478784789748787661";
        String value3 = "+1.236574588256285268216261861e123";

        assertTrue(_converter.tryParseBigDecimal(value1));
        assertTrue(_converter.tryParseBigDecimal(value2));
        assertTrue(_converter.tryParseBigDecimal(value3));
    }

    @Test
    public void testParseBigDecimal_CorrectValues() throws Exception {
        String value1 = "123.457896541232E12345";
        String value2 = "-8965874513269115.11445458478784789748787661";
        String value3 = "+1.236574588256285268216261861e123";

        assertNotNull(_converter.parseBigDecimal(value1));
        assertNotNull(_converter.parseBigDecimal(value2));
        assertNotNull(_converter.parseBigDecimal(value3));
    }


    // LocalDateTime
    @Test
    public void testTryParseLocalDateTime_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.tryParseLocalDateTime(value);
    }

    @Test
    public void testParseLocalDateTime_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.parseLocalDateTime(value);
    }

    @Test
    public void testTryParseLocalDateTime_EmptyValue() throws Exception {
        String value = "";

        assertFalse(_converter.tryParseLocalDateTime(value));
    }

    @Test
    public void testParseLocalDateTime_EmptyValue() throws Exception {
        String value = "";

        assertFalse(_converter.tryParseLocalDateTime(value));

        exception.expect(ParseException.class);
        _converter.parseLocalDateTime(value);
    }

    @Test
    public void testTryParseLocalDateTime_IncorrectValues() throws Exception {
        String value1 = "2012:12:07T02:23:56";
        String value2 = "3-6-2-24:24:24";
        String value3 = "3.12.2014T14:15:16";

        assertFalse(_converter.tryParseLocalDateTime(value1));
        assertFalse(_converter.tryParseLocalDateTime(value2));
        assertFalse(_converter.tryParseLocalDateTime(value3));
    }

    @Test
    public void testParseLocalDateTime_IncorrectValue() throws Exception {
        String value = "2012:12:7T02:23:56";

        exception.expect(ParseException.class);
        _converter.parseLocalDateTime(value);
    }

    @Test
    public void testTryParseLocalDateTime_CorrectValues() throws Exception {
        String value1 = "2014-06-23T14:23:56";
        String value2 = "2012-12-08T12:12:12";

        assertTrue(_converter.tryParseLocalDateTime(value1));
        assertTrue(_converter.tryParseLocalDateTime(value2));
    }

    @Test
    public void testParseLocalDateTime_CorrectValues() throws Exception {
        String value1 = "2014-06-23T14:23:56";
        String value2 = "2012-12-08T12:12:12";

        assertNotNull(_converter.parseLocalDateTime(value1));
        assertNotNull(_converter.parseLocalDateTime(value2));
    }
}