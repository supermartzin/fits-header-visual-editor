package cz.muni.fi.fits.input.converters;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.fail;

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

    @Test
    public void testTryParseInt_NullValue() throws Exception {
        String value = null;

        exception.expect(IllegalArgumentException.class);
        _converter.tryParseInt(value);
    }

    @Test
    public void testTryParseLong() throws Exception {
        fail();
    }

    @Test
    public void testTryParseDouble() throws Exception {
        fail();
    }

    @Test
    public void testTryParserBoolean() throws Exception {
        fail();
    }
}