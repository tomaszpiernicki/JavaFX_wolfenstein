package application;

import utils.Position;

public class BadGuy extends Character {

	Position playerPosition;

	public BadGuy(Position pos, Integer rot) {
		super(pos, rot, 50);
	}

	public Position getPlayerPosition() {
		return playerPosition;
	}

	public void setPlayerPosition(Position playerPosition) {
		this.playerPosition = playerPosition;
	}
}
