package org.mstepan.advanced.ds.geometry;

public class XYPoint implements ComparableDimensions<XYPoint> {

    private static final int LO_DIM_INDEX = 0;
    private static final int HI_DIM_INDEX = 1;

    private final int x;
    private final int y;

    public XYPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareWith(XYPoint other, int dimIndex) {
        return Integer.compare(dimension(dimIndex), other.dimension(dimIndex));
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
                                                      LO_DIM_INDEX, HI_DIM_INDEX));
    }
}
