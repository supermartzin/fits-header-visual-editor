package cz.muni.fi.fits.gui.models.operations.add;

import cz.muni.fi.fits.gui.models.operations.PropertyBasedEnum;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public enum AddRecordToPlace implements PropertyBasedEnum {
    END("oper.add.place.end"),
    INDEX("oper.add.place.index");

    private String _propertyName;

    AddRecordToPlace(String propertyName) {
        _propertyName = propertyName;
    }

    @Override
    public String getPropertyName() {
        return _propertyName;
    }
}
