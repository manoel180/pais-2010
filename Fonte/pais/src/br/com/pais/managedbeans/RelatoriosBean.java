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
	private int geracoes = 0;
	

	public void carregarLideres(Discipulos discipulos, TreeNode pai) {
		treeNode = new DefaultTreeNode();
		listDiscipulos =  discipulos.getDiscipuloses();
			for (Discipulos d : listDiscipulos) {
				treeNode = new DefaultTreeNode(d,pai);
				
				//nodes.add(new DefaultTreeNode(d, pai));
				nodes.add(treeNode);
				index = nodes.indexOf(treeNode);

				if (!discipulos.getDiscipuloses().isEmpty()) {
					carregarLideres(d, (nodes.get(index)));
				}
			}
	}

	/*
	 * public SelectItem[] getGeracaoCombo() { listaGeracoes = new
	 * ArrayList<Geracoes>(); listaGeracoes.addAll(new
	 * GeracaoDaoImp().listarGeracoes()); List<SelectItem> itens = new
	 * ArrayList<SelectItem>(listaGeracoes.size());
	 * 
	 * for(Geracoes g : listaGeracoes) { itens.add(new SelectItem(g.getGerCod(),
	 * g.getGerDescricao())); } return itens.toArray(new
	 * SelectItem[itens.size()]); }
	 */
	public Discipulos montarRelatorioGeracoes(Discipulos discipulos) {
		// listaDiscipulos = new ArrayList<Discipulos>();
		/* listaDiscipulos = discipulos.getDiscipuloses(); */
		for (Discipulos d : discipulos.getDiscipuloses()) {
			
			
			if (!d.getDiscipuloses().isEmpty()) {
				montarRelatorioGeracoes(d);
				discipulos.setTotalDiscipulos(discipulos.getTotalDiscipulos()+ d.getTotalDiscipulos() + 1);
				/*if (!d.getBasesesForBasDisCod().isEmpty()) {
					discipulos.setTotalBases(d.getTotalBases() + 1);
				}*/

			} else {
				/*if (!d.getBasesesForBasDisCod().isEmpty()) {
					discipulos.setTotalBases(d.getBasesesForBasDisCod().size() + 1);
				}*/

				discipulos.setTotalDiscipulos(discipulos.getTotalDiscipulos() + 1);
			}

			System.out.println(discipulos.getDisnome() + " "
					+ discipulos.getTotalDiscipulos() + " "
					+ discipulos.getTotalBases());
			// listaDiscipulos.add(d);

		}
		return discipulos;

	}

	public void gerarRelatorioGeracoesCadastradas() {
		discipulador = 1;
		new RelatorioDaoImp().gerarRelatorioGeracoes(selectedLider.getDisCod());
	}

	public void listarGeracoesCadastradas() {
		totalDiscipulos = 0;
		listaDiscipulos = new ArrayList<Discipulos>();
		listaDiscipulos.addAll(selectedLider.getDiscipuloses());
		for (Discipulos d : listaDiscipulos) {
			listaDiscipulos.set(listaDiscipulos.indexOf(d),(montarRelatorioGeracoes(d)));
			totalDiscipulos = totalDiscipulos + d.getTotalDiscipulos();
		}
	}

	public String prepararRelatorioGeracoesCadastradas() {
		nodes = new ArrayList<TreeNode>();
		treeNode = new DefaultTreeNode();

		index = 0;
		totalDiscipulos = 0;
		selectedLider = new Discipulos();
		listaDiscipulos = new ArrayList<Discipulos>();
		listDiscipulos = new ArrayList<Discipulos>();
		root = new DefaultTreeNode("root", null);
		nodes.add(new DefaultTreeNode(discipuloSessao.getDiscipulos(), root));
		carregarLideres(discipuloSessao.getDiscipulos(), nodes.get(0));
		// listaDiscipulos.addAll(new
		// DiscipuloDaoImp().listarDiscipulos(discipuloSessao.getDiscipulos().getDisCod()));
		selectedLider = discipuloSessao.getDiscipulos();
		listaDiscipulos.addAll(selectedLider.getDiscipuloses());
		for (Discipulos d : listaDiscipulos) {
			listaDiscipulos.set(listaDiscipulos.indexOf(d),(montarRelatorioGeracoes(d)));
			totalDiscipulos = totalDiscipulos + d.getTotalDiscipulos();
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
	 * @param totalDiscipulos the totalDiscipulos to set
	 */
	public void setTotalDiscipulos(int totalDiscipulos) {
		this.totalDiscipulos = totalDiscipulos;
	}
}
