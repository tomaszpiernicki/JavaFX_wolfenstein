package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import application.FloorInfo;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class BitmapUtils {

	static Map<String, Image> loadedBitmaps = new HashMap<String, Image>();

	public static void readBitmapAsArray(FloorInfo levelInfo) {

		BufferedImage image = readBufferedImage(levelInfo.getFloorName());
		int height = image.getHeight(), width = image.getWidth();

		levelInfo.setSizeX(width);
		levelInfo.setSizeZ(height);
		int[][] array = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int color = image.getRGB(j, i);
				if (color == -1) // white
					array[i][j] = 0;
				else if (color == -16777216) // black
					array[i][j] = 1;
				else if (color == -256) {
					levelInfo.setStartingX(i);
					levelInfo.setStartingZ(j);
					array[i][j] = 1; // yellow = starting position.
				} else {
					System.out.println("--------> color: " + color);
					levelInfo.getBadGuysX().add(i);
					levelInfo.getBadGuysZ().add(j);
					array[i][j] = 1;
				}
			}
		}
		levelInfo.setLevelMap(array);
	}

	static BufferedImage readBufferedImage(String fileName) {
		try {
			File bmpFile = new File("images/" + fileName);
			return ImageIO.read(bmpFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	static Image readImage(String fileName) {
		BufferedImage bImage = readBufferedImage(fileName);

		int width = bImage.getWidth();
		int height = bImage.getHeight();

		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

		int oldRGB = new Color(152, 0, 136).getRGB();
		int newRGB = new Color(152, 0, 136, 0).getRGB();
		int currRGB;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				currRGB = bImage.getRGB(x, y);

				if (oldRGB == currRGB) {
					result.setRGB(x, y, newRGB);
				} else {
					result.setRGB(x, y, currRGB);
				}
			}
		}

		Image image = SwingFXUtils.toFXImage(result, null);
		return image;
	}

	public static Image getBitmap(String string) {

		if (loadedBitmaps.containsKey(string))
			return loadedBitmaps.get(string);
		Image image = readImage(string);
		loadedBitmaps.put(string, image);
		return image;
	}

}
