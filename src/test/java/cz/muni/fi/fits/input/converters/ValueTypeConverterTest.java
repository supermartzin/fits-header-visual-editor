package cz.muni.fi.fits.input.converters;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * TODO description
 */
public class ValueTypeConverterTest {

    private ValueTypeConverter _converter;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        _converter = new ValueTypeConverter();
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
    public void testTryParseInt_EmptyValue() throws Exception {
        String value = "";

        assertFalse(_converter.tryParseInt(value));
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


    // Long
    @Test
    public void testTryParseLong_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.tryParseLong(value);
    }

    @Test
    public void testTryParseLong_EmptyValue() throws Exception {
        String value = "";

        assertFalse(_converter.tryParseLong(value));
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


    // Double
    @Test
    public void testTryParseDouble_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.tryParseDouble(value);
    }

    @Test
    public void testTryParseDouble_EmptyValue() throws Exception {
        String value = "";

        assertFalse(_converter.tryParseDouble(value));
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


    // Boolean
    @Test
    public void testTryParseBoolean_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.tryParseBoolean(value);
    }

    @Test
    public void testTryParseBoolean_EmptyValue() throws Exception {
        String value = "";

        assertFalse(_converter.tryParseBoolean(value));
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


    // BigInteger
    @Test
    public void testTryParseBigInteger_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.tryParseBigInteger(value);
    }

    @Test
    public void testTryParseBigInteger_EmptyValue() throws Exception {
        String value = "";

        assertFalse(_converter.tryParseBigInteger(value));
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
    public void testTryParseBigInteger_CorrectValues() throws Exception {
        String value1 = "12345678901234567890";
        String value2 = "-56324";
        String value3 = "+6658999898989784512154121";

        assertTrue(_converter.tryParseBigInteger(value1));
        assertTrue(_converter.tryParseBigInteger(value2));
        assertTrue(_converter.tryParseBigInteger(value3));
    }


    // BigDecimal
    @Test
    public void testTryParseBigDecimal_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.tryParseBigDecimal(value);
    }

    @Test
    public void testTryParseBigDecimal_EmptyValue() throws Exception {
        String value = "";

        assertFalse(_converter.tryParseBigDecimal(value));
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
    public void testTryParseBigDecimal_CorrectValues() throws Exception {
        String value1 = "123.457896541232E12345";
        String value2 = "-8965874513269115.11445458478784789748787661";
        String value3 = "+1.236574588256285268216261861e123";

        assertTrue(_converter.tryParseBigDecimal(value1));
        assertTrue(_converter.tryParseBigDecimal(value2));
        assertTrue(_converter.tryParseBigDecimal(value3));
    }
}