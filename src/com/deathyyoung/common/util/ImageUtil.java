package com.deathyyoung.common.util;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageUtil {

	/**
	 * 图片裁剪通用接口
	 */
	public static void cutImage(String src, String dest, int x, int y, int w, int h) throws IOException {
		String formatName = src.substring(src.lastIndexOf(".") + 1).toLowerCase();
		Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(formatName);
		ImageReader reader = (ImageReader) iterator.next();
		InputStream in = new FileInputStream(src);
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle rect = new Rectangle(x, y, w, h);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, formatName, new File(dest));

	}

	/**
	 * 图片缩放
	 */
	public static void zoomImage(String src, String dest, int w, int h) throws Exception {
		double wr = 0, hr = 0;
		File srcFile = new File(src);
		File destFile = new File(dest);
		BufferedImage bi = ImageIO.read(srcFile);
		Image Itemp = bi.getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH);
		wr = w * 1.0 / bi.getWidth();
		hr = h * 1.0 / bi.getHeight();
		AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
		Itemp = ato.filter(bi, null);
		try {
			ImageIO.write((BufferedImage) Itemp, dest.substring(dest.lastIndexOf(".") + 1), destFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	* <p> 剪切并缩放
	*
	* @param src
	* @param dest
	* @param cutX
	* @param cutY
	* @param cutW
	* @param cutH
	* @param zoomW
	* @param zoomH
	* @throws IOException
	*/ 
	public static void cutThenZoomImage(String src, String dest, int cutX, int cutY, int cutW, int cutH, int zoomW,
			int zoomH) throws IOException {
		String formatName = src.substring(src.lastIndexOf(".") + 1).toLowerCase();
		Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(formatName);
		ImageReader reader = (ImageReader) iterator.next();
		InputStream in = new FileInputStream(src);
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle rect = new Rectangle(cutX, cutY, cutW, cutH);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		////////////////////////
		Image Itemp = bi.getScaledInstance(zoomW, zoomH, BufferedImage.SCALE_SMOOTH);
		double wr = zoomW * 1.0 / cutW;
		double hr = zoomH * 1.0 / cutH;
		AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
		Itemp = ato.filter(bi, null);
		File destFile = new File(dest);
		try {
			ImageIO.write((BufferedImage) Itemp, dest.substring(dest.lastIndexOf(".") + 1), destFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
