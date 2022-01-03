package org.mstepan.advanced.ds.geometry;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class KDTreeTest {

    @Test
    public void findNearest() {
        KDTree<XYPoint> tree = new KDTree<>();

        tree.add(new XYPoint(5, 4));
        tree.add(new XYPoint(2, 6));
        tree.add(new XYPoint(13, 3));
        tree.add(new XYPoint(3, 12));
        tree.add(new XYPoint(10, 2));
        tree.add(new XYPoint(8, 7));

        final XYPoint searchPoint1 = new XYPoint(9, 4);
        assertThat(tree.findNearest(searchPoint1)).
                isNotEmpty().
                hasValue(new XYPoint(10, 2));

        final XYPoint searchPoint2 = new XYPoint(4, 2);
        assertThat(tree.findNearest(searchPoint2)).
                isNotEmpty().
                hasValue(new XYPoint(5, 4));

        final XYPoint searchPoint3 = new XYPoint(2, 10);
        assertThat(tree.findNearest(searchPoint3)).
                isNotEmpty().
                hasValue(new XYPoint(3, 12));

        final XYPoint searchPoint4 = new XYPoint(15, 5);
        assertThat(tree.findNearest(searchPoint4)).
                isNotEmpty().
                hasValue(new XYPoint(13, 3));

        // exact root match
        final XYPoint searchPoint5 = new XYPoint(5, 4);
        assertThat(tree.findNearest(searchPoint5)).
                isNotEmpty().
                hasValue(new XYPoint(5, 4));

        // exact leaf match
        final XYPoint searchPoint6 = new XYPoint(8, 7);
        assertThat(tree.findNearest(searchPoint6)).
                isNotEmpty().
                hasValue(new XYPoint(8, 7));

        // exact node match
        final XYPoint searchPoint7 = new XYPoint(13, 3);
        assertThat(tree.findNearest(searchPoint7)).
                isNotEmpty().
                hasValue(new XYPoint(13, 3));
    }

    @Test
    public void addToEmptyTree() {
        KDTree<XYPoint> tree = new KDTree<>();

        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());

        tree.add(new XYPoint(10, 20));

        assertFalse(tree.isEmpty());
        assertEquals(1, tree.size());
    }

    @Test
    public void addAndContains() {
        XYPoint p1 = new XYPoint(10, 20);
        XYPoint p2 = new XYPoint(5, 20);
        XYPoint p3 = new XYPoint(18, 14);

        XYPoint p4 = new XYPoint(7, 14);
        XYPoint p5 = new XYPoint(18, 15);

        KDTree<XYPoint> tree = new KDTree<>();

        assertTrue(tree.add(p1));
        assertTrue(tree.contains(p1));
        assertFalse(tree.contains(p2));
        assertFalse(tree.contains(p3));
        assertFalse(tree.contains(p4));
        assertFalse(tree.contains(p5));

        assertTrue(tree.add(p2));
        assertTrue(tree.contains(p1));
        assertTrue(tree.contains(p2));
        assertFalse(tree.contains(p3));
        assertFalse(tree.contains(p4));
        assertFalse(tree.contains(p5));

        assertTrue(tree.add(p3));
        assertTrue(tree.contains(p1));
        assertTrue(tree.contains(p2));
        assertTrue(tree.contains(p3));
        assertFalse(tree.contains(p4));
        assertFalse(tree.contains(p5));
        assertFalse(tree.isEmpty());
        assertEquals(3, tree.size());
    }

    @Test
    public void addAlreadyExistingValues() {

        XYPoint p1 = new XYPoint(10, 20);
        XYPoint p2 = new XYPoint(5, 20);

        KDTree<XYPoint> tree = new KDTree<>();
        tree.add(p1);
        tree.add(p2);

        assertFalse(tree.add(p1));
        assertFalse(tree.add(p2));

        assertEquals(2, tree.size());
    }

    @Test
    public void containsForNullValueShouldReturnFalse() {
        KDTree<XYPoint> tree = new KDTree<>();
        assertFalse(tree.contains(null));
    }

    @Test
    public void containsForEmptyTree() {
        KDTree<XYPoint> tree = new KDTree<>();
        assertFalse(tree.contains(new XYPoint(5, 20)));
    }

}
