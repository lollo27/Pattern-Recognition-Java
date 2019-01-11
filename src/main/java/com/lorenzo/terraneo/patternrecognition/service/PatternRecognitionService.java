package com.lorenzo.terraneo.patternrecognition.service;

import com.lorenzo.terraneo.patternrecognition.domain.Plane;
import com.lorenzo.terraneo.patternrecognition.domain.Point;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PatternRecognitionService {

    private Plane my_plane = new Plane();

    public void addPointIntoPlane(final Point point){

        Assert.notNull(point,"Point point cannot be null");

        if(!my_plane.getPoints().contains(point)){
            my_plane.addPoint(new Point(point.getX(),point.getY()));
        }

    }

    public List<Point> getPointsOfPlane(){
        return new ArrayList<>(this.my_plane.getPoints());
    }

    public List<List<Point>> getLinesCollinearPoints(int n){
        return this.my_plane.getLinesContainsCollinearPoints(n)
                .stream()
                .map(l -> new ArrayList<Point>(l.getPoints()))
                .collect(Collectors.toList());
    }

    public List<Point> getLongestLineCollinearPoints(int n){
        return getLinesCollinearPoints(n)
                .stream()
                .max(Comparator.comparing(List::size))
                .get();
    }

    public void deletePlane(){
        this.my_plane.clear();
    }

    public boolean contain(Point p){
        return my_plane.alreadyInPlane(p);
    }

}
