package cz.muni.fi.fits.models.inputData;

import cz.muni.fi.fits.models.OperationType;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * TODO description
 */
public class ShiftTimeInputData extends InputData {

    private final String _keyword;
    private final int _yearShift;
    private final int _monthShift;
    private final int _dayShift;
    private final int _hourShift;
    private final int _minuteShift;
    private final int _secondShift;
    private final int _nanosecondsShift;

    public ShiftTimeInputData(String keyword,
                              int yearShift,
                              int monthShift,
                              int dayShift,
                              int hourShift,
                              int minuteShift,
                              int secondShift,
                              int milisecondShift) {
        this(keyword, yearShift, monthShift, dayShift, hourShift, minuteShift, secondShift, milisecondShift, new HashSet<>());
    }

    public ShiftTimeInputData(String keyword,
                              int yearShift,
                              int monthShift,
                              int dayShift,
                              int hourShift,
                              int minuteShift,
                              int secondShift,
                              int milisecondShift,
                              Collection<File> fitsFiles) {
        super(OperationType.SHIFT_TIME, fitsFiles);
        _keyword = keyword != null ? keyword.toUpperCase() : null;
        _yearShift = yearShift;
        _monthShift = monthShift;
        _dayShift = dayShift;
        _hourShift = hourShift;
        _minuteShift = minuteShift;
        _secondShift = secondShift;
        _nanosecondsShift = milisecondShift * 1000 * 1000; // convert to nanoseconds
    }

    public String getKeyword() {
        return _keyword;
    }

    public int getYearShift() {
        return _yearShift;
    }

    public int getMonthShift() {
        return _monthShift;
    }

    public int getDayShift() {
        return _dayShift;
    }

    public int getHourShift() {
        return _hourShift;
    }

    public int getMinuteShift() {
        return _minuteShift;
    }

    public int getSecondShift() {
        return _secondShift;
    }

    public int getMilisecondsShift() {
        return _nanosecondsShift / 1000000;
    }

    public int getNanosecondShift() {
        return _nanosecondsShift;
    }
}
