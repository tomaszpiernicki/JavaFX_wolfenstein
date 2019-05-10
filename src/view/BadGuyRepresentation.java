package view;

import application.BadGuy;
import javafx.animation.Animation;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import utils.BitmapUtils;
import utils.CharacterActionType;
import utils.DimentionUtils;

public class BadGuyRepresentation extends CharacterRepresentation {

	ImageView imageView;

	public BadGuyRepresentation(BadGuy monster) {
		super(new Group());
		imageView = new ImageView(BitmapUtils.getBitmap("soldier.png"));
		imageView.setFitHeight(DimentionUtils.badGuyDimention);
		imageView.setFitWidth(DimentionUtils.badGuyDimention);

		getNode().setTranslateY(-DimentionUtils.blockSize / 4);
		imageView.setTranslateX(-DimentionUtils.badGuyDimention / 2);
		getNode().setRotationAxis(new Point3D(0, 1, 0));
		((Group) getNode()).getChildren().add(imageView);
		addAnimations();
	}

	@Override
	public void addAnimations() {

		final Animation idleAnimation = new SpriteAnimation(imageView, Duration.millis(1000),
				1, 1 , 0 ,  65,  65,  65, true);
		
		final Animation moveAnimation = new SpriteAnimation(imageView, Duration.millis(1000),
				4, 1 , 0 ,  65,  65,  65, true);

		final Animation shootAnimation = new SpriteAnimation(imageView, Duration.millis(504), 	
				2, 2 ,  0 ,  65*6,  65, 65, false);

		final Animation dieAnimation = new SpriteAnimation(imageView, Duration.millis(1000),
				 5, 5 ,  0 ,  65*5,  65,  65, false);
		
		super.animations.put(CharacterActionType.IDLE, idleAnimation);
		super.animations.put(CharacterActionType.MOVED, moveAnimation);
		super.animations.put(CharacterActionType.SHOOT, shootAnimation);
		super.animations.put(CharacterActionType.DIED, dieAnimation);
	}
}
