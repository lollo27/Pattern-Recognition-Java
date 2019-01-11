package com.lorenzo.terraneo.patternrecognition.domain;

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
}
