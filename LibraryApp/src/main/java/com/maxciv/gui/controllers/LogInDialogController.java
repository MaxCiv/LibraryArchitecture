package com.maxciv.gui.controllers;

import com.maxciv.Main;
import com.maxciv.businesslogic.exceptions.LogInErrorException;
import com.maxciv.gui.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInDialogController {

    private Facade facade = Main.FACADE;

    @FXML private TextField loginTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private Label errorLabel;
    @FXML private Button logInButton;

    public LogInDialogController() {
    }

    @FXML
    private void initialize() {
        errorLabel.setText("");
    }

    @FXML
    private void onClickLogInButton() {
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        if (login.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Enter login and password.");
            return;
        }
        try {
            int userId = facade.logInUser(login, password);
            Main.showMainView(userId);
            errorLabel.setText("All good, congrats.");
        } catch (LogInErrorException e) {   //LogInErrorException
            errorLabel.setText(e.getMessage());
        }
    }
}
