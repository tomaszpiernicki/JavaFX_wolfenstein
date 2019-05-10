package threads;

import java.util.concurrent.BlockingQueue;

import application.CharacterAction;
import application.FloorInfo;
import application.BadGuy;
import application.Player;
import utils.DimentionUtils;
import utils.Position;

public class BadGuyThread extends CharacterLogicThread {

	boolean justAttacked = false;

	public BadGuyThread(BlockingQueue<CharacterAction> characterQueue, FloorInfo level, Player player) {
		super(level, characterQueue);
		super.updatePlayerInfo(player);

		for (int i = 0; i < level.getBadGuysX().size(); i++) {
			BadGuy badGuy = new BadGuy(
					new Position(DimentionUtils.blockSize * level.getBadGuysX().get(i) + DimentionUtils.blockSize / 2,
							DimentionUtils.blockSize * level.getBadGuysZ().get(i) + DimentionUtils.blockSize / 2),
					360 / (i + 1));
			characters.add(badGuy);
		}

	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			for (int i = 0; i < characters.size(); i++) {
				BadGuy bg = (BadGuy) characters.get(i);
				double alfa = player.getPosition().getAngleOfLineBetweenTwoPoints(bg.getPosition());
				double beta = bg.getRotation();
				double gamma = 0;

				if (Math.abs(alfa - beta) < 11) // good angle
				{
					if (bg.getPosition().distance(player.getPosition()) < bg.getPistol().getDistance()) {
						if (justAttacked == false) {
							makeAttack(i);
							justAttacked = true;
						} else {
							justAttacked = false;
							moveBackward(i);
						}
//						try {
//							Thread.sleep(400);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
					} else {
						moveForward(i);
					}
				} else {
					if (alfa <= 180) {
						gamma = alfa + 180;
						if (alfa < beta && beta <= gamma) {
							rotateLeft(i);

						} else {
							rotateRight(i);
						}
					} else {
						gamma = alfa;
						alfa = gamma - 180;
						if (alfa < beta && beta <= gamma) {
							rotateRight(i);

						} else {
							rotateLeft(i);
						}
					}
				}
			}
		}
	}
}
