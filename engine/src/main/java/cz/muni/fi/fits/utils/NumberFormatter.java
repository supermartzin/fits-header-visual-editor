package cz.muni.fi.fits.utils;

import java.text.NumberFormat;

/**
 * Helper class for formatting numbers info specific format
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public final class NumberFormatter {

    private NumberFormatter() { }

    /**
     * Formats inserted number into specified format usable in time or degree inscription,
     * e.g.
     * <pre>
     * {@code 123.5874 => "123.58"
     *   2.0      => "02"
     *   12.5     => "12.5"}
     * </pre>
     *
     * @param number    number to format
     * @return          formatted number in {@link String}
     */
    public static String format(double number) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumIntegerDigits(2);
        numberFormat.setMaximumFractionDigits(2);

        return numberFormat.format(number);
    }
}
