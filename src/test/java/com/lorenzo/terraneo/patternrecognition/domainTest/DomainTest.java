package com.lorenzo.terraneo.patternrecognition.domainTest;

import com.lorenzo.terraneo.patternrecognition.domain.Line;
import com.lorenzo.terraneo.patternrecognition.domain.Plane;
import com.lorenzo.terraneo.patternrecognition.domain.Point;
import com.lorenzo.terraneo.patternrecognition.exception.LineErrorException;
import org.junit.Test;

import static org.junit.Assert.*;

public class DomainTest {

    private Point p1,p2,p3,p4,p5;
    private Plane plane;

    private void setTest(){
        this.p1 = new Point(0d,0d);
        this.p2 = new Point(1d,1d);
        this.p3 = new Point(2d,2d);

        this.p4 = new Point(p1);
        this.p5 = new Point(1d,5d);
    }

    private void setTestPlane(){
        setTest();
        plane = new Plane();
    }

    @Test
    public void lineTest(){
        setTest();

        Line l = Line.newLine(p1,p2,p3);
        assertNotNull(l);

        //Add a no collinear point at the line

        l.addPoint(p5);
        assertEquals(3,l.getPoints().size());

        //Add a collinear point

        l.addPoint(new Point(3d,3d));
        assertEquals(4,l.getPoints().size());
    }

    @Test(expected = LineErrorException.class)
    public void lineNotCollinearsPointsTest(){
        setTest();
        Line l = Line.newLine(p1,p2,p5);
    }

    @Test(expected = LineErrorException.class)
    public void lineOnePointTest(){
        setTest();
        Line l = Line.newLine(p1);
    }

    @Test
    public void planeTest(){
        setTestPlane();

        //Add 5 points but one is already in the plane

        plane.addPoint(p1);
        plane.addPoint(p2);
        plane.addPoint(p3);
        plane.addPoint(p4);
        plane.addPoint(p5);

        assertEquals(4,plane.getPoints().size());

        //Check the lines with at least 2 collinear points

        assertEquals(4,plane.getLinesContainsCollinearPoints(2).size());

        //Check the lines with at least 3 collinear points

        assertEquals(1,plane.getLinesContainsCollinearPoints(3).size());

        //Check the lines with at least 5 collinear points

        assertEquals(0, plane.getLinesContainsCollinearPoints(5).size());

        //Add a new point and check the lines with at least 2 collinear points

        plane.addPoint(new Point(3.3d,6.8d));

        assertEquals(8,plane.getLinesContainsCollinearPoints(2).size());
    }
}
