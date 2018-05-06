package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Task;

import java.time.LocalDate;

public class TaskViewController extends GridPane {

    private Task test;
    private Text taskName, taskDesc, taskDueDate;
    private Scene scene = new Scene(this, 200, 200);

    public TaskViewController(Task task) {
        test = task;
        taskName = new Text(test.getName());
        taskDesc = new Text("Description: \n" + test.getDescription());
        taskDueDate = new Text("Due date: " + test.getDate().toString());
        
        this.setAlignment(Pos.TOP_LEFT);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        this.add(taskName, 1, 1);
        this.add(taskDesc, 1, 2);
        this.add(taskDueDate, 1, 3);

        TextField nameField = new TextField(taskName.getText());
        taskName.setOnMouseClicked(event -> {

            this.getChildren().remove(taskName);
            this.add(nameField, 1, 1);

            nameField.setOnAction(event1 -> {
                String newName = nameField.getText();
                test.setName(newName);
                taskName.setText(newName);
                this.getChildren().remove(nameField);
                this.add(taskName, 1, 1); //}
            });
        });

        TextField descField = new TextField(test.getDescription());
        taskDesc.setOnMouseClicked(event -> {
            this.getChildren().remove(taskDesc);
            this.add(descField, 1, 2);

            descField.setOnAction(event1 -> {
                String newDesc = descField.getText();
                test.setDescription(newDesc);
                taskDesc.setText(newDesc);
                this.getChildren().remove(descField);
                this.add(taskDesc, 1, 2);
            });
        });

        DatePicker dp = new DatePicker(LocalDate.now());
        taskDueDate.setOnMouseClicked(event -> {
            this.getChildren().remove(taskDueDate);
            this.add(dp, 1, 3);

            dp.setOnAction(event1 -> {
                test.setDate(dp.getValue());
                taskDueDate.setText(dp.getValue().toString());
                this.getChildren().remove(dp);
                this.add(taskDueDate, 1, 3);
            });
        });

        taskName.setWrappingWidth(scene.getWidth() - 100);
        taskDesc.setWrappingWidth(scene.getWidth() - 100);
        
    }
}
