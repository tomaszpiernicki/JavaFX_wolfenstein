package view;

import application.Player;
import javafx.animation.Animation;
import javafx.scene.Group;
import javafx.util.Duration;
import utils.CharacterActionType;

public class PlayerRepresentation extends CharacterRepresentation {

	PerspectiveCameraWrapper camera;
	final Hud hud;

	public PlayerRepresentation(Player player, Integer floorNumer) {
		super(new Group());

		hud = new Hud(floorNumer, player.getPoints(), player.getLives(), player.getHp(), player.getPistol().getAmmo());

		camera = new PerspectiveCameraWrapper();
		((Group) getNode()).getChildren().addAll(camera);

		updatePositionAndRotation(player);

		addAnimations();

		super.animations.get(CharacterActionType.IDLE).play();
	}

	public PerspectiveCameraWrapper getPc() {
		return camera;
	}

	public void setPc(PerspectiveCameraWrapper pc) {
		this.camera = pc;
	}

	public Hud getHud() {
		return hud;
	}

	@Override
	public void runAnimation(CharacterActionType cat) {
		if (!animations.containsKey(cat)) {
			return;
		}
		if (activeAnimationType == null) {
			activeAnimationType = cat;
			activeAnimation = animations.get(activeAnimationType);
			activeAnimation.play();
		} else if (activeAnimationType == cat) {
			activeAnimation.playFromStart();
		} else {
			activeAnimationType = cat;
			activeAnimation.stop();
			activeAnimation = animations.get(activeAnimationType);
			activeAnimation.playFromStart();
		}
	}

	@Override
	public void addAnimations() {

		final Animation idleAnimation = new SpriteAnimation(hud.getGunImage(), Duration.millis(1000),
				1, 1, 0, 0, 64,	64, true);

		final Animation shootAnimation = new SpriteAnimation(hud.getGunImage(), Duration.millis(1000),
				7, 7, 0, 0, 64,	64, false, idleAnimation);

		shootAnimation.play();

		super.animations.put(CharacterActionType.IDLE, idleAnimation);
		super.animations.put(CharacterActionType.SHOOT, shootAnimation);
	}
}
