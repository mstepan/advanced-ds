package org.mstepan.advanced.ds.util;

public final class PreConditions {

    private PreConditions() {
        throw new AssertionError("Can't instantiate utility class, should be used in STATIC way only.");
    }

    public static void checkArgument(boolean predicate, String errorMsg, Object... msgArgs) {
        if (!predicate) {
            throw new IllegalArgumentException(String.format(errorMsg, msgArgs));
        }
    }

}
