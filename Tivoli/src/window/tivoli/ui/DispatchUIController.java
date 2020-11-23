/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.ui;

import com.sun.imageio.plugins.common.BogusColorSpace;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import window.tivoli.dao.CommonDao;
import window.tivoli.dao.CompanyDao;
import window.tivoli.dao.DispatchDao;
import window.tivoli.dao.VehicleDao;
import window.tivoli.entity.Company;
import window.tivoli.entity.Dispatch;
import window.tivoli.entity.DispatchNote;
import window.tivoli.entity.Item;
import window.tivoli.entity.OnProgress;
import window.tivoli.entity.PurchaseOrder;
import window.tivoli.entity.vehicle;


/**
 * FXML Controller class
 *
 * @author Sashenka
 */
public class DispatchUIController implements Initializable {

    @FXML
    private TableView<Dispatch> tblDispatch;
    @FXML
    private TableColumn<Dispatch, String> tblcPONumber;
    @FXML
    private TableColumn<Dispatch, String> tblcItemCode;
    @FXML
    private TableColumn<Dispatch, String> tblcItemDesc;
    @FXML
    private TableColumn<Dispatch, Integer> tblcQty;
    @FXML
    private TableColumn<Dispatch, Integer> tblcDisQty;
    @FXML
    private TableColumn<Dispatch, Void> tblcCreateDN;

    private ObservableList<Dispatch> dispatchList;
    private ObservableList<Dispatch> allDispatchList;
    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<Company> cmbCompanies;
    @FXML
    private ComboBox<vehicle> cmbVehicle;
    @FXML
    private Button btnCDN;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        dispatchList = DispatchDao.getDispatch();
        allDispatchList = DispatchDao.getDispatch();
        loadDispatchTable(dispatchList);
        loadCompanies();
        loadVehicles();
    }

    public void loadCompanies() {
        ObservableList<Company> companyList = CompanyDao.getCompanies();
        cmbCompanies.setItems(companyList);
    }
    
    public void loadVehicles() {
        ObservableList<vehicle> vehicleList = VehicleDao.getVehicles();
        cmbVehicle.setItems(vehicleList);
    }

    public void loadDispatchTable(ObservableList<Dispatch> dispatchList) {

        Callback<TableColumn<Dispatch, Integer>, TableCell<Dispatch, Integer>> disquantityFactory
                = new Callback<TableColumn<Dispatch, Integer>, TableCell<Dispatch, Integer>>() {
            @Override
            public TableCell<Dispatch, Integer> call(TableColumn<Dispatch, Integer> param) {
                final TableCell<Dispatch, Integer> disQuantityCell = new TableCell<Dispatch, Integer>() {
                    private final TextField txtQuantity = new TextField();

                    {
                        txtQuantity.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                            if (event.getCode() == KeyCode.ENTER) {

                                String quantity = txtQuantity.getText();
                                if (!quantity.equals("")) {
                                    int rowIndex = getTableRow().getIndex();
                                    getTableView().getSelectionModel().select(rowIndex);

                                    Dispatch dis = getTableView().getSelectionModel().getSelectedItem();
                                    Company company = cmbCompanies.getSelectionModel().getSelectedItem();
                                    Item item = dis.getItemId();
                                    String itemDesc = dis.getItemDesc();
                                    PurchaseOrder po = dis.getPurchaseorderId();
                                    Integer qty = Integer.parseInt(quantity);
                                    vehicle vehicle = cmbVehicle.getSelectionModel().getSelectedItem();
                                    
                                    DispatchNote dn = new DispatchNote();
                                    dn.setItemId(item);
                                    dn.setItemDesc(itemDesc);
                                    dn.setPurchaseorderId(po);
                                    dn.setQuantity(qty);
                                    dn.setCompanyId(company);
                                    dn.setVehicleId(vehicle);
                                    
                                    CommonDao.saveData(dn);

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
                return disQuantityCell;
            }
        };

        Callback<TableColumn<Dispatch, Void>, TableCell<Dispatch, Void>> createFactory
                = new Callback<TableColumn<Dispatch, Void>, TableCell<Dispatch, Void>>() {

            @Override
            public TableCell<Dispatch, Void> call(TableColumn<Dispatch, Void> param) {
                final TableCell<Dispatch, Void> createCell = new TableCell<Dispatch, Void>() {

                    private final Button btnCreate = new Button("Create");//constructor method

                    {//constructor block execute after the constructor method

                        btnCreate.setOnAction((ActionEvent a) -> { //-> lambda expression 
                            int rowIndex = getTableRow().getIndex();
                            getTableView().getSelectionModel().select(rowIndex);

                            Dispatch dis = getTableView().getSelectionModel().getSelectedItem();

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean b) {

                        super.updateItem(item, b);//super- call parent class methods
                        if (b) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnCreate);
                        }

                    }
                };
                return createCell;
            }
        };

        tblcPONumber.setCellValueFactory(new PropertyValueFactory("purchaseorderId"));
        tblcItemCode.setCellValueFactory(new PropertyValueFactory("itemId"));
        tblcItemDesc.setCellValueFactory(new PropertyValueFactory("itemDesc"));
        tblcQty.setCellValueFactory(new PropertyValueFactory("quantity"));
        //tblcManufactured.setCellValueFactory(new PropertyValueFactory("manufactured"));

        tblcCreateDN.setCellFactory(createFactory);
        tblcDisQty.setCellFactory(disquantityFactory);

        tblDispatch.setItems(dispatchList);
    }

    public ObservableList<Dispatch> filterPO(Predicate<Dispatch> p) {

        ObservableList<Dispatch> disList = FXCollections.observableArrayList();

        allDispatchList.stream().filter((b) -> p.test(b)).forEach((b) -> {
            disList.add(b);
        });

        return disList;
    }

    @FXML
    private void txtSearchKeyAction(KeyEvent event) {
        String value = txtSearch.getText();

        dispatchList = filterPO((p) -> p.getPurchaseorderId().getPoNumber().toLowerCase().startsWith(value.toLowerCase()));
        loadDispatchTable(dispatchList);
    }

    @FXML
    private void companyOnAction(ActionEvent event) {
        Company company = cmbCompanies.getSelectionModel().getSelectedItem();
        dispatchList = DispatchDao.getDispatchByCompany(company);
        loadDispatchTable(dispatchList);
    }

    @FXML
    private void cmbVehicleOnAction(ActionEvent event) {
    }

    @FXML
    private void btnCDNClickAction(ActionEvent event) throws JRException {
        
        try {
            String company = cmbCompanies.getSelectionModel().getSelectedItem().toString();
            
            String reportName = "C:\\Users\\Sashenka\\Documents\\NetBeansProjects\\Tivoli\\src\\window\\tivoli\\report\\DispatchNote.jasper";
            
            Connection con
                    = DriverManager.getConnection("jdbc:mysql://localhost:3306/tivolidb", "root", "");

            HashMap<String, Object> paraMap = new HashMap();
            paraMap.put("companyId", company);

            JasperPrint jPrint = JasperFillManager.fillReport(reportName, paraMap, con);

            JRViewer reportViewer = new JRViewer(jPrint);
            reportViewer.setVisible(true);
            reportViewer.setOpaque(true);

            JFrame frame = new JFrame();
            frame.getContentPane().add(reportViewer);
            frame.setSize(500, 650);
            frame.setLocation(50, 10);
            frame.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(DispatchUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
