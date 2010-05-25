package br.com.pais.controller;

import javax.annotation.Resource;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import br.com.pais.dao.DaoGenerico;
import br.com.pais.entities.Estado;

public class EstadoController {

	private Estado estado;
	private DataModel model;
		
	/*
	 * Recurso injetado pelo Spring
	 * 
	 */
	@Resource
	private  DaoGenerico<Estado,Integer> estadoDao;
	
	
	
	
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public Estado getEstado() {
		return estado;
	}	
	
	public void setEstadoDao(DaoGenerico<Estado,Integer> estadoDao) {
		this.estadoDao = estadoDao;
	}


	public DaoGenerico<Estado,Integer> getEstadoDao() {
		return estadoDao;
	}

	//cadastra uma nova categoria
	public String novaEst() {
		this.estado = new Estado();
		
		return "formEstado";
	}
	
	
	// mostra todas as categorias em um DataTable
	public DataModel getTodos() {
		return model = new ListDataModel(estadoDao.todos());		
	}
	
	//pega o Estado selecionada na tabela
	//para editar ou excluir
	public Estado getEstadoParaEditarExcluir() {
		Estado estado= (Estado) model.getRowData();
		return estado;
	}

	//edita a categoria
	public String editar() {
		setEstado(getEstadoParaEditarExcluir());
		
		return "formEstado";
	}	
	
	//salva uma nova categoria 
	//ou que est� em edi��o
	public String salvar(Estado estado){
		//verifica se n�o � uma categoria em edi��o
		if (estado.getUfeNo() == null) {
			estadoDao.salvar(estado);
		//	FacesUtils.mensInfo("Cadastrado com sucesso");
		} else {
			estadoDao.atualizar(estado);	
			//FacesUtils.mensInfo("Atualizado com sucesso");
		}
		return "sucesso";
	}
	
	//exclui a categoria selecionada
	//no DataTable
	public String excluir(){	
		Estado estado = getEstadoParaEditarExcluir();
		estadoDao.excluir(estado);
		//FacesUtils.mensInfo("Exclu�do com sucesso");
		return "mostrarEstado";
	}	
	
}