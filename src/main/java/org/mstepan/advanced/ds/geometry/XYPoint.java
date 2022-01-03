package org.mstepan.advanced.ds.geometry;

public final class XYPoint implements ComparableDimensions<XYPoint> {

    private final int x;
    private final int y;

    public XYPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int dimensionsCount() {
        return 2;
    }

    @Override
    public int compareWith(XYPoint other, int dimIndex) {
        return Integer.compare(dimension(dimIndex), other.dimension(dimIndex));
    }

    @Override
    public int dimDiff(XYPoint other, int dimIndex) {
        return dimension(dimIndex) - other.dimension(dimIndex);
    }

    private int dimension(int dimIndex) {
        if (dimIndex == 0) {
            return x;
        }
        if (dimIndex == 1) {
            return y;
        }

        throw new IllegalStateException(String.format("Can't obtain dimension value for class %s with index %d, " +
                                                              "dimension should be in range [%d ... %d]",
                                                      XYPoint.class.getCanonicalName(),
                                                      dimIndex,
                                                      0, dimensionsCount() - 1));
    }
}
