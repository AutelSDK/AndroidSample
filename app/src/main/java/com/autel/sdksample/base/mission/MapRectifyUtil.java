package com.autel.sdksample.base.mission;

public class MapRectifyUtil {

    private static Rectangle[] exclude;
    private static Rectangle[] region;

    static {
        Rectangle[] arrayOfRectangle1 = new Rectangle[6];
        arrayOfRectangle1[0] = new Rectangle(49.220399999999998D, 79.446200000000005D, 42.889899999999997D,
                96.329999999999998D);
        arrayOfRectangle1[1] = new Rectangle(54.141500000000001D, 109.6872D, 39.374200000000002D, 135.00020000000001D);
        arrayOfRectangle1[2] = new Rectangle(42.889899999999997D, 73.124600000000001D, 29.529699999999998D,
                124.143255D);
        arrayOfRectangle1[3] = new Rectangle(29.529699999999998D, 82.968400000000003D, 26.718599999999999D,
                97.035200000000003D);
        arrayOfRectangle1[4] = new Rectangle(29.529699999999998D, 97.025300000000001D, 20.414096000000001D,
                124.367395D);
        arrayOfRectangle1[5] = new Rectangle(20.414096000000001D, 107.975793D, 17.871542000000002D,
                111.74410399999999D);
        region = arrayOfRectangle1;
        Rectangle[] arrayOfRectangle2 = new Rectangle[6];
        arrayOfRectangle2[0] = new Rectangle(25.398623000000001D, 119.92126500000001D, 21.785005999999999D,
                122.497559D);
        arrayOfRectangle2[1] = new Rectangle(22.283999999999999D, 101.8652D, 20.098800000000001D, 106.66500000000001D);
        arrayOfRectangle2[2] = new Rectangle(21.542200000000001D, 106.4525D, 20.4878D, 108.051D);
        arrayOfRectangle2[3] = new Rectangle(55.817500000000003D, 109.03230000000001D, 50.325699999999998D, 119.127D);
        arrayOfRectangle2[4] = new Rectangle(55.817500000000003D, 127.4568D, 49.557400000000001D, 137.02269999999999D);
        arrayOfRectangle2[5] = new Rectangle(44.892200000000003D, 131.2662D, 42.569200000000002D, 137.02269999999999D);
        exclude = arrayOfRectangle2;
    }

    private static boolean InRectangle(Rectangle paramRectangle, AutelLatLng paramAutelLatLng) {
        if (null == paramAutelLatLng) {
            return false;
        }
        return (paramRectangle.West <= paramAutelLatLng.longitude)
                && (paramRectangle.East >= paramAutelLatLng.longitude)
                && (paramRectangle.North >= paramAutelLatLng.latitude)
                && (paramRectangle.South <= paramAutelLatLng.latitude);
    }

    public static boolean IsInsideChina(AutelLatLng autelLatLng) {

        boolean isInChina = false;
        for (Rectangle aRegion : region) {
            if (InRectangle(aRegion, autelLatLng)) {
                isInChina = true;
                for (Rectangle anExclude : exclude) {
                    if (InRectangle(anExclude, autelLatLng)) {
                        isInChina = false;
                    }
                }
            }
        }
        return isInChina;
    }

    private static AutelLatLng delta(AutelLatLng paramAutelLatLng) {

        double d1 = transformLat(paramAutelLatLng.longitude - 105.0D, paramAutelLatLng.latitude - 35.0D);
        double d2 = transformLon(paramAutelLatLng.longitude - 105.0D, paramAutelLatLng.latitude - 35.0D);
        double d3 = 3.141592653589793D * (paramAutelLatLng.latitude / 180.0D);
        double d4 = Math.sin(d3);
        double d5 = 1.0D - d4 * (0.006693421622965943D * d4);
        double d6 = Math.sqrt(d5);
        double d7 = 180.0D * d1 / (3.141592653589793D * (6378245.0D * (1.0D - 0.006693421622965943D) / (d5 * d6)));
        double d8 = 180.0D * d2 / (3.141592653589793D * (6378245.0D / d6 * Math.cos(d3)));
        return new AutelLatLng(d7, d8);
    }

