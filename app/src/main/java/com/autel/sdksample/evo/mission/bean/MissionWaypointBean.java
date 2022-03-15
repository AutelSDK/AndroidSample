package com.autel.sdksample.evo.mission.bean;

import java.util.List;

/**
 * Created by A15387 on 2017/11/16.
 */

public class MissionWaypointBean {

    /**
     * method : FMUMissionData
     * params : {"MissionId":0,"NumberOfWaypoints":2,"FinishAction":1,"ObstacleAvoidanceMode":1,"ObstacleAvoidanceTimeout":1,"LostControlAction":1,"Waypoints":[{"MissionId":0,"WaypointId":0,"WaypointType":0,"Latitude":1717986918,"Longitude":1717986918,"Altitude":1717986918,"Speed":10,"FocusLatitude":1717986918,"FocusLongitude":1717986918,"FocusAltitude":1717986918,"BeizerParameter":0,"AltitudePriorityMode":1,"HeadingMode":1,"UserdefinedHeading":1,"CameraPitch":1,"CameraYaw":1,"NumberOfActions":2,"Actions":[{"MissionId":0,"WaypointId":0,"ActionId":0,"ActionType":1,"ActionTimeout":1,"ActionParameters":[1717986918,1717986918,1717986918,1717986918,1717986918,1717986918,1717986918]},{"MissionId":0,"WaypointId":0,"ActionId":1,"ActionType":1,"ActionTimeout":1,"ActionParameters":[1717986918,1717986918,1717986918,1717986918,1717986918,1717986918,1717986918]}]},{"MissionId":0,"WaypointId":1,"WaypointType":1,"Latitude":1717986918,"Longitude":1717986918,"Altitude":1717986918,"Speed":10,"FocusLatitude":1717986918,"FocusLongitude":1717986918,"FocusAltitude":1717986918,"BeizerParameter":1,"AltitudePriorityMode":1,"HeadingMode":1,"UserdefinedHeading":1,"CameraPitch":1,"CameraYaw":1,"NumberOfActions":2,"Actions":[{"MissionId":0,"WaypointId":1,"ActionId":0,"ActionType":1,"ActionTimeout":1,"ActionParameters":[1717986918,1717986918,1717986918,1717986918,1717986918,1717986918,1717986918]},{"MissionId":0,"WaypointId":1,"ActionId":1,"ActionType":1,"ActionTimeout":10,"ActionParameters":[1717986918,1717986918,1717986918,1717986918,1717986918,1717986918,1717986918]}],"WaypointTypeData":{"MissionId":0,"WaypointId":1,"Speed":10,"Radius":10,"Cycles":10,"RemainDegree":10,"RotateDirection":1,"HeadingDirection":1,"CenterLattidue":1717986918,"CenterLongitude":1717986918,"CenterAltitude":1717986918,"EntryDirection":1717986918}}]}
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
         * MissionId : 0
         * NumberOfWaypoints : 2
         * FinishAction : 1
         * ObstacleAvoidanceMode : 1
         * ObstacleAvoidanceTimeout : 1
         * LostControlAction : 1
         * Waypoints : [{"MissionId":0,"WaypointId":0,"WaypointType":0,"Latitude":1717986918,"Longitude":1717986918,"Altitude":1717986918,"Speed":10,"FocusLatitude":1717986918,"FocusLongitude":1717986918,"FocusAltitude":1717986918,"BeizerParameter":0,"AltitudePriorityMode":1,"HeadingMode":1,"UserdefinedHeading":1,"CameraPitch":1,"CameraYaw":1,"NumberOfActions":2,"Actions":[{"MissionId":0,"WaypointId":0,"ActionId":0,"ActionType":1,"ActionTimeout":1,"ActionParameters":[1717986918,1717986918,1717986918,1717986918,1717986918,1717986918,1717986918]},{"MissionId":0,"WaypointId":0,"ActionId":1,"ActionType":1,"ActionTimeout":1,"ActionParameters":[1717986918,1717986918,1717986918,1717986918,1717986918,1717986918,1717986918]}]},{"MissionId":0,"WaypointId":1,"WaypointType":1,"Latitude":1717986918,"Longitude":1717986918,"Altitude":1717986918,"Speed":10,"FocusLatitude":1717986918,"FocusLongitude":1717986918,"FocusAltitude":1717986918,"BeizerParameter":1,"AltitudePriorityMode":1,"HeadingMode":1,"UserdefinedHeading":1,"CameraPitch":1,"CameraYaw":1,"NumberOfActions":2,"Actions":[{"MissionId":0,"WaypointId":1,"ActionId":0,"ActionType":1,"ActionTimeout":1,"ActionParameters":[1717986918,1717986918,1717986918,1717986918,1717986918,1717986918,1717986918]},{"MissionId":0,"WaypointId":1,"ActionId":1,"ActionType":1,"ActionTimeout":10,"ActionParameters":[1717986918,1717986918,1717986918,1717986918,1717986918,1717986918,1717986918]}],"WaypointTypeData":{"MissionId":0,"WaypointId":1,"Speed":10,"Radius":10,"Cycles":10,"RemainDegree":10,"RotateDirection":1,"HeadingDirection":1,"CenterLattidue":1717986918,"CenterLongitude":1717986918,"CenterAltitude":1717986918,"EntryDirection":1717986918}}]
         */

