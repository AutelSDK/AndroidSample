package com.autel.sdksample.evo.mission.bean;

/**
 * Created by autel on 17/11/28.
 */

public enum AdvancedItemEnum {

    ORBIT_FLIGHT_DIRECTION("orbit_flight_direction"),
    ORBIT_HEADING("orbit_heading"),
    ORBIT_ENTRY_POINT("orbit_entry_point"),
    ORBIT_COMPLETION("orbit_completion"),
    WAYPOINT_HEADING("waypoint_heading"),
    WAYPOINT_FINISH_ACTION("waypoint_finish_action"),
    WAYPOINT_AVOID("waypoint_avoid"),
    WAYPOINT_CREATE_CHOICE("waypoint_create_choice"),
    WAYPOINT_MISSION("waypoint_mission"),
    DEFAULT("default");

    private final String value;

    AdvancedItemEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
