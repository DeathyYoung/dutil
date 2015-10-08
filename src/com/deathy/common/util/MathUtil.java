package com.deathy.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MathUtil {
	/**
	 * 两点间的距离
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.abs(x1 - x2) * Math.abs(x1 - x2)
				+ Math.abs(y1 - y2) * Math.abs(y1 - y2));
	}

	/**
	 * 计算点a(x,y)的角度
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static double pointTotoDegrees(double x, double y) {
		return Math.toDegrees(Math.atan2(x, y));
	}

	/**
	 * 点在圆内
	 * 
	 * @param sx
	 * @param sy
	 * @param r
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean checkInRound(float sx, float sy, float r, float x,
			float y) {
		return Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y)) < r;
	}

	/**
	 * @Fields df2Digit
	 */
	public static final DecimalFormat df2Digit = new DecimalFormat("#0.00");

	/**
	 * @Title: get2Digit
	 * @param number
	 * @return String
	 */
	public static String get2Digit(double number) {
		return df2Digit.format(number);
	}

	/**
	 * 对double数据进行取精度.
	 * 
	 * @param value
	 *            double数据.
	 * @param scale
	 *            精度位数(保留的小数位数).
	 * @param roundingMode
	 *            精度取值方式.
	 * @return 精度计算后的数据.
	 */
	public static double round(double value, int scale, int roundingMode) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, roundingMode);
		double d = bd.doubleValue();
		bd = null;
		return d;
	}

	/* subIndex */
	private int subIndex;
	/* indexs */
	private int[] indexs;

	/**
	 * Title: getRandomDoubleArray
	 * 
	 * @see 随机生成num个和为sum的非负数
	 * @param num
	 *            num个数
	 * @return
	 */
	public double[] getRandomDoubleArray(int length, int maxFractionDigits,
			double sum) {
		randomIndex(length);
		double[] nums = new double[length];
		while (sum > 0) {
			double temp = (double) (int) (Math.random() * Math.pow(10,
					maxFractionDigits)) / Math.pow(10, maxFractionDigits);
			if (sum > temp) {
				nums[nextInt()] = temp;
				sum = sub(sum, temp);
			} else {
				nums[nextInt()] = sum;
				sum = 0;
			}
		}
		return nums;
	}

	/**
	 * Title: nextInt
	 * 
	 * @see 获取下一个序列数
	 * @return 下一个序列数
	 */
	public int nextInt() {
		return indexs[subIndex++];
	}

	/**
	 * Title: randomIndex
	 * 
	 * @see 随机化连续序列
	 * @param length
	 *            序列长度
	 */
	public void randomIndex(int length) {
		indexs = new int[length];
		subIndex = 0;
		for (int i = 0; i < length; i++) {
			indexs[i] = i;
		}
		for (int j = 0; j < length; j++) {
			int i1 = (int) (Math.random() * length);
			int i2 = (int) (Math.random() * length);
			int temp = indexs[i1];
			indexs[i1] = indexs[i2];
			indexs[i2] = temp;
		}
	}

	/**
	 * 两个Double数相加
	 */
	public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
		return v1.add(v2);
	}

	/**
	 * 两个Double数相加
	 */
	public static double add(double v1, double v2) {
		return add(new BigDecimal(v1), new BigDecimal(v2)).doubleValue();
	}

	/**
	 * 两个Double数相加
	 */
	public static Double add(Double v1, Double v2) {
		return new Double(add(v1.doubleValue(), v2.doubleValue()));
	}

	/**
	 * 两个Double数相加
	 */
	public static String add(String v1, String v2) {
		return add(new BigDecimal(v1), new BigDecimal(v2)).toString();
	}

	/**
	 * 两个Double数相减
	 */
	public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
		return v1.subtract(v2);
	}

	/**
	 * 两个Double数相减
	 */
	public static double sub(double v1, double v2) {
		return sub(new BigDecimal(v1), new BigDecimal(v2)).doubleValue();
	}

	/**
	 * 两个Double数相减
	 */
	public static Double sub(Double v1, Double v2) {
		return new Double(sub(v1.doubleValue(), v2.doubleValue()));
	}

	/**
	 * 两个Double数相减
	 */
	public static String sub(String v1, String v2) {
		return sub(new BigDecimal(v1), new BigDecimal(v2)).toString();
	}

	/**
	 * 两个Double数相乘
	 */
	public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
		return v1.multiply(v2);
	}

	/**
	 * 两个Double数相乘
	 */
	public static double mul(double v1, double v2) {
		return mul(new BigDecimal(v1), new BigDecimal(v2)).doubleValue();
	}

	/**
	 * 两个Double数相乘
	 */
	public static Double mul(Double v1, Double v2) {
		return new Double(mul(v1.doubleValue(), v2.doubleValue()));
	}

	/**
	 * 两个Double数相乘
	 */
	public static String mul(String v1, String v2) {
		return mul(new BigDecimal(v1), new BigDecimal(v2)).toString();
	}

	/**
	 * 求n次方
	 */
	public static BigDecimal pow(BigDecimal v1, int n) {
		BigDecimal value = new BigDecimal(1);
		for (int i = 1; i <= n; i++)
			value = mul(value, v1);
		return value;
	}

	/**
	 * 求n次方
	 */
	public static BigDecimal pow(double v1, int n) {
		return pow(new BigDecimal(v1), n);
	}

	/**
	 * 求n次方
	 */
	public static BigDecimal pow(Double v1, int n) {
		return pow(new BigDecimal(v1.doubleValue()), n);
	}

	/**
	 * 求n次方
	 */
	public static BigDecimal pow(String v1, int n) {
		return pow(new BigDecimal(v1), n);
	}

	/**
	 * 两个Double数相除
	 */
	public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
		return div(v1, v2, 2);
	}

	/**
	 * 两个Double数相除
	 */
	public static double div(double v1, double v2) {
		return div(new BigDecimal(v1), new BigDecimal(v2)).doubleValue();
	}

	/**
	 * 两个Double数相除
	 */
	public static Double div(Double v1, Double v2) {
		return new Double(div(v1.doubleValue(), v2.doubleValue()));
	}

	/**
	 * 两个Double数相除
	 */
	public static String div(String v1, String v2) {
		return div(new BigDecimal(v1), new BigDecimal(v2)).toString();
	}

	/**
	 * 两个Double数相除，并保留scale位小数
	 */
	public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
		if (scale < 0)
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 两个Double数相除，并保留scale位小数
	 */
	public static double div(double v1, double v2, int scale) {
		return div(new BigDecimal(v1), new BigDecimal(v2), scale).doubleValue();
	}

	/**
	 * 两个Double数相除
	 */
	public static Double div(Double v1, Double v2, int scale) {
		return new Double(div(v1.doubleValue(), v2.doubleValue(), scale));
	}

	/**
	 * 两个Double数相除，并保留scale位小数
	 */
	public static String div(String v1, String v2, int scale) {
		return div(new BigDecimal(v1), new BigDecimal(v2), scale).toString();
	}

	/**
	 * 并保留scale位小数
	 */
	public static BigDecimal getScale(BigDecimal v1, int scale) {
		return v1.setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 并保留scale位小数
	 */
	public static double getScale(double v1, int scale) {
		return getScale(new BigDecimal(v1), scale).doubleValue();
	}

	/**
	 * 并保留scale位小数
	 */
	public static Double getScale(Double v1, int scale) {
		return new Double(getScale(v1.doubleValue(), scale));
	}

	/**
	 * 并保留scale位小数
	 */
	public static String getScale(String v1, int scale) {
		return getScale(new BigDecimal(v1), scale).toString();
	}
}
