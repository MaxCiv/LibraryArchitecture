package com.maxciv.gui.controllers;

import com.maxciv.Main;
import com.maxciv.Util;
import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookOrder;
import com.maxciv.businesslogic.exceptions.LibraryAppException;
import com.maxciv.businesslogic.exceptions.NotFoundException;
import com.maxciv.gui.facades.Facade;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.Date;

public class MainSupplierViewController {

    private Facade facade = Main.FACADE;
    private int currentSupplierId;
    private String currentSupplierLogin;
    private Object lastChosenRow = null;
    private Thread closeErrorToolBarThread;


    @FXML private Label currentSupplierIdLabel;
    @FXML private Label currentSupplierLoginLabel;
    @FXML private Label currentSupplierNameLabel;

    @FXML private ImageView infoImage;
    @FXML private ToolBar errorToolBar;
    @FXML private TextField errorTextField;

    @FXML private Label bookTitleLabel;
    @FXML private Label authorLabel;
    @FXML private Label publisherLabel;
    @FXML private Label statusLabel;
    @FXML private Label conditionLabel;
    @FXML private Label startDateLabel;
    @FXML private Label endDateLabel;
    @FXML private Label supplierLoginLabel;
    @FXML private Label publishYearLabel;
    @FXML private TextField idTextField;
    @FXML private TextField bookIdTextField;
    @FXML private TextField supplierIdTextField;

    private ObservableList<Book> allLibraryBooksObservableList = FXCollections.observableArrayList();
    @FXML private TableView<Book> allLibraryBooksTable1View;
    @FXML private TableColumn<Book, Integer> t1IdTableColumn;
    @FXML private TableColumn<Book, String> t1BookTitleTableColumn;
    @FXML private TableColumn<Book, String> t1AuthorTableColumn;
    @FXML private TableColumn<Book, String> t1PublisherTableColumn;
    @FXML private TableColumn<Book, Integer> t1PublishYearTableColumn;
    @FXML private TableColumn<Book, String> t1StatusTableColumn;
    @FXML private TableColumn<Book, String> t1ConditionTableColumn;

    private ObservableList<BookOrder> myOrderingsObservableList = FXCollections.observableArrayList();
    @FXML private TableView<BookOrder> myOrderingsTable2View;
    @FXML private TableColumn<BookOrder, Integer> t2IdTableColumn;
    @FXML private TableColumn<BookOrder, String> t2BookIdTableColumn;
    @FXML private TableColumn<BookOrder, String> t2BookTitleTableColumn;
    @FXML private TableColumn<BookOrder, String> t2StartDateTableColumn;
    @FXML private TableColumn<BookOrder, String> t2EndDateTableColumn;

    public MainSupplierViewController() {
    }

    @FXML
    private void initialize() {
    }

