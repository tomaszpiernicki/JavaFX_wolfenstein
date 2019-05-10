package application;

import utils.DimentionUtils;

public class PlayerGun extends Gun {
	PlayerGun(){
	super(DimentionUtils.blockSize * 4, 80, 999);
	}
}