package com.maxciv.gui.controllers;

import com.maxciv.Main;
import com.maxciv.Util;
import com.maxciv.businesslogic.entities.Book;
import com.maxciv.businesslogic.entities.libraryrecords.BookBorrow;
import com.maxciv.businesslogic.entities.libraryrecords.BookExchange;
import com.maxciv.businesslogic.entities.users.Reader;
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

public class MainReaderViewController {

    private Facade facade = Main.FACADE;
    private int currentReaderId;
    private String currentReaderLogin;
    private Object lastChosenRow = null;
    private Thread closeErrorToolBarThread;

    @FXML private Label currentReaderIdLabel;
    @FXML private Label currentReaderLoginLabel;
    @FXML private Label currentReaderNameLabel;
    @FXML private Label countOfBooksAvailableByExchangeLabel;

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
    @FXML private Label openExchangeDateLabel;
    @FXML private Label readerLoginLabel;
    @FXML private Label ownerLoginLabel;
    @FXML private TextField idTextField;
    @FXML private TextField bookIdTextField;
    @FXML private TextField readerIdTextField;
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

    private ObservableList<BookBorrow> myBorrowingsObservableList = FXCollections.observableArrayList();
    @FXML private TableView<BookBorrow> myBorrowingsTable2View;
    @FXML private TableColumn<BookBorrow, Integer> t2IdTableColumn;
    @FXML private TableColumn<BookBorrow, String> t2BookIdTableColumn;
    @FXML private TableColumn<BookBorrow, String> t2BookTitleTableColumn;
    @FXML private TableColumn<BookBorrow, String> t2StartDateTableColumn;
    @FXML private TableColumn<BookBorrow, String> t2EndDateTableColumn;

    private ObservableList<BookExchange> myExchangesObservableList = FXCollections.observableArrayList();
    @FXML private TableView<BookExchange> myExchangesTable3View;
    @FXML private TableColumn<BookExchange, Integer> t3IdTableColumn;
    @FXML private TableColumn<BookExchange, String> t3BookIdTableColumn;
    @FXML private TableColumn<BookExchange, String> t3BookTitleTableColumn;
    @FXML private TableColumn<BookExchange, String> t3OwnerIdTableColumn;
    @FXML private TableColumn<BookExchange, String> t3OwnerLoginTableColumn;
    @FXML private TableColumn<BookExchange, String> t3OpenExchangeDateTableColumn;
    @FXML private TableColumn<BookExchange, String> t3ReaderIdTableColumn;
    @FXML private TableColumn<BookExchange, String> t3ReaderLoginTableColumn;
    @FXML private TableColumn<BookExchange, String> t3StartDateTableColumn;
    @FXML private TableColumn<BookExchange, String> t3EndDateTableColumn;

    private ObservableList<BookExchange> exchangesObservableList = FXCollections.observableArrayList();
    @FXML private TableView<BookExchange> exchangesTable4View;
    @FXML private TableColumn<BookExchange, Integer> t4IdTableColumn;
    @FXML private TableColumn<BookExchange, String> t4BookIdTableColumn;
    @FXML private TableColumn<BookExchange, String> t4BookTitleTableColumn;
    @FXML private TableColumn<BookExchange, String> t4OwnerIdTableColumn;
    @FXML private TableColumn<BookExchange, String> t4OwnerLoginTableColumn;
    @FXML private TableColumn<BookExchange, String> t4OpenExchangeDateTableColumn;
    @FXML private TableColumn<BookExchange, String> t4ReaderIdTableColumn;
    @FXML private TableColumn<BookExchange, String> t4ReaderLoginTableColumn;
    @FXML private TableColumn<BookExchange, String> t4StartDateTableColumn;
    @FXML private TableColumn<BookExchange, String> t4EndDateTableColumn;


    public MainReaderViewController() {
    }

    @FXML
    private void initialize() {
    }

