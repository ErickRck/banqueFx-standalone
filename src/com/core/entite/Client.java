/**
 *
 */
package com.core.entite;

import com.core.exception.ClientException;
import com.core.interfaces.IControl;

import java.io.Serializable;

/**
 * @author KADIATA
 *
 */
public class Client implements Serializable, IControl {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String nom;
	private String postNom;
	private String prenom;
	private String adresse;
	private String telephone;
	private String password;

	//ivate String clientCode;

	public Client(String nom, String postNom, String prenom,  String telephone,String adresse, String password) throws ClientException{
		super();
		if (password.isEmpty()) throw new ClientException();
		else {
			this.setId(id);
			this.setNom(nom);
			this.setPostNom(postNom);
			this.setPrenom(prenom);
			this.setAdresse(adresse);
			this.setTelephone(telephone);
			this.setPassword(password);
		}
	}

	public Client( String id, String nom, String postNom, String prenom,  String telephone,String adresse, String password) throws ClientException {
		super();
		if (nom.isEmpty())
			throw new ClientException();
			else
			{
			this.setId(id);
			this.setNom(nom);
			this.setPostNom(postNom);
			this.setPrenom(prenom);
			this.setTelephone(telephone);
			this.setAdresse(adresse);
			this.setPassword(password);
			}
	}
	public Client( String nom, String postNom, String prenom, String telephone, String adresse) throws ClientException {
		super();
		if (nom.isEmpty())
			throw new ClientException();
		else
		{
			this.setNom(nom);
			this.setPostNom(postNom);
			this.setPrenom(prenom);
			this.setTelephone(telephone);
			this.setAdresse(adresse);
		}
	}
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Client(String id) throws ClientException{
		super();
		if (id.isEmpty())
			throw new ClientException();
		else {
			this.setId(id);
		}
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		if (!(nom.isEmpty()) & nom.length()> 3)
			{
			nom.toUpperCase();
			this.nom = nom;
			}
	}
	public String getPostNom() {
		return postNom;
	}
	public void setPostNom(String postNom) {
		if (!(postNom.isEmpty()) & postNom.length()> 3)
		{
			postNom.toUpperCase();
			this.postNom = postNom;
		}

	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		if (!(prenom.isEmpty()) & prenom.length()> 3)
		this.prenom = prenom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		//if (!(adresse.isEmpty()) & adresse.length()>3)
		this.adresse = adresse;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {

		this.telephone = telephone;
	}
	@Override
	public boolean isValid() {

		return true;
		//return (this.postNom !=null && this.nom !=null && this.prenom!=null && this.adresse!=null && this.telephone!=null)? true:false;
	}
	@Override
	public boolean isNew() {

		return true;
	}
}
