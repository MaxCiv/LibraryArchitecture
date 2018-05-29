package com.maxciv.gui.controllers;

import com.maxciv.Main;
import com.maxciv.Util;
import com.maxciv.businesslogic.Condition;
import com.maxciv.businesslogic.Status;
import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookBorrow;
import com.maxciv.businesslogic.entities.libraryrecords.BookExchange;
import com.maxciv.businesslogic.entities.libraryrecords.BookOrder;
import com.maxciv.businesslogic.entities.libraryrecords.BookRecord;
import com.maxciv.businesslogic.entities.users.Supplier;
import com.maxciv.businesslogic.entities.users.User;
import com.maxciv.businesslogic.exceptions.NotFoundException;
import com.maxciv.gui.facades.Facade;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainLibrarianViewController {



    private Facade facade = Main.FACADE;
    private int currentLibrarianId;
    private String currentLibrarianLogin;
    private Object lastChosenRow = null;

    @FXML private Label currentLibrarianIdLabel;
    @FXML private Label currentLibrarianLoginLabel;
    @FXML private Label currentLibrarianNameLabel;

    @FXML private ImageView infoImage;

    @FXML private Label bookTitleLabel;
    @FXML private Label authorLabel;
    @FXML private Label publisherLabel;
    @FXML private Label statusLabel;
    @FXML private Label conditionLabel;
    @FXML private Label startDateLabel;
    @FXML private Label endDateLabel;
    @FXML private Label openExchangeDateLabel;
    @FXML private Label readerLoginLabel;
    @FXML private Label ownerLoginLabel;
    @FXML private TextField idTextField;
    @FXML private TextField bookIdTextField;
    @FXML private TextField readerIdTextField;
    @FXML private TextField supplierIdTextField;
    @FXML private TextField ownerIdTextField;

    private ObservableList<Book> allLibraryBooksObservableList = FXCollections.observableArrayList();
    @FXML private TableView<Book> allLibraryBooksTable1View;
    @FXML private TableColumn<Book, Integer> t1IdTableColumn;
    @FXML private TableColumn<Book, String> t1BookTitleTableColumn;
    @FXML private TableColumn<Book, String> t1AuthorTableColumn;
    @FXML private TableColumn<Book, String> t1PublisherTableColumn;
    @FXML private TableColumn<Book, Integer> t1PublishYearTableColumn;
    @FXML private TableColumn<Book, String> t1StatusTableColumn;
    @FXML private TableColumn<Book, String> t1ConditionTableColumn;

    private ObservableList<BookBorrow> borrowingsObservableList = FXCollections.observableArrayList();
    @FXML private TableView<BookBorrow> borrowingsTable2View;
    @FXML private TableColumn<BookBorrow, Integer> t2IdTableColumn;
    @FXML private TableColumn<BookBorrow, String> t2BookIdTableColumn;
    @FXML private TableColumn<BookBorrow, String> t2BookTitleTableColumn;
    @FXML private TableColumn<BookBorrow, String> t2ReaderIdTableColumn;
    @FXML private TableColumn<BookBorrow, String> t2ReaderLoginTableColumn;
    @FXML private TableColumn<BookBorrow, String> t2StartDateTableColumn;
    @FXML private TableColumn<BookBorrow, String> t2EndDateTableColumn;

    private ObservableList<BookExchange> exchangesObservableList = FXCollections.observableArrayList();
    @FXML private TableView<BookExchange> exchangesTable3View;
    @FXML private TableColumn<BookExchange, Integer> t3IdTableColumn;
    @FXML private TableColumn<BookExchange, String> t3BookIdTableColumn;
    @FXML private TableColumn<BookExchange, String> t3BookTitleTableColumn;
    @FXML private TableColumn<BookExchange, String> t3OwnerIdTableColumn;
    @FXML private TableColumn<BookExchange, String> t3OwnerLoginTableColumn;
    @FXML private TableColumn<BookExchange, String> t3OpenExchangeTableColumn;
    @FXML private TableColumn<BookExchange, String> t3ReaderIdTableColumn;
    @FXML private TableColumn<BookExchange, String> t3ReaderLoginTableColumn;
    @FXML private TableColumn<BookExchange, String> t3StartDateTableColumn;
    @FXML private TableColumn<BookExchange, String> t3EndDateTableColumn;

    private ObservableList<BookOrder> orderingsObservableList = FXCollections.observableArrayList();
    @FXML private TableView<BookOrder> orderingsTable4View;
    @FXML private TableColumn<BookOrder, Integer> t4IdTableColumn;
    @FXML private TableColumn<BookOrder, String> t4BookIdTableColumn;
    @FXML private TableColumn<BookOrder, String> t4BookTitleTableColumn;
    @FXML private TableColumn<BookOrder, String> t4SupplierIdTableColumn;
    @FXML private TableColumn<BookOrder, String> t4SupplierLoginTableColumn;
    @FXML private TableColumn<BookOrder, String> t4StartDateTableColumn;
    @FXML private TableColumn<BookOrder, String> t4EndDateTableColumn;

    private ObservableList<BookRecord> requireConfirmationObservableList = FXCollections.observableArrayList();
    @FXML private TableView<BookRecord> requireConfirmationTable5View;
    @FXML private TableColumn<BookRecord, Integer> t5IdTableColumn;
    @FXML private TableColumn<BookRecord, String> t5BookIdTableColumn;
    @FXML private TableColumn<BookRecord, String> t5BookTitleTableColumn;
    @FXML private TableColumn<BookRecord, String> t5OwnerIdTableColumn;
    @FXML private TableColumn<BookRecord, String> t5OwnerLoginTableColumn;
    @FXML private TableColumn<BookRecord, String> t5OpenExchangeDateTableColumn;
    @FXML private TableColumn<BookRecord, String> t5ReaderIdTableColumn;
    @FXML private TableColumn<BookRecord, String> t5ReaderLoginTableColumn;

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
        setUpBorrowingsTable();
        setUpExchangesTable();
        setUpOrderingsTable();
        setUpRequireConfirmationTable();
        setUpUsersTable();

        clearAllLabelsInfo();

        allLibraryBooksTable1View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    clearAllLabelsInfo();
                    if (newValue != null) showInfoAllLibraryBooksTable(newValue);
                    lastChosenRow = newValue;
                });
        borrowingsTable2View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    clearAllLabelsInfo();
                    if (newValue != null) showInfoBorrowingsTable(newValue);
                    lastChosenRow = newValue;
                });
        exchangesTable3View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    clearAllLabelsInfo();
                    if (newValue != null) showInfoExchangesTable(newValue);
                    lastChosenRow = newValue;
                });
        orderingsTable4View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    clearAllLabelsInfo();
                    if (newValue != null) showInfoOrderingsTable(newValue);
                    lastChosenRow = newValue;
                });
        requireConfirmationTable5View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    clearAllLabelsInfo();
                    if (newValue != null) showInfoRequireConfirmationTable(newValue);
                    lastChosenRow = newValue;
                });
        usersTable6View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    clearAllLabelsInfo();
                    if (newValue != null) showInfoUsersTable(newValue);
                    lastChosenRow = newValue;
                });

        //setMessageOnImage(1, "Error!");
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

    private void setUpBorrowingsTable() {
        t2IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t2BookIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getBook().getId())));
        t2BookTitleTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBook().getTitle()));
        t2ReaderIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getReader().getId())));
        t2ReaderLoginTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getReader().getLogin()));
        t2StartDateTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getStartDate() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getStartDate()));
        });
        t2EndDateTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getEndDate() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getEndDate()));
        });

        try {
            borrowingsObservableList.addAll(facade.getAllBorrows());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
        borrowingsTable2View.setItems(borrowingsObservableList);
    }

    private void setUpExchangesTable() {
        t3IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t3BookIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getBook().getId())));
        t3BookTitleTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBook().getTitle()));
        t3OwnerIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getOwner().getId())));
        t3OwnerLoginTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOwner().getLogin()));
        t3OpenExchangeTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getOpenExchangeDate() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getOpenExchangeDate()));
        });
        t3ReaderIdTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getReader() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(String.valueOf(cell.getValue().getReader().getId()));
        });
        t3ReaderLoginTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getReader() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(cell.getValue().getReader().getLogin());
        });
        t3StartDateTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getStartDate() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getStartDate()));
        });
        t3EndDateTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getEndDate() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getEndDate()));
        });

        try {
            exchangesObservableList.addAll(facade.getAllExchanges());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
        exchangesTable3View.setItems(exchangesObservableList);
    }

    private void setUpOrderingsTable() {
        t4IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t4BookIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getBook().getId())));
        t4BookTitleTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBook().getTitle()));
        t4SupplierIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getSupplier().getId())));
        t4SupplierLoginTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSupplier().getLogin()));
        t4StartDateTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getStartDate())));
        t4EndDateTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getEndDate() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getEndDate()));
        });

        try {
            orderingsObservableList.addAll(facade.getAllOrderings());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
        orderingsTable4View.setItems(orderingsObservableList);
    }

    private void setUpRequireConfirmationTable() {
        t5IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t5BookIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getBook().getId())));
        t5BookTitleTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBook().getTitle()));
        t5OwnerIdTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getClass() != BookExchange.class) return new SimpleStringProperty("");
            return new SimpleStringProperty(String.valueOf(((BookExchange)cell.getValue()).getOwner().getId()));
        });
        t5OwnerLoginTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getClass() != BookExchange.class) return new SimpleStringProperty("");
            return new SimpleStringProperty(((BookExchange)cell.getValue()).getOwner().getLogin());
        });
        t5OpenExchangeDateTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getClass() != BookExchange.class) return new SimpleStringProperty("");
            return new SimpleStringProperty(Util.getStringFromFormattedDate(((BookExchange)cell.getValue()).getOpenExchangeDate()));
        });
        t5ReaderIdTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getClass() != BookExchange.class)
                return new SimpleStringProperty(String.valueOf(((BookBorrow)cell.getValue()).getReader().getId()));
            return new SimpleStringProperty(String.valueOf(((BookExchange)cell.getValue()).getReader().getId()));
        });
        t5ReaderLoginTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getClass() != BookExchange.class)
                return new SimpleStringProperty(((BookBorrow)cell.getValue()).getReader().getLogin());
            return new SimpleStringProperty(((BookExchange)cell.getValue()).getReader().getLogin());
        });

        try {
            requireConfirmationObservableList.addAll(facade.getAllRequireConfirmation());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
        requireConfirmationTable5View.setItems(requireConfirmationObservableList);
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

    private void updateBorrowingsTable() {
        borrowingsObservableList.removeAll(borrowingsObservableList);
        try {
            borrowingsObservableList.addAll(facade.getAllBorrows());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
    }

    private void updateExchangesTable() {
        exchangesObservableList.removeAll(exchangesObservableList);
        try {
            exchangesObservableList.addAll(facade.getAllExchanges());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
    }

    private void updateOrderingsTable() {
        orderingsObservableList.removeAll(orderingsObservableList);
        try {
            orderingsObservableList.addAll(facade.getAllOrderings());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
    }

    private void updateRequireConfirmationTable() {
        requireConfirmationObservableList.removeAll(requireConfirmationObservableList);
        try {
            requireConfirmationObservableList.addAll(facade.getAllRequireConfirmation());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
    }

    private void updateUsersTable() {
        usersObservableList.removeAll(usersObservableList);
        try {
            usersObservableList.addAll(facade.getAllUsers());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
    }

    @FXML
    public void onClickRefreshButton() {
        updateAllLibraryBooksTable();
        updateBorrowingsTable();
        updateExchangesTable();
        updateOrderingsTable();
        updateRequireConfirmationTable();
        updateUsersTable();
    }

    @FXML
    public void onClickLogOutButton() {
        Main.showLogInDialog();
    }

    @FXML
    public void onClickRegisterNewUserButton() {
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
        ((TextField)mouseEvent.getSource()).selectAll();
    }

    @FXML
    public void onClickCloseExchange() {

    }

    @FXML
    public void onClickConfirmExchange() {

    }

    @FXML
    public void onClickCloseBorrowing() {

    }

    @FXML
    public void onClickConfirmBorrowingButton() {

    }

    @FXML
    public void onClickOrderTheBookButton() {
        String bookId = "", supplierId = "";
        if (lastChosenRow != null && lastChosenRow.getClass().getSimpleName().equals("Book")) {
            if (((Book)lastChosenRow).getStatus() == Status.ORDER && ((Book)lastChosenRow).getCondition() == Condition.NOT_AVAILABLE)
                bookId = String.valueOf(((Book)lastChosenRow).getId());
        }
        if (lastChosenRow != null && lastChosenRow.getClass().getSimpleName().equals("Supplier")) {
            supplierId = String.valueOf(((Supplier)lastChosenRow).getId());
        }

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/OrderBookDialog.fxml"));
            AnchorPane root = loader.load();

            OrderBookDialogController orderBookDialogController = loader.getController();
            orderBookDialogController.init(bookId, supplierId);

            Scene scene = new Scene(root);

            Stage newWindow = new Stage();
            newWindow.setTitle("New Order");
            newWindow.setScene(scene);
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();    //TODO
        }
    }

    @FXML
    public void onClickAddNewBookButton() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/AddBookDialog.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);

            Stage newWindow = new Stage();
            newWindow.setTitle("New Book");
            newWindow.setScene(scene);
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();    //TODO
        }
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

    public void showInfoBorrowingsTable(BookBorrow bookBorrow) {
        clearAllLabelsInfo();
        idTextField.setText(String.valueOf(bookBorrow.getId()));
        bookIdTextField.setText(String.valueOf(bookBorrow.getBook().getId()));
        bookTitleLabel.setText(bookBorrow.getBook().getTitle());
        readerIdTextField.setText(String.valueOf(bookBorrow.getReader().getId()));
        readerLoginLabel.setText(bookBorrow.getReader().getLogin());
        if (bookBorrow.getStartDate() != null) startDateLabel.setText(Util.getStringFromFormattedDate(bookBorrow.getStartDate()));
        if (bookBorrow.getEndDate() != null) endDateLabel.setText(Util.getStringFromFormattedDate(bookBorrow.getEndDate()));
        authorLabel.setText(bookBorrow.getBook().getAuthor());
        publisherLabel.setText(bookBorrow.getBook().getPublisher());
        statusLabel.setText(bookBorrow.getBook().getStatus().getStatusName());
        conditionLabel.setText(bookBorrow.getBook().getCondition().getConditionName());
    }

    public void showInfoExchangesTable(BookExchange bookExchange) {
        clearAllLabelsInfo();
        idTextField.setText(String.valueOf(bookExchange.getId()));
        bookIdTextField.setText(String.valueOf(bookExchange.getBook().getId()));
        bookTitleLabel.setText(bookExchange.getBook().getTitle());
        ownerIdTextField.setText(String.valueOf(bookExchange.getOwner().getId()));
        ownerLoginLabel.setText(bookExchange.getOwner().getLogin());
        if (bookExchange.getOpenExchangeDate() != null) openExchangeDateLabel.setText(Util.getStringFromFormattedDate(bookExchange.getOpenExchangeDate()));
        if (bookExchange.getReader() != null) readerIdTextField.setText(String.valueOf(bookExchange.getReader().getId()));
        if (bookExchange.getReader() != null) readerLoginLabel.setText(bookExchange.getReader().getLogin());
        if (bookExchange.getStartDate() != null) startDateLabel.setText(Util.getStringFromFormattedDate(bookExchange.getStartDate()));
        if (bookExchange.getEndDate() != null) endDateLabel.setText(Util.getStringFromFormattedDate(bookExchange.getEndDate()));
        authorLabel.setText(bookExchange.getBook().getAuthor());
        publisherLabel.setText(bookExchange.getBook().getPublisher());
        statusLabel.setText(bookExchange.getBook().getStatus().getStatusName());
        conditionLabel.setText(bookExchange.getBook().getCondition().getConditionName());
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

    public void showInfoRequireConfirmationTable(BookRecord bookRecord) {
        clearAllLabelsInfo();
        idTextField.setText(String.valueOf(bookRecord.getId()));
        bookIdTextField.setText(String.valueOf(bookRecord.getBook().getId()));
        bookTitleLabel.setText(bookRecord.getBook().getTitle());
        if (bookRecord.getClass() == BookExchange.class) ownerIdTextField.setText(String.valueOf(((BookExchange)bookRecord).getOwner().getId()));
        if (bookRecord.getClass() == BookExchange.class) ownerLoginLabel.setText(((BookExchange)bookRecord).getOwner().getLogin());
        if (bookRecord.getClass() == BookExchange.class) openExchangeDateLabel.setText(Util.getStringFromFormattedDate(((BookExchange)bookRecord).getOpenExchangeDate()));
        if (bookRecord.getClass() == BookExchange.class) readerIdTextField.setText(String.valueOf(((BookExchange)bookRecord).getReader().getId()));
        if (bookRecord.getClass() == BookExchange.class) readerLoginLabel.setText(((BookExchange)bookRecord).getReader().getLogin());
        if (bookRecord.getClass() == BookBorrow.class) readerIdTextField.setText(String.valueOf(((BookBorrow)bookRecord).getReader().getId()));
        if (bookRecord.getClass() == BookBorrow.class) readerLoginLabel.setText(((BookBorrow)bookRecord).getReader().getLogin());
        authorLabel.setText(bookRecord.getBook().getAuthor());
        publisherLabel.setText(bookRecord.getBook().getPublisher());
        statusLabel.setText(bookRecord.getBook().getStatus().getStatusName());
        conditionLabel.setText(bookRecord.getBook().getCondition().getConditionName());
    }

    public void showInfoUsersTable(User user) {
        clearAllLabelsInfo();
        idTextField.setText(String.valueOf(user.getId()));
        readerLoginLabel.setText(user.getLogin());
    }

    public void clearAllLabelsInfo() {
        bookTitleLabel.setText("");
        authorLabel.setText("");
        publisherLabel.setText("");
        statusLabel.setText("");
        conditionLabel.setText("");
        startDateLabel.setText("");
        endDateLabel.setText("");
        openExchangeDateLabel.setText("");
        readerLoginLabel.setText("");
        ownerLoginLabel.setText("");

        idTextField.setText("");
        bookIdTextField.setText("");
        readerIdTextField.setText("");
        supplierIdTextField.setText("");
        ownerIdTextField.setText("");
    }

    public void setMessageOnImage(int code, String message) {
        if (code == 0) {
            infoImage.setImage(new Image("/info.png"));
        } else infoImage.setImage(new Image("/error.png"));
        Tooltip tooltip = new Tooltip();
        tooltip.setText(message);
        tooltip.setShowDelay(new Duration(0));
        Tooltip.install(infoImage, tooltip);
    }
}
