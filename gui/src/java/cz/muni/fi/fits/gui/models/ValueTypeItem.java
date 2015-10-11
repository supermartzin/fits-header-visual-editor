package cz.muni.fi.fits.gui.models;

/**
 * TODO description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ValueTypeItem {

    private ValueType _valueType;
    private String _name;

    public ValueTypeItem(ValueType valueType, String name) {
        _valueType = valueType;
        _name = name;
    }

    public ValueType getValueType() {
        return _valueType;
    }

    public void setValueType(ValueType valueType) {
        _valueType = valueType;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    @Override
    public String toString() {
        return _name;
    }
}
