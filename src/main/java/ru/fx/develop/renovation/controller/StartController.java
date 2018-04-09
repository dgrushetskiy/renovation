package ru.fx.develop.renovation.controller;

import com.sun.glass.ui.Window;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.fx.develop.renovation.RenovationApplication;
import ru.fx.develop.renovation.fxml.StartView;
import ru.fx.develop.renovation.generator.PptxAlbumGenerator;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.service.HouseService;
import ru.fx.develop.renovation.util.DateUtil;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import static com.sun.glass.ui.CommonDialogs.showFolderChooser;
import static ru.fx.develop.renovation.generator.PptxAlbumGenerator.getFilePathDirOutput;
import static ru.fx.develop.renovation.generator.PptxAlbumGenerator.setFilePathDirOutput;

@Component
public class StartController extends Observable {

    public static final int MAX_PAGE_SHOW = 10;
    private static final int PAGE_SIZE = 35;
    public static DataFormat CLIPBOARD_DATA_FORMAT = new DataFormat(House.class.getName());
    private ResourceBundle resourceBundle;
    @Autowired
    private StartView startView;
    @Autowired
    private HouseService houseService;
    private Page page;
    @Autowired
    private PptxAlbumGenerator albumGenerator;

    private ObservableList<House> housesData;

    @FXML
    private CustomTextField unomSearchTextField;
    @FXML
    private CustomTextField mrSearchTextField;
    @FXML
    private CustomTextField adressSearchTextField;

    @FXML//таблица источник
    private TableView<House> tableViewHouseData = new TableView<>();// 1 -ая таблица
    @FXML
    private TableColumn<House, Long> columnUnomData = new TableColumn<>();
    @FXML
    private TableColumn<House, String> columnMrData = new TableColumn<>();
    @FXML
    private TableColumn<House, String> columnAdressData = new TableColumn<>();
    @FXML
    private Pagination paginationData;

    @FXML// Таблица приемник
    private TableView<House> tableViewHouseUpdata = new TableView<>();// 2 -ая таблица динамическая - данные хранятся в памяти для шаблона
    @FXML
    private TableColumn<House, Long> columnUnomUpdata = new TableColumn<>();
    @FXML
    private TableColumn<House, String> columnMrUpdata = new TableColumn<>();
    @FXML
    private TableColumn<House, String> columnAdressUpdata = new TableColumn<>();
    @FXML
    private Pagination paginationUpdata;

    @FXML
    private Label labelRecordTableData;
    @FXML
    private Label labelId;
    @FXML
    private Label labelUnom;
    @FXML
    private Label labelMr;
    @FXML
    private Label labelAddress;
    @FXML
    private Label labelSOForm;
    @FXML
    private Label labelVidPrava;
    @FXML
    private Label labelNameSubject;
    @FXML
    private Button buttonDeleteRow;
    @FXML
    private Button buttonDeleteAllRows;
    @FXML
    private Button buttonReport;
    @FXML
    private Button buttonFile;
    @FXML
    private Label labelDirectory;
    @FXML
    private Label labelDataDocument;


    public StartController() {
        // For Desktop.isDesktopSupported()
        System.setProperty("java.awt.headless", "false");

    }

    @FXML
    private void initialize() {
        // Инициализация таблицы адресатов с двумя столбцами.
        paginationData.setMaxPageIndicatorCount(MAX_PAGE_SHOW);
        this.resourceBundle = startView.getResourceBundle();//определяем локацию по умолчанию
        tableViewHouseData.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // tableViewHouseUpdata.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        columnUnomData.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getUnom()));
        columnMrData.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getMr()));
        columnAdressData.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getAddress()));
