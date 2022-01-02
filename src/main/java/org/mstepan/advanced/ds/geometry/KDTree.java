package org.mstepan.advanced.ds.geometry;

public class KDTree<T extends ComparableDimensions<T>> {

    private KDEntry<T> root;
    private int size;


    /**
     * Insert new value into K-D tree.
     *
     * @param value to insert into K-D tree.
     * @return true if value was successfully inserted otherwise false.
     */
    public boolean add(T value) {
        if (isEmpty()) {
            root = new KDEntry<>(value, 0);
            ++size;
            return true;
        }

        return false;
    }

    public boolean contains(T value) {
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private static class KDEntry<U extends ComparableDimensions<U>> {
        final U value;
        final int dimensionIndex;

        KDEntry(U value, int dimensionIndex) {
            this.value = value;
            this.dimensionIndex = dimensionIndex;
        }
    }
}
