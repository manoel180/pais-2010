package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Geracoes;
import br.com.pais.relatorio.imp.RelatorioDaoImp;
import br.com.pais.util.ApplicationSecurityManager;

public class RelatoriosBean {

	private int discipulador;
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();

	// List
	private List<Geracoes> listaGeracoes;
	private List<Discipulos> listaDiscipulos;
	private List<Discipulos> listDiscipulos;
	private int comboGeracao;

	private int index;
	private Discipulos selectedDiscipulos;
	private Discipulos selectedLider;

	private DefaultTreeNode treeNode;
	private TreeNode root;
	private ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
	private int totalDiscipulos = 0;
	private int totalCelulas = 0;
	private int totalBases = 0;
	private int totalGeracoes = 0;
	private int geracoes = 0;

	public void carregarLideres(Discipulos discipulos, TreeNode pai) {
		treeNode = new DefaultTreeNode();
		listDiscipulos = discipulos.getDiscipuloses();
		for (Discipulos d : listDiscipulos) {
			treeNode = new DefaultTreeNode(d, pai);

			// nodes.add(new DefaultTreeNode(d, pai));
			nodes.add(treeNode);
			index = nodes.indexOf(treeNode);

			if (!discipulos.getDiscipuloses().isEmpty()) {
				carregarLideres(d, (nodes.get(index)));
			}
		}
	}

	public Discipulos montarRelatorioGeracoes(Discipulos discipulos) {

		discipulos.setTotalBases(discipulos.getBasesesForBasDisCod().size());
		discipulos.setTotalCelulas(discipulos.getCelulases_1().size());
		discipulos.setTotalGeracoes(0);
		int geracoes = 0;
		
		if(!discipulos.getDiscipuloses().isEmpty()){
			
			discipulos.setTotalGeracoes(+1);
		for (Discipulos d : discipulos.getDiscipuloses()) {
			// Verifica se possui bases
			if (!d.getBasesesForBasDisCod().isEmpty()) {
				discipulos.setTotalBases(discipulos.getTotalBases()
						+ d.getBasesesForBasDisCod().size());
			}
			// Verifica se possui celulas
			if (!d.getCelulases_1().isEmpty()) {
				discipulos.setTotalCelulas(discipulos.getTotalCelulas()
						+ d.getCelulases_1().size());
			}

			// Conta quantidade de discipulos
			if (!d.getDiscipuloses().isEmpty()) {

				d = montarRelatorioGeracoes(d);
				discipulos.setTotalGeracoes(d.getTotalGeracoes()+1);
				discipulos.setTotalDiscipulos(discipulos.getTotalDiscipulos()
						+ d.getTotalDiscipulos() + 1);

			} else {

				discipulos
						.setTotalDiscipulos(discipulos.getTotalDiscipulos() + 1);
			}
			
			}
		}
		
		/*if (geracoes >= discipulos.getTotalGeracoes()) {
			discipulos.setTotalGeracoes(geracoes);*/
		//}
		
		return discipulos;

	}

	public void gerarRelatorioGeracoesCadastradas() {
		new RelatorioDaoImp().gerarRelatorioGeracoes(listaDiscipulos);
	}

	public void listarGeracoesCadastradas() {
		totalDiscipulos = 0;
		totalBases= 0 ;
		totalCelulas = 0;
		totalGeracoes = 0;
		listaDiscipulos = new ArrayList<Discipulos>();
		listaDiscipulos = (new DiscipuloDaoImp().listarDiscipulos(selectedLider
				.getDisCod()));
		for (Discipulos d : listaDiscipulos) {
			listaDiscipulos.set(listaDiscipulos.indexOf(d),
					(montarRelatorioGeracoes(d)));
			totalDiscipulos = totalDiscipulos + d.getTotalDiscipulos();
			totalCelulas = totalCelulas + d.getTotalCelulas();
			totalBases = totalBases + d.getTotalBases();
			totalGeracoes = totalGeracoes + d.getTotalGeracoes();
		}
	}

