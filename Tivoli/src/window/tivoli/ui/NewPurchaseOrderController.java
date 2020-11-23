/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.ui;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.JDBCType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import window.tivoli.dao.CommonDao;
import window.tivoli.dao.CompanyDao;
import window.tivoli.dao.ItemDao;
import window.tivoli.entity.Company;
import window.tivoli.entity.Item;
import window.tivoli.entity.POItem;
import window.tivoli.entity.PurchaseOrder;

/**
 * FXML Controller class
 *
 * @author Sashenka
 */
public class NewPurchaseOrderController implements Initializable {

    @FXML
    private ComboBox<Company> cmbCompanies;
    @FXML
    private TableView<POItem> tblPO;
    @FXML
    private TableColumn<POItem, String> tblcItemCode;
    @FXML
    private TableColumn<POItem, String> tblcItemDesc;
    @FXML
    private TableColumn<POItem, Integer> tblcQty;
    @FXML
    private TableColumn<POItem, BigDecimal> tblcPrice;
    @FXML
    private TableColumn<POItem, Integer> tblcTotWeight;
    @FXML
    private TableColumn<POItem, Void> tblcClear;

    @FXML
    private TextField txtItem;
    @FXML
    private ListView<Item> lstItem;

    private ObservableList<Item> itemList;
    private ObservableList<Item> allItemList;
    private Item itemObj;
    private Double totalPrice;
    private Integer noOfRows;
    @FXML
    private Button btnInsertIntoTable;
    @FXML
    private TextField txtTotalPrice;
    @FXML
    private TextField txtPoNumber;
    @FXML
    private TextField txtDate;
    @FXML
    private Button btnSave;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        itemList = ItemDao.getItems();
        allItemList = ItemDao.getItems();
        totalPrice = 0.0;
        loadCompanies();
        loadItemList(itemList);
        loadPOItem();
        noOfRows = 0;
        loadDate();
    }

    public void loadItemList(ObservableList<Item> itemList) {//parameterizedfor reuse the method  
        lstItem.setItems(itemList);
    }

    public void loadCompanies() {
        ObservableList<Company> companyList = CompanyDao.getCompanies();
        cmbCompanies.setItems(companyList);
    }
    
    public void loadDate() {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek day = currentDate.getDayOfWeek();
        txtDate.setText(LocalDate.now().toString() + ", " + day.toString()); //return the system currnt date time
    }

    public void clear(){
        cmbCompanies.getSelectionModel().clearSelection();
        txtPoNumber.clear();
        loadItemList(itemList);
        tblPO.refresh();
    }
    
    public void loadPOItem() {
        tblcItemCode.setCellValueFactory(new PropertyValueFactory("itemId"));
        tblcItemDesc.setCellValueFactory(new PropertyValueFactory("itemDesc"));

        Callback<TableColumn<POItem, Integer>, TableCell<POItem, Integer>> quantityFactory
                = new Callback<TableColumn<POItem, Integer>, TableCell<POItem, Integer>>() {
            @Override
            public TableCell<POItem, Integer> call(TableColumn<POItem, Integer> param) {
                final TableCell<POItem, Integer> quantityCell = new TableCell<POItem, Integer>() {
                    private final TextField txtQuantity = new TextField();

                    {
                        txtQuantity.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                            if (event.getCode() == KeyCode.ENTER) {

                                String quantity = txtQuantity.getText();
                                if (!quantity.equals("")) {
                                    int rowIndex = getTableRow().getIndex();
                                    getTableView().getSelectionModel().select(rowIndex);

                                    POItem poItem = getTableView().getSelectionModel().getSelectedItem();
                                    BigDecimal price = poItem.getUnitPrice();
                                    Integer weight = poItem.getUnitWeight();
                                    Integer quanInt = Integer.parseInt(quantity);
                                    BigDecimal quan = BigDecimal.valueOf(Double.valueOf(quanInt));
                                    BigDecimal lineTotal = price.multiply(quan);
                                    
                                    Integer totalWeight = weight*quanInt;

                                    poItem.setLineTotal(lineTotal);
                                    poItem.setQuantity(quanInt);
                                    poItem.setTobeMan(quanInt);
                                    poItem.setLineWeight(totalWeight);
//                                    txtQuantity.setText(purItem.getLinetotal().toString());
//                                    tblcQty.setCellValueFactory(new PropertyValueFactory("qty"));

                                    totalPrice = totalPrice + lineTotal.doubleValue();
                                    String totPrice = String.valueOf(totalPrice);
                                    txtTotalPrice.setText(totPrice);
                                    tblcPrice.setCellValueFactory(new PropertyValueFactory("lineTotal"));
                                    noOfRows++;
                                    //lblNoOfItems.setText(noOfRows.toString());
                                    tblPO.refresh();

                                }
                            }

                        });

                    }

                    ;
 
                    @Override
                    public void updateItem(Integer item, boolean b) {

                        super.updateItem(item, b);//super- call parent class methods
                        if (b) {
                            setGraphic(null);
                        } else {
                            setGraphic(txtQuantity);
                        }

                    }
                };
                return quantityCell;
            }
        };

        Callback<TableColumn<POItem, Void>, TableCell<POItem, Void>> clearFactory
                = new Callback<TableColumn<POItem, Void>, TableCell<POItem, Void>>() {

            @Override
            public TableCell<POItem, Void> call(TableColumn<POItem, Void> param) {
                final TableCell<POItem, Void> clearCell = new TableCell<POItem, Void>() {
                    private final Button btnClear = new Button("Clear");//constructor method

                    {//constructor block execute after the constructor method

                        btnClear.setOnAction((ActionEvent a) -> { //-> lambda expression 
                            int rowIndex = getTableRow().getIndex();
                            getTableView().getSelectionModel().select(rowIndex);

                            POItem clearItem = getTableView().getSelectionModel().getSelectedItem();

                            Alert warnMsg = new Alert(Alert.AlertType.WARNING);
                            warnMsg.setHeaderText("Are you sure you want to clear this");
                            //warnMsg.setContentText("Employee Name :- " + deleteEmployee.getFirstname() + "\nAddress :- " + deleteEmployee.getAddress() + "\nMobile Number :- " + deleteEmployee.getMobile() + "\nEmail :- " + deleteEmployee.getEmail());
                            warnMsg.setTitle("Clear Record");
                            Optional<ButtonType> userInput = warnMsg.showAndWait();

                            if (userInput.get() == ButtonType.OK) {

                                //CommonDao.deleteData(deleteItem);
                                Alert succMsg = new Alert(Alert.AlertType.INFORMATION);
                                succMsg.setHeaderText("Success");
                                succMsg.setContentText("Successfully Deleted");
                                succMsg.showAndWait();
                                tblPO.getItems().remove(clearItem);
                                //lstItem.setItems(clearItem.getItemId());

                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean b) {

                        super.updateItem(item, b);//super- call parent class methods
                        if (b) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnClear);
                        }

                    }
                };
                return clearCell;
            }
        };

        //String totPrice = String.valueOf(totalPrice);
        tblcTotWeight.setCellValueFactory(new PropertyValueFactory("lineWeight"));
        tblcQty.setCellFactory(quantityFactory);
        tblcClear.setCellFactory(clearFactory);

    }

    public ObservableList<Item> filterItem(Predicate<Item> p) {

        ObservableList<Item> eList = FXCollections.observableArrayList();
        allItemList.stream().filter((e) -> p.test(e)).forEach((e) -> {
            eList.add(e);
        });

        return eList;
    }
