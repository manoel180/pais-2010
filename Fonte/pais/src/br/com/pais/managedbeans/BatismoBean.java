package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.pais.classe.nopersistence.EncontrosNoPersistenceDiscipulos;
import br.com.pais.dao.BatismoDao;
import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.DiscipulosBatismoDao;
import br.com.pais.dao.FormacaoeclesiasticasDao;
import br.com.pais.dao.impl.BatismoDaoImp;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.dao.impl.DiscipulosBatismoDaoImp;
import br.com.pais.dao.impl.FormacaoeclesiasticasDaoImp;
import br.com.pais.entities.Batismo;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Discipulosbatismo;
import br.com.pais.entities.DiscipulosbatismoId;
import br.com.pais.entities.Encontrostatus;
import br.com.pais.entities.Formacaoeclesiasticas;
import br.com.pais.util.ApplicationSecurityManager;

public class BatismoBean {
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();
	private BatismoDao batismoDao = new BatismoDaoImp();
	private Batismo batismo = new Batismo();
	private List<Batismo> listaBatismo = new ArrayList<Batismo>();
	private DiscipulosBatismoDao discipuloBatismoDao = new DiscipulosBatismoDaoImp();
	private DiscipulosbatismoId discipuloBatismoId = new DiscipulosbatismoId();
	private Discipulosbatismo discipuloBatismo = new Discipulosbatismo();
	private int filtroBatismo;
	private Encontrostatus encontroStatus = new Encontrostatus();
	private DiscipuloDao discipuloDao = new DiscipuloDaoImp();
	private FormacaoeclesiasticasDao formacaoEclesiasticaDao = new FormacaoeclesiasticasDaoImp();
	private List<Discipulos> listaDiscipulos = new ArrayList<Discipulos>();
	
	//ADICIONAR REMOVER DISCIPULOS
	private List<EncontrosNoPersistenceDiscipulos> dtDisBatizandos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	private List<EncontrosNoPersistenceDiscipulos> dtDisAclamados = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	private List<EncontrosNoPersistenceDiscipulos> dtDisGeracoes = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	private List<EncontrosNoPersistenceDiscipulos> dtDisEscolhidos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	private EncontrosNoPersistenceDiscipulos[] dtDisSelecionadosAdicionar;
	private EncontrosNoPersistenceDiscipulos[] dtDisSelecionadosRemover;
	private EncontrosNoPersistenceDiscipulos batismoDiscipulo = new EncontrosNoPersistenceDiscipulos();
	
	private boolean mostrarDtBatismo = false;
	
	
	public String prepararCadastrarBatismo(){
		batismo = new Batismo();
		filtroBatismo = 0;
		dtDisGeracoes = new ArrayList<EncontrosNoPersistenceDiscipulos>();
		dtDisEscolhidos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
		dtDisBatizandos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
		dtDisAclamados = new ArrayList<EncontrosNoPersistenceDiscipulos>();
		listarDiscipulosBatizandosAclamados(discipuloSessao.getDiscipulos());
		
		return "/cad/batismo.mir";
	}
	
	public void listarDiscipulosBatizandosAclamados(Discipulos discipulo){
		//lista todos os discipulos do logado
		List<Discipulos> listaDiscipulosBatizados = new ArrayList<Discipulos>();
		listaDiscipulosBatizados.addAll(discipulo.getDiscipuloses());
		
		for(Discipulos dis : listaDiscipulosBatizados){
			List<Formacaoeclesiasticas> listaFormacaoEclesiasticaDiscipulo = new ArrayList<Formacaoeclesiasticas>();
			listaFormacaoEclesiasticaDiscipulo.addAll(dis.getFormacaoeclesiasticases());
			int contador = 0;
			boolean temBatismo = false;
			for(Formacaoeclesiasticas formacao : listaFormacaoEclesiasticaDiscipulo){
				contador++;
				if(formacao.getForEcCod() == 1){
					temBatismo = true;
				}
				if(contador == listaFormacaoEclesiasticaDiscipulo.size() && temBatismo == false){
					//Não possui Batismo
					batismoDiscipulo = new EncontrosNoPersistenceDiscipulos();
					batismoDiscipulo.setDiscipulo(dis);
					batismoDiscipulo.setPossuiBatismoNasAguas(false);
					dtDisBatizandos.add(batismoDiscipulo);
				}
				else if(contador == listaFormacaoEclesiasticaDiscipulo.size() && temBatismo == true){
					//Tem Batismo
					batismoDiscipulo = new EncontrosNoPersistenceDiscipulos();
					batismoDiscipulo.setDiscipulo(dis);
					batismoDiscipulo.setPossuiBatismoNasAguas(true);
					dtDisAclamados.add(batismoDiscipulo);
				}
			}
			//discipulo possui discipulos
			if(!dis.getDiscipuloses().isEmpty()) {
				listarDiscipulosBatizandosAclamados(dis);
			}
		}
	}
	
