package views;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import models.*;

import java.time.LocalDate;

public class MainScreen extends Application {
	static private final int BUTTON_SIZE = 12;
	static private final String ADD_IMG_URL = "/add.png";
	static private final String OPEN_IMG_URL = "/open.png";
	private static final String SAVE_IMG_URL = "/save.png"; 
	private TaskBoard tskbd;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		//buttons
		Image addImg = new Image(getClass().getResourceAsStream(ADD_IMG_URL));
		ImageView addView = new ImageView(addImg);
		addView.setFitHeight(BUTTON_SIZE);
		addView.setFitWidth(BUTTON_SIZE);
		Button addButt = new Button("", addView);
		addButt.setTooltip(new Tooltip("Create new project"));

		Image openImg = new Image(getClass().getResourceAsStream(OPEN_IMG_URL));
		ImageView openView = new ImageView(openImg);
		openView.setFitHeight(BUTTON_SIZE);
		openView.setFitWidth(BUTTON_SIZE);
		Button openButt = new Button("", openView);
		openButt.setTooltip(new Tooltip("Open project"));

		Image saveImg = new Image(getClass().getResourceAsStream(SAVE_IMG_URL));
		ImageView saveView = new ImageView(saveImg);
		saveView.setFitHeight(BUTTON_SIZE);
		saveView.setFitWidth(BUTTON_SIZE);
		Button saveButt = new Button("", saveView);
		saveButt.setTooltip(new Tooltip("Save project"));

		//toolbar
		HBox buffer = new HBox();
		HBox.setHgrow(buffer, Priority.ALWAYS);
		ToolBar toolbar = new ToolBar(/*new Label(proj.getName()),*/ buffer, addButt, 
				openButt, saveButt);
		BorderPane pane = new BorderPane();
		pane.setTop(toolbar);
		pane.setPrefSize(800, 600);
		toolbar.setPrefWidth(pane.getPrefWidth());
		
		addButt.setOnAction(e -> {

			
		});

		FileChooser fc = new FileChooser();
		openButt.setOnMouseClicked(e -> {
			fc.setTitle("Open B++ file");
			fc.getExtensionFilters().add(
					new ExtensionFilter("B++ file", "*.bpp"));
			File bppFile = fc.showOpenDialog(primaryStage);
			ObjectInputStream in;
			try {
				in = new ObjectInputStream(new FileInputStream(bppFile));
				//	TODO: proj = (Project) in.readobject(); 
				in.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NullPointerException e1) {
				
			}
			

			if (bppFile == null) {
				//TODO: deal with this idk man
			}
		});

		saveButt.setOnMouseClicked(e -> {
			fc.setTitle("Save B++ file");
			fc.getExtensionFilters().add(
					new ExtensionFilter("B++ file", "*.bpp"));
			File bppFile = fc.showSaveDialog(primaryStage);
			ObjectOutputStream out;
			try {
				out = new ObjectOutputStream(new FileOutputStream(bppFile));
				//out.writeObject(PROJECT);
				out.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NullPointerException e1) {
				
			}
			if (bppFile == null) {
				//TODO: deal with this idk man
			}
		});

		
		Scene scene = new Scene(pane, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();

	}
}
