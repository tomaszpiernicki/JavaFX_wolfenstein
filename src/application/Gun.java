package application;

import utils.DimentionUtils;

public class Gun {

	Double distance;
	Integer damage, ammo;
	Gun()
	{
		distance = DimentionUtils.blockSize; //* 4;
		damage = 40;
		ammo = 5;
	}
	
	Gun(Double distance, Integer damage, Integer ammo)
	{
		this.distance = distance;
		this.damage = damage;
		this.ammo = ammo;
	}
	
	public int getAmmo() {
		return ammo;
	}
	public Double getDistance() {
		return distance;
	}
	public Integer getDamage() {
		return damage;
	}	
	
	public void spareBullet()
	{
		ammo--;
	}
}