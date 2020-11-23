/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.ui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import window.tivoli.dao.CommonDao;
import window.tivoli.dao.OnProgressDao;
import window.tivoli.entity.Company;
import window.tivoli.entity.Dispatch;
import window.tivoli.entity.Item;
import window.tivoli.entity.OnProgress;
import window.tivoli.entity.POItem;
import window.tivoli.entity.PurchaseOrder;

/**
 * FXML Controller class
 *
 * @author Sashenka
 */
public class OnProgressUIController implements Initializable {

    @FXML
    private TableView<OnProgress> tblOnProgress;
    @FXML
    private TableColumn<OnProgress, String> tblcCompany;
    @FXML
    private TableColumn<OnProgress, String> tblcPONumber;
    @FXML
    private TableColumn<OnProgress, Date> tblcDate;
    @FXML
    private TableColumn<OnProgress, String> tblcItemCode;
    @FXML
    private TableColumn<OnProgress, String> tblcItemDesc;
    @FXML
    private TableColumn<OnProgress, Integer> tblcQty;
    @FXML
    private TableColumn<OnProgress, Void> tblcStatus;

    private ObservableList<OnProgress> opList;
    private ObservableList<OnProgress> allOPList;
    @FXML
    private TextField txtSearch;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        opList = OnProgressDao.getOnProgress();
        allOPList = OnProgressDao.getOnProgress();
        loadOPTable(opList);
    }

    public void loadOPTable(ObservableList<OnProgress> opList) {

        Callback<TableColumn<OnProgress, Void>, TableCell<OnProgress, Void>> finishFactory
                = new Callback<TableColumn<OnProgress, Void>, TableCell<OnProgress, Void>>() {

            @Override
            public TableCell<OnProgress, Void> call(TableColumn<OnProgress, Void> param) {
                final TableCell<OnProgress, Void> statusCell = new TableCell<OnProgress, Void>() {
                    private final Button btnFinish = new Button("Finished");//constructor method

                    {//constructor block execute after the constructor method

                        btnFinish.setOnAction((ActionEvent a) -> { //-> lambda expression 
                            int rowIndex = getTableRow().getIndex();
                            getTableView().getSelectionModel().select(rowIndex);

                            OnProgress onP = getTableView().getSelectionModel().getSelectedItem();
                            Company company = onP.getCompanyId();
                            PurchaseOrder po = onP.getPurchaseorderId();
                            Item item = onP.getItemId();
                            String itemDesc = onP.getItemDesc();
                            Integer qty = onP.getQuantity();
                            

                            Dispatch d = new Dispatch();
                            d.setCompanyId(company);
                            d.setPurchaseorderId(po);
                            d.setItemId(item);
                            d.setItemDesc(itemDesc);
                            d.setQuantity(qty);
                            CommonDao.saveData(d);

                            CommonDao.deleteData(onP);

                            allOPList = OnProgressDao.getOnProgress();
                            loadOPTable(allOPList);

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean b) {

                        super.updateItem(item, b);//super- call parent class methods
                        if (b) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnFinish);
                        }

                    }
                };
                return statusCell;
            }
        };
        tblcCompany.setCellValueFactory(new PropertyValueFactory("companyId"));
        tblcPONumber.setCellValueFactory(new PropertyValueFactory("purchaseorderId"));
        tblcDate.setCellValueFactory(new PropertyValueFactory("date"));
        tblcItemCode.setCellValueFactory(new PropertyValueFactory("itemId"));
        tblcItemDesc.setCellValueFactory(new PropertyValueFactory("itemDesc"));
        tblcQty.setCellValueFactory(new PropertyValueFactory("quantity"));
        //tblcManufactured.setCellValueFactory(new PropertyValueFactory("manufactured"));

        tblcStatus.setCellFactory(finishFactory);

        tblOnProgress.setItems(opList);
    }
    
     public ObservableList<OnProgress> filterPO(Predicate<OnProgress> p) {

        ObservableList<OnProgress> opList = FXCollections.observableArrayList();

        allOPList.stream().filter((b) -> p.test(b)).forEach((b) -> {
            opList.add(b);
        });

        return opList;
    }

    @FXML
    private void txtSearchKeyAction(KeyEvent event) {
        String value = txtSearch.getText();

        opList = filterPO((p) -> p.getPurchaseorderId().getPoNumber().toLowerCase().startsWith(value.toLowerCase()));
        loadOPTable(opList);
    }

}
