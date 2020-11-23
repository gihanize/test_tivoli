/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import window.tivoli.dao.CommonDao;
import window.tivoli.dao.POItemDao;
import window.tivoli.entity.Company;
import window.tivoli.entity.Item;
import window.tivoli.entity.OnProgress;
import window.tivoli.entity.POItem;
import window.tivoli.entity.PurchaseOrder;

/**
 * FXML Controller class
 *
 * @author Sashenka
 */
public class ManifactureManagementUIController implements Initializable {

    @FXML
    private TableView<POItem> tblPO;
    @FXML
    private TableColumn<POItem, String> tblcCompany;
    @FXML
    private TableColumn<POItem, String> tblcPONumber;
    @FXML
    private TableColumn<POItem, String> tblcItemCode;
    @FXML
    private TableColumn<POItem, String> tblcItemDesc;
    @FXML
    private TableColumn<POItem, Integer> tblcQty;
    @FXML
    private TableColumn<POItem, Integer> tblcToBeMan;
    @FXML
    private TableColumn<POItem, Integer> tblcManufactured;
    @FXML
    private TableColumn<POItem, Integer> tblcIntoProcess;
    @FXML
    private TableColumn<POItem, Void> tblcProceed;

    private ObservableList<POItem> poList;
    private ObservableList<POItem> allPOList;
    private String processStock;
    private POItem updatePO;
    @FXML
    private TextField txtSearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        poList = POItemDao.getPOs();
        allPOList = POItemDao.getPOs();
        loadPOTable(poList);

    }

    public void loadPOTable(ObservableList<POItem> poList) {

        Callback<TableColumn<POItem, Void>, TableCell<POItem, Void>> proceedFactory
                = new Callback<TableColumn<POItem, Void>, TableCell<POItem, Void>>() {

            @Override
            public TableCell<POItem, Void> call(TableColumn<POItem, Void> param) {
                final TableCell<POItem, Void> proceedCell = new TableCell<POItem, Void>() {
                    private final Button btnProceed = new Button("Proceed Now");//constructor method

                    {//constructor block execute after the constructor method

                        btnProceed.setOnAction((ActionEvent a) -> { //-> lambda expression 
                            int rowIndex = getTableRow().getIndex();
                            getTableView().getSelectionModel().select(rowIndex);

                            POItem poItem = getTableView().getSelectionModel().getSelectedItem();

                            Company company = poItem.getCompanyId();
                            PurchaseOrder po = poItem.getPurchaseorderId();
                            Item item = poItem.getItemId();
                            String itemDesc = poItem.getItemDesc();
                            Integer toBeMan = poItem.getTobeMan();
                            Integer manufactured = poItem.getManufactured();
                            Integer qty = poItem.getQuantity();
                            Integer processQty = Integer.parseInt(processStock);
                            LocalDate date = LocalDate.now();
                            Date processStartDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

                            OnProgress op = new OnProgress();

                            op.setCompanyId(company);
                            op.setPurchaseorderId(po);
                            op.setItemId(item);
                            op.setItemDesc(itemDesc);
                            op.setQuantity(processQty);
                            op.setDate(processStartDate);
                            op.setPoitemId(poItem);
                            CommonDao.saveData(op);

                            allPOList = POItemDao.getPOs();
                            loadPOTable(allPOList);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean b) {

                        super.updateItem(item, b);//super- call parent class methods
                        if (b) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnProceed);
                        }

                    }
                };
                return proceedCell;
            }
        };

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
                                    Integer qty = Integer.parseInt(quantity);

                                    processStock = quantity;

                                    //Integer totQty = poItem.getQuantity();
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
        tblcCompany.setCellValueFactory(new PropertyValueFactory("companyId"));
        tblcPONumber.setCellValueFactory(new PropertyValueFactory("purchaseorderId"));
        tblcItemCode.setCellValueFactory(new PropertyValueFactory("itemId"));
        tblcItemDesc.setCellValueFactory(new PropertyValueFactory("itemDesc"));
        tblcQty.setCellValueFactory(new PropertyValueFactory("quantity"));
        tblcManufactured.setCellValueFactory(new PropertyValueFactory("manufactured"));
        tblcToBeMan.setCellValueFactory(new PropertyValueFactory("tobeMan"));
        tblcIntoProcess.setCellFactory(quantityFactory);
        tblcProceed.setCellFactory(proceedFactory);

        tblPO.setItems(poList);
    }

     public ObservableList<POItem> filterPO(Predicate<POItem> p) {

        ObservableList<POItem> poList = FXCollections.observableArrayList();

        allPOList.stream().filter((b) -> p.test(b)).forEach((b) -> {
            poList.add(b);
        });

        return poList;
    }
     
    @FXML
    private void txtSearchKeyAction(KeyEvent event) {
        String value = txtSearch.getText();

        poList = filterPO((p) -> p.getPurchaseorderId().getPoNumber().toLowerCase().startsWith(value.toLowerCase()));
        loadPOTable(poList);
    }

}
