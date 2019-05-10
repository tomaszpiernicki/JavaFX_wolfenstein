package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import utils.BitmapUtils;
import utils.DimentionUtils;

public class Hud extends BorderPane {

	ImageView playerHud;
	final ImageView gunImage;
	StackPane hudStack = new StackPane();
	
	HudLabel floorLabel = new HudLabel("floor", -395);
	HudLabel scoreLabel = new HudLabel("score", -260);
	HudLabel livesLabel = new HudLabel("lives", -130);
	HudLabel healthLabel = new HudLabel("Life", 75);
	HudLabel ammoLabel = new HudLabel("∞", 185);	
	Text gameOver = new Text(10,10, "GAME OVER");
	
	
	Hud(int f, int s, int l, int h, int a)
	{
		gunImage = new ImageView(BitmapUtils.getBitmap("shotgun.bmp"));
		gunImage.setPreserveRatio(true);
		gunImage.setFitHeight(DimentionUtils.height * 3 / 4);
		Hud.setMargin(gunImage, new Insets(0,0,-1,0));
		Hud.setAlignment(gunImage, Pos.BOTTOM_CENTER);
		
		gameOver.setFont(Font.font("Impact", 60));
		gameOver.setFill(Color.RED);
		
		playerHud = new ImageView(BitmapUtils.getBitmap("hud.bmp"));
		playerHud.setPreserveRatio(true);
		playerHud.setFitWidth(DimentionUtils.width);
		
		ImageView instructions = new ImageView(BitmapUtils.getBitmap("howToPlay_good.bmp"));
		instructions.setPreserveRatio(true);
		instructions.setFitWidth(DimentionUtils.width + 2);
		instructions.setTranslateX(5);
		
		hudStack.getChildren().add(playerHud);

		hudStack.getChildren().add(floorLabel);
		hudStack.getChildren().add(scoreLabel);
		hudStack.getChildren().add(livesLabel);
		hudStack.getChildren().add(healthLabel);
		hudStack.getChildren().add(ammoLabel);
		
		updateFloorLabel(f);
		updateScoreLabel(s);
		updateLivesLabel(l);
		updateHealthLabel(h);
		updateAmmoLabel(a);
		
		this.setCenter(gunImage);
		this.setBottom(hudStack);
		this.setTop(instructions);
	}
	
	void updateFloorLabel(Integer value)
	{
		floorLabel.setText(value.toString());
	}
	
	void updateScoreLabel(Integer value)
	{
		scoreLabel.setText(value.toString());
	}
	
	void updateLivesLabel(Integer value)
	{
		livesLabel.setText(value.toString());
	}
	
	void updateHealthLabel(Integer value)
	{
		healthLabel.setText(value.toString());
	}
	
	void updateAmmoLabel(Integer value)
	{
		ammoLabel.setText(value.toString());
	}
	
	void endGame()
	{
		this.setCenter(gameOver);
	}
	
	public ImageView getGunImage() {
		return gunImage;
	}

}
