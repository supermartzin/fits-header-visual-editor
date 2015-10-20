package cz.muni.fi.fits.gui.utils;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public final class Parsers {

    public static class Integer {

        /**
         *
         * @param text
         * @return
         */
        public static boolean tryParse(String text) {
            try {
                java.lang.Integer.parseInt(text, 10);
                return true;
            } catch (NumberFormatException nfEx) {
                return false;
            }
        }
    }

    public static class Long {

        /**
         *
         * @param text
         * @return
         */
        public static boolean tryParse(String text) {
            try {
                java.lang.Long.parseLong(text, 10);
                return true;
            } catch (NumberFormatException nfEx) {
                return false;
            }
        }
    }

    public static class Double {

        /**
         *
         * @param text
         * @return
         */
        public static boolean tryParse(String text) {
            try {
                double parsed = java.lang.Double.parseDouble(text);

                return java.lang.Double.isFinite(parsed)
                        && !java.lang.Double.isNaN(parsed);
            } catch (NumberFormatException nfEx) {
                return false;
            }
        }
    }

    public static class Time {

        /**
         *
         * @param text
         * @return
         */
        public static boolean tryParse(String text) {
            try {
                LocalTime.parse(text);
                return true;
            } catch (DateTimeParseException dtpEx) {
                return false;
            }
        }

        /**
         *
         * @param text
         * @return
         */
        public static LocalTime parse(String text) {
            return LocalTime.parse(text);
        }
    }
}
