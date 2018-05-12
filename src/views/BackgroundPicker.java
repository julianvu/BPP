package views;
import java.io.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;




public class BackgroundPicker {
	
	static private final String BG1_IMG_URL = "/background1.jpg";
	static private final String BG2_IMG_URL = "/background2.jpg";
	static private final String BG3_IMG_URL = "/background3.jpg";
	static private final String BG4_IMG_URL = "/background4.jpg";
	static private final String BG5_IMG_URL = "/background5.jpg";
	static private final String BG6_IMG_URL = "/background6.jpg";
	static private final int BUTTON_SIZE = 100;
	
	static int answer = -1;
	
	public int display() {
		
		
		Stage window = new Stage();
		window.setTitle("Available Backgrounds");
		window.initModality(Modality.APPLICATION_MODAL);
		
		//make gridpane
		GridPane bgpicker = new GridPane();
		bgpicker.setHgap(10);
		bgpicker.setVgap(10);
		bgpicker.setPadding(new Insets(20, 150, 10, 10));
		
		//buttons
		Image bg1 = new Image(getClass().getResourceAsStream(BG1_IMG_URL));
		ImageView bg1View = new ImageView(bg1);	
		bg1View.setFitHeight(BUTTON_SIZE);
		bg1View.setFitWidth(BUTTON_SIZE);
		Button bg1Butt = new Button("", bg1View);

		Image bg2 = new Image(getClass().getResourceAsStream(BG2_IMG_URL));
		ImageView bg2View = new ImageView(bg2);
		bg2View.setFitHeight(BUTTON_SIZE);
		bg2View.setFitWidth(BUTTON_SIZE);
		Button bg2Butt = new Button("", bg2View);
		
		Image bg3 = new Image(getClass().getResourceAsStream(BG3_IMG_URL));
		ImageView bg3View = new ImageView(bg3);
		bg3View.setFitHeight(BUTTON_SIZE);
		bg3View.setFitWidth(BUTTON_SIZE);
		Button bg3Butt = new Button("", bg3View);
		
		Image bg4 = new Image(getClass().getResourceAsStream(BG4_IMG_URL));
		ImageView bg4View = new ImageView(bg4);
		bg4View.setFitHeight(BUTTON_SIZE);
		bg4View.setFitWidth(BUTTON_SIZE);
		Button bg4Butt = new Button("", bg4View);
		
		Image bg5 = new Image(getClass().getResourceAsStream(BG5_IMG_URL));
		ImageView bg5View = new ImageView(bg5);
		bg5View.setFitHeight(BUTTON_SIZE);
		bg5View.setFitWidth(BUTTON_SIZE);
		Button bg5Butt = new Button("", bg5View);
		
		Image bg6 = new Image(getClass().getResourceAsStream(BG6_IMG_URL));
		ImageView bg6View = new ImageView(bg6);
		bg6View.setFitHeight(BUTTON_SIZE);
		bg6View.setFitWidth(BUTTON_SIZE);
		Button bg6Butt = new Button("", bg6View);
		
		
		bgpicker.add(bg1Butt, 0, 0);
		bgpicker.add(bg2Butt, 1, 0);
		bgpicker.add(bg3Butt, 2, 0);
		bgpicker.add(bg4Butt, 0, 1);
		bgpicker.add(bg5Butt, 1, 1);
		bgpicker.add(bg6Butt, 2, 1);
        
		
		bg1Butt.setOnMouseClicked(event -> {
			answer = 1;
			window.close();
		});
		bg2Butt.setOnAction(event -> {
			answer = 2;
			window.close();
		});
		
		bg3Butt.setOnMouseClicked(event -> {
			answer = 3;
			window.close();
		});
		bg4Butt.setOnAction(event -> {
			answer = 4;
			window.close();
		});
		
		bg5Butt.setOnMouseClicked(event -> {
			answer = 5;
			window.close();
		});
		
		bg6Butt.setOnMouseClicked(event -> {
			answer = 6;
			window.close();
		});
		
	
		
		Scene scene = new Scene(bgpicker);
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	}

	
}
