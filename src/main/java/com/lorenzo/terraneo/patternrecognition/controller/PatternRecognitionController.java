package com.lorenzo.terraneo.patternrecognition.controller;

import com.lorenzo.terraneo.patternrecognition.domain.Point;
import com.lorenzo.terraneo.patternrecognition.exception.CustomErrorType;
import com.lorenzo.terraneo.patternrecognition.service.PatternRecognitionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PatternRecognitionController {

    private final PatternRecognitionService recognitionService;

    public PatternRecognitionController(PatternRecognitionService service){
        this.recognitionService = service;
    }

    @RequestMapping(value = "/point", method = RequestMethod.POST)
    public ResponseEntity addPointIntoPlane(@RequestBody Point p){

        if(!recognitionService.contain(p)){
            recognitionService.addPointIntoPlane(p);
            return new ResponseEntity<>(p, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new CustomErrorType("Unable to add the point, it is already in the plane."),HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/space", method = RequestMethod.GET)
    public ResponseEntity<List<Point>> getPoints(){
        List<Point> points = recognitionService.getPointsOfPlane();
        if (points.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(points, HttpStatus.OK);
    }

    @RequestMapping(value = "/lines/{n}", method = RequestMethod.GET)
    public ResponseEntity getLineCollinearPoint(@PathVariable("n") int n){
        if(n < 2){
            return new ResponseEntity<>(new CustomErrorType("Error: n must be greater than 1"),HttpStatus.BAD_REQUEST);
        }

        if(this.recognitionService.getLinesCollinearPoints(n).isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(this.recognitionService.getLinesCollinearPoints(n), HttpStatus.OK);
    }

    @RequestMapping(value = "/line/{n}", method = RequestMethod.GET)
    public ResponseEntity getLongestLineCollinearPoint(@PathVariable("n") int n){
        if(n < 2){
            return new ResponseEntity<>(new CustomErrorType("Error: n must be greater than 1"),HttpStatus.BAD_REQUEST);
        }

        if(this.recognitionService.getLinesCollinearPoints(n).isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(this.recognitionService.getLongestLineCollinearPoints(n), HttpStatus.OK);
    }

    @RequestMapping(value = "/space", method = RequestMethod.DELETE)
    public ResponseEntity clearPlane(){
        this.recognitionService.deletePlane();
        return new ResponseEntity<Point>(HttpStatus.NO_CONTENT);
    }
}
