package views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Task;

import java.time.LocalDate;

public class TaskView extends Application {

    private Task test = new Task("Redo LoginView", "LoginView needs to be rewritten to a ViewController", LocalDate.now());


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Text taskName = new Text(test.getName());
        Text taskDesc = new Text("Description: \n" + test.getDescription());
        Text taskDueDate = new Text("Due date: " + test.getDate().toString());

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(taskName, 1, 1);
        pane.add(taskDesc, 1, 2);
        pane.add(taskDueDate, 1, 3);

        taskName.setOnMouseClicked(event -> {
            TextField newName = new TextField(taskName.getText());
            pane.getChildren().remove(taskName);
            pane.add(newName, 1, 1);

            pane.setOnMouseClicked(event1 -> {
                test.setName(newName.getText());
                this.start(primaryStage);
            });

            pane.setOnKeyPressed(event1 -> {
                if (event1.getCode().equals(KeyCode.ENTER)) {
                    test.setName(newName.getText());
                    this.start(primaryStage);
                }
            });
        });

        taskDesc.setOnMouseClicked(event -> {
            TextField newDesc = new TextField(test.getDescription());
            pane.getChildren().remove(taskDesc);
            pane.add(newDesc, 1, 2);
            pane.setOnMouseClicked(event1 -> {
                test.setDescription(newDesc.getText());
                this.start(primaryStage);
            });

            pane.setOnKeyPressed(event1 -> {
                if (event1.getCode().equals(KeyCode.ENTER)) {
                    test.setDescription(newDesc.getText());
                    this.start(primaryStage);
                }
            });
        });

        taskDueDate.setOnMouseClicked(event -> {
            DatePicker dp = new DatePicker(LocalDate.now());
            pane.getChildren().remove(taskDueDate);
            pane.add(dp, 1, 3);

            pane.setOnMouseClicked(event1 -> {
                test.setDate(dp.getValue());
                this.start(primaryStage);
            });

            pane.setOnKeyPressed(event1 -> {
                if (event1.getCode().equals(KeyCode.ENTER)) {
                    test.setDate(dp.getValue());
                    this.start(primaryStage);
                }
            });
        });

        Scene scene = new Scene(pane, 400, 200);
        primaryStage.setScene(scene);

        taskName.setWrappingWidth(scene.getWidth() - 100);
        taskDesc.setWrappingWidth(scene.getWidth() - 100);

        primaryStage.show();
    }
}
