/**
 *
 */
package com.core.entite;

import java.io.Serializable;
import java.util.Date;

/**
 * @author KADIATA
 *
 */
public class Compte implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private Date dateCreation;
	private double solde;
	private String sold;
	private String codeClient;
	private String codClient;
	private String typeCpte;
	public Compte() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Compte(String code, Date dateCreation, double solde, Client client, String typeCpte) {
		super();
		this.setCode(code);
		this.setDateCreation(dateCreation);
		this.setSolde(solde);
		this.setClient(client.getId());
		this.setTypeCpte(typeCpte);
	}
	public Compte(String code, String sold, String codClient, String typeCpte){
		super();
		this.setCode(code);
		this.setSold(sold);
		this.setcodClient(codClient);
		this.setTypeCpte(typeCpte);
	}

	public Compte(String code, String sold) {
		super();
		this.setCode(code);
		this.setSold(sold);
	}
	public Compte(String code){
		super();
		this.setCode(code);
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public double getSolde() {
		return solde;
	}
	public void setSolde(double solde) {
		this.solde = solde;
	}
	public String getClient() {
		return codeClient;
	}
	public void setClient(String codeClient) {
		this.codeClient = codeClient;
	}
	public String getTypeCpte() {
		return typeCpte;
	}
	public void setTypeCpte(String typeCpte) {
		this.typeCpte = typeCpte;
	}

	public void setSold(String sold) {
		this.sold = sold;
	}
	public String getSold() {
		return sold;
	}

	public void setcodClient(String codClient) {
		this.codClient = codClient;
	}

	public String getCodClient() {
		return codClient;
	}
}
