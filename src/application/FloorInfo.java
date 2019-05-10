package application;

import java.util.ArrayList;

import utils.BitmapUtils;

public class FloorInfo {

	final Integer floorNumber;
	final String floorName;

	public String getFloorName() {
		return floorName;
	}

	public Integer getFloorNumber() {
		return floorNumber;
	}

	int[][] levelMap = null;
	int startingX = 0, startingZ = 0;
	int sizeX = 0, sizeZ = 0;
	ArrayList<Integer> badGuysX = new ArrayList<>(), badGuysZ = new ArrayList<>();

	public int[][] getLevelMap() {
		return levelMap;
	}

	public void setLevelMap(int[][] levelMap) {
		this.levelMap = levelMap;
	}

	public ArrayList<Integer> getBadGuysX() {
		return badGuysX;
	}

	public void setBadGuysX(ArrayList<Integer> badGuysX) {
		this.badGuysX = badGuysX;
	}

	public ArrayList<Integer> getBadGuysZ() {
		return badGuysZ;
	}

	public void setBadGuysZ(ArrayList<Integer> badGuysZ) {
		this.badGuysZ = badGuysZ;
	}

	public void setStartingX(int startingX) {
		this.startingX = startingX;
	}

	public void setStartingZ(int startingZ) {
		this.startingZ = startingZ;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public void setSizeZ(int sizeZ) {
		this.sizeZ = sizeZ;
	}

	public FloorInfo(String ln, Integer fn) {
		this.floorName = ln;
		this.floorNumber = fn;
		BitmapUtils.readBitmapAsArray(this);
	}

	public int getAtIndex(int i, int j) {
		try {
			return levelMap[i][j];
		} catch (ArrayIndexOutOfBoundsException e) {
			return 0;
		} finally {
		}
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeZ() {
		return sizeZ;
	}

	public int getStartingX() {
		return startingX;
	}

	public int getStartingZ() {
		return startingZ;
	}
}
