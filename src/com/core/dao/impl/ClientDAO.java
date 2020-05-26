package com.core.dao.impl;

import com.core.dao.DAO;
import com.core.entite.Client;
import com.core.exception.ClientException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO extends DAO<Client> {

	public ClientDAO(Connection connexion) {
		super(connexion);

	}

	@Override
	public boolean create(Client obj) {

		boolean status=false;
		if (obj.isNew() && obj.isValid())
		{
			try {
				PreparedStatement prepare= this.connexion.prepareStatement("INSERT INTO client SET "
						+ "nom=?, postnom=?, prenom=?, telephone=?, adresse=?, password=?");

				prepare.setString(1, obj.getNom());
				prepare.setString(2, obj.getPostNom());
				prepare.setString(3, obj.getPrenom());
				prepare.setString(4, obj.getTelephone());
				prepare.setString(5, obj.getAdresse());
				prepare.setString(6, obj.getPassword());

				int etat= prepare.executeUpdate();

				status= (etat> 0)? true:false;

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Objet invalid");
		}
		return status;
	}

	@Override
	public boolean update(Client obj) {

		boolean status=false;
		if (obj.isValid())
		{
			try {
				PreparedStatement prepare= this.connexion.prepareStatement("UPDATE client SET nom=?, postnom=?, prenom=?, telephone=?, adresse=?, password=? WHERE codeClient=?");

				prepare.setString(1, obj.getNom());
				prepare.setString(2, obj.getPostNom());
				prepare.setString(3, obj.getPrenom());
				prepare.setString(4, obj.getTelephone());
				prepare.setString(5, obj.getAdresse());
				prepare.setString(6, obj.getPassword());
				prepare.setString(7, obj.getId());

				int etat= prepare.executeUpdate();

				status= (etat>0)? true:false;
				prepare.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return status;
	}

	public Client find(Object obj) {
		Client client = new Client();
		ResultSet resultSet;
		try{
			PreparedStatement prepare= this.connexion.prepareStatement("Select * FROM client where codeClient=?");

			prepare.setInt(1, Integer.parseInt(obj.toString()));

			resultSet= prepare.executeQuery();

			while (resultSet.next())
			{
				try {
					client= new Client(resultSet.getString("codeClient"),resultSet.getString("nom"), resultSet.getString("postnom"), resultSet.getString("prenom"),resultSet.getString("telephone"),resultSet.getString("adresse"),resultSet.getString("password"));

				} catch (ClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return client;
	}

	@Override
	public boolean delete(Client obj) {

		boolean status= false;

			try {
				PreparedStatement prepare= this.connexion.prepareStatement("DELETE FROM client WHERE codeClient=?");

				prepare.setString(1, obj.getId());

				int etat= prepare.executeUpdate();
				status= (etat>0)? true:false;

				prepare.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return status;
	}

	@Override
	public List<Client> findList() {

		List<Client> liste= new ArrayList<Client>();
		ResultSet result;

		try {
			PreparedStatement prepare= this.connexion.prepareStatement("select * FROM client");

			result= prepare.executeQuery();

			while (result.next())
			{
				liste.add(this.find(result.getObject("codeClient")));

			}
			result.close();
			prepare.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
	}
}
