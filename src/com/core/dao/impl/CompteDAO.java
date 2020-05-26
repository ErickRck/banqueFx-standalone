package com.core.dao.impl;

import com.core.dao.DAO;
import com.core.entite.Compte;
import com.core.entite.CompteCourant;
import com.core.entite.CompteEpargne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompteDAO extends DAO<Compte>{

	public CompteDAO(Connection connexion) {
		super(connexion);
		// TODO Auto-generated constructor stub
	}



	@SuppressWarnings("null")
	@Override
	public boolean create(Compte obj) {
		boolean status=false;

		try {
			PreparedStatement prepare= this.connexion.prepareStatement("INSERT INTO compte SET "
					+ "codeCpte=?, solde=?, codeClient=?, decouvert=?, taux=?, typeCpte=?, dateCreation=NOW()");
			prepare.setString(1, obj.getCode());
			prepare.setString(2, obj.getSold());
			prepare.setString(3, obj.getCodClient());
			if (obj instanceof CompteCourant)
			{
				prepare.setDouble(4, ((CompteCourant) obj).getDecouvert());
			}
			else{
				prepare.setString(4,null);
			}
			if (obj instanceof CompteEpargne)
			{
				prepare.setDouble(5, ((CompteEpargne)obj).getTaux());
			}
			else{
				prepare.setString(5, null);
			}

			prepare.setString(6, obj.getTypeCpte());

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
	public boolean update(Compte obj) {

		boolean status=false;
		try {
			PreparedStatement prepare=this.connexion.prepareStatement("UPDATE compte SET solde=? WHERE codeCpte=?");

			prepare.setString(1, String.valueOf(obj.getSolde()));
			prepare.setString(2, obj.getCode());

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
	public boolean delete(Compte obj) {
		boolean status=false;

		try {
			PreparedStatement prepare= this.connexion.prepareStatement("DELETE FROM compte WHERE codeCpte=?");

			prepare.setString(1, obj.getCode());

			int etat= prepare.executeUpdate();

			status=(etat>0)? true:false;
			prepare.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Compte find(Object obj) {
		Object compte = null;
		String typeCpte=null;
		ClientDAO clientDao = new ClientDAO(connexion);
		ResultSet result;

		try {
			PreparedStatement prepare= this.connexion.prepareStatement("SELECT *FROM compte WHERE codeCpte=?");
			prepare.setString(1, obj.toString());

			result=prepare.executeQuery();

			while(result.next())
			{
				typeCpte=result.getString("typeCpte");

				if (typeCpte.equals("CC"))
				{
					compte= new CompteCourant(result.getString("codeCpte"), result.getDate("dateCreation"), result.getDouble("solde"), clientDao.find(result.getString("codeClient")),result.getString("typeCpte"), result.getDouble("decouvert"));
				}
				else if (typeCpte.equals("CE"))
				{
					compte= new CompteEpargne(result.getString("codeCpte"), result.getDate("dateCreation"), result.getDouble("solde"), clientDao.find(result.getString("codeClient")),result.getString("typeCpte"), result.getDouble("taux"));
				}
				typeCpte=null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (Compte)compte;
	}

	@Override
	public List<Compte> findList() {
		List<Compte> liste=new ArrayList<Compte>();

		ResultSet result;
		try {
			PreparedStatement prepare= this.connexion.prepareStatement("SELECT *FROM compte ");

			result=prepare.executeQuery();
			while(result.next())
			{
				liste.add(this.find(result.getString("codeCpte")));
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
