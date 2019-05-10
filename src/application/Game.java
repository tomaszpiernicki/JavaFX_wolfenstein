package application;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.application.Platform;
import javafx.stage.Stage;
import threads.BadGuyThread;
import threads.PlayerThread;
import utils.DimentionUtils;
import view.DisplayManager;

public class Game {

	BlockingQueue<String> playerInQueue = new LinkedBlockingQueue<>();
	BlockingQueue<CharacterAction> characterQueue = new LinkedBlockingQueue<>();
	FloorInfo levelInfo = new FloorInfo("level_1.bmp", 1);
	DisplayManager displayManager;
	Player player;

	public void run(Stage primaryStage) throws Exception {
		playerInQueue = new LinkedBlockingDeque<>();
		characterQueue = new LinkedBlockingDeque<>();

		player = new Player(DimentionUtils.blockSize * levelInfo.getStartingX() + DimentionUtils.blockSize / 2,
				DimentionUtils.blockSize * levelInfo.getStartingZ() + DimentionUtils.blockSize / 2);
		startPlayerTask();
		startMonsterTask();
		displayManager = new DisplayManager(primaryStage, levelInfo, characterQueue);
		runCharacterReceiverThread();

		displayManager.scene2d.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case W:
				playerInQueue.offer("W");
				break;
			case S:
				playerInQueue.offer("S");
				break;
			case A:
				playerInQueue.offer("A");
				break;
			case D:
				playerInQueue.offer("D");
				break;
			case CONTROL:
				playerInQueue.offer("SPACE");
				break;
			case SHIFT:
				playerInQueue.offer("SHIFT");
				break;
			default:
				break;

			}
		});
	}

	private void runCharacterReceiverThread() {
		Thread t = new Thread(new CharacterReceiverTask(characterQueue));
		t.setDaemon(true);
		t.start();
	}

	private void startMonsterTask() {
		Thread backgroundThread = new Thread(new BadGuyThread(characterQueue, levelInfo, player));
		backgroundThread.setDaemon(false);
		backgroundThread.start();

	}

	public void startPlayerTask() {
		Thread backgroundThread = new Thread(new PlayerThread(this.playerInQueue, characterQueue, levelInfo, player));
		backgroundThread.setDaemon(false);
		backgroundThread.start();
	}

	public class CharacterReceiverTask implements Runnable {

		BlockingQueue<CharacterAction> characters;

		CharacterReceiverTask(BlockingQueue<CharacterAction> cq) {
			this.characters = cq;
		}

		@Override
		public void run() {
			while (true) {
				CharacterAction chAction;
				try {
					chAction = characters.take();
					switch (chAction.getType()) {
					case MOVED:
						Platform.runLater(() -> {
							displayManager.moveCharacter(chAction.getCharacter());
						});
						break;
					case SHOOT:
						Platform.runLater(() -> {
							displayManager.characterShoot(chAction.getCharacter());
						});
						break;
					case DIED:
						Platform.runLater(() -> {
							displayManager.killCharacter(chAction.getCharacter());
						});
						break;
					case HIT:
						Platform.runLater(() -> {
							displayManager.hitCharacter(chAction.getCharacter());
						});
						break;
					default:
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}