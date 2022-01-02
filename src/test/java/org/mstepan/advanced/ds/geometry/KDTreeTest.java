package org.mstepan.advanced.ds.geometry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KDTreeTest {

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

        tree.add(p1);
        tree.add(p2);
        tree.add(p3);

        assertFalse(tree.isEmpty());
        assertEquals(3, tree.size());

        assertTrue(tree.contains(p1));
        assertTrue(tree.contains(p2));
        assertTrue(tree.contains(p3));
        assertFalse(tree.contains(p4));
        assertFalse(tree.contains(p5));
    }

}
