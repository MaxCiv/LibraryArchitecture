package com.maxciv.gui.controllers;

import com.maxciv.Main;
import com.maxciv.businesslogic.exceptions.LibraryAppException;
import com.maxciv.gui.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OrderBookDialogController {

    private Facade facade = Main.FACADE;

    @FXML private TextField bookIdTextField;
    @FXML private TextField supplierIdTextField;
    @FXML private Label errorLabel;

    public OrderBookDialogController() {
    }

    @FXML
    private void initialize() {
        errorLabel.setText("");
    }

    public void init(String bookId, String supplierId) {
        bookIdTextField.setText(bookId);
        supplierIdTextField.setText(supplierId);
    }

    public void onClickOpenNewOrderButton() {
        int bookId, supplierId;
        try {
            bookId = Integer.parseInt(bookIdTextField.getText());
            supplierId = Integer.parseInt(supplierIdTextField.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("Incorrect input.");
            return;
        }
        try {
            facade.openNewOrder(bookId, supplierId);
        } catch (LibraryAppException e) {
            errorLabel.setText(e.getMessage());
            return;
        }

        Stage stage = (Stage) errorLabel.getScene().getWindow();
        stage.close();
    }
}
