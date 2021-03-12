package ar.edu.itba.ss.y2021_q1_g123.models;

import java.util.Arrays;
import java.util.List;

public class L  {
    static Position NEUTRAL = new Position(0,0);
    static Position NORTH = new Position(-1,0);
    static Position NORTH_EAST = new Position(-1,1);
    static Position EAST = new Position(0,1);
    static Position SOUTH_EAST = new Position(1,1);
    static List<Position> POSITIONS = Arrays.asList(NORTH, NORTH_EAST, EAST, SOUTH_EAST, NEUTRAL);
}
