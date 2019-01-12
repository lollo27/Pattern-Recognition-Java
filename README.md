# Pattern Recognition

## Problem to solve

Given a set of P feature points in the bidimensional plane, determine every line that contains at least N or more COLLINEAR points.

## REST API

Maven project that use Spring Boot 2.1.1 developed in Java that allow the use of 5 REST API
- Add a point to the space: POST "/point" Body {"x":..., "y":...}
- Get all points in the space: GET "/space"
- Get every segment passing through at least N points: GET "/lines/{n}"
- Get the longest line segment passing through at least N points: GET "/line/{n}"
- Remove all points from the space: DELETE "/space"

There are also some simple JUnit tests for the primary functions of the software.