        private int MissionId;
        private int NumberOfWaypoints;
        private int FinishAction;
        private int ObstacleAvoidanceMode;
        private int ObstacleAvoidanceTimeout;
        private int LostControlAction;
        private List<WaypointsBean> Waypoints;

        public int getMissionId() {
            return MissionId;
        }

        public void setMissionId(int MissionId) {
            this.MissionId = MissionId;
        }

        public int getNumberOfWaypoints() {
            return NumberOfWaypoints;
        }

        public void setNumberOfWaypoints(int NumberOfWaypoints) {
            this.NumberOfWaypoints = NumberOfWaypoints;
        }

        public int getFinishAction() {
            return FinishAction;
        }

        public void setFinishAction(int FinishAction) {
            this.FinishAction = FinishAction;
        }

        public int getObstacleAvoidanceMode() {
            return ObstacleAvoidanceMode;
        }

        public void setObstacleAvoidanceMode(int ObstacleAvoidanceMode) {
            this.ObstacleAvoidanceMode = ObstacleAvoidanceMode;
        }

        public int getObstacleAvoidanceTimeout() {
            return ObstacleAvoidanceTimeout;
        }

        public void setObstacleAvoidanceTimeout(int ObstacleAvoidanceTimeout) {
            this.ObstacleAvoidanceTimeout = ObstacleAvoidanceTimeout;
        }

        public int getLostControlAction() {
            return LostControlAction;
        }

        public void setLostControlAction(int LostControlAction) {
            this.LostControlAction = LostControlAction;
        }

        public List<WaypointsBean> getWaypoints() {
            return Waypoints;
        }

        public void setWaypoints(List<WaypointsBean> Waypoints) {
            this.Waypoints = Waypoints;
        }

        public static class WaypointsBean {
            /**
             * MissionId : 0
             * WaypointId : 0
             * WaypointType : 0
             * Latitude : 1717986918
             * Longitude : 1717986918
             * Altitude : 1717986918
             * Speed : 10
             * FocusLatitude : 1717986918
             * FocusLongitude : 1717986918
             * FocusAltitude : 1717986918
             * BeizerParameter : 0
             * AltitudePriorityMode : 1
             * HeadingMode : 1
             * UserdefinedHeading : 1
             * CameraPitch : 1
             * CameraYaw : 1
             * NumberOfActions : 2
             * Actions : [{"MissionId":0,"WaypointId":0,"ActionId":0,"ActionType":1,"ActionTimeout":1,"ActionParameters":[1717986918,1717986918,1717986918,1717986918,1717986918,1717986918,1717986918]},{"MissionId":0,"WaypointId":0,"ActionId":1,"ActionType":1,"ActionTimeout":1,"ActionParameters":[1717986918,1717986918,1717986918,1717986918,1717986918,1717986918,1717986918]}]
             * WaypointTypeData : {"MissionId":0,"WaypointId":1,"Speed":10,"Radius":10,"Cycles":10,"RemainDegree":10,"RotateDirection":1,"HeadingDirection":1,"CenterLattidue":1717986918,"CenterLongitude":1717986918,"CenterAltitude":1717986918,"EntryDirection":1717986918}
             */

            private int MissionId;
            private int WaypointId;
            private int WaypointType;
            private int Latitude;
            private int Longitude;
            private int Altitude;
            private int Speed;
            private int FocusLatitude;
            private int FocusLongitude;
            private int FocusAltitude;
            private int BeizerParameter;
            private int AltitudePriorityMode;
            private int HeadingMode;
            private int UserdefinedHeading;
            private int CameraPitch;
            private int CameraYaw;
            private int NumberOfActions;
            private WaypointTypeDataBean WaypointTypeData;
            private List<ActionsBean> Actions;

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

            public int getWaypointType() {
                return WaypointType;
            }

