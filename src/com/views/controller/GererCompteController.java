package com.views.controller;

import com.core.dao.DAO;
import com.core.dao.DbConnection;
import com.core.dao.impl.CompteDAO;
import com.core.entite.Client;
import com.core.entite.Compte;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class GererCompteController implements Initializable {
    String AdminId;
    @FXML private Label welcome;

    Connection con= null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String rowClicked;

    DAO<Compte> c = new CompteDAO(DbConnection.Connection());
    boolean etat = false;


    @FXML private TableView<Compte> compteTableGerer;
    @FXML private TableColumn<Compte, String> col_code_compte;
    @FXML private TableColumn<Compte, Date> col_date_creation;
    @FXML private TableColumn<Compte, String> col_type_compte;
    @FXML private TableColumn<Compte, Client> col_code_client;
    @FXML private TableColumn<Compte, Double> col_solde;

    @FXML private TextField ch_code_compte_click;

    @FXML private TextField ch_code_compte;
    @FXML private DatePicker ch_date_creation;
    @FXML private TextField ch_solde_compte;
    @FXML private TextField ch_type_compte;
    @FXML private TextField ch_code_client;

    @FXML private Button btn_ajouter_compte;
    @FXML private Button btn_supprimer_compte;
    @FXML private Button btn_modifier_compte;
    @FXML private Button actualiser_compte;
    @FXML private RadioButton btn_radio_CC;
    @FXML private RadioButton btn_radio_CE;

    public GererCompteController() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            con = DbConnection.Connection();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        LoardCompte();
        btn_supprimer_compte.setDisable(true);
        btn_modifier_compte.setDisable(false);
        ch_code_compte_click.setDisable(true);
    }
    public void GetAdminID(String id) throws SQLException {
        AdminId = id;
        ps = con.prepareStatement("SELECT * FROM admins WHERE id = ?");
        ps.setString(1, id);
        rs = ps.executeQuery();
        while (rs.next()){
            welcome.setText(rs.getString("nom"));
        }
        ps.close();
        rs.close();

    }
    public void LoardCompte(){

        compteTableGerer.getItems().clear();
        compteTableGerer.getColumns().addAll();
        compteTableGerer.getItems().addAll(c.findList());
        col_code_compte.setCellValueFactory(new PropertyValueFactory("code"));
        col_date_creation.setCellValueFactory(new PropertyValueFactory("dateCreation"));
        col_solde.setCellValueFactory(new PropertyValueFactory("solde"));
        col_type_compte.setCellValueFactory(new PropertyValueFactory("typeCpte"));
        col_code_client.setCellValueFactory(new PropertyValueFactory("client"));
    }
    public void tableClickedClient(MouseEvent mouseEvent){
        try {
            rowClicked = compteTableGerer.getSelectionModel().getSelectedItems().get(0).getCode();

            ch_code_compte_click.setText(rowClicked);
            ch_solde_compte.setText(String.valueOf(compteTableGerer.getSelectionModel().getSelectedItems().get(0).getSolde()));
            ch_type_compte.setText(compteTableGerer.getSelectionModel().getSelectedItems().get(0).getTypeCpte());
            ch_code_client.setText(compteTableGerer.getSelectionModel().getSelectedItems().get(0).getClient());

            ch_code_compte.setDisable(true);
            ch_code_compte_click.setDisable(false);
            btn_modifier_compte.setDisable(false);
            btn_supprimer_compte.setDisable(false);
            btn_ajouter_compte.setDisable(true);
            btn_radio_CC.setDisable(true);
            btn_radio_CE.setDisable(true);


        }catch (Exception e){
            System.out.println("Table vide");
        }
    }
    public void selectCompteCourant(ActionEvent event){

        ch_type_compte.setText("CC");
    }
    public void selectCompteEpargne(){

        ch_type_compte.setText("CE");
    }
    public void ajouterCompte(ActionEvent event){

        if (ch_code_compte.getText().isEmpty() && ch_solde_compte.getText().isEmpty() && ch_code_client.getText().isEmpty() && ch_type_compte.getText().isEmpty()){
            Alert ajouterChampsVides = new Alert(Alert.AlertType.ERROR);
            ajouterChampsVides.setHeaderText(null);
            ajouterChampsVides.setContentText("VEUILLEZ REMPLIR TOUT LES CHAMPS");
            ajouterChampsVides.showAndWait();
        }else {

            try {
                Compte compte = new Compte(ch_code_compte.getText(), ch_solde_compte.getText(),ch_code_client.getText(), ch_type_compte.getText());
                etat = c.create(compte);
            }catch (Exception e){
                e.printStackTrace();
            }
            Alert ajouterReussi = new Alert(Alert.AlertType.CONFIRMATION);
            ajouterReussi.setHeaderText(null);
            ajouterReussi.setContentText("L'AJOUT DU COMPTE NUMERO "+this.ch_code_compte.getText()+" REUSSI");
            ajouterReussi.showAndWait();
        }
        LoardCompte();
    }
    public void modifierCompte(ActionEvent event) throws IOException {

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
        stage.setTitle("Versement Page");
        stage.setScene(scene);
        stage.show();
    }
    public void supprimerCompte(ActionEvent event){
        if (Integer.parseInt(rowClicked)>0){

            try {
                Compte compte = new Compte(ch_code_compte_click.getText());
                etat = c.delete(compte);
                Alert supprimerReussi = new Alert(Alert.AlertType.CONFIRMATION);
                supprimerReussi.setHeaderText(null);
                supprimerReussi.setContentText("SUPPRESSIONDU DU COMPTE "+this.ch_code_compte_click.getText()+" REUSSITE");
                supprimerReussi.showAndWait();
            }catch (Exception e){
                e.printStackTrace();
            }
            LoardCompte();
            ViderChamps();
        }
    }
    public void ActualiserCompte(ActionEvent event){

        LoardCompte();
        ViderChamps();
        btn_ajouter_compte.setDisable(false);
        btn_modifier_compte.setDisable(false);
        btn_supprimer_compte.setDisable(true);
        btn_radio_CE.setDisable(false);
        btn_radio_CC.setDisable(false);

    }
    public void ViderChamps(){
        ch_code_compte.setText("");
        ch_type_compte.setText("");
        ch_solde_compte.setText("");
        ch_code_compte_click.setText("");
        ch_code_client.setText("");

    }
}