    /**
     * 火星坐标到地球坐标
     *
     * @param paramAutelLatLng
     * @return
     */
    public static AutelLatLng gcj2wgs(AutelLatLng paramAutelLatLng) {
        if (!IsInsideChina(paramAutelLatLng))
            return paramAutelLatLng;
        AutelLatLng mAutelLatLng = delta(paramAutelLatLng);
        return new AutelLatLng(paramAutelLatLng.latitude - mAutelLatLng.latitude, paramAutelLatLng.longitude
                - mAutelLatLng.longitude);
    }

    /**
     * 地球坐标到火星坐标
     *
     * @param paramAutelLatLng
     * @return
     */
    public static AutelLatLng wgs2gcj(AutelLatLng paramAutelLatLng) {

        if (!IsInsideChina(paramAutelLatLng))
            return paramAutelLatLng;
        AutelLatLng localAutelLatLng = delta(paramAutelLatLng);
        return new AutelLatLng(paramAutelLatLng.latitude + localAutelLatLng.latitude, paramAutelLatLng.longitude
                + localAutelLatLng.longitude);
    }

    private static double transformLat(double paramDouble1, double paramDouble2) {

        return -100.0D
                + 2.0D
                * paramDouble1
                + 3.0D
                * paramDouble2
                + paramDouble2
                * (0.2D * paramDouble2)
                + paramDouble2
                * (0.1D * paramDouble1)
                + 0.2D
                * Math.sqrt(Math.abs(paramDouble1))
                + 2.0D
                * (20.0D * Math.sin(3.141592653589793D * (6.0D * paramDouble1)) + 20.0D * Math
                .sin(3.141592653589793D * (2.0D * paramDouble1)))
                / 3.0D
                + 2.0D
                * (20.0D * Math.sin(3.141592653589793D * paramDouble2) + 40.0D * Math
                .sin(3.141592653589793D * (paramDouble2 / 3.0D)))
                / 3.0D
                + 2.0D
                * (160.0D * Math.sin(3.141592653589793D * (paramDouble2 / 12.0D)) + 320.0D * Math
                .sin(3.141592653589793D * paramDouble2 / 30.0D)) / 3.0D;
    }

    private static double transformLon(double paramDouble1, double paramDouble2) {

        return 300.0D
                + paramDouble1
                + 2.0D
                * paramDouble2
                + paramDouble1
                * (0.1D * paramDouble1)
                + paramDouble2
                * (0.1D * paramDouble1)
                + 0.1D
                * Math.sqrt(Math.abs(paramDouble1))
                + 2.0D
                * (20.0D * Math.sin(3.141592653589793D * (6.0D * paramDouble1)) + 20.0D * Math
                .sin(3.141592653589793D * (2.0D * paramDouble1)))
                / 3.0D
                + 2.0D
                * (20.0D * Math.sin(3.141592653589793D * paramDouble1) + 40.0D * Math
                .sin(3.141592653589793D * (paramDouble1 / 3.0D)))
                / 3.0D
                + 2.0D
                * (150.0D * Math.sin(3.141592653589793D * (paramDouble1 / 12.0D)) + 300.0D * Math
                .sin(3.141592653589793D * (paramDouble1 / 30.0D))) / 3.0D;
    }

    private static class Rectangle {

        public double East;
        public double North;
        public double South;
        public double West;

        public Rectangle(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {

            this.West = Math.min(paramDouble2, paramDouble4);
            this.North = Math.max(paramDouble1, paramDouble3);
            this.East = Math.max(paramDouble2, paramDouble4);
            this.South = Math.min(paramDouble1, paramDouble3);
        }
    }

}
