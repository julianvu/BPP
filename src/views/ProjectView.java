
package views; 
import models.*; 
 
import java.util.ArrayList; 
 
import javafx.application.Application; 
import javafx.scene.layout.HBox; 
import javafx.stage.Stage; 

public class ProjectView extends HBox/*extends Application*/ { 
  private String projTitle; 
  private ArrayList<CategoryView> cats; 
   
  public ProjectView(String name) { 
    super(10.0); 
    this.projTitle = name; 
    for (CategoryView c:cats) { 
      this.getChildren().add(c); 
    } 
  } 
   
   
   
  /*public static void main(String[] args) { 
    launch(args); 
  } 
 
    @Override 
    public void start(Stage primaryStage) { 
  @Override 
  public void start(Stage primaryStage) { 
 
    } 
  }*/ 
} 