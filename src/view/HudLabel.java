package view;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class HudLabel extends Label {

	public HudLabel(String text, Integer offsetX) {

		setText(text);
		setTranslateX(offsetX);

		setFont(Font.font("Impact", 40));
		setTextFill(Color.rgb(157, 157, 255));
		setTranslateY(20);
	}
}
