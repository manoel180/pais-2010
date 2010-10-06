/**
 * 
 */
package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.pais.dao.EncontrosDao;
import br.com.pais.dao.FuncaoeclesiasticasDao;
import br.com.pais.dao.impl.EncontrosDaoImp;
import br.com.pais.dao.impl.FuncaoeclesiasticasDaoImp;
import br.com.pais.entities.Encontros;
import br.com.pais.entities.Funcaoeclesiasticas;

/**
 * @author manoel
 * 
 */
public class EncontrosBean {

	private Encontros encontros = new Encontros();

	// Objetos Daos
	private  EncontrosDao encontrosDao = new EncontrosDaoImp();

	

	
	// ComboBox Encontros
	public SelectItem[] getEncontrosCheck() {
		List<Encontros> le = encontrosDao.todos();
		List<SelectItem> itens = new ArrayList<SelectItem>(le.size());

		for (Encontros e : le) {
			itens.add(new SelectItem(e.getEncCod(), e.getEncDescricao()));
		}// for end
		return itens.toArray(new SelectItem[itens.size()]);
	}


	/**
	 * GETTERS and SETTERS
	 */

	/**
	 * @return the encontros
	 */
	public Encontros getEncontros() {
		return encontros;
	}




	/**
	 * @param encontros the encontros to set
	 */
	public void setEncontros(Encontros encontros) {
		this.encontros = encontros;
	}
}
	


