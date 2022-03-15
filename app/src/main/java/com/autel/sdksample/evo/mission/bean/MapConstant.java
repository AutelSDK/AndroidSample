package com.autel.sdksample.evo.mission.bean;

/**
 * Created by A15387 on 2016/5/12.
 */
@SuppressWarnings("DefaultFileTemplate")
public class MapConstant {
    public static final int MAP_TYPE_NORMAL = 0;
    public static final int MAP_TYPE_HYBRID = 1;
    public static final int MAP_TYPE_SATELLITE = 2;

    public static final String USE_AMAP_SP_KEY = "use_Amap_sp_key";
    public static final String MAP_TYPE_SP_KEY = "map_type_sp_key";
    public static final String MAP_USE_RECTIFY = "map_use_rectify";

    public static final String HOMELAT = "com.autel.maxifly.homeLat"; // home点纬度
    public static final String HOMELNG = "com.autel.maxifly.homeLng"; // home点经度
    public static final String HOMEALT = "com.autel.maxifly.homeAlt"; // homed点高度

    public static final String MAP_RECTIFY_ACTION = "com.autel.map.recitfy.action"; //坐标纠偏广播
    public static final String MAP_RECTIFY_INTENT_KEY = "com.autel.map.rectify.key";//坐标纠偏数据key

    public static final String MAP_TYPE_CHANGE_ACTION = "com.autel.map.type.change.action"; //地图类型切换广播
    public static final String MAP_TYPE_CHANGE_KEY = "com.autel.map.type.change.key"; //地图类型切换数据key

    public static final String MAP_TYPE_RANGE_CHANGE_ACTION = "com.autel.map.type.range.change.action"; //最大距离切换时的广播
    public static final String MAP_TYPE_ALTITUDE_CHANGE_ACTION = "com.autel.map.type.altitude.change.action"; //高度设置切换时的广播

    public static final String MAP_MOVE_CAMERA_LOCATION_ACTION = "com.autel.map.camera.location.action"; //地图中心点重新定位广播
    public static final String MAP_MOVE_CAMERA_LOCATION_KEY = "com.autel.map.camera.location.key"; //地图中心点重定位数据key
    public static final int MAP_MOVE_TO_HOME_LOCATION = 1; //地图中心点定位到home点
    public static final int MAP_MOVE_TO_DRONE_LOCATION = 2; //地图中心点定位到无人机的位置


    public static final String MAP_ENABLE_ROTATE_MAP_ACTION = "com.autel.map.enable.rotate.map"; //地图旋转广播
    public static final String MAP_ENABLE_ROTATE_MAP_KEY = "com.autel.map.enable.rotate.map.key";//地图旋转数据key

    public static final String MAP_USE_FLYLINE = "com.autel.map.use.flyline"; //使用航线轨迹

    public static final String MAP_CLEAR_FLY_LINE_ACTION = "com.autel.map.clear.flyline"; //清空航线轨迹action


    public static final String MAP_IS_NEED_SHOW_AUTOPILOT_NOTE_KEY = "com.autel.map.need_show_autopilot_note";
    public static final String MAP_IS_NEED_SHOW_OFFLINE_MISSION_NOTE_KEY = "com.autel.map.need_show_offline_mission_note";

    public static final int LOCATION_PERMISSION_RESULT_CODE = 10;


    public static final String MAP_TYPE_BEGINNER_OPEN_ACTION = "com.autel.map.type.beginner.open.action";
    public static final String GPS_PERMISSION_NOTE_KEY = "com.autel.GPS_PERMISSION_NOTE_KEY";
    public static final String SDCARD_PERMISSION_NOTE_KEY = "com.autel.SDCARD_PERMISSION_NOTE_KEY";
    public static final String MAP_TYPE_SMALL_SIZE = "com.autel.MAP_TYPE_SMALL_SIZE";
    public static final float MapInitZoomSize = 18.0f;

    public static final String MAP_SHOW_COORDINATE_SP_KEY = "map_show_coordinate_sp_key";
    public static final int MAP_SHOW_COORDINATE_NULL = 1;
    public static final int MAP_SHOW_COORDINATE_LAT_LNG = 2;
    public static final int MAP_SHOW_COORDINATE_UTM = 3;
    public static final int MAP_SHOW_COORDINATE_DMS = 4;

    public static final int MAP_CLICK_MODE_NULL = 0;
    public static final int MAP_CLICK_MODE_ORBIT = 1;
    public static final int MAP_CLICK_MODE_WAYPOINT = 2;

}
