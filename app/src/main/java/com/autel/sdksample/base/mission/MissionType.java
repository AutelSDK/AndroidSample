package com.autel.sdksample.base.mission;

public enum MissionType {
    WAYPOINT(0),
    ORBIT(1),
    FOLLOW(2);
    int value;

    MissionType(int value) {
        this.value = value;
    }

    public static MissionType find(int value) {
        if (value == WAYPOINT.value) {
            return WAYPOINT;
        }
        if (value == ORBIT.value) {
            return ORBIT;
        }
        if (value == FOLLOW.value) {
            return FOLLOW;
        }
        return WAYPOINT;
    }
}
