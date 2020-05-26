package com.core.dao.impl;

import com.core.dao.DAO;
import com.core.entite.Compte;
import com.core.entite.Operation;
import com.core.entite.Retrait;
import com.core.entite.Versement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperationDAO extends DAO<Operation> {

	public OperationDAO(Connection connexion) {
		super(connexion);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Operation obj) {
		boolean status=false;
		try {
			PreparedStatement prepare= this.connexion.prepareStatement("INSERT INTO operation SET "
					+ " montant=?, typeOperation=?, codeCpte=? , dateOperation=NOW()");

			prepare.setDouble(1, obj.getMontant());
			if (obj instanceof Retrait)
			prepare.setString(2, ((Retrait)obj).getTypeOp());
			else if (obj instanceof Versement)
				prepare.setString(2, ((Versement)obj).getTypeOp());
			prepare.setString(3, obj.getCompte());

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
	public boolean update(Operation obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Operation find(Object obj) {

		Object operation = null;
		CompteDAO compteDAO= new CompteDAO(connexion);
		ResultSet result;
		String typeOp;

		try {
			PreparedStatement prepare= this.connexion.prepareStatement("SELECT *FROM operation WHERE numOperation=?");

			prepare.setString(1, obj.toString());
			//prepare.setInt(1,Integer.parseInt(obj.toString()));

			result=prepare.executeQuery();
			while(result.next())
			{
				typeOp= result.getString("typeOperation");

				if (typeOp.equals("VERSEMENT"))
					operation=new Versement(result.getInt("numOperation"),result.getDate("dateOperation"), result.getDouble("montant"), compteDAO.find(result.getString("codeCpte")));
				else if (typeOp.equals("RETRAIT"))
					operation= new Retrait(result.getDate("dateOperation"), result.getDouble("montant"), compteDAO.find(result.getString("codeCpte")));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return (Operation)operation;
	}

	@Override
	public boolean delete(Operation obj) {

		return false;
	}

	@Override
	public List<Operation> findList() {

		List<Operation> liste= new ArrayList<Operation>();
		ResultSet result;

		try {
			PreparedStatement prepare= this.connexion.prepareStatement("SELECT * FROM operation");

			result=prepare.executeQuery();
			while(result.next())
			{
				liste.add(this.find(result.getObject("numOperation")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
	}

	public List<Operation> findListOperation(Compte compte) {

		List<Operation> liste= new ArrayList<Operation>();
		ResultSet result;

		try {
			PreparedStatement prepare= this.connexion.prepareStatement("SELECT *FROM operation WHERE codeCpte=?");
			prepare.setString(1, compte.getCode());

			result=prepare.executeQuery();
			while(result.next())
			{
				liste.add(this.find(result.getObject("numOperation")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
	}

}