            public void setWaypointType(int WaypointType) {
                this.WaypointType = WaypointType;
            }

            public int getLatitude() {
                return Latitude;
            }

            public void setLatitude(int Latitude) {
                this.Latitude = Latitude;
            }

            public int getLongitude() {
                return Longitude;
            }

            public void setLongitude(int Longitude) {
                this.Longitude = Longitude;
            }

            public int getAltitude() {
                return Altitude;
            }

            public void setAltitude(int Altitude) {
                this.Altitude = Altitude;
            }

            public int getSpeed() {
                return Speed;
            }

            public void setSpeed(int Speed) {
                this.Speed = Speed;
            }

            public int getFocusLatitude() {
                return FocusLatitude;
            }

            public void setFocusLatitude(int FocusLatitude) {
                this.FocusLatitude = FocusLatitude;
            }

            public int getFocusLongitude() {
                return FocusLongitude;
            }

            public void setFocusLongitude(int FocusLongitude) {
                this.FocusLongitude = FocusLongitude;
            }

            public int getFocusAltitude() {
                return FocusAltitude;
            }

            public void setFocusAltitude(int FocusAltitude) {
                this.FocusAltitude = FocusAltitude;
            }

            public int getBeizerParameter() {
                return BeizerParameter;
            }

            public void setBeizerParameter(int BeizerParameter) {
                this.BeizerParameter = BeizerParameter;
            }

            public int getAltitudePriorityMode() {
                return AltitudePriorityMode;
            }

            public void setAltitudePriorityMode(int AltitudePriorityMode) {
                this.AltitudePriorityMode = AltitudePriorityMode;
            }

            public int getHeadingMode() {
                return HeadingMode;
            }

            public void setHeadingMode(int HeadingMode) {
                this.HeadingMode = HeadingMode;
            }

            public int getUserdefinedHeading() {
                return UserdefinedHeading;
            }

            public void setUserdefinedHeading(int UserdefinedHeading) {
                this.UserdefinedHeading = UserdefinedHeading;
            }

            public int getCameraPitch() {
                return CameraPitch;
            }

            public void setCameraPitch(int CameraPitch) {
                this.CameraPitch = CameraPitch;
            }

            public int getCameraYaw() {
                return CameraYaw;
            }

            public void setCameraYaw(int CameraYaw) {
                this.CameraYaw = CameraYaw;
            }

            public int getNumberOfActions() {
                return NumberOfActions;
            }

            public void setNumberOfActions(int NumberOfActions) {
                this.NumberOfActions = NumberOfActions;
            }

            public WaypointTypeDataBean getWaypointTypeData() {
                return WaypointTypeData;
            }

            public void setWaypointTypeData(WaypointTypeDataBean WaypointTypeData) {
                this.WaypointTypeData = WaypointTypeData;
            }

            public List<ActionsBean> getActions() {
                return Actions;
            }

            public void setActions(List<ActionsBean> Actions) {
                this.Actions = Actions;
            }

            public static class WaypointTypeDataBean {
                /**
                 * MissionId : 0
                 * WaypointId : 1
                 * Speed : 10
                 * Radius : 10
                 * Cycles : 10
                 * RemainDegree : 10
                 * RotateDirection : 1
                 * HeadingDirection : 1
                 * CenterLattidue : 1717986918
                 * CenterLongitude : 1717986918
                 * CenterAltitude : 1717986918
                 * EntryDirection : 1717986918
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
            }

            public static class ActionsBean {
                /**
                 * MissionId : 0
                 * WaypointId : 0
                 * ActionId : 0
                 * ActionType : 1
                 * ActionTimeout : 1
                 * ActionParameters : [1717986918,1717986918,1717986918,1717986918,1717986918,1717986918,1717986918]
                 */

                private int MissionId;
                private int WaypointId;
                private int ActionId;
                private int ActionType;
                private int ActionTimeout;
                private List<Integer> ActionParameters;

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

                public int getActionId() {
                    return ActionId;
                }

                public void setActionId(int ActionId) {
                    this.ActionId = ActionId;
                }

                public int getActionType() {
                    return ActionType;
                }

                public void setActionType(int ActionType) {
                    this.ActionType = ActionType;
                }

                public int getActionTimeout() {
                    return ActionTimeout;
                }

                public void setActionTimeout(int ActionTimeout) {
                    this.ActionTimeout = ActionTimeout;
                }

                public List<Integer> getActionParameters() {
                    return ActionParameters;
                }

                public void setActionParameters(List<Integer> ActionParameters) {
                    this.ActionParameters = ActionParameters;
                }
            }
        }
    }
}
