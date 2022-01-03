package org.mstepan.advanced.ds.geometry;


import java.util.Optional;

/**
 * Good article about K-D tree can be found here:
 * https://gopalcdas.com/2017/05/24/construction-of-k-d-tree-and-using-it-for-nearest-neighbour-search/
 */
public class KDTree<T extends ComparableDimensions<T>> {

    private KDEntry<T> root;
    private int size;
    private int dimensionsCount = -1;

    /**
     * Insert new value into K-D tree.
     *
     * @param value to insert into K-D tree.
     * @return true if value was successfully inserted otherwise false.
     */
    public boolean add(T value) {

        checkNotNull(value, "Can't add 'null' value to KDTree");

        initDimensionsCount(value);

        if (isEmpty()) {
            root = new KDEntry<>(value, 0);
        }
        else {
            SearchResult<T> searchRes = find(value);

            // value already found in a tree
            if (isEquals(searchRes.entry.value, value)) {
                return false;
            }

            // insert LEFT
            if (searchRes.lastDirection == Direction.LEFT) {
                searchRes.entry.left = new KDEntry<>(value, searchRes.lastDimension);
            }
            // insert RIGHT
            else {
                searchRes.entry.right = new KDEntry<>(value, searchRes.lastDimension);
            }
        }

        ++size;
        return true;
    }

    /**
     * Find single nearest neighbour closest to 'searchValue' using Euclidean distance as a measurement.
     */
    public Optional<T> findNearest(T searchValue) {
        checkNotNull(searchValue, "Can't search nearest neighbour for 'null' value.");
        return Optional.ofNullable(findNearestRec(root, searchValue));
    }

    private T findNearestRec(KDEntry<T> curEntry, T searchValue) {
        if (curEntry == null) {
            return null;
        }

        T bestChild;
        Direction visitedDirection;

        // search right
        if (searchValue.compareWith(curEntry.value, curEntry.dimensionIndex) >= 0) {
            visitedDirection = Direction.RIGHT;
            bestChild = findNearestRec(curEntry.right, searchValue);
        }
        // search left
        else {
            visitedDirection = Direction.LEFT;
            bestChild = findNearestRec(curEntry.left, searchValue);
        }

        T bestSoFar = curEntry.value;

        if (bestChild != null && distanceSquared(bestChild, searchValue) < distanceSquared(bestSoFar, searchValue)) {
            bestSoFar = bestChild;
        }

        // calculate perpendicular distance squared
        final T curValue = curEntry.value;
        final int curDim = curEntry.dimensionIndex;
        final int dimPerpendicularDistance = curValue.dimDiff(searchValue, curDim);
        final int dimPerpendicularSquared = dimPerpendicularDistance * dimPerpendicularDistance;

        // search in opposite direction (was LEFT -> go RIGHT), (was RIGHT -> go LEFT)
        if (dimPerpendicularSquared < distanceSquared(bestSoFar, searchValue)) {
            bestChild = findNearestRec((visitedDirection == Direction.LEFT) ? curEntry.right : curEntry.left, searchValue);
        }

        if (bestChild != null && distanceSquared(bestChild, searchValue) < distanceSquared(bestSoFar, searchValue)) {
            bestSoFar = bestChild;
        }

        return bestSoFar;
    }

    /**
     * distanceSquared = (x1 - x2)^2 + (y1 - y2)^2 + ... + (z1 - z2)^2
     */
    private int distanceSquared(T first, T second) {
        int distSquaredRes = 0;

        for (int dim = 0; dim < dimensionsCount; ++dim) {
            int diff = first.dimDiff(second, dim);
            distSquaredRes += (diff * diff);
        }
        return distSquaredRes;
    }

    public boolean contains(T searchValue) {
        if (searchValue == null || isEmpty()) {
            return false;
        }

        initDimensionsCount(searchValue);

        SearchResult<T> searchRes = find(searchValue);

        return isEquals(searchRes.entry.value, searchValue);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void initDimensionsCount(T value) {
        if (dimensionsCount < 0) {
            dimensionsCount = value.dimensionsCount();
        }
    }

    private void checkNotNull(T value, String errorMsg) {
        if (value == null) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    /**
     * It's better to use comparison for equality testing, in this way we don't need to rely on equals implementation.
     * Just iterate over all possible dimensions and compare each value from each dimension for two objects.
     */
    private boolean isEquals(T first, T second) {
        for (int dim = 0; dim < dimensionsCount; ++dim) {
            if (first.compareWith(second, dim) != 0) {
                return false;
            }
        }

        return true;
    }

    private SearchResult<T> find(T valueToSearch) {

        KDEntry<T> cur = root;

        Direction lastDirection = Direction.LEFT;
        KDEntry<T> lastNotNull = null;

        int curDimIndex = 0;

        while (cur != null) {

            lastNotNull = cur;

            if (isEquals(cur.value, valueToSearch)) {
                return SearchResult.withEntry(cur, curDimIndex, lastDirection);
            }

            // go right
            if (valueToSearch.compareWith(cur.value, curDimIndex) >= 0) {
                lastDirection = Direction.RIGHT;
                cur = cur.right;
            }
            // go left
            else {
                lastDirection = Direction.LEFT;
                cur = cur.left;
            }

            curDimIndex = (curDimIndex + 1) % dimensionsCount;
        }

        return SearchResult.withEntry(lastNotNull, curDimIndex, lastDirection);
    }

    private enum Direction {
        LEFT,
        RIGHT;
    }

    private static class KDEntry<U extends ComparableDimensions<U>> {
        final U value;
        final int dimensionIndex;

        KDEntry<U> left;
        KDEntry<U> right;

        KDEntry(U value, int dimensionIndex) {
            this.value = value;
            this.dimensionIndex = dimensionIndex;
        }
    }

    private static class SearchResult<R extends ComparableDimensions<R>> {
        final KDEntry<R> entry;
        final int lastDimension;
        final Direction lastDirection;

        SearchResult(KDEntry<R> entry, int lastDimension, Direction lastDirection) {
            this.entry = entry;
            this.lastDimension = lastDimension;
            this.lastDirection = lastDirection;
        }

        static <R1 extends ComparableDimensions<R1>> SearchResult<R1> withEntry(KDEntry<R1> entry, int dimIndex,
                                                                                Direction lastDirection) {
            return new SearchResult<>(entry, dimIndex, lastDirection);
        }
    }
}
