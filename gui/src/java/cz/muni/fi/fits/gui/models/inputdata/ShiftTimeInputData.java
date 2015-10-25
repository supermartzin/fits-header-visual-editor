package cz.muni.fi.fits.gui.models.inputdata;

import cz.muni.fi.fits.gui.utils.Constants;

/**
 * Class for storing input data for operation <code>Shift time of time record</code>
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ShiftTimeInputData extends InputDataBase {

    private final String _keyword;
    private final int _years;
    private final int _months;
    private final int _days;
    private final int _hours;
    private final int _minutes;
    private final int _seconds;
    private final int _milliseconds;

    /**
     * TODO
     * @param keyword
     * @param years
     * @param months
     * @param days
     * @param hours
     * @param minutes
     * @param seconds
     * @param milliseconds
     */
    public ShiftTimeInputData(String keyword, int years, int months, int days,
                              int hours, int minutes, int seconds, int milliseconds) {
        _keyword = keyword;
        _years = years;
        _months = months;
        _days = days;
        _hours = hours;
        _minutes = minutes;
        _seconds = seconds;
        _milliseconds = milliseconds;

        _operation = Operation.SHIFT_TIME;
    }

    /**
     * {@inheritDoc}
     *
     * @return  {@link String} with ordered and formatted input data
     *          or <code>null</code> if some of the required parameters is not set
     */
    @Override
    public String getInputDataString() {
        if (_keyword == null
                || _inputFilePath == null
                || shiftValuesNotSet())
            return null;

        return _operation.getStringValue() + Constants.EXPRESSIONS_DELIMITER +
                _inputFilePath + Constants.EXPRESSIONS_DELIMITER +
                _keyword.toUpperCase() + Constants.EXPRESSIONS_DELIMITER +
                ((_years != Integer.MIN_VALUE) ? "-y=" + _years + Constants.EXPRESSIONS_DELIMITER : "") +
                ((_months != Integer.MIN_VALUE) ? "-m=" + _months + Constants.EXPRESSIONS_DELIMITER : "") +
                ((_days != Integer.MIN_VALUE) ? "-d=" + _days + Constants.EXPRESSIONS_DELIMITER : "") +
                ((_hours != Integer.MIN_VALUE) ? "-h=" + _hours + Constants.EXPRESSIONS_DELIMITER : "") +
                ((_minutes != Integer.MIN_VALUE) ? "-min=" + _minutes + Constants.EXPRESSIONS_DELIMITER : "") +
                ((_seconds != Integer.MIN_VALUE) ? "-s=" + _seconds + Constants.EXPRESSIONS_DELIMITER : "") +
                ((_milliseconds != Integer.MIN_VALUE) ? "-ms=" + _milliseconds + Constants.EXPRESSIONS_DELIMITER : "");
    }

    private boolean shiftValuesNotSet() {
        return _years == Integer.MIN_VALUE
                && _months == Integer.MIN_VALUE
                && _days == Integer.MIN_VALUE
                && _hours == Integer.MIN_VALUE
                && _minutes == Integer.MIN_VALUE
                && _seconds == Integer.MIN_VALUE
                & _milliseconds == Integer.MIN_VALUE;
    }
}
