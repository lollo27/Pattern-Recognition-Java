package com.lorenzo.terraneo.patternrecognition.domain;

import com.lorenzo.terraneo.patternrecognition.exception.PointNullException;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

public class Plane {

    private Set<Point> points;
    private Map<Integer, List<Line>> lines;

    public Plane(){
        this.points = new HashSet<>();
        this.lines = new HashMap<>();
    }

    /*

    Everytime the user add a new point in the plane, the software check and calculate again the possibile lines

     */

    public void addPoint(Point p) throws PointNullException {

        /*if(p == null){
            throw new PointNullException("Point p cannot be null!");
        }*/

        Assert.notNull(p,"Point p cannot be null!");

        if(!alreadyInPlane(p)){
            checkLines(p);
            this.points.add(p);
        }

    }

    public Set<Point> getPoints(){
        return this.points;
    }

    public void clear(){
        this.points = new HashSet<>();
        this.lines = new HashMap<>();
    }

    public List<Line> getLinesContainsCollinearPoints(int n){
        return this.lines.entrySet()
                .stream()
                .filter(p -> p.getKey() >= n)
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /*

    Methods that allow to calculate and check all the possible lines of the plane.

     */

    private void checkLines(final Point p){
        Map<Integer,List<Line>> tmpMap = new HashMap<>();

        this.lines.values()
                .stream()
                .flatMap(Collection::stream)
                .forEach(l -> {
                    if(pointCollinearToLine(p,l)){
                        l.addPoint(p);
                    }
                    List<Line> lineList = tmpMap.get(l.getPoints().size());
                    if(lineList == null){
                        lineList = new ArrayList<>();
                    }
                    lineList.add(l);

                    tmpMap.put(l.getPoints().size(),lineList);
                });

        this.lines = tmpMap;
        newLinesInPlane(p);
    }


    /*
    Check if a point is collinear to a line
     */

    public boolean pointCollinearToLine(Point p, Line l){
        final List<Point> linePoints = l.getPoints();
        final Point p1 = linePoints.get(0);
        final Point p2 = linePoints.get(1);

        double calcolate = (p2.getY() - p1.getY()) * p.getX();
        calcolate += (p1.getX() - p2.getX()) * p.getY();
        calcolate += (p2.getX() * p1.getY() - p2.getY() * p1.getX());

        return calcolate == 0.0;
    }

    /*
    Method that store all new line that are generate by the inclusion of a new point
     */

    private void newLinesInPlane(final Point point) {
        final List<Line> newLine;
        if(this.lines.get(2) != null){
            newLine = this.lines.get(2);
        } else {
            newLine = new ArrayList<>();
        }

        this.points
                .forEach(p -> {
                    if(!inLine(point,p)){
                        newLine.add(Line.newLine(point,p));
                    }
                });

        if(!newLine.isEmpty()){
            this.lines.put(2, newLine);
        }
    }

    /*
    This method check if two points and them line is already stored
     */

    private boolean inLine(Point p1, Point p2){
        List<Point> points = new ArrayList<>();
        points.add(p1);
        points.add(p2);

        return this.lines.values()
                .stream()
                .flatMap(Collection::stream)
                .anyMatch(l -> l.getPoints().containsAll(points));
    }


    /*
    Check if a point is already in the plane
     */

    public boolean alreadyInPlane(Point point){
        return this.points.stream()
                .anyMatch(p -> p.equals(point));
    }
}
