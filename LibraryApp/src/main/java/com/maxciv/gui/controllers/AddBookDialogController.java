package com.maxciv.gui.controllers;

import com.maxciv.Main;
import com.maxciv.businesslogic.Role;
import com.maxciv.businesslogic.Status;
import com.maxciv.businesslogic.exceptions.NotFoundException;
import com.maxciv.gui.facades.Facade;
import com.maxciv.gui.facades.ServiceFacade;
import com.maxciv.service.googlebooks.GoogleBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AddBookDialogController {

    private Facade facade = Main.FACADE;
    private ServiceFacade serviceFacade = new ServiceFacade();
    private MainLibrarianViewController librarianViewController;
    private ExecutorService executor = Executors.newFixedThreadPool(2);
    private Future lastFuture;
    private ObservableList<String> statuses = FXCollections.observableArrayList(Status.LIBRARY.getStatusName(),
            Status.EXCHANGE.getStatusName(), Status.ORDER.getStatusName());

    @FXML private TextField titleTextField;
    @FXML private TextField authorTextField;
    @FXML private TextField publisherTextField;
    @FXML private TextField publishYearTextField;
    @FXML private ChoiceBox<String> statusChoiceBox;
    @FXML private TextField ownerIdTextField;
    @FXML private Label errorLabel;

    private ObservableList<GoogleBook> googleBookObservableList = FXCollections.observableArrayList();
    @FXML private TableView<GoogleBook> googleBooksTableView;
    @FXML private TableColumn<GoogleBook, String> titleTableColumn;
    @FXML private TableColumn<GoogleBook, String> authorsTableColumn;
    @FXML private TableColumn<GoogleBook, String> publisherTableColumn;
    @FXML private TableColumn<GoogleBook, String> publishYearTableColumn;

    public AddBookDialogController() {
    }

    @FXML
    private void initialize() {
        errorLabel.setText("");

        statusChoiceBox.setItems(statuses);
        statusChoiceBox.setValue(Status.LIBRARY.getStatusName());

        setGoogleBooksTable();

        googleBooksTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        titleTextField.setText(newValue.getTitle());
                        authorTextField.setText(newValue.getAuthor());
                        publisherTextField.setText(newValue.getPublisher());
                        publishYearTextField.setText(newValue.getPublishYear());
                    }
                });

        titleTextField.textProperty().addListener((observable, oldValue, newValue) -> onActionTitleTextField());
        authorTextField.textProperty().addListener((observable, oldValue, newValue) -> onActionAuthorTextField());
    }

    private void setGoogleBooksTable() {
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorsTableColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherTableColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        publishYearTableColumn.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
        googleBooksTableView.setItems(googleBookObservableList);
    }

    @FXML
    private void onClickAddButton() {
        String title, author, publisher;
        int publishYear, statusInt, ownerId;

        title = titleTextField.getText();
        author = authorTextField.getText();
        publisher = publisherTextField.getText();
        if (title == null || title.isEmpty() || author == null || author.isEmpty() || publisher == null || publisher.isEmpty()) {
            errorLabel.setText("Input all information.");
            return;
        }
        try {
            publishYear = Integer.parseInt(publishYearTextField.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("The publish year must be a number.");
            return;
        }
        statusInt = Status.valueOfString(statusChoiceBox.getValue()).getStatusId();
        if (statusInt == Status.EXCHANGE.getStatusId()) {
            try {
                ownerId = Integer.parseInt(ownerIdTextField.getText());
            } catch (NumberFormatException e) {
                errorLabel.setText("The owner ID must be a number.");
                return;
            }
            try {
                if (Role.valueOfString(facade.getUserRole(ownerId)) != Role.READER) {
                    errorLabel.setText("The owner must be a reader.");
                    return;
                }
            } catch (NotFoundException e) {
                errorLabel.setText(e.getMessage());
                return;
            }
            facade.addNewBook(title, author, publisher, publishYear, statusInt, ownerId);
        } else {
            facade.addNewBook(title, author, publisher, publishYear, statusInt, -1);
        }

        librarianViewController.onClickRefreshButton();
        librarianViewController.setMessageOnImage(0, "Book added successfully.");

        Stage stage = (Stage) titleTextField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onClickSelectAll(MouseEvent mouseEvent) {
        ((TextField)mouseEvent.getSource()).selectAll();
    }

    @FXML
    private void onActionTitleTextField() {
        if (lastFuture != null) lastFuture.cancel(true);
        lastFuture = executor.submit(() -> {
            if (titleTextField.getText().length() > 3) searchGoogleBooksByTitle(titleTextField.getText());
        });
    }

    @FXML
    private void onActionAuthorTextField() {
        if (lastFuture != null) lastFuture.cancel(true);
        lastFuture = executor.submit(() -> {
            if (authorTextField.getText().length() > 3) searchGoogleBooksByAuthor(authorTextField.getText());
        });
    }

    private void searchGoogleBooksByTitle(String title) {
        List<GoogleBook> googleBooks = serviceFacade.searchGoogleBooksByTitle(title);
        googleBookObservableList.removeAll(googleBookObservableList);
        googleBookObservableList.addAll(googleBooks);
    }

    private void searchGoogleBooksByAuthor(String author) {
        List<GoogleBook> googleBooks = serviceFacade.searchGoogleBooksByAuthor(author);
        googleBookObservableList.removeAll(googleBookObservableList);
        googleBookObservableList.addAll(googleBooks);
    }

    public void setLibrarianViewController(MainLibrarianViewController librarianViewController) {
        this.librarianViewController = librarianViewController;
    }

    public void onCloseWindow() {
        executor.shutdownNow();
    }
}
