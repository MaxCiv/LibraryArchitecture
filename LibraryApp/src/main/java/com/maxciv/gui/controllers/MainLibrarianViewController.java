package com.maxciv.gui.controllers;

import com.maxciv.Main;
import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.users.User;
import com.maxciv.businesslogic.exceptions.NotFoundException;
import com.maxciv.gui.facades.Facade;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainLibrarianViewController {

    private Facade facade = Main.FACADE;
    private int currentLibrarianId;
    private String currentLibrarianLogin;

    @FXML private Label currentLibrarianIdLabel;
    @FXML private Label currentLibrarianLoginLabel;
    @FXML private Label currentLibrarianNameLabel;

    private ObservableList<Book> allLibraryBooksObservableList = FXCollections.observableArrayList();
    @FXML private TableView<Book> allLibraryBooksTable1View;
    @FXML private TableColumn<Book, Integer> t1IdTableColumn;
    @FXML private TableColumn<Book, String> t1BookTitleTableColumn;
    @FXML private TableColumn<Book, String> t1AuthorTableColumn;
    @FXML private TableColumn<Book, String> t1PublisherTableColumn;
    @FXML private TableColumn<Book, Integer> t1PublishYearTableColumn;
    @FXML private TableColumn<Book, String> t1StatusTableColumn;
    @FXML private TableColumn<Book, String> t1ConditionTableColumn;

    private ObservableList<User> usersObservableList = FXCollections.observableArrayList();
    @FXML private TableView<User> usersTable6View;
    @FXML private TableColumn<Book, Integer> t6IdTableColumn;
    @FXML private TableColumn<Book, String>  t6LoginTableColumn;
    @FXML private TableColumn<Book, String>  t6NameTableColumn;
    @FXML private TableColumn<Book, String>  t6RoleTableColumn;

    public MainLibrarianViewController() {
    }

    @FXML
    private void initialize() {
    }

    public void init(int userId) {
        currentLibrarianId = userId;
        try {
            currentLibrarianLogin = facade.getUserLogin(userId);
            currentLibrarianIdLabel.setText(String.valueOf(currentLibrarianId));
            currentLibrarianLoginLabel.setText(String.valueOf(currentLibrarianLogin));
            currentLibrarianNameLabel.setText(facade.getUserName(userId));
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }

        setUpAllLibraryBooksTable();
        setUpUsersTable();
    }

    private void setUpAllLibraryBooksTable() {
        t1IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t1BookTitleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        t1AuthorTableColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        t1PublisherTableColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        t1PublishYearTableColumn.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
        t1StatusTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        t1ConditionTableColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));

        try {
            allLibraryBooksObservableList.addAll(facade.getAllBooks());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
        allLibraryBooksTable1View.setItems(allLibraryBooksObservableList);
    }

    private void setUpUsersTable() {
        t6IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t6LoginTableColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        t6NameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        t6RoleTableColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        try {
            usersObservableList.addAll(facade.getAllUsers());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
        usersTable6View.setItems(usersObservableList);
    }

    private void updateAllLibraryBooksTable() {
        allLibraryBooksObservableList.removeAll(allLibraryBooksObservableList);
        try {
            allLibraryBooksObservableList.addAll(facade.getAllBooks());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
    }

    private void updateUsersTableTable() {
        usersObservableList.removeAll(usersObservableList);
        try {
            usersObservableList.addAll(facade.getAllUsers());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
    }

    @FXML
    public void onSelectionChangedTabUpdate(Event event) {

    }

    @FXML
    public void onClickRefreshButton(ActionEvent actionEvent) {
        updateAllLibraryBooksTable();
        updateUsersTableTable();
    }

    @FXML
    public void onClickLogOutButton(MouseEvent mouseEvent) {

    }

    @FXML
    public void onClickRegisterNewUserButton(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/RegistrationDialog.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);

            Stage newWindow = new Stage();
            newWindow.setTitle("New User Registration");
            newWindow.setScene(scene);
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();    //TODO
        }
    }

    @FXML
    public void onClickTextFieldSelectAll(MouseEvent mouseEvent) {

    }

    @FXML
    public void onClickCloseExchange(MouseEvent mouseEvent) {

    }

    @FXML
    public void onClickConfirmExchange(MouseEvent mouseEvent) {

    }

    @FXML
    public void onClickCloseBorrowing(MouseEvent mouseEvent) {

    }

    @FXML
    public void onClickConfirmBorrowingButton(MouseEvent mouseEvent) {

    }

    @FXML
    public void onClickOrderTheBookButton(MouseEvent mouseEvent) {

    }

    @FXML
    public void onClickAddNewBookButton(MouseEvent mouseEvent) {

    }
}
