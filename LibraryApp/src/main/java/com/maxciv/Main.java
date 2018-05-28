package com.maxciv;

import com.maxciv.businesslogic.exceptions.NotFoundException;
import com.maxciv.gui.facades.CommonFacade;
import com.maxciv.gui.facades.Facade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static final Facade FACADE = new CommonFacade();
    private static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setTitle("LibraryApp");

        showLogInDialog();
    }

    public static void showLogInDialog() {
        try {
            AnchorPane root = FXMLLoader.load(Main.class.getResource("/view/LogInDialog.fxml"));

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showMainView(int userId) {
        String userRole = "";
        try {
            userRole = FACADE.getUserRole(userId);
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
        switch (userRole) {
            case "Librarian":
                showMainLibrarianView(userId);
                break;
            case "Reader":
                showMainReaderView(userId);
                break;
            case "Supplier":
                showMainSupplierView(userId);
                break;
            default:
                //TODO
        }
    }

    private static void showMainLibrarianView(int userId) {

    }

    private static void showMainReaderView(int userId) {

    }

    private static void showMainSupplierView(int userId) {

    }
}
