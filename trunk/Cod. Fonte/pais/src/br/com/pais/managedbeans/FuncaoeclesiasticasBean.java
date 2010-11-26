/**
 * 
 */
package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.pais.dao.FuncaoeclesiasticasDao;
import br.com.pais.dao.impl.FuncaoeclesiasticasDaoImp;
import br.com.pais.entities.Funcaoeclesiasticas;
import br.com.pais.util.ApplicationSecurityManager;

/**
 * @author manoel
 * 
 */
public class FuncaoeclesiasticasBean {

	private Funcaoeclesiasticas funcaoeclesiasticas = new Funcaoeclesiasticas();

	// Objetos Daos
	private  FuncaoeclesiasticasDao funcaoeclesiasticasDao = new FuncaoeclesiasticasDaoImp();
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();
	

	
	// ComboBox Estado Civil
	public SelectItem[] getFuncEclesiasticaCombo() {
		List<Funcaoeclesiasticas> lfe = funcaoeclesiasticasDao.listarFuncaoPorSexo(discipuloSessao.getDiscipulos().getDisSexo(),discipuloSessao.getDiscipulos().getFuncaoeclesiasticas().getFunCod());
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