package com.views.controller;

import com.core.banqueMetier.BanqueMetierImpl;
import com.core.banqueMetier.IBanqueMetier;
import com.core.dao.DAO;
import com.core.dao.DbConnection;
import com.core.dao.impl.ClientDAO;
import com.core.dao.impl.CompteDAO;
import com.core.dao.impl.OperationDAO;
import com.core.entite.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EtatCompteController implements Initializable {
    @FXML private Label lbl_nom;
    @FXML private Label lbl_nom_client;
    @FXML private Label lbl_code_compte;
    @FXML private Label lbl_solde;
    @FXML private Label lbl_date_creation;
    @FXML private Label lbl_type_compte;
    @FXML private Label lbl_categorie;
    @FXML private Label lbl_decouvert;

    @FXML private TextField ch_code_compte;

    @FXML private TextField ch_code_compte_versement;
    @FXML private TextField ch_montant_depot;

    @FXML private TextField ch_code_compte_1;
    @FXML private TextField ch_montant_virement;
    @FXML private TextField ch_code_compte_2;

    @FXML private TextField ch_code_compte_retrait;
    @FXML private TextField ch_montant_retrait;

    @FXML private TableView<Operation> operationTable;
    @FXML private TableColumn<Operation, Integer> col_id_operation;
    @FXML private TableColumn<Operation, String> col_date_operation;
    @FXML private TableColumn<Operation, Double> col_montant;
    @FXML private TableColumn<Operation, String> col_type_operation;
    @FXML private TableColumn<Operation, String> col_code_compte;
    @FXML private TextField ch_code_compte_operation;


    Client client= new Client();
    Compte cpte=null;
    CompteDAO compteDAO=new CompteDAO(DbConnection.Connection());
    ClientDAO clientDAO= new ClientDAO(DbConnection.Connection());
    IBanqueMetier iBanqueMetier= new BanqueMetierImpl();

    OperationDAO Opera = new OperationDAO(DbConnection.Connection());
    Compte compteOp = null;
    DAO<Operation> Op = new OperationDAO(DbConnection.Connection());

    public EtatCompteController() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void GetClientName(String name){
    lbl_nom.setText(name);
    }
    public void getCodeCompte(ActionEvent event)  {
            findCompte();
    }
    public void findCompte(){
        cpte = compteDAO.find((Object) this.ch_code_compte.getText());
        if (cpte instanceof CompteCourant) {
            client= clientDAO.find((Object) ((CompteCourant)cpte).getClient());
            this.lbl_code_compte.setText(((CompteCourant)cpte).getCode());
            this.lbl_nom_client.setText(client.getNom());
            this.lbl_solde.setText(String.valueOf(((CompteCourant)cpte).getSolde()));
            this.lbl_date_creation.setText(String.valueOf(((CompteCourant)cpte).getDateCreation()));
            this.lbl_type_compte.setText("Compte courant");
            this.lbl_decouvert.setText(String.valueOf(((CompteCourant)cpte).getDecouvert()));
        }else if (cpte instanceof CompteEpargne){

            client=clientDAO.find((Object) ((CompteEpargne)cpte).getClient());
            this.lbl_code_compte.setText(((CompteCourant)cpte).getCode());
            this.lbl_nom_client.setText(client.getNom());
            this.lbl_solde.setText(String.valueOf(((CompteCourant)cpte).getSolde()));
            this.lbl_date_creation.setText(String.valueOf(((CompteCourant)cpte).getDateCreation()));
            this.lbl_type_compte.setText("Compte Epargne");
            this.lbl_decouvert.setText(String.valueOf(((CompteEpargne)cpte).getTaux()));
            this.lbl_categorie.setText("Taux");
        }else {
            Alert compteInexistant = new Alert(Alert.AlertType.ERROR);
            compteInexistant.setHeaderText(null);
            compteInexistant.setContentText("COMPTE INTROUVABLE");
            compteInexistant.showAndWait();
        }
    }
    public void Depot(ActionEvent event){
        if (ch_code_compte_versement.getText().isEmpty() && ch_montant_depot.getText().isEmpty()){

            Alert depotChampsVide = new Alert(Alert.AlertType.ERROR);
            depotChampsVide.setHeaderText(null);
            depotChampsVide.setContentText("VEUILLEZ REMPLIR TOUT LES CHAMPS");
            depotChampsVide.showAndWait();

        }else {
            iBanqueMetier.verser(this.ch_code_compte_versement.getText(), Double.parseDouble(this.ch_montant_depot.getText()));

            Alert versementReussi = new Alert(Alert.AlertType.CONFIRMATION);
            versementReussi.setHeaderText(null);
            versementReussi.setContentText("VOTRE COMPTE A ETE DEBUTER DE "+this.ch_montant_depot.getText()+" USD");
            versementReussi.showAndWait();
            this.ch_montant_depot.clear();
        }

    }
    public void Virer(ActionEvent event){
        if (ch_code_compte_1.getText().isEmpty() && ch_code_compte_2.getText().isEmpty() && ch_montant_virement.getText().isEmpty()){
            Alert virerChampsVides = new Alert(Alert.AlertType.ERROR);
            virerChampsVides.setHeaderText(null);
            virerChampsVides.setContentText("VEUILLEZ REMPLIR TOUT LES CHAMPS");
            virerChampsVides.showAndWait();
        }else {
            iBanqueMetier.virement(this.ch_code_compte_1.getText(), this.ch_code_compte_2.getText(), Double.parseDouble(this.ch_montant_virement.getText()));
           Alert virementReussi = new Alert(Alert.AlertType.CONFIRMATION);
           virementReussi.setHeaderText(null);
           virementReussi.setContentText("VOUS AVEZ TRANSFERER "+this.ch_montant_virement.getText()+"USD VERS LE COMPTE "+this.ch_code_compte_2.getText());
            this.ch_code_compte_1.clear();
            this.ch_code_compte_2.clear();
            this.ch_montant_virement.clear();
        }
    }
    public void Retirer(ActionEvent event){
        if (ch_code_compte_retrait.getText().isEmpty() && ch_montant_retrait.getText().isEmpty()){
            Alert retraitChampsVides = new Alert(Alert.AlertType.ERROR);
            retraitChampsVides.setHeaderText(null);
            retraitChampsVides.setContentText("VEUILLEZ REMPLIR TOUT LES CHAMPS");
            retraitChampsVides.showAndWait();
        }else {
            iBanqueMetier.retrait(this.ch_code_compte_retrait.getText(), Double.parseDouble(this.ch_montant_retrait.getText()));
            Alert retraitReussi = new Alert(Alert.AlertType.CONFIRMATION);
            retraitReussi.setHeaderText(null);
            retraitReussi.setContentText("LE RETRAIT DE "+this.ch_montant_retrait.getText()+" USD EFFECTUER");
            retraitReussi.showAndWait();
            this.ch_montant_retrait.clear();
        }
    }
    public void ListeOperationCompte(){

        cpte = compteDAO.find((Object)this.ch_code_compte_operation.getText());
        if (cpte!=null) {
            operationTable.getItems().clear();
            operationTable.getColumns().addAll();
            operationTable.getItems().addAll(Opera.findListOperation(cpte));
            col_id_operation.setCellValueFactory(new PropertyValueFactory("id"));
            col_date_operation.setCellValueFactory(new PropertyValueFactory("dateOperation"));
            col_montant.setCellValueFactory(new PropertyValueFactory("montant"));
            col_type_operation.setCellValueFactory(new PropertyValueFactory("typeOp"));
            col_code_compte.setCellValueFactory(new PropertyValueFactory("compte"));
        }else {

            Alert cpteIntouvable = new Alert(Alert.AlertType.ERROR);
            cpteIntouvable.setHeaderText(null);
            cpteIntouvable.setContentText("COMPTE INTROUVABLE");
            cpteIntouvable.showAndWait();
        }
    }
}
