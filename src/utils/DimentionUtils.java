package utils;

public class DimentionUtils {
	public static int height = 600;
	public static int width = 900;
	
	public static Double blockSize = 500.0;
	public static Double blockBorder = 1.0;
	public static Float  stepSize = (float) 50.0;
	public static Double rotationAngle = 7.0;
	public static Double collisionMargin = 100.0;
	public static Double badGuyDimention = blockSize * 3 / 4.0;

	public static Position getFuturePositionForward(application.Character character) {
		double alfa = character.getRotation();
		Position vector =  new Position(DimentionUtils.stepSize * Math.sin(Math.toRadians(alfa)), DimentionUtils.stepSize * Math.cos(Math.toRadians(alfa)));
		return character.getPosition().newIncrementedPosition(vector);
	}
	
	public static Position getFuturePositionBackward(application.Character character) {
		double alfa = character.getRotation();
		Position vector =  new Position(-DimentionUtils.stepSize * Math.sin(Math.toRadians(alfa)), -DimentionUtils.stepSize * Math.cos(Math.toRadians(alfa)));
		return character.getPosition().newIncrementedPosition(vector);
	}
}
