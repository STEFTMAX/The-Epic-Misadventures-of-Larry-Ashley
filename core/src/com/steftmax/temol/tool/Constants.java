package com.steftmax.temol.tool;

/**
 * @author pieter3457
 *
 */
public class Constants {
	private Constants() {}
	
	public static final float LARRYHEIGHT = 1.5f; // Meters
	public static final float SCALE = LARRYHEIGHT / 32f;// in meters per pixel
	public static final float PIXELSTOMETERS = SCALE;
	public static final float METERSTOPIXELS = 1f / SCALE; 
}