    public void init(int userId) {
        currentReaderId = userId;
        try {
            currentReaderLogin = facade.getUserLogin(userId);
            currentReaderIdLabel.setText(String.valueOf(currentReaderId));
            currentReaderLoginLabel.setText(String.valueOf(currentReaderLogin));
            currentReaderNameLabel.setText(facade.getUserName(userId));
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }

        setUpAllLibraryBooksTable();
        setUpBorrowingsTable();
        setUpMyExchangesTable();
        setUpExchangesTable();

        clearAllLabelsInfo();

        allLibraryBooksTable1View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    clearAllLabelsInfo();
                    if (newValue != null) showInfoAllLibraryBooksTable(newValue);
                    lastChosenRow = newValue;
                });
        myBorrowingsTable2View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    clearAllLabelsInfo();
                    if (newValue != null) showInfoBorrowingsTable(newValue);
                    lastChosenRow = newValue;
                });
        myExchangesTable3View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    clearAllLabelsInfo();
                    if (newValue != null) showInfoExchangesTable(newValue);
                    lastChosenRow = newValue;
                });
        exchangesTable4View.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    clearAllLabelsInfo();
                    if (newValue != null) showInfoExchangesTable(newValue);
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

    private void setUpBorrowingsTable() {
        t2IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t2BookIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getBook().getId())));
        t2BookTitleTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBook().getTitle()));
        t2StartDateTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getStartDate() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getStartDate()));
        });
        t2EndDateTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getEndDate() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getEndDate()));
        });

        try {
            myBorrowingsObservableList.addAll(facade.getAllUserBorrows(currentReaderId));
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
        myBorrowingsTable2View.setItems(myBorrowingsObservableList);
    }

    private void setUpMyExchangesTable() {
        t3IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t3BookIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getBook().getId())));
        t3BookTitleTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBook().getTitle()));
        t3OwnerIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getOwner().getId())));
        t3OwnerLoginTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOwner().getLogin()));
        t3OpenExchangeDateTableColumn.setCellValueFactory(cell -> {
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
            myExchangesObservableList.addAll(facade.getAllUserExchanges(currentReaderId));
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
        myExchangesTable3View.setItems(myExchangesObservableList);
    }

    private void setUpExchangesTable() {
        t4IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        t4BookIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getBook().getId())));
        t4BookTitleTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBook().getTitle()));
        t4OwnerIdTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getOwner().getId())));
        t4OwnerLoginTableColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOwner().getLogin()));
        t4OpenExchangeDateTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getOpenExchangeDate() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getOpenExchangeDate()));
        });
        t4ReaderIdTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getReader() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(String.valueOf(cell.getValue().getReader().getId()));
        });
        t4ReaderLoginTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getReader() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(cell.getValue().getReader().getLogin());
        });
        t4StartDateTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getStartDate() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getStartDate()));
        });
        t4EndDateTableColumn.setCellValueFactory(cell -> {
            if (cell.getValue().getEndDate() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(Util.getStringFromFormattedDate(cell.getValue().getEndDate()));
        });

        try {
            exchangesObservableList.addAll(facade.getAllExchanges());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
        exchangesTable4View.setItems(exchangesObservableList);
    }

    private void updateAllLibraryBooksTable() {
        allLibraryBooksObservableList.removeAll(allLibraryBooksObservableList);
        try {
            allLibraryBooksObservableList.addAll(facade.getAllBooks());
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO

        }
    }

    private void updateMyBorrowingsTable() {
        myBorrowingsObservableList.removeAll(myBorrowingsObservableList);
        try {
            myBorrowingsObservableList.addAll(facade.getAllUserBorrows(currentReaderId));
        } catch (NotFoundException e) {
            e.printStackTrace();    //TODO
        }
    }

    private void updateMyExchangesTable() {
        myExchangesObservableList.removeAll(myExchangesObservableList);
        try {
            myExchangesObservableList.addAll(facade.getAllUserExchanges(currentReaderId));
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

    @FXML
    public void onClickLogOutButton() {
        Main.showLogInDialog();
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
    public void onClickBorrowBookButton() {
        try {
            facade.borrowBookToReader(lastChosenRow);
        } catch (LibraryAppException e) {
            setMessageOnImage(1, e.getMessage());
            return;
        }
        setMessageOnImage(0, "Successfully! Now librarian can confirm your borrowing.");
        onClickRefreshButton();
    }

    @FXML
    public void onClickGetBookByExchangeButton() {
        try {
            facade.takeBookByExchangeToReader(lastChosenRow);
        } catch (LibraryAppException e) {
            setMessageOnImage(1, e.getMessage());
            return;
        }
        setMessageOnImage(0, "Successfully! Now librarian can confirm your exchange.");
        onClickRefreshButton();
    }

    @FXML
    public void onClickOpenMyExchangeButton() {
        try {
            facade.startExchange(lastChosenRow);
        } catch (LibraryAppException e) {
            setMessageOnImage(1, e.getMessage());
            return;
        }
        setMessageOnImage(0, "Successfully! Your book on the exchange now.");
        onClickRefreshButton();
    }

    @FXML
    public void onClickCloseMyExchangeButton() {
        try {
            facade.closeExchangeAndTakeBookToOwner(lastChosenRow);
        } catch (LibraryAppException e) {
            setMessageOnImage(1, e.getMessage());
            return;
        }
        setMessageOnImage(0, "Successfully! You closed exchange and got your book back.");
        onClickRefreshButton();
    }

    @FXML
    public void onClickRefreshButton() {
        updateAllLibraryBooksTable();
        updateMyBorrowingsTable();
        updateMyExchangesTable();
        updateExchangesTable();
        updateCountExchangeLabel();
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
        ownerIdTextField.setText("");
    }

    public void setMessageOnImage(int code, String message) {
        String mess = Util.getStringTimeFromFormattedDate(new Date()) + ": " + message;
        if (code == 0) {
            infoImage.setImage(new Image("/info.png"));
        } else infoImage.setImage(new Image("/error.png"));
        Tooltip tooltip = new Tooltip();
        tooltip.setText(mess);
//        tooltip.setShowDelay(new Duration(0));
        Tooltip.install(infoImage, tooltip);

        //TODO появления error toolbar с сообщением и последующее удаление
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

    public void updateCountExchangeLabel() {
        try {
            Reader reader = (Reader) facade.getUser(currentReaderId);
            countOfBooksAvailableByExchangeLabel.setText(String.valueOf(reader.getCountAvailableExchangeBook()));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onClickCloseError() {
        if (closeErrorToolBarThread.isAlive()) closeErrorToolBarThread.interrupt();
        errorToolBar.setVisible(false);
    }

    public void onClickInfoImage() {
        if (closeErrorToolBarThread.isAlive()) closeErrorToolBarThread.interrupt();
        if (errorToolBar.isVisible()) {
            errorToolBar.setVisible(false);
        } else errorToolBar.setVisible(true);
    }
}
