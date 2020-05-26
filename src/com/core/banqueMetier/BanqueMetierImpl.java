package com.core.banqueMetier;

import com.core.dao.DbConnection;
import com.core.dao.impl.CompteDAO;
import com.core.dao.impl.OperationDAO;
import com.core.entite.*;
import javafx.scene.control.TextField;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class BanqueMetierImpl implements IBanqueMetier {

	private Connection con= DbConnection.Connection();
	private CompteDAO compteDAO=new CompteDAO(con);
	private OperationDAO operationDAO= new OperationDAO(con);

	public BanqueMetierImpl() throws SQLException, ClassNotFoundException {
	}

	@Override
	public Compte consulterCompte(String codeCpte) {

		Compte cp =compteDAO.find(codeCpte);
		if (cp==null)
			throw new RuntimeException("Compte introuvable");
		return cp;
	}

	@Override
	public void verser(String codeCpte, double montant) {
		Compte cp= consulterCompte(codeCpte);
		Versement v= new Versement(new Date(), montant, cp);
		operationDAO.create(v);
			cp.setSolde(cp.getSolde()+montant);
			compteDAO.update(cp);
	}

	@Override
	public void retrait(String codeCpte, double montant) {
		Compte cp= consulterCompte(codeCpte);
		double facilteCaisse=0;
		if (cp instanceof CompteCourant)
			facilteCaisse= ((CompteCourant)cp).getDecouvert();
		if (cp.getSolde()+facilteCaisse< montant)
			throw new RuntimeException("Solde inssufisante");
		else
		{
			Retrait r= new Retrait(new Date(), montant, cp);
			operationDAO.create(r);
				cp.setSolde(cp.getSolde()-montant);
				compteDAO.update(cp);
		}

	}

	@Override
	public void virement(String codeCpt1, String codeCtp2, double montant) {
		this.retrait(codeCpt1, montant);
		this.verser(codeCtp2, montant);

	}

	@Override
	public List<Operation> listOperation(String codeCpte) {

		return null;
	}

}