	public String prepararRelatorioGeracoesCadastradas() {
		nodes = new ArrayList<TreeNode>();
		treeNode = new DefaultTreeNode();

		index = 0;
		totalDiscipulos = 0;
		totalBases = 0;
		totalCelulas = 0;
		totalGeracoes = 0;
		selectedLider = new Discipulos();
		listaDiscipulos = new ArrayList<Discipulos>();
		root = new DefaultTreeNode("root", null);
		nodes.add(new DefaultTreeNode(discipuloSessao.getDiscipulos(), root));
		carregarLideres(discipuloSessao.getDiscipulos(), nodes.get(0));

		selectedLider = discipuloSessao.getDiscipulos();

		listaDiscipulos = (new DiscipuloDaoImp().listarDiscipulos(selectedLider
				.getDisCod()));
		for (Discipulos d : listaDiscipulos) {
			listaDiscipulos.set(listaDiscipulos.indexOf(d),
					(montarRelatorioGeracoes(d)));
			totalDiscipulos = totalDiscipulos + d.getTotalDiscipulos();
			totalCelulas = totalCelulas + d.getTotalCelulas();
			totalBases = totalBases + d.getTotalBases();
			totalGeracoes = totalGeracoes + d.getTotalGeracoes();
		}

		return "/list/geracoesCadastradas.mir";
	}

	/**
	 * @return the discipulador
	 */
	public int getDiscipulador() {
		return discipulador;
	}

	/**
	 * @param discipulador
	 *            the discipulador to set
	 */
	public void setDiscipulador(int discipulador) {
		this.discipulador = discipulador;
	}

	/**
	 * @return the listaDiscipulos
	 */
	public List<Discipulos> getListaDiscipulos() {
		return listaDiscipulos;
	}

	/**
	 * @param listaDiscipulos
	 *            the listaDiscipulos to set
	 */
	public void setListaDiscipulos(List<Discipulos> listaDiscipulos) {
		this.listaDiscipulos = listaDiscipulos;
	}

	/**
	 * @return the comboGeracao
	 */
	public int getComboGeracao() {
		return comboGeracao;
	}

	/**
	 * @param comboGeracao
	 *            the comboGeracao to set
	 */
	public void setComboGeracao(int comboGeracao) {
		this.comboGeracao = comboGeracao;
	}

	/**
	 * @return the listaGeracoes
	 */
	public List<Geracoes> getListaGeracoes() {
		return listaGeracoes;
	}

	/**
	 * @param listaGeracoes
	 *            the listaGeracoes to set
	 */
	public void setListaGeracoes(List<Geracoes> listaGeracoes) {
		this.listaGeracoes = listaGeracoes;
	}

	/**
	 * @return the listDiscipulos
	 */
	public List<Discipulos> getListDiscipulos() {
		return listDiscipulos;
	}

	/**
	 * @param listDiscipulos
	 *            the listDiscipulos to set
	 */
	public void setListDiscipulos(List<Discipulos> listDiscipulos) {
		this.listDiscipulos = listDiscipulos;
	}

	/**
	 * @return the selectedLider
	 */
	public Discipulos getSelectedLider() {
		return selectedLider;
	}

	/**
	 * @param selectedLider
	 *            the selectedLider to set
	 */
	public void setSelectedLider(Discipulos selectedLider) {
		this.selectedLider = selectedLider;
	}

	/**
	 * @return the root
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * @param root
	 *            the root to set
	 */
	public void setRoot(TreeNode root) {
		this.root = root;
	}

	/**
	 * @return the selectedDiscipulos
	 */
	public Discipulos getSelectedDiscipulos() {
		return selectedDiscipulos;
	}

	/**
	 * @param selectedDiscipulos
	 *            the selectedDiscipulos to set
	 */
	public void setSelectedDiscipulos(Discipulos selectedDiscipulos) {
		this.selectedDiscipulos = selectedDiscipulos;
	}

	/**
	 * @return the totalDiscipulos
	 */
	public int getTotalDiscipulos() {
		return totalDiscipulos;
	}

	/**
	 * @param totalDiscipulos
	 *            the totalDiscipulos to set
	 */
	public void setTotalDiscipulos(int totalDiscipulos) {
		this.totalDiscipulos = totalDiscipulos;
	}

	/**
	 * @return the totalCelulas
	 */
	public int getTotalCelulas() {
		return totalCelulas;
	}

	/**
	 * @param totalCelulas
	 *            the totalCelulas to set
	 */
	public void setTotalCelulas(int totalCelulas) {
		this.totalCelulas = totalCelulas;
	}

	/**
	 * @return the totalBases
	 */
	public int getTotalBases() {
		return totalBases;
	}

	/**
	 * @param totalBases
	 *            the totalBases to set
	 */
	public void setTotalBases(int totalBases) {
		this.totalBases = totalBases;
	}

	/**
	 * @return the totalGeracoes
	 */
	public int getTotalGeracoes() {
		return totalGeracoes;
	}

	/**
	 * @param totalGeracoes
	 *            the totalGeracoes to set
	 */
	public void setTotalGeracoes(int totalGeracoes) {
		this.totalGeracoes = totalGeracoes;
	}
}