//        setCellValueFactoriesTableData();
//        setCellValueFactoriesTableUpdata();
        setupClearButtonField(mrSearchTextField);
        setupClearButtonField(unomSearchTextField);
        setupClearButtonField(adressSearchTextField);
        fillData();
        initListeners();

    }

    private void initListeners() {
        tableViewHouseData.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showHousePrimaryRightDetals(newValue));
        // Начало перетаскивания
        tableViewHouseData.setOnDragDetected(event -> {
            TableView<House> tableView = (TableView) event.getSource();
            //HousePrimaryRights selectedItem = tableView.getSelectionModel().getSelectedItem();//SelectionMode.SINGLE
            List<House> selectedItems = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());
            if (selectedItems != null) {
                ClipboardContent content = new ClipboardContent();
                content.put(CLIPBOARD_DATA_FORMAT, selectedItems);

                tableView.startDragAndDrop(TransferMode.ANY).setContent(content);
            }
            event.consume();
        });

        columnUnomUpdata.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getUnom()));
        columnMrUpdata.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getMr()));
        columnAdressUpdata.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getAddress()));
        // Перетаскиваем с зажатой мышкой (до бросания)
        tableViewHouseUpdata.setOnDragOver(event -> {
            if (event.getGestureSource() != event.getSource() && event.getDragboard().hasContent(CLIPBOARD_DATA_FORMAT)) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        // Бросание объекта
        tableViewHouseUpdata.setOnDragDropped(event -> {
            TableView tableView = (TableView) event.getSource();
            Dragboard db = event.getDragboard();
            boolean completed = false;
            if (db.hasContent(CLIPBOARD_DATA_FORMAT)) {
                // HousePrimaryRights content = (HousePrimaryRights) db.getContent(CLIPBOARD_DATA_FORMAT);//SelectionMode.SINGLE
                List<House> contents = (ArrayList<House>) db.getContent(CLIPBOARD_DATA_FORMAT);
                tableView.getItems().addAll(contents);
                //tableView.getItems().add(content);//SelectionMode.SINGLE
                completed = true;
            }
            event.setDropCompleted(completed);
            event.consume();
        });
        //удаление строки
        buttonDeleteRow.setDisable(false);
        buttonDeleteRow.setOnAction(event -> {
            House selectedItem = tableViewHouseUpdata.getSelectionModel().getSelectedItem();
            tableViewHouseUpdata.getItems().remove(selectedItem);
        });
        //удаление все строк
        buttonDeleteAllRows.setOnAction(event -> {
            removeAllRows();
        });
        //
        buttonReport.setOnAction(event -> {
            String path = getFilePathDirOutput();
            if (path == null) {
                File selected = showFolderChooser(Window.getFocusedWindow(),
                        new File(System.getProperty("user.home")), "Выберите папку для отчётов");
                if (selected != null) setFilePathDirOutput(path = selected.getAbsolutePath());
            }
            if (path != null) {
                labelDataDocument.setText("Отчет создан : " + DateUtil.format(LocalDate.now()));
                labelDirectory.setText("Отчет находится : " + path);
                reportPowerPointWrite();
            }

        });

        buttonFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Презентация PowerPoint", "*.pptx"));
            String path = getFilePathDirOutput();
            if (path != null) {
                fileChooser.setInitialDirectory(new File(path));
                File file = fileChooser.showOpenDialog(RenovationApplication.stage);
                if (file != null && Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().open(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        paginationData.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> fillTable(newValue.intValue()));
    }

    @SuppressWarnings("unchecked")
    private void reportPowerPointWrite() {
        housesData = tableViewHouseUpdata.getItems();
        albumGenerator.generateReport(housesData);
    }

    public void actionSearchUnom(ActionEvent actionEvent) {
        fillTableUnom();
    }

    public void actionSearchMr(ActionEvent actionEvent) {
        fillTable();
    }

    public void actionSearchAdress(ActionEvent actionEvent) {
        fillTableAdress();
    }

    private void fillData() {
        if (mrSearchTextField.getText().trim().length() == 0) {
            fillTable();
        } else if (adressSearchTextField.getText().trim().length() == 0) {
            fillTableAdress();
        } else if (unomSearchTextField.getText().trim().length() == 0) {
            fillTableUnom();
        }
    }

    private void fillTableUnom() {
        if (unomSearchTextField.getText().trim().length() == 0) {
            page = houseService.getAllUnom(0, PAGE_SIZE);
        } else {
            page = houseService.getAllUnom(0, PAGE_SIZE, Long.valueOf(unomSearchTextField.getText()));
        }
        fillPagination(page);
        paginationData.setCurrentPageIndex(0);
        updateCountLabel(page.getTotalElements());
    }

    private void fillTableUnom(int pageNumber) {
        if (unomSearchTextField.getText().trim().length() == 0) {
            page = houseService.getAllUnom(pageNumber, PAGE_SIZE);
        } else {
            page = houseService.getAllUnom(pageNumber, PAGE_SIZE, Long.valueOf(unomSearchTextField.getText()));
        }
        fillPagination(page);
        updateCountLabel(page.getTotalElements());
    }

    private void fillTable() {
        if (mrSearchTextField.getText().trim().length() == 0) {
            page = houseService.getAll(0, PAGE_SIZE);

        } else {
            page = houseService.getAll(0, PAGE_SIZE, mrSearchTextField.getText());
        }
        fillPagination(page);
        paginationData.setCurrentPageIndex(0);
        updateCountLabel(page.getTotalElements());
    }

    private void fillTable(int pageNumber) {
        if (mrSearchTextField.getText().trim().length() == 0) {
            page = houseService.getAll(pageNumber, PAGE_SIZE);
        } else {
            page = houseService.getAll(pageNumber, PAGE_SIZE, mrSearchTextField.getText());
        }
        fillPagination(page);
        updateCountLabel(page.getTotalElements());
    }

    private void fillTableAdress() {
        if (adressSearchTextField.getText().trim().length() == 0) {
            page = houseService.getAllAddress(0, PAGE_SIZE);
        } else {
            page = houseService.getAllAddress(0, PAGE_SIZE, adressSearchTextField.getText());
        }
        fillPagination(page);
        paginationData.setCurrentPageIndex(0);
        updateCountLabel(page.getTotalElements());
    }

    private void fillTableAdress(int pageNumber) {
        if (adressSearchTextField.getText().trim().length() == 0) {
            page = houseService.getAllAddress(pageNumber, PAGE_SIZE);
        } else {
            page = houseService.getAllAddress(pageNumber, PAGE_SIZE, adressSearchTextField.getText());
        }
        fillPagination(page);
        updateCountLabel(page.getTotalElements());
    }

    private void setupClearButtonField(CustomTextField customTextField) {
        try {
            Method method = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
            method.setAccessible(true);
            method.invoke(null, customTextField, customTextField.rightProperty());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void fillPagination(Page page) {
        if (page.getTotalPages() <= 1) {
            paginationData.setDisable(true);
        } else {
            paginationData.setDisable(false);
        }
        paginationData.setPageCount(page.getTotalPages());
        housesData = FXCollections.observableList(page.getContent());
        //set Dummy Data for the TableView
        tableViewHouseData.setItems(housesData);
    }

    private void updateCountLabel(long textRecordTable) {
        labelRecordTableData.setText(resourceBundle.getString("textRecordTable") + ":" + textRecordTable);
    }

    private void showHousePrimaryRightDetals(House house) {
        if (house != null) {
            // labelId.setText(String.valueOf(house.getId()));
            labelUnom.setText(String.valueOf(house.getUnom()));
            labelMr.setText(house.getMr());
            labelAddress.setText(house.getAddress());
            // labelSOForm.setText(String.valueOf());
            //labelVidPrava.setText(house.getVidPrava());
            //labelNameSubject.setText(house.getNameSubject());
        } else {
            // labelId.setText("");
            labelUnom.setText("");
            labelMr.setText("");
            labelAddress.setText("");
            // labelSOForm.setText("");
            // labelVidPrava.setText("");
            // labelNameSubject.setText("");
        }
    }

    private void removeAllRows() {
        for (int i = 0; i < tableViewHouseUpdata.getItems().size(); i++) {
            tableViewHouseUpdata.getItems().clear();
        }
    }
}
