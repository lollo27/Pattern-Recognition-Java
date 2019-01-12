package com.lorenzo.terraneo.patternrecognition.domain;

import java.util.List;

public class Point {

    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Point(Point p){
        this.x = p.getX();
        this.y = p.getY();
    }

    public static Point newPoint(double x, double y){
        return new Point(x,y);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    /*
    Method that allow the compare between two points, in order to say if a point is already in the plane
     */

    public boolean equals(Point point) {
        if(point.getX() == this.x && point.getY() == this.y){
            return true;
        }
        else{
            return false;
        }
    }

        /*
    Check if a point is collinear to a line
     */

    public boolean pointCollinearToLine(Line l){
        final List<Point> linePoints = l.getPoints();
        final Point p1 = linePoints.get(0);
        final Point p2 = linePoints.get(1);

        double calcolate = (p2.getY() - p1.getY()) * this.getX();
        calcolate += (p1.getX() - p2.getX()) * this.getY();
        calcolate += (p2.getX() * p1.getY() - p2.getY() * p1.getX());

        return calcolate == 0.0;
    }
}