	public void filtrarDiscipulosBatismo() {  
		dtDisGeracoes = new ArrayList<EncontrosNoPersistenceDiscipulos>();
		
		if(filtroBatismo == 1){
			dtDisGeracoes.addAll(dtDisBatizandos);
		}
		if(filtroBatismo == 2){
			dtDisGeracoes.addAll(dtDisAclamados);
		}
    } 
	
	public void adicionarDiscipulosBatismo() { 
		List<EncontrosNoPersistenceDiscipulos> listaTempSelecionados = new ArrayList<EncontrosNoPersistenceDiscipulos>();
    	//Guarda os Selecionados Na Lista Temporaria
		for(EncontrosNoPersistenceDiscipulos dis: dtDisSelecionadosAdicionar){
			listaTempSelecionados.add(dis);
		}
		
		//Adiciona na lista dos escolhidos a lista de baixo na tela
		for(EncontrosNoPersistenceDiscipulos dis: listaTempSelecionados){
			dtDisEscolhidos.add(dis);
		}
		
		//Remove o discipulo na lista da geração filtrada a lista de cima na tela
		List<EncontrosNoPersistenceDiscipulos> listaTempRemover = new ArrayList<EncontrosNoPersistenceDiscipulos>(dtDisGeracoes);
	    for(EncontrosNoPersistenceDiscipulos dis : listaTempSelecionados){
	    	for(EncontrosNoPersistenceDiscipulos dis2 : dtDisGeracoes){
	    		if(dis.getDiscipulo().getDisCod().equals(dis2.getDiscipulo().getDisCod())){
	    			listaTempRemover.remove(dis2);
	    		}
	    	}
	    }
	    dtDisGeracoes = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	    dtDisGeracoes.addAll(listaTempRemover);
	    
	    //Possui Batismo
	    if(filtroBatismo == 1){
	    	dtDisBatizandos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	    	dtDisBatizandos.addAll(dtDisGeracoes);
	    }
	    //Sem Batismo
	    if(filtroBatismo == 2){
	    	dtDisAclamados = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	    	dtDisAclamados.addAll(dtDisGeracoes);
	    }
	}
	
	public void removerDiscipulosBatismo() {  
		List<EncontrosNoPersistenceDiscipulos> listaTempSelecionados = new ArrayList<EncontrosNoPersistenceDiscipulos>();
    	//Guarda os Selecionados Na Lista Temporaria
		for(EncontrosNoPersistenceDiscipulos dis: dtDisSelecionadosRemover){
			listaTempSelecionados.add(dis);
		}
		
		//Adiciona o discipulo na lista da geração filtrada a lista de cima na tela
		for(EncontrosNoPersistenceDiscipulos dis: listaTempSelecionados){
			//Possui Batismo
			if(dis.isPossuiBatismoNasAguas() == true){
			    if(filtroBatismo == 1){
			    	dtDisAclamados.add(dis);
			    }
			    if(filtroBatismo == 2){
			    	dtDisGeracoes.add(dis);
			    	dtDisAclamados.add(dis);
			    }
			}		
			 //Sem Batismo
			else{
			    if(filtroBatismo == 1){
			    	dtDisGeracoes.add(dis);
			    	dtDisBatizandos.add(dis);
			    }
			    if(filtroBatismo == 2){
			    	dtDisBatizandos.add(dis);
			    }
			}
		}
	    
	    //Remove o discipulo na lista dos escolhidos a lista de baixo na tela
		List<EncontrosNoPersistenceDiscipulos> listaTempRemover = new ArrayList<EncontrosNoPersistenceDiscipulos>(dtDisEscolhidos);
	    for(EncontrosNoPersistenceDiscipulos dis : listaTempSelecionados) {
	    	for(EncontrosNoPersistenceDiscipulos dis2 : dtDisEscolhidos){
	    		if(dis.getDiscipulo().getDisCod().equals(dis2.getDiscipulo().getDisCod())){
	    			listaTempRemover.remove(dis2);
	    		}
	    	}
	    }
	    dtDisEscolhidos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	    dtDisEscolhidos.addAll(listaTempRemover);
	}
	
