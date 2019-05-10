package application;

import utils.Position;

public class Character {

	Position position;
	Integer rotation;
	Integer radius;
	Gun pistol;
	Integer hp;

	public Gun getPistol() {
		return pistol;
	}

	public Integer getRadius() {
		return radius;
	}

	public Character(Position pos, Integer rot) {
		this.position = pos;
		this.rotation = rot;
		this.radius = 140;
		this.hp = 100;
		this.pistol = new Gun();
	}

	public Character(Position pos, Integer rot, Gun gun) {
		this.position = pos;
		this.rotation = rot;
		this.radius = 140;
		this.hp = 100;
		this.pistol = gun;
	}

	public Integer getHp() {
		return hp;
	}

	public Character(Position pos, Integer rot, Integer rad) {
		this.position = pos;
		this.rotation = rot;
		this.hp = 100;
		this.radius = rad;
		this.pistol = new Gun();
	}

	public void move(Position vector) {
		this.position = this.position.newIncrementedPosition(vector);
	}

	public void rotate(double angle) {
		rotation += new Integer((int) angle);
		if (rotation < 0) {
			rotation += 360;
		}
		rotation %= 360;
	}

	public Position getPosition() {
		return position;
	}

	public Integer getRotation() {
		return rotation;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public boolean hitAndDead(int damage) {
		this.hp -= damage;
		if (hp <= 0) {
			return true;
		}
		return false;
	}
}
