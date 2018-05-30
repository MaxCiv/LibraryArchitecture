package com.maxciv.gui.controllers;

import com.maxciv.Main;
import com.maxciv.businesslogic.Role;
import com.maxciv.businesslogic.Status;
import com.maxciv.businesslogic.exceptions.NotFoundException;
import com.maxciv.gui.facades.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddBookDialogController {

    private Facade facade = Main.FACADE;
    private MainLibrarianViewController librarianViewController;
    private ObservableList<String> statuses = FXCollections.observableArrayList(Status.LIBRARY.getStatusName(),
            Status.EXCHANGE.getStatusName(), Status.ORDER.getStatusName());

    @FXML private TextField titleTextField;
    @FXML private TextField authorTextField;
    @FXML private TextField publisherTextField;
    @FXML private TextField publishYearTextField;
    @FXML private ChoiceBox<String> statusChoiceBox;
    @FXML private TextField ownerIdTextField;
    @FXML private Label errorLabel;

    public AddBookDialogController() {
    }

    @FXML
    private void initialize() {
        errorLabel.setText("");

        statusChoiceBox.setItems(statuses);
        statusChoiceBox.setValue(Status.LIBRARY.getStatusName());
    }

    @FXML
    public void onClickAddButton() {
        String title, author, publisher;
        int publishYear, statusInt, ownerId;

        title = titleTextField.getText();
        author = authorTextField.getText();
        publisher = publisherTextField.getText();
        if (title.isEmpty() || author.isEmpty() || publisher.isEmpty()) {
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

    public void setLibrarianViewController(MainLibrarianViewController librarianViewController) {
        this.librarianViewController = librarianViewController;
    }
}
