/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.ui;

import com.sun.imageio.plugins.common.BogusColorSpace;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import window.tivoli.dao.CommonDao;
import window.tivoli.entity.Company;
import window.tivoli.entity.POItem;
import window.tivoli.entity.PurchaseOrder;

/**
 * FXML Controller class
 *
 * @author Sashenka
 */
public class PurchaseOrderUpdateUIController implements Initializable {

    @FXML
    private TextField txtItemCode;
    @FXML
    private TextField txtItemDesc;
    @FXML
    private TextField txtQty;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtWeight;
    @FXML
    private TextField txtTotalPrice;
    @FXML
    private TextField txtPONumber;
    @FXML
    private Button btnUpdate;

    private POItem updatePO;
    private PurchaseOrderUpdateUIController poUpdateUI;
    private Stage viewStage;
    private PurchaseOrder updatePurchaseOrder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setPOUpdateUI(PurchaseOrderUpdateUIController poUpdateUI) {
        this.poUpdateUI = poUpdateUI;
    }

    public void setViewStage(Stage viewStage) {
        this.viewStage = viewStage;
    }

    public void fillForm(POItem poi) {

        updatePO = poi;

        txtPONumber.setText(poi.getPurchaseorderId().getPoNumber());
        txtItemCode.setText(poi.getItemId().getItemCode());
        txtItemDesc.setText(poi.getItemDesc());
        txtQty.setText(poi.getQuantity().toString());
        txtPrice.setText(poi.getLineTotal().toString());
        txtWeight.setText(poi.getLineWeight().toString());
        txtTotalPrice.setText(poi.getPurchaseorderId().getTotalPrice().toString());

    }

    @FXML
    private void btnUpdateClickAction(ActionEvent event) {
        
        String qtySt = txtQty.getText();
        String pricest = txtPrice.getText();
        String weightSt = txtWeight.getText();
        String totPrice = txtTotalPrice.getText();
        
//        updatePurchaseOrder.setPoNumber(updatePO.getPurchaseorderId().getPoNumber());
//        //updatePurchaseOrder.setDate(purchaseDate);
//        updatePurchaseOrder.setCompanyId(updatePO.getPurchaseorderId().getCompanyId());
//        updatePurchaseOrder.setTotalPrice(BigDecimal.valueOf(Double.valueOf(totPrice)));
//        CommonDao.updateData(updatePurchaseOrder);
//        
        updatePO.setQuantity(Integer.parseInt(qtySt));
        updatePO.setLineTotal(BigDecimal.valueOf(Double.valueOf(pricest)));
        updatePO.setLineWeight(Integer.parseInt(weightSt));
        CommonDao.updateData(updatePO);
        
        
        viewStage.close();
        
        //newTotPrice(updatePO.getLineTotal());
        
        
    }

    @FXML
    private void onQtyChange(KeyEvent event) {

        String qtySt = txtQty.getText();

        if (qtySt.isEmpty()) {
            txtPrice.setText("0");
            txtWeight.setText("0");
            txtTotalPrice.setText(("0"));
        } else {
            BigDecimal unitPri = updatePO.getItemId().getUnitPrice();
            BigDecimal lineTot = updatePO.getLineTotal();
            BigDecimal totPrice = updatePO.getPurchaseorderId().getTotalPrice();

            Integer unitWeight = updatePO.getItemId().getUnitWeight();
            Integer lineWeight = updatePO.getLineWeight();

//            String qtySt = txtQty.getText();
            Integer qtyInt = Integer.valueOf(qtySt);
            BigDecimal qtyBD = BigDecimal.valueOf(Double.valueOf(qtySt));

            BigDecimal newLineTot = unitPri.multiply(qtyBD);
            Integer newLineWeight = unitWeight * qtyInt;
            
            //newTotPrice(lineTot);
            
            txtPrice.setText(newLineTot.toString());
            txtWeight.setText(newLineWeight.toString());

        }

    }

    public void newTotPrice(BigDecimal lineTot) {
            String newLineSt = txtPrice.getText();
            BigDecimal newLineTot = BigDecimal.valueOf(Double.valueOf(newLineSt));
            
            String totPriceSt = txtTotalPrice.getText();
            BigDecimal totPrice = BigDecimal.valueOf(Double.valueOf(totPriceSt));
            
            
        if (Integer.parseInt(newLineTot.toString()) > Integer.parseInt(lineTot.toString())) {
            BigDecimal ex = newLineTot.subtract(lineTot);
            BigDecimal newTotPric = totPrice.add(ex);
            txtTotalPrice.setText(newTotPric.toString());

        } else {
            BigDecimal ex = lineTot.subtract(newLineTot);
            BigDecimal newTotPric = totPrice.subtract(ex);
            txtTotalPrice.setText(newTotPric.toString());
        }
    }

}
