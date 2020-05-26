package com.views.controller;

import com.core.dao.DAO;
import com.core.dao.DbConnection;
import com.core.dao.impl.ClientDAO;
import com.core.entite.Client;
import com.core.exception.ClientException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GererClientController implements Initializable {
    Connection con= null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    @FXML
    private TableView<Client> clientTableGerer;
    @FXML
    private TableColumn<Client,Integer> col_code;
    @FXML
    private TableColumn<Client,String> col_nom;
    @FXML
    private TableColumn<Client,String> col_postnom;
    @FXML
    private TableColumn<Client,String> col_prenom;
    @FXML
    private TableColumn<Client,Integer> col_telephone;
    @FXML
    private TableColumn<Client,String> col_adresse;
    @FXML
    private TableColumn<Client,String> col_password;
    ObservableList<Client> data = FXCollections.observableArrayList();
    DAO<Client> c = new ClientDAO(DbConnection.Connection());
    Client cl = new Client();
    boolean etat = false;
    @FXML
    private Label welcome;
    private String AdminID;

    //champs de la page cliente
    @FXML private TextField ch_password_client;
    @FXML private TextField ch_nom_client;
    @FXML private TextField ch_postnom_client;
    @FXML private TextField ch_prenom_client;
    @FXML private TextField ch_telephone_client;
    @FXML private TextField ch_adresse_client;

    @FXML private TextField ch_code_client_click;

    //button de la page cliente
    @FXML private Button btn_ajouter_client;
    @FXML private Button btn_supprimer_client;
    @FXML private Button btn_modifier_client;

    private String rowClicked;

    public GererClientController() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            con = DbConnection.Connection();
            LoadClient();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void GetAdminId(String id) throws SQLException {

        AdminID = id;
        ps = con.prepareStatement("SELECT * FROM admins WHERE id = ?");
        ps.setString(1, id);
        rs = ps.executeQuery();
        while (rs.next()){
            welcome.setText(rs.getString("nom"));
        }
        ps.close();
        rs.close();
        con.close();
    }
    @FXML
    public void LoadClient()   {

        clientTableGerer.getItems().clear();
        clientTableGerer.getColumns().addAll();
        clientTableGerer.getItems().addAll(c.findList());
        col_code.setCellValueFactory(new PropertyValueFactory("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory("nom"));
        col_postnom.setCellValueFactory(new PropertyValueFactory("postNom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory("prenom"));
        col_adresse.setCellValueFactory(new PropertyValueFactory("adresse"));
        col_telephone.setCellValueFactory(new PropertyValueFactory("telephone"));
        col_password.setCellValueFactory(new PropertyValueFactory("password"));


        btn_ajouter_client.setDisable(false);
        btn_supprimer_client.setDisable(true);
        btn_modifier_client.setDisable(true);

    }

    public void clearTextFields(){
        ch_code_client_click.setText("");
        ch_nom_client.setText("");
        ch_postnom_client.setText("");
        ch_prenom_client.setText("");
        ch_telephone_client.setText("");
        ch_adresse_client.setText("");
        ch_password_client.setText("");
    }
    public void ActualiserClient(ActionEvent event){
        LoadClient();
        btn_modifier_client.setDisable(true);
        btn_supprimer_client.setDisable(true);
        btn_ajouter_client.setDisable(false);
        clearTextFields();
    }

    public void ajouterClient(ActionEvent event){

        try {
            Client client = new Client(ch_nom_client.getText(), ch_postnom_client.getText(), ch_prenom_client.getText(), ch_telephone_client.getText(), ch_adresse_client.getText(), ch_password_client.getText());
            etat = c.create(client);
        }catch (ClientException e){
            e.printStackTrace();
        }
        clearTextFields();
        LoadClient();
    }

    public void tableClickedClient(MouseEvent mouseEvent){
        try {
            rowClicked = clientTableGerer.getSelectionModel().getSelectedItems().get(0).getId();

            ch_code_client_click.setText(rowClicked);
            ch_nom_client.setText(clientTableGerer.getSelectionModel().getSelectedItems().get(0).getNom());
            ch_postnom_client.setText(clientTableGerer.getSelectionModel().getSelectedItems().get(0).getPostNom());
            ch_prenom_client.setText(clientTableGerer.getSelectionModel().getSelectedItems().get(0).getPrenom());
            ch_telephone_client.setText(clientTableGerer.getSelectionModel().getSelectedItems().get(0).getTelephone());
            ch_adresse_client.setText(clientTableGerer.getSelectionModel().getSelectedItems().get(0).getAdresse());
            ch_password_client.setText(clientTableGerer.getSelectionModel().getSelectedItems().get(0).getPassword());

            btn_ajouter_client.setDisable(true);
            btn_modifier_client.setDisable(false);
            btn_supprimer_client.setDisable(false);
        }catch (Exception e){
            System.out.println("Table vide");
        }
    }

    public void modifierClient(ActionEvent event){
        if (Integer.parseInt(rowClicked)>0) {
            try {
                Client cliente = new Client(ch_code_client_click.getText(),ch_nom_client.getText(), ch_postnom_client.getText(), ch_prenom_client.getText(), ch_telephone_client.getText(), ch_adresse_client.getText(), ch_password_client.getText());
                etat = c.update(cliente);


            }catch (ClientException e){
                e.printStackTrace();
            }
        }else {

        }
        LoadClient();
        clearTextFields();
    }

    public void supprimerClient(ActionEvent event){
        if (Integer.parseInt(rowClicked)>0){
            try {
                Client client = new Client(ch_code_client_click.getText());
                etat = c.delete(client);

                LoadClient();
                clearTextFields();
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
    }
}
