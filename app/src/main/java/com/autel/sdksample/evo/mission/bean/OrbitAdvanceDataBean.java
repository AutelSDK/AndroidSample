package com.autel.sdksample.evo.mission.bean;

/**
 * Created by A15387 on 2017/11/28.
 */

public class OrbitAdvanceDataBean {

    public static final int TYPE_BASIC_MODE = 0;
    public static final int TYPE_BASIC_SPEED_CHANGE = 1;
    public static final int TYPE_BASIC_FLIGHT_DIRECTION = 2;

    public static final int TYPE_ADVANCE_MODE = 3;
    public static final int TYPE_ADVANCE_ALTITUDE = 4;
    public static final int TYPE_ADVANCE_RADIUS = 5;
    public static final int TYPE_ADVANCE_SPEED = 6;
    public static final int TYPE_ADVANCE_ROTATION = 7;
    public static final int TYPE_ADVANCE_FLIGHT_DIRECTION = 8;
    public static final int TYPE_ADVANCE_HEADING = 9;
    public static final int TYPE_ADVANCE_ENTRY_POINT = 10;
    public static final int TYPE_ADVANCE_COMPLETION = 11;


    public static final int FLIGHT_DIRECTION_CW = 0;
    public static final int FLIGHT_DIRECTION_CCW = 1;

    public static final int HEADING_FACE_FORWARD = 0;
    public static final int HEADING_FACE_TOWARDS_CENTER = 1;
    public static final int HEADING_AWAYS_FROM_CENTER = 2;
    public static final int HEADING_FACE_BACKWARD = 3;
    public static final int HEADING_USER_CONTROLLED = 4;


    public static final int ENTRY_POINT_NEAREST = 0;
    public static final int ENTRY_POINT_NORTH = 1;
    public static final int ENTRY_POINT_SOUTH = 2;
    public static final int ENTRY_POINT_EAST = 3;
    public static final int ENTRY_POINT_WEST = 4;

    public static final int COMPLETION_HOVER = 0;
    public static final int COMPLETION_RTH = 1;

    boolean isBasicMode = true;
    int speed = 5;
    int flightDirection = FLIGHT_DIRECTION_CW;
    int altitude = 30;
    int radius = 10;
    int rotation = 360;
    int heading;
    int entryPoint;
    int completion;

    public int getSpeed() {
        return speed;
    }

    public int getFlightDirection() {
        return flightDirection;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setFlightDirection(int flightDirection) {
        this.flightDirection = flightDirection;
    }

    public boolean isBasicMode() {
        return isBasicMode;
    }

    public void setBasicMode(boolean basicMode) {
        isBasicMode = basicMode;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getHeading() {
        return heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    public int getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(int entryPoint) {
        this.entryPoint = entryPoint;
    }

    public int getCompletion() {
        return completion;
    }

    public void setCompletion(int completion) {
        this.completion = completion;
    }
}