	public boolean validarBatismo(){
		FacesContext context = FacesContext.getCurrentInstance();
		int contadorValidador = 0;
		
		//horario
		if(batismo.getBathorario() == null){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Informe o Horário!"));
		}
		//Data de Inicio
		if(batismo.getBatdatarealizacao() == null){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Informe a data de realização!"));
		}
		//local
		if(batismo.getBatlocal() == null || batismo.getBatlocal() == ""){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Informe o Local!"));
		}
		//Discipulos Escolhidos
		if(dtDisEscolhidos.size() <= 0){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Nenhum Discipulo Adicionado no batismo!"));
		}
		
		if(contadorValidador <= 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public String SalvarBatismo(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		//Se For Tudo Validado
		if(validarBatismo() == true){
			//Salva Batismo
			encontroStatus = new Encontrostatus();
			encontroStatus.setEncstacod(1);//1 = Em Andamento 2 = Finalizado
			batismo.setEncontrostatus(encontroStatus);
			batismo.setDiscipulos(discipuloSessao.getDiscipulos());
			batismoDao.salvar(batismo);
			
			char batizando = 'N';
			for(EncontrosNoPersistenceDiscipulos dis : dtDisEscolhidos){
				discipuloBatismoId = new DiscipulosbatismoId();
				discipuloBatismoId.setBatismo(batismo.getBatcod());
				discipuloBatismoId.setDiscipulo(dis.getDiscipulo().getDisCod());
				discipuloBatismo = new Discipulosbatismo();
				discipuloBatismo.setId(discipuloBatismoId);
				if(dis.isPossuiBatismoNasAguas())batizando = 'N';
				else batizando = 'S';
				discipuloBatismo.setBatizando(batizando);
				discipuloBatismoDao.salvar(discipuloBatismo);
			}
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!","Batismo Cadastrado com Sucesso!"));
			filtroBatismo = 0;
			mostrarDtBatismo = false;
			listaBatismo = new ArrayList<Batismo>();
			
			return "/list/batismo.mir";
		}
		else{
			return null;
		}
	}
	
	public String prepararListarBatismo(){
		filtroBatismo = 0;
		mostrarDtBatismo = false;
		listaBatismo = new ArrayList<Batismo>();
		
		return "/list/batismo.mir";
	}
	
	public void filtrarDiscipulosBatismoListar() {  
		listaBatismo = new ArrayList<Batismo>();
		
		if(filtroBatismo <= 0){
			mostrarDtBatismo = false;
		}
		else{
			mostrarDtBatismo = true;
			listaBatismo.addAll(batismoDao.listarBatismo(discipuloSessao.getDiscipulos().getDisCod(), filtroBatismo));
		}
    }
	
	public String prepararEditarBatismo(){
		filtroBatismo = 0;
		dtDisEscolhidos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
		dtDisGeracoes = new ArrayList<EncontrosNoPersistenceDiscipulos>();
		dtDisBatizandos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
		dtDisAclamados = new ArrayList<EncontrosNoPersistenceDiscipulos>();
		listaDiscipulos = new ArrayList<Discipulos>();
		listarDiscipulosBatizandosAclamados(discipuloSessao.getDiscipulos());
		
		List<Discipulosbatismo> listaTempDiscipulosBatismo = new ArrayList<Discipulosbatismo>();
		listaTempDiscipulosBatismo.addAll(batismo.getDiscipulosbatismos());
		for(Discipulosbatismo dis: listaTempDiscipulosBatismo){
			batismoDiscipulo = new EncontrosNoPersistenceDiscipulos();
			batismoDiscipulo.setDiscipulo(dis.getDiscipulos());
			if(dis.getBatizando() == 'S'){
				batismoDiscipulo.setPossuiBatismoNasAguas(false);
			}else if(dis.getBatizando() == 'N'){
				batismoDiscipulo.setPossuiBatismoNasAguas(true);
			}
			dtDisEscolhidos.add(batismoDiscipulo);
			listaDiscipulos.add(dis.getDiscipulos());
		}
		removerDiscipuloBatizando();
		removerDiscipuloAclamado();
		
		return "/editar/batismo.mir";
	}
	
	public void removerDiscipuloBatizando(){
		List<EncontrosNoPersistenceDiscipulos> listaTempRemoverAclamados = new ArrayList<EncontrosNoPersistenceDiscipulos>(dtDisBatizandos);		
		//remove o discipulo dentro do batismo nas listas dtDisBatizando		
	    for(EncontrosNoPersistenceDiscipulos dis : dtDisBatizandos) {
	    	for(EncontrosNoPersistenceDiscipulos dis2 : dtDisEscolhidos){
	    		if(dis.getDiscipulo().getDisCod().equals(dis2.getDiscipulo().getDisCod())){
	    			listaTempRemoverAclamados.remove(dis);
	    		}
	    	}
	    }
	    dtDisBatizandos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	    dtDisBatizandos.addAll(listaTempRemoverAclamados);
	}
	
	public void removerDiscipuloAclamado(){
		List<EncontrosNoPersistenceDiscipulos> listaTempRemoverAclamados = new ArrayList<EncontrosNoPersistenceDiscipulos>(dtDisAclamados);		
		//remove o discipulo dentro do batismo nas listas dtDisAclamados		
	    for(EncontrosNoPersistenceDiscipulos dis : dtDisAclamados) {
	    	for(EncontrosNoPersistenceDiscipulos dis2 : dtDisEscolhidos){
	    		if(dis.getDiscipulo().getDisCod().equals(dis2.getDiscipulo().getDisCod())){
	    			listaTempRemoverAclamados.remove(dis);
	    		}
	    	}
	    }
	    dtDisAclamados = new ArrayList<EncontrosNoPersistenceDiscipulos>();
		dtDisAclamados.addAll(listaTempRemoverAclamados);
	}
	
	public String EditarBatismo(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		//Se For Tudo Validado
		if(validarBatismo() == true){
			//Edita o Batismo
			batismoDao.atualizar(batismo);
			ExcluirTodosDiscipulosBatismo();
			
			char batizando = 'N';
			for(EncontrosNoPersistenceDiscipulos dis : dtDisEscolhidos){
				discipuloBatismoId = new DiscipulosbatismoId();
				discipuloBatismoId.setBatismo(batismo.getBatcod());
				discipuloBatismoId.setDiscipulo(dis.getDiscipulo().getDisCod());
				discipuloBatismo = new Discipulosbatismo();
				discipuloBatismo.setId(discipuloBatismoId);
				if(dis.isPossuiBatismoNasAguas())batizando = 'N';
				else batizando = 'S';
				discipuloBatismo.setBatizando(batizando);
				discipuloBatismoDao.salvar(discipuloBatismo);
			}
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!","Batismo Editado com Sucesso!"));
			filtroBatismo = 0;
			mostrarDtBatismo = false;
			listaBatismo = new ArrayList<Batismo>();
			
			return "/list/batismo.mir";
		}
		else{
			return null;
		}
	}
	
	public void ExcluirTodosDiscipulosBatismo(){
		List<Discipulosbatismo> listaTempDiscipulosBatismo = new ArrayList<Discipulosbatismo>();
		listaTempDiscipulosBatismo.addAll(batismo.getDiscipulosbatismos());
		for(Discipulosbatismo dis: listaTempDiscipulosBatismo){
			discipuloBatismoDao.excluirPorChaves(dis.getId());
		}
	}
	
	public void RemoverBatismoDiscipulo(Discipulos discipulo){
		List<Formacaoeclesiasticas> listaTempFormacaoEclesiastica = new ArrayList<Formacaoeclesiasticas>();
		listaTempFormacaoEclesiastica.addAll(discipulo.getFormacaoeclesiasticases());
		for(Formacaoeclesiasticas form: listaTempFormacaoEclesiastica){
			if(form.getForEcCod() == 1){
				listaTempFormacaoEclesiastica.remove(form);
			}
		}
		discipulo.setFormacaoeclesiasticases(listaTempFormacaoEclesiastica);
		discipuloDao.atualizar(discipulo);
	}
	
	public String prepararFinalizarBatismo(){
		List<Discipulosbatismo> listaTempDiscipulosBatismo = new ArrayList<Discipulosbatismo>();
		listaTempDiscipulosBatismo.addAll(batismo.getDiscipulosbatismos());
		dtDisEscolhidos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
		for(Discipulosbatismo dis: listaTempDiscipulosBatismo){
			batismoDiscipulo = new EncontrosNoPersistenceDiscipulos();
			batismoDiscipulo.setDiscipulo(dis.getDiscipulos());
			if(dis.getBatizando() == 'S'){
				batismoDiscipulo.setPossuiBatismoNasAguas(false);
			}else if(dis.getBatizando() == 'N'){
				batismoDiscipulo.setPossuiBatismoNasAguas(true);
			}
			dtDisEscolhidos.add(batismoDiscipulo);
		}
		
		return "/editar/batismoFinalizar.mir";
	}
	
	public String FinalizarBatismo(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		//Finaliza o Batismo
		encontroStatus = new Encontrostatus();
		encontroStatus.setEncstacod(2);//1 = Em Andamento 2 = Finalizado
		batismo.setEncontrostatus(encontroStatus);
		batismoDao.atualizar(batismo);
		
		for(EncontrosNoPersistenceDiscipulos dis : dtDisEscolhidos){
			if(dis.isPossuiBatismoNasAguas() == false){
				inserirBatismoDiscipulo(dis.getDiscipulo());
			}
		}
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!","Batismo Finalizado com Sucesso!"));
		filtroBatismo = 0;
		mostrarDtBatismo = false;
		listaBatismo = new ArrayList<Batismo>();
		
		return "/list/batismo.mir";
	}
	
	public void inserirBatismoDiscipulo(Discipulos discipulo){
		List<Formacaoeclesiasticas> listaTempFormacaoEclesiastica = new ArrayList<Formacaoeclesiasticas>();
		listaTempFormacaoEclesiastica.addAll(discipulo.getFormacaoeclesiasticases());
		listaTempFormacaoEclesiastica.add(formacaoEclesiasticaDao.listaFormacaoPorTipo(1).get(0));
		
		discipulo.setFormacaoeclesiasticases(listaTempFormacaoEclesiastica);
		discipuloDao.atualizar(discipulo);
	}
	
	public String prepararDetalheBatismo(){
		List<Discipulosbatismo> listaTempDiscipulosBatismo = new ArrayList<Discipulosbatismo>();
		listaTempDiscipulosBatismo.addAll(batismo.getDiscipulosbatismos());
		dtDisEscolhidos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
		for(Discipulosbatismo dis: listaTempDiscipulosBatismo){
			batismoDiscipulo = new EncontrosNoPersistenceDiscipulos();
			batismoDiscipulo.setDiscipulo(dis.getDiscipulos());
			//Como este batismo está finalizado todos só na tela teram batismo mas na tabela haverá a diferença
			batismoDiscipulo.setPossuiBatismoNasAguas(true);
			dtDisEscolhidos.add(batismoDiscipulo);
		}
		
		return "/editar/batismoDetalhe.mir";
	}
	
	public ApplicationSecurityManager getDiscipuloSessao() {
		return discipuloSessao;
	}

	public void setDiscipuloSessao(ApplicationSecurityManager discipuloSessao) {
		this.discipuloSessao = discipuloSessao;
	}

	public Batismo getBatismo() {
		return batismo;
	}

	public void setBatismo(Batismo batismo) {
		this.batismo = batismo;
	}

	public int getFiltroBatismo() {
		return filtroBatismo;
	}

	public void setFiltroBatismo(int filtroBatismo) {
		this.filtroBatismo = filtroBatismo;
	}

	public List<EncontrosNoPersistenceDiscipulos> getDtDisGeracoes() {
		return dtDisGeracoes;
	}

	public void setDtDisGeracoes(
			List<EncontrosNoPersistenceDiscipulos> dtDisGeracoes) {
		this.dtDisGeracoes = dtDisGeracoes;
	}

	public List<EncontrosNoPersistenceDiscipulos> getDtDisEscolhidos() {
		return dtDisEscolhidos;
	}

	public void setDtDisEscolhidos(
			List<EncontrosNoPersistenceDiscipulos> dtDisEscolhidos) {
		this.dtDisEscolhidos = dtDisEscolhidos;
	}

	public EncontrosNoPersistenceDiscipulos[] getDtDisSelecionadosAdicionar() {
		return dtDisSelecionadosAdicionar;
	}

	public void setDtDisSelecionadosAdicionar(
			EncontrosNoPersistenceDiscipulos[] dtDisSelecionadosAdicionar) {
		this.dtDisSelecionadosAdicionar = dtDisSelecionadosAdicionar;
	}

	public EncontrosNoPersistenceDiscipulos[] getDtDisSelecionadosRemover() {
		return dtDisSelecionadosRemover;
	}

	public void setDtDisSelecionadosRemover(
			EncontrosNoPersistenceDiscipulos[] dtDisSelecionadosRemover) {
		this.dtDisSelecionadosRemover = dtDisSelecionadosRemover;
	}

	public EncontrosNoPersistenceDiscipulos getBatismoDiscipulo() {
		return batismoDiscipulo;
	}

	public void setBatismoDiscipulo(
			EncontrosNoPersistenceDiscipulos batismoDiscipulo) {
		this.batismoDiscipulo = batismoDiscipulo;
	}

	public List<EncontrosNoPersistenceDiscipulos> getDtDisBatizandos() {
		return dtDisBatizandos;
	}

	public void setDtDisBatizandos(
			List<EncontrosNoPersistenceDiscipulos> dtDisBatizandos) {
		this.dtDisBatizandos = dtDisBatizandos;
	}

	public List<EncontrosNoPersistenceDiscipulos> getDtDisAclamados() {
		return dtDisAclamados;
	}

	public void setDtDisAclamados(
			List<EncontrosNoPersistenceDiscipulos> dtDisAclamados) {
		this.dtDisAclamados = dtDisAclamados;
	}

	public Encontrostatus getEncontroStatus() {
		return encontroStatus;
	}

	public void setEncontroStatus(Encontrostatus encontroStatus) {
		this.encontroStatus = encontroStatus;
	}

	public DiscipulosbatismoId getDiscipuloBatismoId() {
		return discipuloBatismoId;
	}

	public void setDiscipuloBatismoId(DiscipulosbatismoId discipuloBatismoId) {
		this.discipuloBatismoId = discipuloBatismoId;
	}

	public Discipulosbatismo getDiscipuloBatismo() {
		return discipuloBatismo;
	}

	public void setDiscipuloBatismo(Discipulosbatismo discipuloBatismo) {
		this.discipuloBatismo = discipuloBatismo;
	}

	public List<Batismo> getListaBatismo() {
		return listaBatismo;
	}

	public void setListaBatismo(List<Batismo> listaBatismo) {
		this.listaBatismo = listaBatismo;
	}

	public boolean isMostrarDtBatismo() {
		return mostrarDtBatismo;
	}

	public void setMostrarDtBatismo(boolean mostrarDtBatismo) {
		this.mostrarDtBatismo = mostrarDtBatismo;
	}

	public List<Discipulos> getListaDiscipulos() {
		return listaDiscipulos;
	}

	public void setListaDiscipulos(List<Discipulos> listaDiscipulos) {
		this.listaDiscipulos = listaDiscipulos;
	}
}
