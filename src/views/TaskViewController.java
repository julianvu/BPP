package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import models.Task;

import java.time.LocalDate;

    public class TaskViewController extends GridPane {

    private Task test;
    private Label taskName, taskDesc, taskDueDate;
    private Scene scene = new Scene(this, 300, 100);

    public TaskViewController(Task task) {
        test = task;
        taskName = new Label(test.getName());
        taskDesc = new Label("Description: \n" + test.getDescription());
        taskDueDate = new Label("Due date: " + test.getDate().toString());

        String style = "-fx-border-radius: 5;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-padding: 0 0 10 0;\n";
        this.setStyle(style);
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5), null)));
        this.setAlignment(Pos.TOP_LEFT);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(0));

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

        taskName.setOnMouseEntered(event -> {
            taskName.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, null, null)));

            taskName.setOnMouseExited(event1 -> {
                taskName.setBackground(null);
            });
        });

        TextField descField = new TextField(test.getDescription());
        taskDesc.setOnMouseClicked(event -> {
            this.getChildren().remove(taskDesc);
            this.add(descField, 1, 2);

            descField.setOnAction(event1 -> {
                String newDesc = descField.getText();
                test.setDescription(newDesc);
                taskDesc.setText("Description: \n" + newDesc);
                this.getChildren().remove(descField);
                this.add(taskDesc, 1, 2);
            });
        });

        taskDesc.setOnMouseEntered(event -> {
            taskDesc.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, null, null)));

            taskDesc.setOnMouseExited(event1 -> taskDesc.setBackground(null));
        });

        DatePicker dp = new DatePicker(LocalDate.now());
        taskDueDate.setOnMouseClicked(event -> {
            this.getChildren().remove(taskDueDate);
            this.add(dp, 1, 3);

            dp.setOnAction(event1 -> {
                test.setDate(dp.getValue());
                taskDueDate.setText("Due date: \n" + dp.getValue().toString());
                this.getChildren().remove(dp);
                this.add(taskDueDate, 1, 3);
            });
        });

        taskDueDate.setOnMouseEntered(event -> {
            taskDueDate.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, null, null)));

            taskDueDate.setOnMouseExited(event1 -> taskDueDate.setBackground(null));
        });

        taskName.setWrapText(true);
        taskDesc.setWrapText(true);
        
    }
}
