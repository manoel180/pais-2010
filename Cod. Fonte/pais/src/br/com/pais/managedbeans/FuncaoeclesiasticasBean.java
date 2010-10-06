/**
 * 
 */
package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.sun.org.apache.bcel.internal.generic.NEW;

import br.com.pais.dao.EstadoCivilDao;
import br.com.pais.dao.FuncaoeclesiasticasDao;
import br.com.pais.dao.impl.EstadoCivilDaoImp;
import br.com.pais.dao.impl.FuncaoeclesiasticasDaoImp;
import br.com.pais.entities.Estadocivil;
import br.com.pais.entities.Funcaoeclesiasticas;

/**
 * @author manoel
 * 
 */
public class FuncaoeclesiasticasBean {

	private Funcaoeclesiasticas funcaoeclesiasticas = new Funcaoeclesiasticas();

	// Objetos Daos
	private  FuncaoeclesiasticasDao funcaoeclesiasticasDao = new FuncaoeclesiasticasDaoImp();

	

	
	// ComboBox Estado Civil
	public SelectItem[] getFuncEclesiasticaCombo() {
		List<Funcaoeclesiasticas> lfe = funcaoeclesiasticasDao.todos();
		List<SelectItem> itens = new ArrayList<SelectItem>(lfe.size());

		for (Funcaoeclesiasticas fe : lfe) {
			itens.add(new SelectItem(fe.getFunCod(), fe.getFunDescricao()));
		}// for end
		return itens.toArray(new SelectItem[itens.size()]);
	}

	/**
	 * GETTERS and SETTERS
	 */



	/**
	 * @param funcaoeclesiasticas the funcaoeclesiasticas to set
	 */
	public void setFuncaoeclesiasticas(Funcaoeclesiasticas funcaoeclesiasticas) {
		this.funcaoeclesiasticas = funcaoeclesiasticas;
	}

	/**
	 * @return the funcaoeclesiasticas
	 */
	public Funcaoeclesiasticas getFuncaoeclesiasticas() {
		return funcaoeclesiasticas;
	}	
}