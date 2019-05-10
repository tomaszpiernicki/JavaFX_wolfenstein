package view;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

import application.Character;
import application.CharacterAction;
import application.FloorInfo;
import application.BadGuy;
import application.Player;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.CharacterActionType;
import utils.DimentionUtils;

public class DisplayManager {

	Stage primaryStage;
	FloorInfo levelInfo;
	BlockingQueue<CharacterAction> characterActionQueue;
	Player player;

	HashMap<application.Character, CharacterRepresentation> map = new HashMap<>();
	StackPane mainStackPane = new StackPane();

	public Scene scene2d;
	SubScene scene3d;
	WorldRepresentation worldRepresentation;

	public DisplayManager(Stage ps, FloorInfo li, BlockingQueue<CharacterAction> cq) {
		this.primaryStage = ps;
		this.levelInfo = li;
		this.characterActionQueue = cq;

		this.worldRepresentation = new WorldRepresentation(li);

		scene3d = new SubScene(this.worldRepresentation, DimentionUtils.width, DimentionUtils.height, true,
				SceneAntialiasing.BALANCED);

		mainStackPane.getChildren().add(scene3d);
		getPlayerRepresentationAndCamera();

		scene2d = new Scene(mainStackPane, DimentionUtils.width, DimentionUtils.height + 200);

		this.primaryStage.setTitle("Wolfenstein 3D - JavaFX remake");
		this.primaryStage.setResizable(false);
		this.primaryStage.setScene(scene2d);
		this.primaryStage.show();
	}

	private void getPlayerRepresentationAndCamera() {
		try {
			player = (Player) characterActionQueue.take().getCharacter();
			PlayerRepresentation playerRepresentation = new PlayerRepresentation(player, levelInfo.getFloorNumber());
			map.put(player, playerRepresentation);
			this.scene3d.setCamera(playerRepresentation.getPc());
			mainStackPane.getChildren().add(playerRepresentation.getHud());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void killCharacter(application.Character character) {
		if (map.containsKey(character)) {
			if (character instanceof Player) {
				((PlayerRepresentation) map.get(character)).getHud().updateHealthLabel(0);
				((PlayerRepresentation) map.get(character)).getHud().endGame();
				return;
			}
			else {
				((PlayerRepresentation) map.get(player)).getHud().updateAmmoLabel(10);
			}
			map.get(character).runAnimation(CharacterActionType.DIED);
		
		}
	}

	public void hitCharacter(application.Character character) {
		if (character instanceof Player) {
			((PlayerRepresentation) map.get(character)).getHud().updateHealthLabel(character.getHp());
		}
	}

	public void moveCharacter(application.Character character) {
		if (!map.containsKey(character)) {
			BadGuyRepresentation monsterRepresentation = new BadGuyRepresentation((BadGuy) character);
			map.put(character, monsterRepresentation);
			this.worldRepresentation.getChildren().add(map.get(character).getNode());
		}
		map.get(character).updatePositionAndRotation(character);
	}

	public void characterShoot(Character character) {
		if (map.containsKey(character)) {
			map.get(character).runAnimation(CharacterActionType.SHOOT);
			if (character instanceof Player) {
				((PlayerRepresentation) map.get(character)).getHud().updateAmmoLabel(character.getPistol().getAmmo());
			}
		}
	}
}
