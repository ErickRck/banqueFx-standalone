package com.views.controller;

import com.core.banqueMetier.BanqueMetierImpl;
import com.core.banqueMetier.IBanqueMetier;
import com.core.dao.DAO;
import com.core.dao.DbConnection;
import com.core.dao.impl.OperationDAO;
import com.core.entite.Operation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OperationPageController implements Initializable {

    @FXML private RadioButton btn_radio_versement;
    @FXML private RadioButton btn_radio_retrait;
    @FXML private RadioButton btn_radio_virement;

    @FXML private TextField ch_code_compte;
    @FXML private TextField ch_montant;
    @FXML private TextField ch_code_compte_destinateur;

    @FXML private Button btn_versement;
    @FXML private Button btn_retrait;
    @FXML private Button btn_virement;

    @FXML private ToggleGroup Ver0rRet;


    @FXML private TableView<Operation> operationTable;
    @FXML private TableColumn<Operation, Integer> col_id_operation;
    @FXML private TableColumn<Operation, String> col_date_operation;
    @FXML private TableColumn<Operation, Double> col_montant;
    @FXML private TableColumn<Operation, String> col_type_operation;
    @FXML private TableColumn<Operation, String> col_code_compte;

    ObservableList<Operation>data = FXCollections.observableArrayList();

    @FXML private Label welcome;
    String AdminID;

    Connection con= null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    boolean etat = false;
    DAO<Operation> Op = new OperationDAO(DbConnection.Connection());
    IBanqueMetier iBanqueMetier= new BanqueMetierImpl();

    public OperationPageController() throws SQLException, ClassNotFoundException {
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
        LoardOperation();
        btn_virement.setDisable(true);
        btn_retrait.setDisable(true);
        btn_versement.setDisable(true);

    }
    public void getAdminId(String id) throws SQLException {
        AdminID = id;
        ps = con.prepareStatement("SELECT * FROM admins WHERE id= ?");
        ps.setString(1, id);
        rs = ps.executeQuery();
        while (rs.next()){
            welcome.setText(rs.getString("nom"));
        }
        ps.close();
        rs.close();
        con.close();
    }

    public void LoardOperation(){

        operationTable.getItems().clear();
        operationTable.getColumns().addAll();

        operationTable.getItems().addAll(Op.findList());
        col_id_operation.setCellValueFactory(new PropertyValueFactory("id"));
        col_date_operation.setCellValueFactory(new PropertyValueFactory("dateOperation"));
        col_montant.setCellValueFactory(new PropertyValueFactory("montant"));
        col_type_operation.setCellValueFactory(new PropertyValueFactory("typeOp"));
        col_code_compte.setCellValueFactory(new PropertyValueFactory("compte"));


    }

    public void selectVersement(ActionEvent event){
        ch_code_compte_destinateur.setDisable(true);
        btn_virement.setDisable(true);
        btn_versement.setDisable(false);
        btn_retrait.setDisable(true);
    }
    public void selectRetrait(ActionEvent event){
        ch_code_compte_destinateur.setDisable(true);
        btn_virement.setDisable(true);
        btn_retrait.setDisable(false);
        btn_versement.setDisable(true);
    }
    public void selectVirement(ActionEvent event){
        ch_code_compte_destinateur.setDisable(false);
        btn_virement.setDisable(false);
        btn_versement.setDisable(true);
        btn_retrait.setDisable(true);
    }

    public void VersementOp(ActionEvent event){
        if (ch_code_compte.getText().isEmpty() && ch_montant.getText().isEmpty()){
            Alert versementOpChampsVides = new Alert(Alert.AlertType.ERROR);
            versementOpChampsVides.setHeaderText(null);
            versementOpChampsVides.setContentText("VEUILLEZ REMPLIR TOUT LES CHAMPS");
            versementOpChampsVides.showAndWait();
        }else {
            iBanqueMetier.verser(this.ch_code_compte.getText(), Double.parseDouble(this.ch_montant.getText()));
            Alert versementOpReussi = new Alert(Alert.AlertType.CONFIRMATION);
            versementOpReussi.setHeaderText(null);
            versementOpReussi.setContentText("LE COMPTE "+this.ch_code_compte.getText()+" A ETE DEBITER DE "+this.ch_montant.getText()+" USD");
            versementOpReussi.showAndWait();
            ch_montant.clear();
        }
    }
    public void RetraitOp(ActionEvent event){
        if (ch_code_compte.getText().isEmpty() && ch_montant.getText().isEmpty()){
            Alert retraitOpChampsVides = new Alert(Alert.AlertType.ERROR);
            retraitOpChampsVides.setHeaderText(null);
            retraitOpChampsVides.setContentText("VEUILLEZ REMPLIR TOUT LES CHAMPS");
        }else {
            iBanqueMetier.retrait(this.ch_code_compte.getText(), Double.parseDouble(this.ch_montant.getText()));
            Alert retraitOpReussi = new Alert(Alert.AlertType.CONFIRMATION);
            retraitOpReussi.setHeaderText(null);
            retraitOpReussi.setContentText("LE RETRAIT DE "+this.ch_montant.getText()+" USD EFFCTUER DEPUIS LE COMPTE "+this.ch_code_compte.getText());
            retraitOpReussi.showAndWait();
            ch_montant.clear();
        }
    }
    public void VirementOp(ActionEvent event){
        if (ch_code_compte.getText().isEmpty() && ch_code_compte_destinateur.getText().isEmpty() && ch_montant.getText().isEmpty()){
            Alert virementOpChapmsVides = new Alert(Alert.AlertType.ERROR);
            virementOpChapmsVides.setHeaderText(null);
            virementOpChapmsVides.setContentText("VEUILLEZ REMPLIR TOUT LES CHAMPS");
            virementOpChapmsVides.showAndWait();
        }else {
            iBanqueMetier.virement(this.ch_code_compte.getText(), this.ch_code_compte_destinateur.getText(), Double.parseDouble(this.ch_montant.getText()));
            Alert vierementOpReussi = new Alert(Alert.AlertType.CONFIRMATION);
            vierementOpReussi.setHeaderText(null);
            vierementOpReussi.setContentText("LE VIEREMENT DE "+this.ch_montant.getText()+" USD DEPUIS LE COMPTE "+this.ch_code_compte.getText()+" VERS "+this.ch_code_compte_destinateur.getText()+" REUSSI");
            vierementOpReussi.showAndWait();
            ch_montant.clear();
        }
    }
    public void ActualiserOperation(ActionEvent event){
        ch_code_compte_destinateur.setText("");
        ch_code_compte_destinateur.setDisable(true);
        btn_virement.setDisable(true);
        btn_versement.setDisable(true);
        btn_retrait.setDisable(true);
        ch_code_compte.setText("");
        ch_montant.setText("");
        LoardOperation();

    }
}
