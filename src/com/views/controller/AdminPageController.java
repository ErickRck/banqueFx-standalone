package com.views.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.core.dao.DAO;
import com.core.dao.DbConnection;
import com.core.dao.impl.ClientDAO;
import com.core.entite.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AdminPageController implements Initializable {
    Connection con= null;
    PreparedStatement ps = null;
    ResultSet rs = null;


     @FXML
    private TableView<Client> clientTable;
    @FXML
    private TableColumn<Client,String> col_code;
    @FXML
    private TableColumn<Client,String> col_nom;
    @FXML
    private TableColumn<Client,String> col_postnom;
    @FXML
    private TableColumn<Client,String> col_prenom;
    @FXML
    private TableColumn<Client,String> col_telephone;
    @FXML
    private TableColumn<Client,String> col_adresse;
    @FXML
    private TableColumn<Client,String> col_password;
    ObservableList<Client>data = FXCollections.observableArrayList();
    DAO<Client> c = new ClientDAO(DbConnection.Connection());
    Client cl = new Client();
    boolean etat = false;
    @FXML
    private Label welcome;
    String AdminID;
    @FXML
    private ImageView adminimage;
    @FXML
    private Label adminname;
    @FXML
    private Label adminid;

    public AdminPageController() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            con = DbConnection.Connection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

            LoadClientData();



    }

    public void GetAdminID(String id) throws SQLException, IOException, ClassNotFoundException {
        AdminID = id;
        ps = con.prepareStatement("SELECT * FROM admins WHERE id = ?");
        ps.setString(1, id);
        rs = ps.executeQuery();
        while(rs.next()){
            adminname.setText(rs.getString("nom"));
            adminid.setText(rs.getString("id"));
            InputStream is = rs.getBinaryStream("image");
            OutputStream os = new FileOutputStream(new File("adminimage.jpeg"));
            byte[] content = new byte[1024];
            int s = 0;
            while((s= is.read(content))!= -1){
            os.write(content, 0, s);
            }
            Image image = new Image("file:adminimage.jpeg");
            adminimage.setImage(image);
            adminimage.setFitWidth(248);
            adminimage.setFitHeight(186);
            Circle clip = new Circle(93,93,93);
            adminimage.setClip(clip);
        }
        ps.close();
        rs.close();
        con.close();
    }
    @FXML
    public void LoadClientData()   {

        clientTable.getItems().clear();
        clientTable.getColumns().addAll();
        clientTable.getItems().addAll(c.findList());
        col_code.setCellValueFactory(new PropertyValueFactory("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory("nom"));
        col_postnom.setCellValueFactory(new PropertyValueFactory("postNom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory("prenom"));
        col_adresse.setCellValueFactory(new PropertyValueFactory("adresse"));
        col_telephone.setCellValueFactory(new PropertyValueFactory("telephone"));
        col_password.setCellValueFactory(new PropertyValueFactory("password"));
    }

    @FXML
    public void gererClient() throws IOException, SQLException {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/GererClient.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        GererClientController apc = loader.getController();
        apc.GetAdminId( adminid.getText());
        Scene scene = new Scene(root);
        scene.getStylesheets().add("com/views/styles/AdminPage.css");
        Image icon = new Image("com/views/icons/UserPage.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Gerer Client Page");
        stage.setScene(scene);
        stage.show();
    }

    public void gererCompte(ActionEvent event) throws IOException, SQLException {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/GererCompte.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        GererCompteController ap = loader.getController();
        ap.GetAdminID( adminid.getText());
        Scene scene = new Scene(root);
        scene.getStylesheets().add("com/views/styles/AdminPage.css");
        Image icon = new Image("com/views/icons/UserPage.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Gerer compte Page");
        stage.setScene(scene);
        stage.show();
    }
    public void gererOperation(ActionEvent event) throws IOException, SQLException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/OperationPage.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        OperationPageController ap = loader.getController();
        ap.getAdminId( adminid.getText());
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
    public void ActualiserAdminPage(ActionEvent event){
        LoadClientData();
    }




}
