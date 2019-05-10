package view;

import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import utils.BitmapUtils;
import utils.DimentionUtils;

public class Cage extends Group {

	static final Color COLOR = Color.DARKGRAY;

	int blockIndexX;
	int blockIndexZ;

	double realPositionX;
	double realPositionZ;

	AmbientLight light;
	Box floor;
	Box ceiling;
	Box frontWall;
	Box backWall;
	Box leftWall;
	Box rightWall;

	public Cage(int i, int j) {
		this.blockIndexX = j;
		this.blockIndexZ = i;

		realPositionX = this.blockIndexX * DimentionUtils.blockSize;
		realPositionZ = this.blockIndexZ * DimentionUtils.blockSize;

		floor = createBox(DimentionUtils.blockSize, DimentionUtils.blockBorder, DimentionUtils.blockSize, "floor");
		floor.setTranslateY(DimentionUtils.blockSize/2);

		ceiling = createBox(DimentionUtils.blockSize, DimentionUtils.blockBorder, DimentionUtils.blockSize, "floor");
		ceiling.setTranslateY(-DimentionUtils.blockSize/2);

		light = new AmbientLight();
		light.setTranslateX(100);
		light.setTranslateZ(100);

		light.setColor(Color.GREY);
		this.getChildren().addAll(light, floor, ceiling);
	}

	public void finalaze() {
		this.setTranslateX(this.realPositionX + DimentionUtils.blockSize / 2);
		this.setTranslateZ(this.realPositionZ + DimentionUtils.blockSize / 2);
	}

	public void addLeftWall() {
		leftWall = createBox(DimentionUtils.blockBorder, DimentionUtils.blockSize, DimentionUtils.blockSize,
				"wall_blue");
		leftWall.setTranslateX(-DimentionUtils.blockSize / 2);
		this.getChildren().add(leftWall);
	}

	public void addRightWall() {
		rightWall = createBox(DimentionUtils.blockBorder, DimentionUtils.blockSize, DimentionUtils.blockSize,
				"wall_blue");
		rightWall.setTranslateX(DimentionUtils.blockSize / 2);
		this.getChildren().add(rightWall);
	}

	public void addBackWall() {
		backWall = createBox(DimentionUtils.blockSize, DimentionUtils.blockSize, DimentionUtils.blockBorder,
				"wall_blue");
		backWall.setTranslateZ(DimentionUtils.blockSize / 2);
		this.getChildren().add(backWall);
	}

	public void addFrontWall() {
		frontWall = createBox(DimentionUtils.blockSize, DimentionUtils.blockSize, DimentionUtils.blockBorder,
				"wall_blue");
		frontWall.setTranslateZ(-DimentionUtils.blockSize / 2);
		this.getChildren().add(frontWall);
	}

	private Box createBox(double w, double h, double d, String type) {
		Box b = new Box(w, h, d);
		b.setMaterial(prepareTexture(type));
		return b;
	}

	private PhongMaterial prepareTexture(String type) {
		PhongMaterial material = new PhongMaterial();

		if (type == null) {
			material.setDiffuseColor(Color.DARKGREY);
			return material;
		}

		switch (type) {
		case "floor":
			material.setDiffuseMap(BitmapUtils.getBitmap("floor.gif"));
			break;
		case "wall_blue":
			material.setDiffuseMap(BitmapUtils.getBitmap("wall_blue.gif"));
			break;
		}
		return material;
	}

	public double getRealPositionX() {
		return realPositionX;
	}

	public void setRealPositionX(double realPositionX) {
		this.realPositionX = realPositionX;
	}

	public double getRealPositionZ() {
		return realPositionZ;
	}

	public void setRealPositionZ(double realPositionZ) {
		this.realPositionZ = realPositionZ;
	}

}
