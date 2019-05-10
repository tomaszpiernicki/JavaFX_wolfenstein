package threads;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import application.Character;
import application.CharacterAction;
import application.FloorInfo;
import application.BadGuy;
import application.Player;
import javafx.application.Platform;
import utils.CharacterActionType;
import utils.DimentionUtils;
import utils.Position;

public abstract class CharacterLogicThread implements Runnable {

	private BlockingQueue<CharacterAction> characterActionQueue;
	private FloorInfo level;
	protected ArrayList<application.Character> characters = new ArrayList<>();
	protected Character player;

	public CharacterLogicThread(FloorInfo l, BlockingQueue<CharacterAction> cq, Player character) {
		level = l;
		characterActionQueue = cq;
		characters.add(character);
		player = character;
		runCharacterInputThread();
		try {
			characterActionQueue.put(new CharacterAction(characters.get(0)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public CharacterLogicThread(FloorInfo l, BlockingQueue<CharacterAction> cq) {
		level = l;
		characterActionQueue = cq;
		runCharacterInputThread();
	}

	private void runCharacterInputThread() {
		Thread t = new Thread(new CharacterInput(characterActionQueue));
		t.start();
	}

	public void moveForward(Integer characterIndex) {

		Character ch = characters.get(characterIndex);
		Position futurePosition = DimentionUtils.getFuturePositionForward(ch);
		if (!isCollidingForward(ch, futurePosition)) {
			characters.get(characterIndex).setPosition(futurePosition);
		}
		characterActionQueue.offer(new CharacterAction(ch));
	}

	public void moveBackward(Integer characterIndex) {
		Character ch = characters.get(characterIndex);
		Position futurePosition = DimentionUtils.getFuturePositionBackward(ch);
		if (!isCollidingBackward(ch, futurePosition)) {
			characters.get(characterIndex).setPosition(futurePosition);
		}
		characterActionQueue.offer(new CharacterAction(ch));
	}

	public void rotateLeft(Integer characterIndex) {
		characters.get(characterIndex).rotate(-DimentionUtils.rotationAngle);
		characterActionQueue.offer(new CharacterAction(characters.get(characterIndex)));
	}

	public void rotateRight(Integer characterIndex) {
		characters.get(characterIndex).rotate(DimentionUtils.rotationAngle);
		characterActionQueue.offer(new CharacterAction(characters.get(characterIndex)));
	}

	public void makeAttack(Integer characterIndex) {
		if (characters.get(characterIndex).getPistol().getAmmo() > 0) {
			characters.get(characterIndex).getPistol().spareBullet();
			characterActionQueue.offer(new CharacterAction(characters.get(characterIndex), CharacterActionType.SHOOT));
		}
	}

	public void resolveAttack(Character attacker) {
		ArrayList<Character> toKill = new ArrayList<Character>();
		for (Character ch : characters) {
			if (!ch.getClass().equals(attacker.getClass())) // not shooting myself
			{
				if (Math.abs(ch.getPosition().getAngleOfLineBetweenTwoPoints(attacker.getPosition())
						- attacker.getRotation()) < 10) // good angle
				{
					if (ch.getPosition().distance(attacker.getPosition()) < attacker.getPistol().getDistance()) // in
																												// range
					{
						try {
							if (ch.hitAndDead(attacker.getPistol().getDamage())) //
							{
								characterActionQueue.put(new CharacterAction(ch, CharacterActionType.DIED));
								toKill.add(ch);
								continue;
							} else {
								characterActionQueue.put(new CharacterAction(ch, CharacterActionType.HIT));
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		for (Character toK : toKill) {
			characters.remove(toK);
		}
	}

	private boolean isCollidingForward(Character character, Position futurePosition) {
		int angle = character.getRotation();
		Position temp = null;
		switch (angle /= 90) {
		case (0):
			temp = futurePosition.newIncrementedPosition(new Position(character.getRadius(), 0, character.getRadius()));
			break;
		case (1):
			temp = futurePosition
					.newIncrementedPosition(new Position(character.getRadius(), 0, -character.getRadius()));
			break;
		case (2):
			temp = futurePosition
					.newIncrementedPosition(new Position(-character.getRadius(), 0, -character.getRadius()));
			break;
		case (3):
			temp = futurePosition
					.newIncrementedPosition(new Position(-character.getRadius(), 0, character.getRadius()));
			break;
		}
		int x = temp.resolveIndexX();
		int y = temp.resolveIndexZ();
		int result = level.getAtIndex(x, y);
		if (result == 0)
			return true;
		return false;
	}

	private boolean isCollidingBackward(Character character, Position futurePosition) {
		int angle = character.getRotation();
		Position temp = null;
		switch (angle /= 90) {
		case (2):
			temp = futurePosition.newIncrementedPosition(new Position(character.getRadius(), 0, character.getRadius()));
			break;
		case (3):
			temp = futurePosition
					.newIncrementedPosition(new Position(character.getRadius(), 0, -character.getRadius()));
			break;
		case (0):
			temp = futurePosition
					.newIncrementedPosition(new Position(-character.getRadius(), 0, -character.getRadius()));
			break;
		case (1):
			temp = futurePosition
					.newIncrementedPosition(new Position(-character.getRadius(), 0, character.getRadius()));
			break;
		}
		int x = temp.resolveIndexX();
		int y = temp.resolveIndexZ();
		int result = level.getAtIndex(x, y);
		if (result == 0)
			return true;
		return false;
	}

	public Character getPlayer() {
		return player;
	}

	public void updatePlayerInfo(Character player) {
		this.player = player;
		characters.forEach((character) -> {
			if (character instanceof BadGuy) {
				((BadGuy) character).setPlayerPosition(player.getPosition());
			}
		});
	}

	public class CharacterInput implements Runnable {

		BlockingQueue<CharacterAction> characters;

		CharacterInput(BlockingQueue<CharacterAction> cq) {
			this.characters = cq;
		}

		@Override
		public void run() {

			CharacterAction last = null;
			while (true) {
				CharacterAction chAction;
				try {
					chAction = characters.element();
					if (chAction == last) {
						continue;
					}
					last = chAction;
					switch (chAction.getType()) {
					case SHOOT:
						Platform.runLater(() -> {
							resolveAttack(chAction.getCharacter());
						});
						break;
					case MOVED:
						if (chAction.getCharacter().equals(player)) {
							Platform.runLater(() -> {
								updatePlayerInfo(chAction.getCharacter());
							});
						}
						break;
					case DIED:
						break;
					case HIT:
						break;
					default:
						break;
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		}
	}

}
