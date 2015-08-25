package com.ZLHW.common.JBPM;

import java.util.HashMap;
import java.util.Map;

public class ImgLocate {
	
	public ImgLocate(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	private int x;
	private int y;
	private int width;
	private int height;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public Map toHashMap(){
		Map map=new HashMap();
		map.put("x", x);
		map.put("y", y);
		map.put("width", width);
		map.put("height", height);
		return map;
		
	}

}
