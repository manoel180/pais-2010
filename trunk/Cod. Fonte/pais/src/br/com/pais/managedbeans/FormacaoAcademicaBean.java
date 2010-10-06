/**
 * 
 */
package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.pais.dao.FormacaoacademicaDao;
import br.com.pais.dao.impl.FormacaoAcademicaDaoImp;
import br.com.pais.entities.Formacaoacademica;

/**
 * @author manoel
 * 
 */
public class FormacaoAcademicaBean {

	private Formacaoacademica formacaoacademica = new  Formacaoacademica();

	// Objetos Daos
	private FormacaoacademicaDao formacaoacademicaDao= new FormacaoAcademicaDaoImp();

	

	
	// ComboBox Estado Civil
	public SelectItem[] getFormacaoAcademicaCombo() {
		List<Formacaoacademica> lfa = formacaoacademicaDao.todos();
		List<SelectItem> itens = new ArrayList<SelectItem>(lfa.size());

		for (Formacaoacademica fc : lfa) {
			itens.add(new SelectItem(fc.getForCod(),fc.getForDescricao()));
		}// for end
		return itens.toArray(new SelectItem[itens.size()]);
	}

	/**
	 * GETTERS and SETTERS
	 */



	/**
	 * @param formacaoacademica the formacaoacademica to set
	 */
	public void setFormacaoacademica(Formacaoacademica formacaoacademica) {
		this.formacaoacademica = formacaoacademica;
	}




	/**
	 * @return the formacaoacademica
	 */
	public Formacaoacademica getFormacaoacademica() {
		return formacaoacademica;
	}

	


	
}