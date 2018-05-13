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
import javafx.stage.Stage;
import javafx.util.Pair;
import models.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class MainScreen extends Application {
	static private final int BUTTON_SIZE = 12;
	static private final String ADD_IMG_URL = "/add.png";
	static private final String OPEN_IMG_URL = "/open.png";
	private static final String SAVE_IMG_URL = "/save.png";
	private static final String LOGIN_IMG_URL = "/login.png";
	private BorderPane pane;
	private Text signInMessage = new Text();
	private TaskBoard tskbd;
	
	public MainScreen() {
		this.tskbd = new TaskBoard();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login");
        dialog.setHeaderText("Login credentials required");

        // Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(LOGIN_IMG_URL))));

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Sign in", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);
        grid.add(signInMessage, 1, 2);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            Platform.exit();
            return null;
        });

        AtomicReference<Optional<Pair<String, String>>> result = new AtomicReference<>(dialog.showAndWait());

        while (result.get().isPresent()) {
            Pair<String, String> pair = result.get().get();
            System.out.println("Username=" + pair.getKey() + ", Password=" + pair.getValue());
            if (!handleLogin(pair.getKey(), pair.getValue())) {
                result.set(dialog.showAndWait());
            }
            else {
                break;
            }
        }

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
		pane = new BorderPane();
		pane.setTop(toolbar);
		pane.setPrefSize(800, 600);
		toolbar.setPrefWidth(pane.getPrefWidth());
		
		addButt.setOnAction(e -> {
            TextInputDialog createProjDialog = new TextInputDialog("Project");
            createProjDialog.setTitle("Create New Project");
            createProjDialog.setHeaderText(null);
            createProjDialog.setContentText("Enter project name:");

            Optional<String> projDialogResult = createProjDialog.showAndWait();
            if (result.get().isPresent()) {
                Project project = new Project(projDialogResult.get());
                ProjectView projectView = new ProjectView(project);
                this.tskbd.setProj(project);
                pane.setCenter(projectView);
            }  
            Label label = new Label("Project - " + tskbd.getProj().getName());
            if (toolbar.getItems().get(0) instanceof Label) {
            	toolbar.getItems().remove(0);
            }
            toolbar.getItems().add(0, label);
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
				Project savedProj = (Project) in.readObject();
				tskbd.setProj(savedProj);				
				in.close();
				reinit();
				//System.out.println(tskbd.getProj().getCategories().getTasks());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NullPointerException e1) {
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
				out.writeObject(tskbd.getProj());
				out.flush();
				out.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NullPointerException e1) {

			}
			
		});
		
		Scene scene = new Scene(pane, 800, 600);
		primaryStage.getIcons().add(new Image("/icon.png"));
		primaryStage.setTitle("B++");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	public void reinit() {
		pane.getChildren().remove(pane.getCenter());
		Project proj = tskbd.getProj();
		ProjectView pv = new ProjectView(proj);
		pane.setCenter(pv);
		ArrayList<Category> cat = proj.getCategories();
		System.out.println(cat);
		for (Category c:cat) {
			CategoryView cv = new CategoryView(c);
			pv.getChildren().add(pv.getChildren().size()-2, cv);
			cv.setAllTasks(c.getTasks());
		}
	}

    public boolean handleLogin(String username, String password) {
        try {
            File file = new File("accounts.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line1 = null;
            String line2 = null;

            while ((line1 = br.readLine()) != null && (line2 = br.readLine()) != null) {
                if (username.equals(line1) && password.equals(line2)) {
                    signInMessage.setFill(Color.GREEN);
                    signInMessage.setText("Sign in success!");
                    return true;
                }
                else {
                    signInMessage.setFill(Color.RED);
                    signInMessage.setText("Sign in failed.");
                    br.readLine();
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File could not be created/opened.");
        }
        catch (IOException e) {
            System.out.println("Could not read from file.");
        }
        return false;
    }
}
