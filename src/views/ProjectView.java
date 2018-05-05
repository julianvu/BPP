package views; 

import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import models.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class ProjectView extends HBox {
	private static final int BUTTON_SIZE = 12;
	private static final String ADD_IMG_URL = "/add.png";
	private Project proj; 
	private ArrayList<VBox> cats; 

	public ProjectView(Project proj) { 
		super(10.0); 
		this.setMinSize(800, 400);
		this.cats = new ArrayList<>();
		this.proj = proj;
        this.getChildren().addAll(cats);
		//this.getChildren().add(projTitle);
		
		VBox addBox = new VBox(); 
		addBox.setPrefSize(200,400);
		Image addImg = new Image(getClass().getResourceAsStream(ADD_IMG_URL));
		ImageView addView = new ImageView(addImg);
		addView.setFitHeight(BUTTON_SIZE);
		addView.setFitWidth(BUTTON_SIZE);
		Button addButt = new Button("", addView);
		addButt.setTooltip(new Tooltip("Create new category"));
		
		
		addButt.setLayoutX((addBox.getPrefWidth()-BUTTON_SIZE)/2);
		addButt.setLayoutY(100);
		addBox.getChildren().add(addButt);
		this.cats.add(addBox);
		this.getChildren().add(addBox);
		
		TextInputDialog newCat = new TextInputDialog("Category Name");
		
		String addLayout = "-fx-border-color: black;\n" +
						   "-fx-border-style: dashed;\n";
		addBox.setStyle(addLayout);
		
		//String catLayout = "-fx-border-color: black;\n" +
			//			   ""
		
		addButt.setOnAction(e -> {
			Optional<String> result = newCat.showAndWait();
			if (result.isPresent()) {
				String newName = result.get();
				CategoryView cv = new CategoryView(new Category(newName));
				this.cats.add(cv);
				this.getChildren().add(cats.size()-2, cv);
			}
			
		});

		
	} 
	
	public void addCatView(CategoryView cv) {
		this.proj.getCategories().add(cv.getCategory());
		this.cats.add(cv);
	}
} 