package cz.muni.fi.fits.utils;

import com.google.common.base.Objects;

/**
 * TODO description
 */
public class Tuple<Type1, Type2> {

    private final Type1 _first;
    private final Type2 _second;

    public Tuple(Type1 first, Type2 second) {
        this._first = first;
        this._second = second;
    }

    public Type1 getFirst() {
        return _first;
    }

    public Type2 getSecond() {
        return _second;
    }

    @Override
    public String toString() {
        return "<" + _first.toString() + " - " + _second.toString() + ">";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equal(_first, tuple._first) &&
                Objects.equal(_second, tuple._second);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(_first, _second);
    }
}
