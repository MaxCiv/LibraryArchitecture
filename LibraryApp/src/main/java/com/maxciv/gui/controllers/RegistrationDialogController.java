package com.maxciv.gui.controllers;

import com.maxciv.Main;
import com.maxciv.businesslogic.Role;
import com.maxciv.gui.facades.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegistrationDialogController {

    private Facade facade = Main.FACADE;
    private MainLibrarianViewController librarianViewController;

    private ObservableList<String> roles = FXCollections.observableArrayList(Role.LIBRARIAN.getRoleName(),
            Role.READER.getRoleName(), Role.SUPPLIER.getRoleName());

    @FXML private TextField loginTextField;
    @FXML private TextField nameTextField;
    @FXML private ChoiceBox<String> userRoleChoiceBox;
    @FXML private PasswordField passwordTextField;
    @FXML private PasswordField repeatPasswordTextField;
    @FXML private Label errorLabel;

    public RegistrationDialogController() {
    }

    @FXML
    private void initialize() {
        errorLabel.setText("");

        userRoleChoiceBox.setItems(roles);
        userRoleChoiceBox.setValue(Role.READER.getRoleName());
    }

    @FXML
    public void onClickRegisterButton() {
        String login, name, role, password, repeatPassword;
        login = loginTextField.getText();
        name = nameTextField.getText();
        role = userRoleChoiceBox.getValue();
        password = passwordTextField.getText();
        repeatPassword = repeatPasswordTextField.getText();

        if (login.isEmpty() || name.isEmpty() || role.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            errorLabel.setText("Input all information.");
            return;
        }

        if (!password.equals(repeatPassword)) {
            errorLabel.setText("The password must be the same in both fields.");
            return;
        }

        facade.addNewUser(login, password, name, role);

        librarianViewController.onClickRefreshButton();
        librarianViewController.setMessageOnImage(0, "Successful registration.");

        Stage stage = (Stage) loginTextField.getScene().getWindow();
        stage.close();
    }

    public void setLibrarianViewController(MainLibrarianViewController librarianViewController) {
        this.librarianViewController = librarianViewController;
    }
}
