package org.mstepan.advanced.ds;

import org.mstepan.advanced.ds.geometry.XYPoint;

public class AdvancedDsMain {

    public static void main(String[] args) {

        XYPoint p1 = new XYPoint(10, 20);

        XYPoint p2 = new XYPoint(5, 20);

        int cmpRes = p1.compareWith(p2, 0);

        System.out.println(cmpRes);

        System.out.println("AdvancedDsMain done...");
    }

}
