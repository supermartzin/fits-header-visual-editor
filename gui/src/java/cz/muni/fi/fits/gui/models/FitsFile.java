package cz.muni.fi.fits.gui.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * TODO insert description
 */
public class FitsFile {

    private final StringProperty _filename;
    private final StringProperty _filepath;
    private final BooleanProperty _selected;

    public FitsFile(String filename, String filepath) {
        this._filename = new SimpleStringProperty(filename);
        this._filepath = new SimpleStringProperty(filepath);
        this._selected = new SimpleBooleanProperty(false);
    }

    public String getFilename() {
        return _filename.get();
    }

    public StringProperty filenameProperty() {
        return _filename;
    }

    public void setFilename(String filename) {
        this._filename.set(filename);
    }

    public String getFilepath() {
        return _filepath.get();
    }

    public StringProperty filepathProperty() {
        return _filepath;
    }

    public void setFilepath(String filepath) {
        this._filepath.set(filepath);
    }

    public boolean isSelected() {
        return _selected.get();
    }

    public BooleanProperty selectedProperty() {
        return _selected;
    }

    public void setSelected(boolean selected) {
        this._selected.set(selected);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FitsFile fitsFile = (FitsFile) o;

        return fitsFile.getFilename().equals(getFilename())
                && fitsFile.getFilepath().equals(getFilepath());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(_filename)
                .append(_filepath)
                .toHashCode();
    }
}
