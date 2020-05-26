package com.core.banqueMetier;

import com.core.entite.Compte;
import com.core.entite.Operation;

import java.util.List;

public interface IBanqueMetier {

	public Compte consulterCompte(String codeCpte);
	public void verser(String codeCpte, double montant);
	public void retrait(String codeCpte, double montant);
	public void virement(String codeCpt1, String codeCtp2, double montant);
	public List<Operation> listOperation(String codeCpte);
}
