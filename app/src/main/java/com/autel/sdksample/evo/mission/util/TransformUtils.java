package com.autel.sdksample.evo.mission.util;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by A13087 on 2016/5/20.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TransformUtils {


	private static final double METER2MILE = 1609.344;
	private static final double FEET2METERUNIT = 0.3048;


	public static boolean isEnUnit() {

		return getUnitFlag() == 2;
	}

	public static int getUnitFlag() {

		return 1;
	}

	/**
	 * 摄氏转华氏
	 *
	 * @param centigrade
	 * @return
	 */
	public static double centigrade2Fahrenheit(double centigrade) {
		BigDecimal bg = new BigDecimal(32 + centigrade * 1.8);
		BigDecimal result = bg.setScale(1, BigDecimal.ROUND_HALF_DOWN);
		return result.doubleValue();
	}


	public static double fahrenheit2Centigrade(double fahrenheit){
		BigDecimal bg = new BigDecimal((fahrenheit - 32) / 1.8);
		BigDecimal result = bg.setScale(1, BigDecimal.ROUND_HALF_DOWN);
		return result.doubleValue();

	}


	public static String getTemperatureUnit(){
		return isEnUnit() ? "℉" : "℃";
	}

	public static String getCentigradeUnit() {
		return "℃";
	}

	public static String getFahrenheitUnit() {
		return "℉";
	}

	public static double doubleFormat(double mps) {
		BigDecimal bg = new BigDecimal(mps);
		BigDecimal result = bg.setScale(1, BigDecimal.ROUND_HALF_DOWN);
		return result.doubleValue();
	}

	public static double doubleFormat(double mps , int value){
		BigDecimal bg = new BigDecimal(mps);
		BigDecimal result = bg.setScale(value, BigDecimal.ROUND_HALF_DOWN);
		return result.doubleValue();
	}

	public static String getAngleUnit() {

		return "°";

	}

	/**
	 * 距离计算长度转换,大于1000米会进位
	 *
	 * @param lenMeter
	 * @return
	 */
	public static String getDistanceChangeUnit(double lenMeter) {

		boolean unitFlag = (getUnitFlag() == 2);
		if (lenMeter >= 1000) { // 1km
			float dis = (float) lenMeter;
			DecimalFormat fNum = new DecimalFormat("##0.00");
			if (unitFlag) {
				dis = (float) m2mile(dis);
			} else {
				dis = dis / 1000;
			}
			return fNum.format(dis);
		} else {// 1000M以下
			float dis = (float) lenMeter;
			if (unitFlag) {
				dis = (float) meter2feet(dis,1);
			}
			DecimalFormat fNum = new DecimalFormat("##0.0");
			return fNum.format(dis);
		}
	}

	public static double getAltitude(double lenMeter){
		boolean unitFlag = (getUnitFlag() == 2);
		if (lenMeter >= 1000) { // 1km
			float dis = (float) lenMeter;
			if (unitFlag) {
				dis = (float) m2mile(dis);
			} else {
				dis = dis / 1000;
			}
			BigDecimal bigDecimal = new BigDecimal(dis);

			return bigDecimal.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
		} else {// 1000M以下
			float dis = (float) lenMeter;
			if (unitFlag) {
				dis = (float) meter2feet(dis,1);
			}
			BigDecimal bigDecimal = new BigDecimal(dis);
			return bigDecimal.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	}


	public static String getDistanceIntValueChangeUnit(double lenMeter) {

		boolean unitFlag = (getUnitFlag() == 2);
		if (lenMeter >= 1000) { // 1km
			float dis = (float) lenMeter;
			DecimalFormat fnum = new DecimalFormat("##0");
			if (unitFlag) {
				dis = (float) m2mile(dis);
			} else {
				dis = dis / 1000;
			}
			return fnum.format(dis);
		} else {// 1000M以下
			float dis = (float) lenMeter;
			if (unitFlag) {
				dis = (float) meter2feet(dis,1);
			}
			DecimalFormat fnum = new DecimalFormat("##0");
			return fnum.format(dis);
		}

	}

	public static String getSpeed(double speed) {
		int flag = getUnitFlag();
		DecimalFormat fNum = new DecimalFormat("##0.0");
		if (flag == 2) {
			speed = mps2mph(speed);
		}else if(flag == 0){
			speed = mps2kmph(speed);
		}
		return fNum.format(speed);
	}

	public static double mps2mph(double meter) {
		BigDecimal bg = new BigDecimal(meter * 2.2369);
		BigDecimal result = bg.setScale(1, BigDecimal.ROUND_HALF_DOWN);

		return result.doubleValue();
	}

	public static double mps2kmph(double meter){
        BigDecimal bg = new BigDecimal(meter * 3.6);
        BigDecimal result = bg.setScale(1,BigDecimal.ROUND_HALF_DOWN);
        return result.doubleValue();
    }


	public static double mph2mps(double mph){
		BigDecimal bg = new BigDecimal(mph / 2.2369);
		BigDecimal result = bg.setScale(1, BigDecimal.ROUND_HALF_DOWN);
		return bg.doubleValue();
	}

	public static double kmph2mps(double kmph){
		BigDecimal bg = new BigDecimal(kmph / 3.6);
		BigDecimal result = bg.setScale(1,BigDecimal.ROUND_HALF_DOWN);
		return result.doubleValue();
	}


	// 速度单位
	public static String getSpeedUnitStrEn() {
		switch (getUnitFlag()){
			case 0:
				return "km/h";
			case 1:
			    return "m/s";
			case 2:
				return "mph";
		}
		return "";
	}

	/**
	 * 米转成英里
	 */
	public static double m2mile(double meter) {

		return meter / METER2MILE;
	}

	/**
	 * 米转 成英尺
	 *
	 * @param meter
	 * @return
	 */
	public static double meter2feet(double meter,int decimalNum) {
		BigDecimal bg = new BigDecimal(meter / FEET2METERUNIT);
		BigDecimal result = bg.setScale(decimalNum, BigDecimal.ROUND_HALF_UP);
		return decimalNum == 0 ? result.intValue() : result.doubleValue();
	}


	public static double feet2meter(double feet, int decimalNum){
		BigDecimal bg = new BigDecimal(feet * FEET2METERUNIT);
		BigDecimal result = bg.setScale(decimalNum, BigDecimal.ROUND_HALF_UP);
		return decimalNum == 0 ? result.intValue() : result.doubleValue();
	}

    /**
     *
     * @param lenMeter  传入数值m
     * @param decimalNum  1和2 保留位数不一  2 用于飞行记录转换
     * @return
     */
	public static String getLittleDistanceNoChangeUnit(double lenMeter,int decimalNum) {

		boolean unitFlag = (getUnitFlag() == 2);
        DecimalFormat fNum = new DecimalFormat(decimalNum == 1 ? "##0.00" : "##0.0");
        float dis = (float) lenMeter;
        if(unitFlag){
            if(dis < 304){
                return new DecimalFormat("##0.0").format((float) meter2feet(dis,2));
            }else {
                return fNum.format((float) m2mile(dis));
            }
        }else {
            if(dis >= 1000){
                return fNum.format(dis / 1000);
            }else {
                return new DecimalFormat("##0.0").format(dis);
            }
        }
	}

	/**
	 * 将传入的距离按1000M进行区分
	 *
	 * @param lenMeter
	 * @return
	 */
	public static String getUnitChangedByDistance(double lenMeter) {
		if (lenMeter >= 1000) { // 1km
			return getUnitKMstrEn();
		} else {
			return getUnitMeterStrEn();
		}
	}

    /**
     * 将传入的距离按公英制进行区分
     *
     * @param lenMeter
     * @return
     */
    public static String changeRangeUnitForUnitFlag(double lenMeter) {
        boolean unitFlag = (getUnitFlag() == 2);
        if (unitFlag) {
            if(lenMeter < 304){
                return "ft";
            }else {
                return "mi";
            }
        } else {
            if(lenMeter < 1000){
                return "m";
            }else {
                return "km";
            }
        }
    }

	private static String getUnitKMstrEn() {

		return isEnUnit() ? "mi" : "km";
	}

	// 长度单位 M/FT
	public static String getUnitMeterStrEn() {

		return isEnUnit()  ? "ft" : "m";
	}


	/**
	 * 保留指定的小数位数
	 * @param value 当前值
	 * @param count 位数
	 * @return
	 */
	public static float getFloatValue(double value, int count){
		BigDecimal format  = new BigDecimal(value);
		return format.setScale(count,BigDecimal.ROUND_HALF_UP).floatValue();
	}

    /**
     * 转换保留特定位数
     * @param value
     * @param format  如"#0.000"保留三位小数
     * @return
     */

    public static String getDecimalFormatValue(double value,String format){
        DecimalFormat fNum = new DecimalFormat(format);
         return fNum.format(value);
    }

	public static String formatData(long byteSize){
        String unitStr;
        int count = 0;
		while (byteSize > 1024){
            byteSize = byteSize / 1024;
            count ++;
        }

        switch (count){
            case 0:
                unitStr = "B";
                break;
            case 1:
                unitStr = "KB";
                break;
            case 2:
                unitStr = "MB";
                break;
            case 3:
                unitStr = "GB";
                break;
            case 4:
                unitStr = "TB";
                break;
            case 5:
                unitStr = "PB";
                break;
            default:
                unitStr = "B";
                break;
        }

        return (int)byteSize + unitStr;
	}

	public static String getTimeData(long longTime){
		DateFormat dateFormat = DateFormat.getDateTimeInstance();
		Date date = new Date(longTime);
		return dateFormat.format(date);
	}
}
