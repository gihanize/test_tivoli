/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Sashenka
 */
public class DashboardUIController implements Initializable {

    @FXML
    private Button btnNewPO;
    @FXML
    private Button btnPOMng;
    @FXML
    private Button btnManu;
    @FXML
    private Button btnOnProgress;
    @FXML
    private Button btnDispatch;
    @FXML
    private StackPane stkPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnNewPOAction(ActionEvent event) {
        try {
            BorderPane pane = FXMLLoader.load(getClass().getResource("NewPurchaseOrder.fxml"));
            stkPane.getChildren().clear();
            stkPane.getChildren().add(pane);
        } catch (IOException ex) {
            Logger.getLogger(DashboardUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnPOMngAction(ActionEvent event) {
        try {
            BorderPane pane = FXMLLoader.load(getClass().getResource("PurchaseOrderMngUI.fxml"));
            stkPane.getChildren().clear();
            stkPane.getChildren().add(pane);
        } catch (IOException ex) {
            Logger.getLogger(DashboardUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnManuAction(ActionEvent event) {
        try {
            BorderPane pane = FXMLLoader.load(getClass().getResource("ManifactureManagementUI.fxml"));
            stkPane.getChildren().clear();
            stkPane.getChildren().add(pane);
        } catch (IOException ex) {
            Logger.getLogger(DashboardUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnOnProgressAction(ActionEvent event) {
        try {
            BorderPane pane = FXMLLoader.load(getClass().getResource("OnProgressUI.fxml"));
            stkPane.getChildren().clear();
            stkPane.getChildren().add(pane);
        } catch (IOException ex) {
            Logger.getLogger(DashboardUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnDispatchAction(ActionEvent event) {
        try {
            BorderPane pane = FXMLLoader.load(getClass().getResource("DispatchUI.fxml"));
            stkPane.getChildren().clear();
            stkPane.getChildren().add(pane);
        } catch (IOException ex) {
            Logger.getLogger(DashboardUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
