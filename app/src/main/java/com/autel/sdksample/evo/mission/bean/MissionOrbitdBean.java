package com.autel.sdksample.evo.mission.bean;

/**
 * Created by A15387 on 2017/11/27.
 */

public class MissionOrbitdBean {


    /**
     * method : OrbitInfo
     * params : {"MissionId":100,"WaypointId":8,"Speed":2,"Radius":4,"Cycles":20,"RemainDegree":30,"RotateDirection":1,"HeadingDirection":2,"CenterLattidue":30,"CenterLongitude":30,"CenterAltitude":30,"EntryDirection":4}
     */

    private String method;
    private ParamsBean params;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * MissionId : 100
         * WaypointId : 8
         * Speed : 2
         * Radius : 4
         * Cycles : 20
         * RemainDegree : 30
         * RotateDirection : 1
         * HeadingDirection : 2
         * CenterLattidue : 30
         * CenterLongitude : 30
         * CenterAltitude : 30
         * EntryDirection : 4
         */

        private int MissionId;
        private int WaypointId;
        private int Speed;
        private int Radius;
        private int Cycles;
        private int RemainDegree;
        private int RotateDirection;
        private int HeadingDirection;
        private int CenterLattidue;
        private int CenterLongitude;
        private int CenterAltitude;
        private int EntryDirection;

        public int getMissionId() {
            return MissionId;
        }

        public void setMissionId(int MissionId) {
            this.MissionId = MissionId;
        }

        public int getWaypointId() {
            return WaypointId;
        }

        public void setWaypointId(int WaypointId) {
            this.WaypointId = WaypointId;
        }

        public int getSpeed() {
            return Speed;
        }

        public void setSpeed(int Speed) {
            this.Speed = Speed;
        }

        public int getRadius() {
            return Radius;
        }

        public void setRadius(int Radius) {
            this.Radius = Radius;
        }

        public int getCycles() {
            return Cycles;
        }

        public void setCycles(int Cycles) {
            this.Cycles = Cycles;
        }

        public int getRemainDegree() {
            return RemainDegree;
        }

        public void setRemainDegree(int RemainDegree) {
            this.RemainDegree = RemainDegree;
        }

        public int getRotateDirection() {
            return RotateDirection;
        }

        public void setRotateDirection(int RotateDirection) {
            this.RotateDirection = RotateDirection;
        }

        public int getHeadingDirection() {
            return HeadingDirection;
        }

        public void setHeadingDirection(int HeadingDirection) {
            this.HeadingDirection = HeadingDirection;
        }

        public int getCenterLattidue() {
            return CenterLattidue;
        }

        public void setCenterLattidue(int CenterLattidue) {
            this.CenterLattidue = CenterLattidue;
        }

        public int getCenterLongitude() {
            return CenterLongitude;
        }

        public void setCenterLongitude(int CenterLongitude) {
            this.CenterLongitude = CenterLongitude;
        }

        public int getCenterAltitude() {
            return CenterAltitude;
        }

        public void setCenterAltitude(int CenterAltitude) {
            this.CenterAltitude = CenterAltitude;
        }

        public int getEntryDirection() {
            return EntryDirection;
        }

        public void setEntryDirection(int EntryDirection) {
            this.EntryDirection = EntryDirection;
        }

        @Override
        public String toString() {
            return "ParamsBean{" +
                    "MissionId=" + MissionId +
                    ", WaypointId=" + WaypointId +
                    ", Speed=" + Speed +
                    ", Radius=" + Radius +
                    ", Cycles=" + Cycles +
                    ", RemainDegree=" + RemainDegree +
                    ", RotateDirection=" + RotateDirection +
                    ", HeadingDirection=" + HeadingDirection +
                    ", CenterLattidue=" + CenterLattidue +
                    ", CenterLongitude=" + CenterLongitude +
                    ", CenterAltitude=" + CenterAltitude +
                    ", EntryDirection=" + EntryDirection +
                    '}';
        }
    }
}
