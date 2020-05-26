package com.views.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.core.dao.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class UserPageController implements Initializable {
    @FXML
    private Label welcome;
    @FXML
    private TextArea quotedis;
    String UserID, UserName;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public void GetUserID(String id, String Name) throws SQLException {
        welcome.setText(Name);
        UserID = id;
        UserName = Name;
        Quotes qt = new Quotes();
        String quote = qt.returnQuotes();
        quotedis.setText(quote);

    }
    public void SoldeCompte(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        Connection con = DbConnection.Connection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ps = con.prepareStatement("SELECT * FROM client WHERE codeClient = ? AND nom = ?");
        ps.setString(1, UserID);
        ps.setString(2, UserName);
        rs = ps.executeQuery();
        while (rs.next()) {

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/EtatComptePage.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            EtatCompteController bpc = loader.getController();
            bpc.GetClientName(UserName);

            Scene scene = new Scene(root);
            scene.getStylesheets().add("com/views/styles/DepositPage.css");
            Image icon = new Image("com/views/icons/DepositPage.png");
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.setTitle("Balance Page");
            stage.setScene(scene);
            stage.show();
        }
        ps.close();
        rs.close();
    }

    @FXML
    void Versement(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/VersementPage.fxml"));
        loader.load();
        Parent root = loader.getRoot();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("com/views/styles/DepositPage.css");
        Image icon = new Image("com/views/icons/DepositPage.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Depot Page");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Virement(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/TransfertPage.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("com/views/styles/DepositPage.css");
        Image icon = new Image("com/views/icons/DepositPage.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Balance Transfert");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void Retrait(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/RetraitPage.fxml"));
        loader.load();
        Parent root = loader.getRoot();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("com/views/styles/DepositPage.css");
        Image icon = new Image("com/views/icons/DepositPage.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Depot Page");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void ListeOperation() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/ListeOperationPage.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("com/views/styles/AdminPage.css");
        Image icon = new Image("com/views/icons/UserPage.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Operation Page");
        stage.setScene(scene);
        stage.show();
    }
}







