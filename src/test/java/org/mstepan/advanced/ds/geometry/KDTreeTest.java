package org.mstepan.advanced.ds.geometry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * https://gopalcdas.com/2017/05/24/construction-of-k-d-tree-and-using-it-for-nearest-neighbour-search/
 */
final class KDTreeTest {

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
