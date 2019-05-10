package view;

import java.util.HashMap;

import application.Character;
import javafx.animation.Animation;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import utils.CharacterActionType;
import utils.Position;

public abstract class CharacterRepresentation {

	private Node node;
	private Rotate yRotate;
	private Translate translation;
	public HashMap<CharacterActionType, Animation> animations = new HashMap<>();
	protected CharacterActionType activeAnimationType = null;
	protected Animation activeAnimation;

	public Node getNode() {
		return node;
	}

	public CharacterRepresentation(Node n) {
		node = n;
		yRotate = new Rotate(0, Rotate.Y_AXIS);
		translation = new Translate();
		node.getTransforms().setAll(yRotate, translation);
	}

	public void updatePositionAndRotation(Character character) {
		moveToPosition(character.getPosition());
		rotate((double) character.getRotation());
		runAnimation(CharacterActionType.MOVED);
	}

	public void moveToPosition(Position position) {
		translation.setX(position.getX());
		translation.setY(position.getY());
		translation.setZ(position.getZ());
		yRotate.setPivotX(position.getX());
		yRotate.setPivotY(position.getY());
		yRotate.setPivotZ(position.getZ());
	}

	public void rotate(double rotation) {
		yRotate.setAngle(rotation);
	}

	public void runAnimation(CharacterActionType cat) {
		if (!animations.containsKey(cat)) {
			return;
		}
		if (activeAnimationType == null) {
			activeAnimationType = cat;
			activeAnimation = animations.get(activeAnimationType);
			activeAnimation.play();
		} else if (activeAnimationType == cat) {
			return;
		} else {
			activeAnimationType = cat;
			activeAnimation.stop();
			activeAnimation = animations.get(activeAnimationType);
			activeAnimation.playFromStart();
		}
	}

	public abstract void addAnimations();
}
