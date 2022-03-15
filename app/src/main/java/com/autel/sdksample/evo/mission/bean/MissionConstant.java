package com.autel.sdksample.evo.mission.bean;


import com.autel.sdksample.evo.mission.util.TransformUtils;

/**
 * Created by A15387 on 2017/10/24.
 */

public class MissionConstant {

    public static final int MISSION_LIMIT_RANGE = 500;

    public static final int MISSION_ORBIT_RADIUS_LIMIT_MIN = 10;
    public static final int MISSION_ORBIT_RADIUS_LIMIT_MAX = 100;

    public static final int MISSION_TEXT_COLOR_RED = 1;
    public static final int MISSION_TEXT_COLOR_BLUE = 2;
    public static boolean isAutoPilotHelpActivityShow = false;  //判断智能飞行帮助教程activity是否显示

    public static double getOrbitSpeedLimitMin(){
        switch (TransformUtils.getUnitFlag()){
            case 0:
                return 3.6;
            case 1:
                return 1;
            case 2:
                return 2.2;
        }
        return 1;
    }

    public static double getDefaultSpeed(){
        switch (TransformUtils.getUnitFlag()){
            case 0:
                return 18;
            case 1:
                return 5;
            case 2:
                return 11;
        }
        return 5;
    }

    public static double getOrbitSpeedLimitMax(){
        switch (TransformUtils.getUnitFlag()){
            case 0:
                return 64.8;
            case 1:
                return 18;
            case 2:
                return 40.3;
        }
        return 18;
    }

    public static int getOrbitRadiusLimitMin(){
        return TransformUtils.isEnUnit() ? 33 : 10;
    }

    public static int getOrbitRadiusLimitMax(){
        return TransformUtils.isEnUnit() ? 328 : 100;
    }

    public static int getOrbitRadiusDefault(){
        return TransformUtils.isEnUnit() ? 33 : 10;
    }

    public static int getOrbitAltitudeLimitMin(){
        return TransformUtils.isEnUnit() ? 33 : 10;
    }

    public static int getOrbitAltitudeLimitMax(){
        return TransformUtils.isEnUnit() ? 2625 : 800;
    }

    public static int getOrbitAltitudeDefault(){
        return TransformUtils.isEnUnit() ? 197 : 60;
    }

    public static int getOrbitRotationLimitMin(){
        return 0;
    }

    public static int getOrbitRotationLimitMax(){
        return 360;
    }

    public static int getOrbitRotationDefault(){
        return 360;
    }

    public static int getWaypointAltitudeLimitMin(){
        return TransformUtils.isEnUnit() ? 33 : 10;
    }

    public static int getWaypointAltitudeLimitMax(){
        return TransformUtils.isEnUnit() ? 2625 : 800;
    }

    public static int getWaypointDefaultAltitude(){
        return TransformUtils.isEnUnit() ? 197 : 60;
    }

    public static double getWaypointSpeedLimitMin(){
        switch (TransformUtils.getUnitFlag()){
            case 0:
                return 3.6;
            case 1:
                return 1;
            case 2:
                return 2.2;
        }
        return 1;
    }

    public static double getWaypointSpeedLimitMax(){
        switch (TransformUtils.getUnitFlag()){
            case 0:
                return 64.8;
            case 1:
                return 18;
            case 2:
                return 40.3;
        }
        return 18;
    }

    public static int getWaypointSpeedDefault(){
        switch (TransformUtils.getUnitFlag()){
            case 0:
                return 18;
            case 1:
                return 5;
            case 2:
                return 11;
        }
        return 5;
    }

    public static int getWaypointHeadingMin(){
        return 0;
    }

    public static int getWaypointHeadingMax(){
        return 360;
    }

    public static int getWaypointHeadingDefault(){
        return 0;
    }
}
