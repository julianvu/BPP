package views; 

import models.*; 

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ProjectView extends HBox{ 
	private static final int BUTTON_SIZE = 12;
	private static final String ADD_IMG_URL = "/add.png";
	private Project proj; 
	private ArrayList<CategoryView> cats; 

	public ProjectView(Project proj) { 
		super(10.0); 
		this.setMinSize(800, 400);
		this.cats = new ArrayList<>();
		this.proj = proj; 
		this.getChildren().addAll(cats);
		
		Pane addBox = new Pane(); 
		Image addImg = new Image(getClass().getResourceAsStream(ADD_IMG_URL));
		ImageView addView = new ImageView(addImg);
		addView.setFitHeight(BUTTON_SIZE);
		addView.setFitWidth(BUTTON_SIZE);
		Button addButt = new Button("", addView);
		addButt.setTooltip(new Tooltip("Create new category"));
		
		addButt.setLayoutX(addBox.getWidth()/2);
		addButt.setLayoutY(addBox.getHeight()/2);
		addBox.getChildren().add(addButt);
		this.getChildren().add(addBox);

	} 
	
	public void addCatView(CategoryView cv) {
		this.proj.getCategories().add(cv.getCategory());
		this.cats.add(cv);
	}
} 