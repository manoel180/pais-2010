/**
 * 
 */
package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.pais.dao.EstadoCivilDao;
import br.com.pais.dao.impl.EstadoCivilDaoImp;
import br.com.pais.entities.Estadocivil;

/**
 * @author manoel
 * 
 */
public class EstadoCivilBean {

	private Estadocivil estadoCivil = new Estadocivil();

	// Objetos Daos
	private EstadoCivilDao estadoCivilDao = new EstadoCivilDaoImp();

	

	
	// ComboBox Estado Civil
	public SelectItem[] getEstCivilCombo() {
		List<Estadocivil> lec = estadoCivilDao.todos();
		List<SelectItem> itens = new ArrayList<SelectItem>(lec.size());

		for (Estadocivil ec : lec) {
			itens.add(new SelectItem(ec.getEstCod(), ec.getEstdescricao()));
		}// for end
		return itens.toArray(new SelectItem[itens.size()]);
	}

	/**
	 * GETTERS and SETTERS
	 */



	/**
	 * @param estadoCivil the estadoCivil to set
	 */
	public void setEstadoCivil(Estadocivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}




	/**
	 * @return the estadoCivil
	 */
	public Estadocivil getEstadoCivil() {
		return estadoCivil;
	}	
}