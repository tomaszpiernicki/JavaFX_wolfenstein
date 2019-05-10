package threads;

import java.util.concurrent.BlockingQueue;

import application.CharacterAction;
import application.FloorInfo;
import application.Player;

public class PlayerThread extends CharacterLogicThread {
	private BlockingQueue<String> playerInQueue;

    public PlayerThread(BlockingQueue<String> actionQueue, BlockingQueue<CharacterAction> characterQueue, FloorInfo level, Player player) {
        super(level, characterQueue, player);
    	this.playerInQueue = actionQueue;
    }
    
    public void run() {
    	while (true) {
			try {
				switch (playerInQueue.take())
				{
				case "W":
					moveForward(0);
					break;
				case "S":
					moveBackward(0);
					break;
				case "A":
					rotateLeft(0);
					break;
				case "D":
					rotateRight(0);
					break;
				case "SPACE":
					makeAttack(0);
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
    }
}
