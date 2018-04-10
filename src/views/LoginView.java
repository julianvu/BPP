package views;

import controllers.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginView extends Application implements View {

    private TextField userTextField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Button signIn = new Button("Sign in");

    public Text getSignInMessage() {
        return signInMessage;
    }

    private final Text signInMessage = new Text();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        grid.add(signInMessage, 1, 6);

        Scene scene = new Scene(grid, 700, 475);
        primaryStage.setScene(scene);

        Text sceneTitle = new Text("Login credentials required");
        sceneTitle.setFont(Font.font("Helvetica Neue", FontWeight.THIN, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label username = new Label("Username:");
        username.setFont(Font.font("Helvetica Neue", FontWeight.THIN, 15));
        grid.add(username, 0, 1);

        grid.add(userTextField, 1, 1);

        Label password = new Label("Password:");
        password.setFont(Font.font("Helvetica Neue", FontWeight.THIN, 15));
        grid.add(password, 0, 2);

        grid.add(passwordField, 1, 2);

        signIn.setFont(Font.font("Helvetica Neue", FontWeight.THIN, 15));
        HBox hboxButton = new HBox(10);
        hboxButton.setAlignment(Pos.BOTTOM_RIGHT);
        hboxButton.getChildren().addAll(signIn);
        grid.add(hboxButton, 1, 4);

        signIn.setOnAction(event -> {
            Controller c = new Controller(this);
            c.handleLogin(userTextField.getText(), passwordField.getText());
        });
        signIn.defaultButtonProperty().bind(signIn.focusTraversableProperty());

        primaryStage.show();
    }
}
