package application;

import utils.Position;

public class Player extends Character {

	Integer points = 0;
	Integer lives = 3;
	
	public Player(double x, double z) {
		super(new Position(x, z), 0, new PlayerGun());
		hp = 200;
	}
	
	public Integer getLives() {
		return lives;
	}

	public void setLives(Integer lives) {
		this.lives = lives;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getPoints() {
		return points;
	}
}