    public void init(int userId) {
        currentSupplierId = userId;
        try {
            currentSupplierLogin = facade.getUserLogin(userId);
            currentSupplierIdLabel.setText(String.valueOf(currentSupplierId));
            currentSupplierLoginLabel.setText(String.valueOf(currentSupplierLogin));
            currentSupplierNameLabel.setText(facade.getUserName(userId));
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }

        setUpAllLibraryBooksTable();
        setUpOrderingsTable();

        clearAllLabelsInfo();

        allLibraryBooksTable1View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    clearAllLabelsInfo();
                    if (newValue != null) showInfoAllLibraryBooksTable(newValue);
                    lastChosenRow = newValue;
                });
        myOrderingsTable2View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    clearAllLabelsInfo();
                    if (newValue != null) showInfoOrderingsTable(newValue);
                    lastChosenRow = newValue;
                });

        closeErrorToolBarThread = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                return;
            }
            errorToolBar.setVisible(false);
        });
        onClickCloseError();
        onClickRefreshButton();
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
            return;
        }
        allLibraryBooksTable1View.setItems(allLibraryBooksObservableList);
    }

    private void setUpOrderingsTable() {
        t2IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t2BookIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getBook().getId())));
        t2BookTitleTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBook().getTitle()));
        t2StartDateTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getStartDate())));
        t2EndDateTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getEndDate() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getEndDate()));
        });

        try {
            myOrderingsObservableList.addAll(facade.getAllUserOrderings(currentSupplierId));
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
        myOrderingsTable2View.setItems(myOrderingsObservableList);
    }

    private void updateAllLibraryBooksTable() {
        allLibraryBooksObservableList.removeAll(allLibraryBooksObservableList);
        try {
            allLibraryBooksObservableList.addAll(facade.getAllBooks());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO

        }
    }

    private void updateOrderingsTable() {
        myOrderingsObservableList.removeAll(myOrderingsObservableList);
        try {
            myOrderingsObservableList.addAll(facade.getAllUserOrderings(currentSupplierId));
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
    }

    @FXML
    public void onClickLogOutButton() {
        Main.showLogInDialog();
    }

    @FXML
    public void onClickFinishOrderButton() {
        try {
            facade.finishOrder(lastChosenRow);
        } catch (LibraryAppException e) {
            setMessageOnImage(1, e.getMessage());
            return;
        }
        setMessageOnImage(0, "Order successfully finished.");
        onClickRefreshButton();
    }

    @FXML
    public void onClickDenyOrderButton() {
        try {
            facade.denyOrder(lastChosenRow);
        } catch (LibraryAppException e) {
            setMessageOnImage(1, e.getMessage());
            return;
        }
        setMessageOnImage(0, "Order successfully denied.");
        onClickRefreshButton();
    }

    @FXML
    public void onClickTextFieldSelectAll(MouseEvent mouseEvent) {
        ((TextField)mouseEvent.getSource()).selectAll();
        String someId = ((TextField)mouseEvent.getSource()).getText();
        if (someId.isEmpty()) return;
        final ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(someId);
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }

    @FXML
    public void onClickRefreshButton() {
        updateAllLibraryBooksTable();
        updateOrderingsTable();
    }

    public void showInfoAllLibraryBooksTable(Book book) {
        clearAllLabelsInfo();
        idTextField.setText(String.valueOf(book.getId()));
        bookTitleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
        publisherLabel.setText(book.getPublisher());
        statusLabel.setText(book.getStatus().getStatusName());
        conditionLabel.setText(book.getCondition().getConditionName());
    }

    public void showInfoOrderingsTable(BookOrder bookOrder) {
        clearAllLabelsInfo();
        idTextField.setText(String.valueOf(bookOrder.getId()));
        bookIdTextField.setText(String.valueOf(bookOrder.getBook().getId()));
        bookTitleLabel.setText(bookOrder.getBook().getTitle());
        supplierIdTextField.setText(String.valueOf(bookOrder.getSupplier().getId()));
        startDateLabel.setText(Util.getStringFromFormattedDate(bookOrder.getStartDate()));
        if (bookOrder.getEndDate() != null) endDateLabel.setText(Util.getStringFromFormattedDate(bookOrder.getEndDate()));
        authorLabel.setText(bookOrder.getBook().getAuthor());
        publisherLabel.setText(bookOrder.getBook().getPublisher());
        statusLabel.setText(bookOrder.getBook().getStatus().getStatusName());
        conditionLabel.setText(bookOrder.getBook().getCondition().getConditionName());
    }

    public void clearAllLabelsInfo() {
        bookTitleLabel.setText("");
        authorLabel.setText("");
        publisherLabel.setText("");
        statusLabel.setText("");
        conditionLabel.setText("");
        startDateLabel.setText("");
        endDateLabel.setText("");
        supplierLoginLabel.setText("");
        publishYearLabel.setText("");

        idTextField.setText("");
        bookIdTextField.setText("");
        supplierIdTextField.setText("");
    }

    public void setMessageOnImage(int code, String message) {
        String mess = Util.getStringTimeFromFormattedDate(new Date()) + ": " + message;
        if (code == 0) {
            infoImage.setImage(new Image("/info.png"));
        } else infoImage.setImage(new Image("/error.png"));
        Tooltip tooltip = new Tooltip();
        tooltip.setText(mess);
        tooltip.setShowDelay(new Duration(0));
        Tooltip.install(infoImage, tooltip);

        errorTextField.setText(mess);
        errorToolBar.setVisible(true);

        if (closeErrorToolBarThread.isAlive()) closeErrorToolBarThread.interrupt();
        closeErrorToolBarThread = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                return;
            }
            errorToolBar.setVisible(false);
        });
        closeErrorToolBarThread.start();
    }

    @FXML
    public void onClickCloseError() {
        if (closeErrorToolBarThread.isAlive()) closeErrorToolBarThread.interrupt();
        errorToolBar.setVisible(false);
    }

    @FXML
    public void onClickInfoImage() {
        if (closeErrorToolBarThread.isAlive()) closeErrorToolBarThread.interrupt();
        if (errorToolBar.isVisible()) {
            errorToolBar.setVisible(false);
        } else errorToolBar.setVisible(true);
    }
}
