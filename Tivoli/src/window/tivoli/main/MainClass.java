/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import window.tivoli.dao.NewHibernateUtil;

/**
 *
 * @author Sashenka
 */
public class MainClass extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        try {
            NewHibernateUtil.getSessionFactory();
            BorderPane root = FXMLLoader.load(getClass().getResource("/window/tivoli/ui/DashboardUI.fxml"));
            
            Scene s = new Scene(root);
            primaryStage.setScene(s);
            //primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setResizable(false);
            this.primaryStage = primaryStage;
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
