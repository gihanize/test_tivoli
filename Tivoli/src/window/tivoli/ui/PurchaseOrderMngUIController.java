/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import window.tivoli.dao.CommonDao;
import window.tivoli.dao.CompanyDao;
import window.tivoli.dao.POItemDao;
import window.tivoli.entity.Company;
import window.tivoli.entity.POItem;

/**
 * FXML Controller class
 *
 * @author Sashenka
 */
public class PurchaseOrderMngUIController implements Initializable {

    @FXML
    private ComboBox<Company> cmbCompanies;
    @FXML
    private TableView<POItem> tblPO;
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
    private TableColumn<POItem, BigDecimal> tblcPrice;
    @FXML
    private TableColumn<POItem, Integer> tblcTotWeight;
    @FXML
    private TableColumn<POItem, Void> tblcUpdate;
    @FXML
    private TableColumn<POItem, Void> tblcDelete;

    private ObservableList<POItem> poList;
    private ObservableList<POItem> allPOList;
    private PurchaseOrderUpdateUIController poUpdate;
    private Stage viewStage;
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
        loadCompanies();
    }

    public void setPOUpdateUI(PurchaseOrderUpdateUIController poUpdate) {
        this.poUpdate = poUpdate;
    }

    public void setViewStage(Stage viewStage) {
        this.viewStage = viewStage;
    }

    public void loadCompanies() {
        ObservableList<Company> companyList = CompanyDao.getCompanies();
        cmbCompanies.setItems(companyList);
    }

    public ObservableList<POItem> filterPO(Predicate<POItem> p) {

        ObservableList<POItem> poList = FXCollections.observableArrayList();

        allPOList.stream().filter((b) -> p.test(b)).forEach((b) -> {
            poList.add(b);
        });

        return poList;
    }
    public void loadPOTable(ObservableList<POItem> poList) {

        Callback<TableColumn<POItem, Void>, TableCell<POItem, Void>> updateFactory
                = new Callback<TableColumn<POItem, Void>, TableCell<POItem, Void>>() {
            @Override
            public TableCell<POItem, Void> call(TableColumn<POItem, Void> param) {
                final TableCell<POItem, Void> updateCell = new TableCell<POItem, Void>() {
                    private final Button btnUpdate = new Button("Update");

                    {

                        btnUpdate.setOnAction((ActionEvent a) -> {
                            try {
                                //-> lambda expression
                                int rowIndex = getTableRow().getIndex();
                                getTableView().getSelectionModel().select(rowIndex);

                                POItem poi = getTableView().getSelectionModel().getSelectedItem();
//                          

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("PurchaseOrderUpdateUI.fxml"));
                                BorderPane root = loader.load();
                                PurchaseOrderUpdateUIController poUpdateController = loader.getController();
                                poUpdateController.setPOUpdateUI(poUpdateController);
                                poUpdateController.fillForm(poi);
                                Scene sc = new Scene(root);
                                Stage st = new Stage();
                                st.setScene(sc);
                                st.initModality(Modality.APPLICATION_MODAL);
                                poUpdateController.setViewStage(st);
                                st.show();
                                //viewStage.close();
                            } catch (IOException ex) {
                                Logger.getLogger(PurchaseOrderMngUIController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
//                        } else {
//                            Alert errMsg = new Alert(Alert.AlertType.ERROR);
//                            errMsg.setHeaderText("Error");
//                            errMsg.setContentText("You don't have permission to perform this action");
//                            errMsg.showAndWait();
//                        }
                    }

                    @Override
                    public void updateItem(Void item, boolean b) {

                        super.updateItem(item, b);//super- call parent class methods
                        if (b) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnUpdate);
                        }

                    }
                };
                return updateCell;
            }
        };

        Callback<TableColumn<POItem, Void>, TableCell<POItem, Void>> deleteFactory
                = new Callback<TableColumn<POItem, Void>, TableCell<POItem, Void>>() {

            @Override
            public TableCell<POItem, Void> call(TableColumn<POItem, Void> param) {
                final TableCell<POItem, Void> deleteCell = new TableCell<POItem, Void>() {
                    private final Button btnDelete = new Button("Delete");//constructor method

                    {//constructor block execute after the constructor method

                        btnDelete.setOnAction((ActionEvent a) -> { //-> lambda expression 
                            int rowIndex = getTableRow().getIndex();
                            getTableView().getSelectionModel().select(rowIndex);

                            POItem poi = getTableView().getSelectionModel().getSelectedItem();

                            Alert warnMsg = new Alert(Alert.AlertType.WARNING);
                            warnMsg.setHeaderText("Are you sure you want to delete this");
                            //warnMsg.setContentText("Employee Name :- " + deleteEmployee.getFirstname() + "\nAddress :- " + deleteEmployee.getAddress() + "\nMobile Number :- " + deleteEmployee.getMobile() + "\nEmail :- " + deleteEmployee.getEmail());
                            warnMsg.setTitle("Delete");
                            Optional<ButtonType> userInput = warnMsg.showAndWait();
                            if (userInput.get() == ButtonType.OK) {
                                CommonDao.deleteData(poi);
                                
                                //poList = POItemDao.getPOs();
                                allPOList = POItemDao.getPOs();
                                loadPOTable(allPOList);


                                
                                Alert succMsg = new Alert(Alert.AlertType.INFORMATION);
                                succMsg.setHeaderText("Success");
                                succMsg.setContentText("Successfully Deleted");
                                succMsg.showAndWait();
                                
                                
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean b) {

                        super.updateItem(item, b);//super- call parent class methods
                        if (b) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnDelete);
                        }

                    }
                };
                return deleteCell;
            }
        };

        tblcPONumber.setCellValueFactory(new PropertyValueFactory("purchaseorderId"));
        tblcItemCode.setCellValueFactory(new PropertyValueFactory("itemId"));
        tblcItemDesc.setCellValueFactory(new PropertyValueFactory("itemDesc"));
        tblcQty.setCellValueFactory(new PropertyValueFactory("quantity"));
        tblcToBeMan.setCellValueFactory(new PropertyValueFactory("tobeMan"));
        tblcPrice.setCellValueFactory(new PropertyValueFactory("lineTotal"));
        tblcTotWeight.setCellValueFactory(new PropertyValueFactory("lineWeight"));

        tblcUpdate.setCellFactory(updateFactory);
        tblcDelete.setCellFactory(deleteFactory);

        tblPO.setItems(poList);
    }

    @FXML
    private void companyOnAction(ActionEvent event) {
        Company company = cmbCompanies.getSelectionModel().getSelectedItem();
        poList = POItemDao.getPOByCompany(company);
        company.getPurchaseOrderList();
        loadPOTable(poList);
    }

    @FXML
    private void txtSearchKeyAction(KeyEvent event) {
        String value = txtSearch.getText();

        poList = filterPO((p) -> p.getPurchaseorderId().getPoNumber().toLowerCase().startsWith(value.toLowerCase()));
        loadPOTable(poList);
    }

}
