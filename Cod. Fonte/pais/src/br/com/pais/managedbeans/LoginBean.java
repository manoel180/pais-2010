/**
 * 
 */
package br.com.pais.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.pais.dao.EstadoDao;
import br.com.pais.dao.impl.EstadoDaoImp;
import br.com.pais.entities.Estado;

/**
 * @author manoel
 */

@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean {
	
	protected Estado estado;

	protected EstadoDao estadoDao;
	
	public void salvar() {
		estado = new Estado();
		estadoDao=new EstadoDaoImp();
		estado.setUfeNo("am");
		
		estado.setUfeSg("MM");
	
		estadoDao.salvar(estado);
		
	}
}
