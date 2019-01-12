package com.lorenzo.terraneo.patternrecognition.domain;

import com.lorenzo.terraneo.patternrecognition.exception.LineErrorException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Line {

    private Set<Point> points;

    private Line(Set<Point> points){
        this.points = points;
    }

    public static Line newLine(Point p1, Point p2){
        Set<Point> points = new HashSet<>();
        points.add(p1);
        points.add(p2);

        return new Line(points);
    }

    /*
    Method that generate a line from a random number of points
     */

    public static Line newLine(Point... points) throws LineErrorException {

        final Set<Point> pointSet = Stream.of(points)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if(pointSet.size() < 2){
            throw new LineErrorException("At least 2 points for a line");
        }

        if(pointSet.size() > 2){
            Line l = newLine(points[0],points[1]);
            for(int i = 0; i < pointSet.size(); i++){
                if(!points[i].pointCollinearToLine(l)){
                    throw new LineErrorException("The points are not collinear");
                }
            }
        }

        return new Line(pointSet);
    }

    public List<Point> getPoints(){
        return new ArrayList<>(points);
    }

    public void addPoint(Point p){
        if(p.pointCollinearToLine(this)){
            this.points.add(p);
        }
    }
}
