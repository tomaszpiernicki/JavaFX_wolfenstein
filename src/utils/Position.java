package utils;

import javafx.geometry.Point3D;

public class Position extends Point3D {

	public Position() {
		super(0.0, 0.0, 0.0);
	}

	public Position(double x, double z) {
		super(x, 0.0, z);
	}

	public Position(double x, double y, double z) {
		super(x, y, z);
	}

	public Position(Point3D add) {
		super(add.getX(), add.getY(), add.getZ());
	}

	public Position newIncrementedPosition(Position vector) {
		return new Position(super.add(vector));
	}

	public Integer getAngleOfLineBetweenTwoPoints(Position position) {
		double xDiff = position.getX() - this.getX();
		double yDiff = position.getZ() - this.getZ();
		return (int) Math.toDegrees(Math.atan2(xDiff, yDiff)) + 180;
	}

	public int resolveIndexX() {
		if (getX() < 0) {
			return -1;
		}
		return (int) (getX() / DimentionUtils.blockSize);
	}

	public int resolveIndexZ() {
		if (getZ() < 0) {
			return -1;
		}
		return (int) (getZ() / DimentionUtils.blockSize);
	}
}
