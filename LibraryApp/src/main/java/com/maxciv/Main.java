package com.maxciv;

import com.maxciv.businesslogic.exceptions.NotFoundException;
import com.maxciv.gui.controllers.MainLibrarianViewController;
import com.maxciv.gui.controllers.MainReaderViewController;
import com.maxciv.gui.controllers.MainSupplierViewController;
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
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/MainLibrarianView.fxml"));
            AnchorPane root = loader.load();
            MainLibrarianViewController librarianViewController = loader.getController();
            librarianViewController.init(userId);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();    //TODO
        }
    }

    private static void showMainReaderView(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/MainReaderView.fxml"));
            AnchorPane root = loader.load();
            MainReaderViewController readerViewController = loader.getController();
            readerViewController.init(userId);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();    //TODO
        }
    }

    private static void showMainSupplierView(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/MainSupplierView.fxml"));
            AnchorPane root = loader.load();
            MainSupplierViewController supplierViewController = loader.getController();
            supplierViewController.init(userId);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();    //TODO
        }
    }
}