//    
//    public void loadInvoiceNumber() {
//        //int size = 0;
//        ObservableList<TrashSales> invoiceList = TrashSalesDao.getTrashSales();
//        Integer size = invoiceList.size();
//
//        int year = LocalDate.now().getYear();
//        int month = LocalDate.now().getMonthValue();
//        size++;
//
//        String strYear = String.valueOf(year); // integer to string convert
//        String strMonth = String.valueOf(month);
//        String strSize = String.valueOf(size);
//        String code = "I-" + strYear;
//
//        if (strMonth.length() == 1) {
//            code = code + "0" + strMonth;
//        } else {
//            code = code + strMonth;
//        }
//
//        switch (strSize.length()) {
//            case 1:
//                code = code + "0000" + strSize;
//                break;
//            case 2:
//                code = code + "000" + strSize;
//                break;
//            case 3:
//                code = code + "00" + strSize;
//                break;
//            case 4:
//                code = code + "0" + strSize;
//                break;
//            default:
//                code = code + strSize;
//        }
//        txtInvoiceNo.setText(code);
//    }

    @FXML
    private void txtItemSearchAction(KeyEvent event) {
        String value = txtItem.getText();

        itemList = filterItem((p) -> p.getItemCode().toLowerCase().startsWith(value.toLowerCase()));
        loadItemList(itemList);
    }

    @FXML
    private void btnInsertIntoTableClickAction(ActionEvent event) {
        itemObj = lstItem.getSelectionModel().getSelectedItem();
        POItem poitem = new POItem();
        poitem.setItemId(itemObj);
        poitem.setItemDesc(itemObj.getItemDesc());
        poitem.setUnitPrice(itemObj.getUnitPrice());
        poitem.setUnitWeight(itemObj.getUnitWeight());
        tblPO.getItems().add(poitem);
        lstItem.getItems().remove(itemObj);
    }

    @FXML
    private void btnSaveClickAction(ActionEvent event) {
        String poNumber = txtPoNumber.getText();
        LocalDate date = LocalDate.now();
        Date purchaseDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Company company = cmbCompanies.getSelectionModel().getSelectedItem();
        String toPString = txtTotalPrice.getText();
        //Integer totPriceInt = Integer.parseInt(toPString);
        BigDecimal totPrice = BigDecimal.valueOf(Double.valueOf(toPString));
        
        PurchaseOrder po = new PurchaseOrder();
        po.setPoNumber(poNumber);
        po.setDate(purchaseDate);
        po.setCompanyId(company);
        po.setTotalPrice(totPrice);
        CommonDao.saveData(po);
        
        // table view list
        ObservableList<POItem> purchase = tblPO.getItems();

        for (POItem pOItem : purchase) {

            pOItem.setPurchaseorderId(po);
            pOItem.setCompanyId(company);
            pOItem.setTobeMan(pOItem.getQuantity());
            pOItem.setManufactured(0);
            CommonDao.saveData(pOItem);

        }
    }
}
