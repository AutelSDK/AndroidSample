package com.autel.sdksample.evo.mission.bean;

/**
 * Created by A15387 on 2017/11/20.
 */

public class WaypointAdvanceDataBean {

    public static final int TYPE_BASIC = 1;
    public static final int TYPE_ADVANCE = 2;

    public static final int TYPE_AVOIDANCE = 3;
    public static final int TYPE_FINISH_ACTION = 4;
    public static final int TYPE_ALTITUDE = 5;
    public static final int TYPE_SPEED = 6;
    public static final int TYPE_HEAD = 7;

    public static final int OBSTACLE_AVOIDANCE_MODE_INVALID = 0;
    public static final int OBSTACLE_AVOIDANCE_MODE_HOVER = 1;
    public static final int OBSTACLE_AVOIDANCE_MODE_HORIZOTAL = 2;
    public static final int OBSTACLE_AVOIDANCE_MODE_CLIMB_FIRST = 3;
    public static final int OBSTACLE_AVOIDANCE_UNKNOWN = -1;

    public static final int FINISH_ACTION_STOP_ON_LAST_POINT = 1;
    public static final int FINISH_ACTION_RETURN_HOME = 2;
    public static final int FINISH_ACTION_LAND_ON_LAST_POINT = 3;
    public static final int FINISH_ACTION_RETURN_TO_FIRST_POINT = 4;
    public static final int FINISH_ACTION_KEEP_ON_LAST_POINT = 5;
    public static final int FINISH_ACTION_UNKNOWN = 0;

    public static final int REMOTE_CONTROL_LOST_SIGNAL_ACTION_INVALID = 0;
    public static final int REMOTE_CONTROL_LOST_SIGNAL_ACTION_RETURN_HOME = 1;
    public static final int REMOTE_CONTROL_LOST_SIGNAL_ACTION_CONTINUE = 2;
    public static final int REMOTE_CONTROL_LOST_SIGNAL_ACTION_UNKNOWN = 3;

    private int waypointIndex;
    private int Altitude;
    private int Speed;
    private int finishType;
    private int heading;
    private int avoidance;
    private int headingDegree;


    public int getWaypointIndex() {
        return waypointIndex;
    }

    public void setWaypointIndex(int waypointIndex) {
        this.waypointIndex = waypointIndex;
    }

    public int getAltitude() {
        return Altitude;
    }

    public void setAltitude(int altitude) {
        Altitude = altitude;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }

    public int getFinishType() {
        return finishType;
    }

    public void setFinishType(int finishType) {
        this.finishType = finishType;
    }

    public int getHeading() {
        return heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    public int getAvoidance() {
        return avoidance;
    }

    public void setAvoidance(int avoidance) {
        this.avoidance = avoidance;
    }

    public void setHeadingDegree(int headingDegree) {
        this.headingDegree = headingDegree;
    }

    public int getHeadingDegree(){
        return headingDegree;
    }
}
