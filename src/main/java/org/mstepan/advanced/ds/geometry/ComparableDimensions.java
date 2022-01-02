package org.mstepan.advanced.ds.geometry;

public interface ComparableDimensions<T> {

    int compareWith(T other, int dimIndex);

    int dimensionsCount();

}
