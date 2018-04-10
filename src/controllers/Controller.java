package controllers;

import javafx.scene.paint.Color;
import views.*;

import java.io.*;

public class Controller {
    private LoginView login;
    private MainScreen mainScreen;
    private ProjectView project;
    private TaskView task;

    public Controller (View view) {
        if (view instanceof LoginView) {
            this.login = (LoginView) view;
        }
        else if (view instanceof MainScreen) {
            this.mainScreen = (MainScreen) view;
        }
        else if (view instanceof ProjectView) {
            this.project = (ProjectView) view;
        }
        else if (view instanceof TaskView) {
            this.task = (TaskView) view;
        }
    }

    public void handleLogin(String username, String password) {
        try {
            File file = new File("accounts.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line1 = null;
            String line2 = null;

            while ((line1 = br.readLine()) != null && (line2 = br.readLine()) != null) {
                if (username.equals(line1) && password.equals(line2)) {
                    this.login.getSignInMessage().setFill(Color.GREEN);
                    this.login.getSignInMessage().setText("Sign in success!");
                    break;
                }
                else {
                    this.login.getSignInMessage().setFill(Color.RED);
                    this.login.getSignInMessage().setText("Sign in failed.");
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
    }
}
