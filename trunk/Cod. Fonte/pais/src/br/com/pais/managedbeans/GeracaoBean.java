/**
 * 
 */
package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.pais.dao.GeracaoDao;
import br.com.pais.dao.impl.GeracaoDaoImp;
import br.com.pais.entities.Geracoes;

/**
 * @author manoel
 * 
 */
public class GeracaoBean {

	
	// Objetos Daos
	private  GeracaoDao geracaoDao = new GeracaoDaoImp();

	

	
	// ComboBox Estado Civil
	public SelectItem[] getGeracaoCombo() {
		List<Geracoes> lg = geracaoDao.listarGeracoes();
		List<SelectItem> itens = new ArrayList<SelectItem>(lg.size());

		for (Geracoes g : lg) {
			itens.add(new SelectItem(g.getGerCod(), g.getGerDescricao()));
		}// for end
		return itens.toArray(new SelectItem[itens.size()]);
	}

	/**
	 * GETTERS and SETTERS
	 */

	
}