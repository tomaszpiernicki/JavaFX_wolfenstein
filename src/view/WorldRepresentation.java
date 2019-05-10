package view;

import application.FloorInfo;
import javafx.scene.Group;

public class WorldRepresentation extends Group {

	FloorInfo levelInfo;

	public WorldRepresentation(FloorInfo li) {
		this.levelInfo = li;
		for (int i = 0; i < levelInfo.getSizeZ(); i++) {
			for (int j = 0; j < levelInfo.getSizeX(); j++) {
				if (levelInfo.getAtIndex(i, j) == 1) {
					Cage cage = getCageWithWalls(j, i);
					this.getChildren().add(cage);
				}
			}
		}
	}

	Cage getCageWithWalls(int zIndex, int xIndex) {
		Cage cage = new Cage(zIndex, xIndex);
		if (levelInfo.getAtIndex(xIndex - 1, zIndex) == 0) {
			cage.addLeftWall();
		}
		if (levelInfo.getAtIndex(xIndex + 1, zIndex) == 0) {
			cage.addRightWall();
		}
		if (levelInfo.getAtIndex(xIndex, zIndex - 1) == 0) {
			cage.addFrontWall();
		}
		if (levelInfo.getAtIndex(xIndex, zIndex + 1) == 0) {
			cage.addBackWall();
		}
		cage.finalaze();
		return cage;
	}
}
