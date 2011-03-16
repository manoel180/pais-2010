package br.com.pais.managedbeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;


import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.GeracaoDao;
import br.com.pais.dao.MensagemAnexosDao;
import br.com.pais.dao.MensagemDao;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.dao.impl.GeracaoDaoImp;
import br.com.pais.dao.impl.MensagemAnexosDaoImp;
import br.com.pais.dao.impl.MensagemDaoImp;
import br.com.pais.entities.Celulas;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Geracoes;
import br.com.pais.entities.Mensagem;
import br.com.pais.entities.Mensagemanexos;
import br.com.pais.entities.MensagemanexosId;
import br.com.pais.fileupload.MensagemAnexoFileUpload;
import br.com.pais.util.ApplicationSecurityManager;
import br.com.pais.util.SendEMail;

public class MensagemBean {
	
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();
	private Discipulos discipulos = new Discipulos();
	private Celulas celulas = new Celulas();
	private Geracoes geracoes = new Geracoes();
	private Mensagem mensagem = new Mensagem();
	private Mensagemanexos mensagemAnexos = new Mensagemanexos();
	private MensagemanexosId mensagemAnexosId = new MensagemanexosId();
	private Discipulos discipulador;
	private Discipulos discipulo;
	private Discipulos discipuloEnvia;
	private Discipulos discipuloRecebe;
	private Mensagem mensagemSelecionada;
	private boolean enviarEmailMensagem = false;

	//List
	private List<Geracoes> listaGeracoes = new ArrayList<Geracoes>();
	private List<Discipulos> listaDiscipulos = new ArrayList<Discipulos>();
	private List<Discipulos> listaDiscipulador = new ArrayList<Discipulos>();
	private List<Discipulos> listaDiscipulosPraCima = new ArrayList<Discipulos>();
	private List<Mensagem> listaRecebidas = new ArrayList<Mensagem>();
	private List<Mensagem> listaEnviadas = new ArrayList<Mensagem>();

	//Daos
	private DiscipuloDao discipuloDao = new DiscipuloDaoImp();
	private  GeracaoDao geracaoDao = new GeracaoDaoImp();
	private  MensagemDao mensagemDao = new MensagemDaoImp();
	private  MensagemAnexosDao mensagemAnexosDao = new MensagemAnexosDaoImp();

	private boolean comboGeracao = false;
	private boolean tabView = false;
	private boolean tabDiscipulador = false;
	private boolean tabM12 = false;
	private boolean tabMensagem = false;
	private boolean tabAnexos = false;
	private boolean btnEnviarDiscipulador = false;
	private boolean btnEnviarAmbos = false;

	private boolean EnviarMensagemSelecionados = true;
	private int qtdMensagem;
	private boolean responder = false;
	private  int tipoEnvio = 0;

	//discipulosSelecionados Grid
	private Discipulos[] DiscipuloSelecionados;
	private List<Discipulos> listaEnviarDiscipuladorGeracao = new ArrayList<Discipulos>();

	//FileUpload
	private  MensagemAnexoFileUpload mensagemAnexoSelecionada;
	private StreamedContent file; 
	private String arquivoSelecionado;

	private List<MensagemAnexoFileUpload> listFileUpload = new ArrayList<MensagemAnexoFileUpload>();
	private List<MensagemAnexoFileUpload> listFileUploadEnviar = new ArrayList<MensagemAnexoFileUpload>();
	private  MensagemAnexoFileUpload mensagemAnexo = new MensagemAnexoFileUpload();

	public String handleFileUpload(FileUploadEvent event) {
		try {
			mensagemAnexo = new MensagemAnexoFileUpload();
			mensagemAnexo.setNomeArquivoSelecionado(toISO(event.getFile().getFileName()));
			mensagemAnexo.setStreamedContent(new DefaultStreamedContent(event.getFile().getInputstream()));
			mensagemAnexo.setConteudoByte(event.getFile().getContents());
			mensagemAnexo.setFotoTipoArquivo(defineExtensaoArquivo(mensagemAnexo.getNomeArquivoSelecionado()));
			listFileUpload.add(mensagemAnexo);	
		} 
		catch (IOException ex) {
			Logger.getLogger(FileUploadController.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
	public String handleFileUploadEnviar(FileUploadEvent event) {
		try {
			mensagemAnexo = new MensagemAnexoFileUpload();
			mensagemAnexo.setNomeArquivoSelecionado(toISO(event.getFile().getFileName()));
			mensagemAnexo.setStreamedContent(new DefaultStreamedContent(event.getFile().getInputstream()));
			mensagemAnexo.setConteudoByte(event.getFile().getContents());
			mensagemAnexo.setFotoTipoArquivo(defineExtensaoArquivo(mensagemAnexo.getNomeArquivoSelecionado()));
			listFileUploadEnviar.add(mensagemAnexo);	
		} 
		catch (IOException ex) {
			Logger.getLogger(FileUploadController.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	String toISO(String strg) throws UnsupportedEncodingException{  
	    byte[] iso = strg.getBytes("ISO-8859-1");  
	    return new String(iso);  
	}
	
	public String getDiretorioReal(String diretorio) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request.getSession().getServletContext().getRealPath(diretorio);
	}
	
	public String defineExtensaoArquivo(String arquivo){
		String icone = null;
		if(arquivo.indexOf(".jpg") > 0)icone = "icon-foto.png";
		if(arquivo.indexOf(".jpeg") > 0)icone = "icon-foto.png";
		if(arquivo.indexOf(".png") > 0)icone = "icon-foto.png";
		if(arquivo.indexOf(".gif") > 0)icone = "icon-foto.png";
		if(arquivo.indexOf(".doc") > 0)icone = "icon-doc.png";
		if(arquivo.indexOf(".docx") > 0)icone = "icon-doc.png";
		if(arquivo.indexOf(".txt") > 0)icone = "icon-txt.png";
		if(arquivo.indexOf(".pdf") > 0)icone = "icon-pdf.png";
		if(arquivo.indexOf(".rar") > 0)icone = "icon-zip.png";
		if(arquivo.indexOf(".zip") > 0)icone = "icon-zip.png";
		return icone;
	}
	
	public void excluirAnexo(){
		listFileUpload.remove(mensagemAnexoSelecionada);
	}
	
	public void excluirAnexoEnviar(){
		listFileUploadEnviar.remove(mensagemAnexoSelecionada);
	}
	
	public SelectItem[] getGeracaoCombo() {
		listaGeracoes = new ArrayList<Geracoes>();
		listaGeracoes.addAll(geracaoDao.listarGeracoes());
		List<SelectItem> itens = new ArrayList<SelectItem>(listaGeracoes.size());
		
		for(Geracoes g : listaGeracoes) {
			itens.add(new SelectItem(g.getGerCod(), g.getGerDescricao()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}
	
	public SelectItem[] getTipoCombo() {		
		//Verifica se possui Discipulos Função Eclesiastica de Lider Pra Cima
		listaDiscipulosPraCima = new ArrayList<Discipulos>();
		listaDiscipulosPraCima.addAll(discipuloDao.listarDiscipulosLiderPraCima(discipuloSessao.getDiscipulos().getDisCod()));
		
		List<SelectItem> itens = new ArrayList<SelectItem>();
		
		//Se ele for apostolo renner
		if(discipuloSessao.getDiscipulos().getDiscipulos() == null){
			//So Pode Mandar Mensagem Para Geração
			itens.add(new SelectItem(1, "Só para a Geração"));
		}
		else{
			//So Pode Mandar Mensagem Pro Discipulador
			if(listaDiscipulosPraCima.size() == 0){
				itens.add(new SelectItem(2, "Só para o Discipulador"));
			}
			else{
				itens.add(new SelectItem(1, "Só para a Geração"));
				itens.add(new SelectItem(2, "Só para o Discipulador"));
				itens.add(new SelectItem(3, "Para a Geração e o Discipulador"));
			}
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}
	
	public String prepararMensagem() {
		celulas = new Celulas();
		geracoes = new Geracoes();
		discipulos = new Discipulos();
		mensagem = new Mensagem();
				
		comboGeracao = false;
		tabView = false;
		tipoEnvio = 0;
		listFileUpload = new ArrayList<MensagemAnexoFileUpload>();
		btnEnviarAmbos = false;
		btnEnviarDiscipulador = false;

		return "/cad/mensagensCadastro.mir";
	}
	
	public String prepararListarMensagem() {	
	   return "/principal.mir";
	}
	
	public String LerMensagemSaida() throws FileNotFoundException {
		//ATUALIZA A MENSAGEM PARA LIDA
		if(mensagemSelecionada.getMensLida().equals("N")){
			mensagemSelecionada.setMensLida("S");
			mensagemDao.atualizar(mensagemSelecionada);
		}
		
		listFileUpload = new ArrayList<MensagemAnexoFileUpload>();
		
		for(Mensagemanexos anex : mensagemSelecionada.getMensagemanexoses())
		{
			mensagemAnexo = new MensagemAnexoFileUpload();
			mensagemAnexo.setNomeArquivoSelecionado(anex.getId().getMensagemarquivo());
			mensagemAnexo.setStreamedContent(null);
			mensagemAnexo.setConteudoByte(null);
			mensagemAnexo.setFotoTipoArquivo(defineExtensaoArquivo(mensagemAnexo.getNomeArquivoSelecionado()));
			listFileUpload.add(mensagemAnexo);
		}
	
	   return "/cad/mensagensLerSaida.mir";
	}
	
	public void download() {  
		String diretorioUpload = getDiretorioReal("/upload/");
		String mimeType = retornaContentType(arquivoSelecionado);
		String fullFileName = diretorioUpload +"\\"+ arquivoSelecionado;
		
        File file = new File(fullFileName); 
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
	    HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");  
        response.setContentLength((int) file.length());
        response.setContentType(mimeType); 
        try {
			FileInputStream in = new FileInputStream(file);
			OutputStream out = response.getOutputStream();

			byte[] buf = new byte[(int) file.length()];
			int count;
			while ((count = in.read(buf)) >= 0) {
				out.write(buf, 0, count);
			}
			in.close();
			out.flush();
			out.close();
			facesContext.responseComplete();  
        } catch (IOException err) {  
            err.printStackTrace();  
        }  
    }
	
	public String BaixarAnexoSaida() throws FileNotFoundException {
		String diretorioUpload = getDiretorioReal("/upload/mensagem_anexos/");
		
		File arquivo = new File(diretorioUpload +"/"+ arquivoSelecionado);   
        FileInputStream fileInputStream = new FileInputStream(arquivo);  
        StreamedContent streamedContent = new DefaultStreamedContent(fileInputStream, retornaContentType(arquivoSelecionado), arquivoSelecionado);
        
        file = streamedContent;
        
        mensagemAnexo = new MensagemAnexoFileUpload();
		mensagemAnexo.setNomeArquivoSelecionado(arquivoSelecionado);
		mensagemAnexo.setStreamedContent(null);
		mensagemAnexo.setConteudoByte(null);
		mensagemAnexo.setFotoTipoArquivo(defineExtensaoArquivo(arquivoSelecionado));
		
        return "/cad/mensagensBaixarAnexoSaida.mir";
    }
	
	public String BaixarAnexoEntrada() throws FileNotFoundException {
		String diretorioUpload = getDiretorioReal("/upload/mensagem_anexos/");
		
		File arquivo = new File(diretorioUpload +"/"+ arquivoSelecionado);   
        FileInputStream fileInputStream = new FileInputStream(arquivo);  
        StreamedContent streamedContent = new DefaultStreamedContent(fileInputStream, retornaContentType(arquivoSelecionado), arquivoSelecionado);
        
        file = streamedContent;
        
        mensagemAnexo = new MensagemAnexoFileUpload();
		mensagemAnexo.setNomeArquivoSelecionado(arquivoSelecionado);
		mensagemAnexo.setStreamedContent(null);
		mensagemAnexo.setConteudoByte(null);
		mensagemAnexo.setFotoTipoArquivo(defineExtensaoArquivo(arquivoSelecionado));
		
        return "/cad/mensagensBaixarAnexoEntrada.mir";
    }
	
	public void ExcluirMensagemSaida(){  
        String diretorioUpload = getDiretorioReal("/upload/mensagem_anexos/");
        
		//Se Tiver Anexos Exclui eles
		if(mensagemSelecionada.getMensagemanexoses().size() > 0){
			for(Mensagemanexos anex : mensagemSelecionada.getMensagemanexoses())
			{
				File arquivo = new File(diretorioUpload +"/"+ anex.getId().getMensagemarquivo()); 
				arquivo.delete();
			}
		}
		
		mensagemSelecionada.setMensLida("S");
		mensagemDao.excluir(mensagemSelecionada,mensagemSelecionada.getMensCod());
		
		listaEnviadas = new ArrayList<Mensagem>();
        listaEnviadas.addAll(mensagemDao.listarCaixaSaida(discipuloSessao.getDiscipulos().getDisCod())); ;
    }  
	
	public void ExcluirMensagemEntrada(){
        String diretorioUpload = getDiretorioReal("/upload/mensagem_anexos/");
        
		//Se Tiver Anexos Exclui eles
		if(mensagemSelecionada.getMensagemanexoses().size() > 0){
			for(Mensagemanexos anex : mensagemSelecionada.getMensagemanexoses())
			{
				File arquivo = new File(diretorioUpload +"/"+ anex.getId().getMensagemarquivo()); 
				arquivo.delete();
			}
		}
		
		mensagemSelecionada.setMensLida("S");
		mensagemDao.excluir(mensagemSelecionada,mensagemSelecionada.getMensCod());
		
		listaRecebidas = new ArrayList<Mensagem>();
        listaRecebidas.addAll(mensagemDao.listarCaixaEntrada(discipuloSessao.getDiscipulos().getDisCod()));
	}
	
	public String retornaContentType(String arquivo){ 
	    String ContentType = ""; 
	    
	    if(arquivo.indexOf(".jpg") > 0)ContentType = "image/jpg";
	    if(arquivo.indexOf(".jpeg") > 0)ContentType = "image/jpeg";
	    if(arquivo.indexOf(".png") > 0)ContentType = "image/png";
	    if(arquivo.indexOf(".gif") > 0)ContentType = "image/gif";
	    if(arquivo.indexOf(".doc") > 0)ContentType = "application/msword";
	    if(arquivo.indexOf(".docx") > 0)ContentType = "application/msword";
	    if(arquivo.indexOf(".txt") > 0)ContentType = "text/plain";
	    if(arquivo.indexOf(".pdf") > 0)ContentType = "application/pdf";
	    if(arquivo.indexOf(".zip") > 0)ContentType = "application/zip";
	    if(arquivo.indexOf(".rar") > 0)ContentType = "application/rar";
	    
	    return ContentType;
	}
	
	public String checkResponder(){
		if(responder == true)responder = true;
		else responder = false;
		return null;
	}
	
	public String LerMensagemEntrada(){
		//ATUALIZA A MENSAGEM PARA LIDA
		if(mensagemSelecionada.getMensLida().equals("N")){
			mensagemSelecionada.setMensLida("S");
			mensagemDao.atualizar(mensagemSelecionada);
		}
		
		mensagem = new Mensagem();
		listFileUpload = new ArrayList<MensagemAnexoFileUpload>();
		listFileUploadEnviar = new ArrayList<MensagemAnexoFileUpload>();
		
		for(Mensagemanexos anex : mensagemSelecionada.getMensagemanexoses())
		{
			mensagemAnexo = new MensagemAnexoFileUpload();
			mensagemAnexo.setNomeArquivoSelecionado(anex.getId().getMensagemarquivo());
			mensagemAnexo.setStreamedContent(null);
			mensagemAnexo.setConteudoByte(null);
			mensagemAnexo.setFotoTipoArquivo(defineExtensaoArquivo(mensagemAnexo.getNomeArquivoSelecionado()));
			listFileUpload.add(mensagemAnexo);
		}
	
	   return "/cad/mensagensLerEntrada.mir";
	}
	
	public String LerMensagemEntradaDepoisBaixarAnexo(){
		listFileUpload = new ArrayList<MensagemAnexoFileUpload>();
		
		for(Mensagemanexos anex : mensagemSelecionada.getMensagemanexoses())
		{
			mensagemAnexo = new MensagemAnexoFileUpload();
			mensagemAnexo.setNomeArquivoSelecionado(anex.getId().getMensagemarquivo());
			mensagemAnexo.setStreamedContent(null);
			mensagemAnexo.setConteudoByte(null);
			mensagemAnexo.setFotoTipoArquivo(defineExtensaoArquivo(mensagemAnexo.getNomeArquivoSelecionado()));
			listFileUpload.add(mensagemAnexo);
		}
	
	   return "/cad/mensagensLerEntrada.mir";
	}
	
	public String caixaEntrada(){
		listaRecebidas = new ArrayList<Mensagem>();
        listaRecebidas.addAll(mensagemDao.listarCaixaEntrada(discipuloSessao.getDiscipulos().getDisCod()));
        return "/cad/mensagensRecebidas.mir";
	}
	
	public String caixaSaida(){
		listaEnviadas = new ArrayList<Mensagem>();
        listaEnviadas.addAll(mensagemDao.listarCaixaSaida(discipuloSessao.getDiscipulos().getDisCod()));
		return "/cad/mensagensEnviadas.mir";
	}
	
	public void ajaxComboTipo(AjaxBehaviorEvent event) {
		//Selecione
		if(tipoEnvio == 0){
			comboGeracao  = false;
			tabView = false;
			btnEnviarDiscipulador = false;
			btnEnviarAmbos = false;
			
			geracoes.setGerCod(0);
		}
		//Apenas Geração
		if(tipoEnvio == 1){
			comboGeracao  = true;
			tabView = false;
			btnEnviarDiscipulador = false;
			btnEnviarAmbos = false;
			
			geracoes.setGerCod(0);
		}
		//Apenas Discipulador
		if(tipoEnvio == 2){
			comboGeracao  = false;
			tabView = true;
			tabDiscipulador = true;
			tabM12 = false;
			tabMensagem = true;
			tabAnexos = true;
			btnEnviarDiscipulador = true;
			btnEnviarAmbos = false;
			
			//Preenche o Discipulador
			listaDiscipulador = new ArrayList<Discipulos>();
			listaDiscipulador.addAll(discipuloDao.listarDiscipulador(discipuloSessao.getDiscipulos().getDiscipulos().getDisCod()));
			discipulador = listaDiscipulador.get(0);
		}
		//Ambos
		if(tipoEnvio == 3){
			comboGeracao  = true;
			tabView = false;
			btnEnviarDiscipulador = false;
			btnEnviarAmbos = false;
			
			geracoes.setGerCod(0);
			
			//Preenche o Discipulador
			listaDiscipulador = new ArrayList<Discipulos>();
			listaDiscipulador.addAll(discipuloDao.listarDiscipulador(discipuloSessao.getDiscipulos().getDiscipulos().getDisCod()));
			discipulador = listaDiscipulador.get(0);
		}
	}
	
	public void listarM12PorGeracao(AjaxBehaviorEvent event) {		
		listaDiscipulos = new ArrayList<Discipulos>();
		listaDiscipulos.addAll(discipuloDao.listarM12Mensagem(discipuloSessao.getDiscipulos().getDisCod(), geracoes.getGerCod()));
		
		//Selecione
		if(geracoes.getGerCod() == 0){
			tabView = false;
		}
		
		//Apenas Geração
		if(geracoes.getGerCod() > 0 && tipoEnvio == 1 && listaDiscipulos.size() > 0){
			tabView = true;
			tabDiscipulador = false;
			tabM12 = true;
			tabMensagem = true;
			tabAnexos = true;
			btnEnviarDiscipulador = false;
			btnEnviarAmbos = true;
		}
		else{
			tabView = true;
			tabDiscipulador = false;
			tabM12 = true;
			tabMensagem = false;
			tabAnexos = false;
			btnEnviarDiscipulador = false;
			btnEnviarAmbos = false;
		}
		
		//Ambos Sem Geração
		if(geracoes.getGerCod() > 0 && tipoEnvio == 3 && listaDiscipulos.size() == 0){
			tabView = true;
			tabDiscipulador = true;
			tabM12 = true;
			tabMensagem = false;
			tabAnexos = false;
			btnEnviarDiscipulador = false;
			btnEnviarAmbos = false;
		}
		//Ambos Com Geração
		if(geracoes.getGerCod() > 0 && tipoEnvio == 3 && listaDiscipulos.size() > 0){
			tabView = true;
			tabDiscipulador = true;
			tabM12 = true;
			tabMensagem = true;
			tabAnexos = true;
			btnEnviarDiscipulador = false;
			btnEnviarAmbos = true;
		}
	}
	
	public void onRowSelectNavigate(SelectEvent event) { 
		discipulo = (Discipulos) event.getObject();
		//int qtdSelecionados = DiscipuloSelecionados.length;
		
    	//FacesContext context = FacesContext.getCurrentInstance();
		//context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!",String.valueOf(qtdSelecionados)));
    } 
	
	public void onRowUnselect(UnselectEvent event) {  
		discipulo = (Discipulos) event.getObject();
		//int qtdSelecionados = DiscipuloSelecionados.length;
		
		//FacesContext context = FacesContext.getCurrentInstance();
		//context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!",String.valueOf(qtdSelecionados)));
    }   
	
	public Date pegaDataAtual() throws Exception {   
		Date dataAtual = new Date(System.currentTimeMillis());    
		SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy");
		
		String dataString = formatarDate.format(dataAtual);
	
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
		java.sql.Date data = new java.sql.Date(format.parse(dataString).getTime()); 
		
        return data;  
    }
	
	public void enviarSelecionados(ActionEvent event) throws Exception{
		mensagem = new Mensagem();
		EnviarMensagemSelecionados = true;
	}
	
	public void enviarTodos(ActionEvent event) throws Exception{
		mensagem = new Mensagem();
		EnviarMensagemSelecionados = false;
	}
	
	public String SalvarEmDiscoAnexos(String codMensagem, String nomeArquivo, byte[] conteudo){		
		FacesContext context = FacesContext.getCurrentInstance();
		String path = getDiretorioReal("/upload/mensagem_anexos/");
		String arquivo = codMensagem +"_" + nomeArquivo;
		String caminho = path + "/".concat(arquivo);
		
		try {
			FileOutputStream fos = new FileOutputStream(caminho);
			fos.write(conteudo);
			fos.close();
		} catch (IOException ex) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!",ex.getMessage()));
		}
		return arquivo;
	}
	
	public void enviarEmailMensagem(String diretorioImg, String nomeEnviou, String nomeRecebe, 
		String emailEnviar){
		
		if(enviarEmailMensagem == true){
			new SendEMail().sendSimpleMailEnviarMensagem(diretorioImg, nomeEnviou, nomeRecebe, emailEnviar);
		}
	}
	
	public String enviarMensagem() throws Exception{
		FacesContext context = FacesContext.getCurrentInstance();
		String mensagemTexto = mensagem.getMensTexto();
		String diretorioImg = getDiretorioReal("/img/");
		
		//Salva a Caixa de Saida
		mensagem.setMensData(pegaDataAtual());
		mensagem.setDiscipulosByMensDisCod(discipuloSessao.getDiscipulos());
		mensagem.setDiscipulosByMensDisCodRecebe(discipulador);
		mensagem.setMensTexto(mensagemTexto);
		mensagem.setMensLida("N");
		mensagem.setMensCaixa("S");
		mensagemDao.salvar(mensagem);
		
		//Se Tiver Anexos
		if(listFileUpload.size() > 0){
			for(MensagemAnexoFileUpload anex : listFileUpload)
			{
				//Salva em Disco o Anexo
				String arquivo = SalvarEmDiscoAnexos(mensagem.getMensCod().toString(), anex.getNomeArquivoSelecionado(),
				anex.getConteudoByte());
				
				mensagemAnexosId = new MensagemanexosId();
				mensagemAnexosId.setMensagemCod(mensagem.getMensCod());
				mensagemAnexosId.setMensagemarquivo(arquivo);
				
				mensagemAnexos = new Mensagemanexos();
				mensagemAnexos.setId(mensagemAnexosId);
				mensagemAnexos.setMensagem(mensagem);
				mensagemAnexosDao.salvar(mensagemAnexos);
			}
		}
		
		//Salva a Caixa de Entrada
		mensagem = new Mensagem();
		mensagem.setMensData(pegaDataAtual());
		mensagem.setDiscipulosByMensDisCod(discipuloSessao.getDiscipulos());
		mensagem.setDiscipulosByMensDisCodRecebe(discipulador);
		mensagem.setMensTexto(mensagemTexto);
		mensagem.setMensLida("N");
		mensagem.setMensCaixa("E");
		mensagemDao.salvar(mensagem);
		
		//Se Tiver Anexos
		if(listFileUpload.size() > 0){
			for(MensagemAnexoFileUpload anex : listFileUpload)
			{
				//Salva em Disco o Anexo
				String arquivo = SalvarEmDiscoAnexos(mensagem.getMensCod().toString(), anex.getNomeArquivoSelecionado(),
				anex.getConteudoByte());
				
				mensagemAnexosId = new MensagemanexosId();
				mensagemAnexosId.setMensagemCod(mensagem.getMensCod());
				mensagemAnexosId.setMensagemarquivo(arquivo);
				
				mensagemAnexos = new Mensagemanexos();
				mensagemAnexos.setId(mensagemAnexosId);
				mensagemAnexos.setMensagem(mensagem);
				mensagemAnexosDao.salvar(mensagemAnexos);
			}
		}
		
		enviarEmailMensagem(diretorioImg, discipuloSessao.getDiscipulos().getDisnome(), 
		discipulador.getDisnome(), discipulador.getDisemail());
		
		listaEnviadas = new ArrayList<Mensagem>();
        listaEnviadas.addAll(mensagemDao.listarCaixaSaida(discipuloSessao.getDiscipulos().getDisCod()));
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!","Mensagem enviada com sucesso!"));
		
		return "/cad/mensagensEnviadas.mir";
	}
	
	public String EnviarSelecionados() throws Exception{
		FacesContext context = FacesContext.getCurrentInstance();
		String mensagemTexto = mensagem.getMensTexto();
		String diretorioImg = getDiretorioReal("/img/");
		
		if(DiscipuloSelecionados.length == 0)
		{
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!!!","Mensagem não enviada selecione algum discipulo!"));
			return "/cad/mensagensCadastro.mir";
		}
		else
		{
			//SÓ PARA A GERAÇÃO
			if(tipoEnvio == 1){
			//Salva a Caixa de Saida
			for(Discipulos dis : DiscipuloSelecionados)
			{
				mensagem = new Mensagem();
				mensagem.setMensData(pegaDataAtual());
				mensagem.setDiscipulosByMensDisCod(discipuloSessao.getDiscipulos());
				mensagem.setDiscipulosByMensDisCodRecebe(dis);
				mensagem.setMensTexto(mensagemTexto);
				mensagem.setMensLida("N");
				mensagem.setMensCaixa("S");
				mensagemDao.salvar(mensagem);
				
				//Se Tiver Anexos
				if(listFileUpload.size() > 0){
					for(MensagemAnexoFileUpload anex : listFileUpload)
					{
						//Salva em Disco o Anexo
						String arquivo = SalvarEmDiscoAnexos(mensagem.getMensCod().toString(), anex.getNomeArquivoSelecionado(),
						anex.getConteudoByte());
						
						mensagemAnexosId = new MensagemanexosId();
						mensagemAnexosId.setMensagemCod(mensagem.getMensCod());
						mensagemAnexosId.setMensagemarquivo(arquivo);
						
						mensagemAnexos = new Mensagemanexos();
						mensagemAnexos.setId(mensagemAnexosId);
						mensagemAnexos.setMensagem(mensagem);
						mensagemAnexosDao.salvar(mensagemAnexos);
					}
				}
			}
			
			//Salva a Caixa de Entrada
			for(Discipulos dis : DiscipuloSelecionados)
			{
				mensagem = new Mensagem();
				mensagem.setMensData(pegaDataAtual());
				mensagem.setDiscipulosByMensDisCod(discipuloSessao.getDiscipulos());
				mensagem.setDiscipulosByMensDisCodRecebe(dis);
				mensagem.setMensTexto(mensagemTexto);
				mensagem.setMensLida("N");
				mensagem.setMensCaixa("E");
				mensagemDao.salvar(mensagem);
				
				enviarEmailMensagem(diretorioImg, discipuloSessao.getDiscipulos().getDisnome(), 
			    dis.getDisnome(), dis.getDisemail());
				
				//Se Tiver Anexos
				if(listFileUpload.size() > 0){
					for(MensagemAnexoFileUpload anex : listFileUpload)
					{
						//Salva em Disco o Anexo
						String arquivo = SalvarEmDiscoAnexos(mensagem.getMensCod().toString(), anex.getNomeArquivoSelecionado(),
						anex.getConteudoByte());
						
						mensagemAnexosId = new MensagemanexosId();
						mensagemAnexosId.setMensagemCod(mensagem.getMensCod());
						mensagemAnexosId.setMensagemarquivo(arquivo);
						
						mensagemAnexos = new Mensagemanexos();
						mensagemAnexos.setId(mensagemAnexosId);
						mensagemAnexos.setMensagem(mensagem);
						mensagemAnexosDao.salvar(mensagemAnexos);
					}
				}
			}
		}
			
			//SÓ PARA A GERAÇÃO E O DISCIPULADOR
			if(tipoEnvio == 3){
			
			    //ADICIONA NA LISTA O DISCIPULADOR E OS DISCIPULOS SELECIONADOS
				listaEnviarDiscipuladorGeracao = new ArrayList<Discipulos>();
				for(Discipulos dis : DiscipuloSelecionados){
				listaEnviarDiscipuladorGeracao.add(dis);
				}
				listaEnviarDiscipuladorGeracao.add(discipulador);
				
			//Salva a Caixa de Saida
			for(Discipulos dis : listaEnviarDiscipuladorGeracao)
			{
				mensagem = new Mensagem();
				mensagem.setMensData(pegaDataAtual());
				mensagem.setDiscipulosByMensDisCod(discipuloSessao.getDiscipulos());
				mensagem.setDiscipulosByMensDisCodRecebe(dis);
				mensagem.setMensTexto(mensagemTexto);
				mensagem.setMensLida("N");
				mensagem.setMensCaixa("S");
				mensagemDao.salvar(mensagem);
				
				//Se Tiver Anexos
				if(listFileUpload.size() > 0){
					for(MensagemAnexoFileUpload anex : listFileUpload)
					{
						//Salva em Disco o Anexo
						String arquivo = SalvarEmDiscoAnexos(mensagem.getMensCod().toString(), anex.getNomeArquivoSelecionado(),
						anex.getConteudoByte());
						
						mensagemAnexosId = new MensagemanexosId();
						mensagemAnexosId.setMensagemCod(mensagem.getMensCod());
						mensagemAnexosId.setMensagemarquivo(arquivo);
						
						mensagemAnexos = new Mensagemanexos();
						mensagemAnexos.setId(mensagemAnexosId);
						mensagemAnexos.setMensagem(mensagem);
						mensagemAnexosDao.salvar(mensagemAnexos);
					}
				}
			}
			
			//Salva a Caixa de Entrada
			for(Discipulos dis : listaEnviarDiscipuladorGeracao)
			{
				mensagem = new Mensagem();
				mensagem.setMensData(pegaDataAtual());
				mensagem.setDiscipulosByMensDisCod(discipuloSessao.getDiscipulos());
				mensagem.setDiscipulosByMensDisCodRecebe(dis);
				mensagem.setMensTexto(mensagemTexto);
				mensagem.setMensLida("N");
				mensagem.setMensCaixa("E");
				mensagemDao.salvar(mensagem);
				
				enviarEmailMensagem(diretorioImg, discipuloSessao.getDiscipulos().getDisnome(), 
				dis.getDisnome(), dis.getDisemail());
				
				//Se Tiver Anexos
				if(listFileUpload.size() > 0){
					for(MensagemAnexoFileUpload anex : listFileUpload)
					{
						//Salva em Disco o Anexo
						String arquivo = SalvarEmDiscoAnexos(mensagem.getMensCod().toString(), anex.getNomeArquivoSelecionado(),
						anex.getConteudoByte());
						
						mensagemAnexosId = new MensagemanexosId();
						mensagemAnexosId.setMensagemCod(mensagem.getMensCod());
						mensagemAnexosId.setMensagemarquivo(arquivo);
						
						mensagemAnexos = new Mensagemanexos();
						mensagemAnexos.setId(mensagemAnexosId);
						mensagemAnexos.setMensagem(mensagem);
						mensagemAnexosDao.salvar(mensagemAnexos);
					}
				}
			}
		 }
			
			listaEnviadas = new ArrayList<Mensagem>();
	        listaEnviadas.addAll(mensagemDao.listarCaixaSaida(discipuloSessao.getDiscipulos().getDisCod()));
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!","Mensagem enviada com sucesso!"));
			
			return "/cad/mensagensEnviadas.mir";
	   } //Fecha o Else da Validação Se Tem Selecionados
	}
	
	public String EnviarTodos() throws Exception{
		String mensagemTexto = mensagem.getMensTexto();
		String diretorioImg = getDiretorioReal("/img/");
		
		FacesContext context = FacesContext.getCurrentInstance();
		if(listaDiscipulos.size() == 0)
		{
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!!!","Mensagem não enviada selecione algum discipulo!"));
		}
		else
		{
			//SÓ PARA A GERAÇÃO
			if(tipoEnvio == 1){
			//Salva a Caixa de Saida
			for(Discipulos dis : listaDiscipulos)
			{
				mensagem = new Mensagem();
				mensagem.setMensData(pegaDataAtual());
				mensagem.setDiscipulosByMensDisCod(discipuloSessao.getDiscipulos());
				mensagem.setDiscipulosByMensDisCodRecebe(dis);
				mensagem.setMensTexto(mensagemTexto);
				mensagem.setMensLida("N");
				mensagem.setMensCaixa("S");
				mensagemDao.salvar(mensagem);
				
				//Se Tiver Anexos
				if(listFileUpload.size() > 0){
					for(MensagemAnexoFileUpload anex : listFileUpload)
					{
						//Salva em Disco o Anexo
						String arquivo = SalvarEmDiscoAnexos(mensagem.getMensCod().toString(), anex.getNomeArquivoSelecionado(),
						anex.getConteudoByte());
						
						mensagemAnexosId = new MensagemanexosId();
						mensagemAnexosId.setMensagemCod(mensagem.getMensCod());
						mensagemAnexosId.setMensagemarquivo(arquivo);
						
						mensagemAnexos = new Mensagemanexos();
						mensagemAnexos.setId(mensagemAnexosId);
						mensagemAnexos.setMensagem(mensagem);
						mensagemAnexosDao.salvar(mensagemAnexos);
					}
				}
			}
			
			//Salva a Caixa de Entrada
			for(Discipulos dis : listaDiscipulos)
			{
				mensagem = new Mensagem();
				mensagem.setMensData(pegaDataAtual());
				mensagem.setDiscipulosByMensDisCod(discipuloSessao.getDiscipulos());
				mensagem.setDiscipulosByMensDisCodRecebe(dis);
				mensagem.setMensTexto(mensagemTexto);
				mensagem.setMensLida("N");
				mensagem.setMensCaixa("E");
				mensagemDao.salvar(mensagem);
				
				enviarEmailMensagem(diretorioImg, discipuloSessao.getDiscipulos().getDisnome(), 
				dis.getDisnome(), dis.getDisemail());
				
				//Se Tiver Anexos
				if(listFileUpload.size() > 0){
					for(MensagemAnexoFileUpload anex : listFileUpload)
					{
						//Salva em Disco o Anexo
						String arquivo = SalvarEmDiscoAnexos(mensagem.getMensCod().toString(), anex.getNomeArquivoSelecionado(),
						anex.getConteudoByte());
						
						mensagemAnexosId = new MensagemanexosId();
						mensagemAnexosId.setMensagemCod(mensagem.getMensCod());
						mensagemAnexosId.setMensagemarquivo(arquivo);
						
						mensagemAnexos = new Mensagemanexos();
						mensagemAnexos.setId(mensagemAnexosId);
						mensagemAnexos.setMensagem(mensagem);
						mensagemAnexosDao.salvar(mensagemAnexos);
					}
				}
			 }
		  }
			
			//SÓ PARA A GERAÇÃO E O DISCIPULADOR
			if(tipoEnvio == 3){
			
			    //ADICIONA NA LISTA O DISCIPULADOR E OS DISCIPULOS SELECIONADOS
				listaEnviarDiscipuladorGeracao = new ArrayList<Discipulos>();
				for(Discipulos dis : listaDiscipulos){
				listaEnviarDiscipuladorGeracao.add(dis);
				}
				listaEnviarDiscipuladorGeracao.add(discipulador);
				
			//Salva a Caixa de Saida
			for(Discipulos dis : listaEnviarDiscipuladorGeracao)
			{
				mensagem = new Mensagem();
				mensagem.setMensData(pegaDataAtual());
				mensagem.setDiscipulosByMensDisCod(discipuloSessao.getDiscipulos());
				mensagem.setDiscipulosByMensDisCodRecebe(dis);
				mensagem.setMensTexto(mensagemTexto);
				mensagem.setMensLida("N");
				mensagem.setMensCaixa("S");
				mensagemDao.salvar(mensagem);
				
				//Se Tiver Anexos
				if(listFileUpload.size() > 0){
					for(MensagemAnexoFileUpload anex : listFileUpload)
					{
						//Salva em Disco o Anexo
						String arquivo = SalvarEmDiscoAnexos(mensagem.getMensCod().toString(), anex.getNomeArquivoSelecionado(),
						anex.getConteudoByte());
						
						mensagemAnexosId = new MensagemanexosId();
						mensagemAnexosId.setMensagemCod(mensagem.getMensCod());
						mensagemAnexosId.setMensagemarquivo(arquivo);
						
						mensagemAnexos = new Mensagemanexos();
						mensagemAnexos.setId(mensagemAnexosId);
						mensagemAnexos.setMensagem(mensagem);
						mensagemAnexosDao.salvar(mensagemAnexos);
					}
				}
			}
			
			//Salva a Caixa de Entrada
			for(Discipulos dis : listaEnviarDiscipuladorGeracao)
			{
				mensagem = new Mensagem();
				mensagem.setMensData(pegaDataAtual());
				mensagem.setDiscipulosByMensDisCod(discipuloSessao.getDiscipulos());
				mensagem.setDiscipulosByMensDisCodRecebe(dis);
				mensagem.setMensTexto(mensagemTexto);
				mensagem.setMensLida("N");
				mensagem.setMensCaixa("E");
				mensagemDao.salvar(mensagem);
				
				enviarEmailMensagem(diretorioImg, discipuloSessao.getDiscipulos().getDisnome(), 
				dis.getDisnome(), dis.getDisemail());
				
				//Se Tiver Anexos
				if(listFileUpload.size() > 0){
					for(MensagemAnexoFileUpload anex : listFileUpload)
					{
						//Salva em Disco o Anexo
						String arquivo = SalvarEmDiscoAnexos(mensagem.getMensCod().toString(), anex.getNomeArquivoSelecionado(),
						anex.getConteudoByte());
						
						mensagemAnexosId = new MensagemanexosId();
						mensagemAnexosId.setMensagemCod(mensagem.getMensCod());
						mensagemAnexosId.setMensagemarquivo(arquivo);
						
						mensagemAnexos = new Mensagemanexos();
						mensagemAnexos.setId(mensagemAnexosId);
						mensagemAnexos.setMensagem(mensagem);
						mensagemAnexosDao.salvar(mensagemAnexos);
					}
				}
			}
		 }
	   }
		listaEnviadas = new ArrayList<Mensagem>();
        listaEnviadas.addAll(mensagemDao.listarCaixaSaida(discipuloSessao.getDiscipulos().getDisCod()));
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!","Mensagem enviada com sucesso!"));
		
		return "/cad/mensagensEnviadas.mir";
	}
	
	public String responderMensagem() throws Exception{
		FacesContext context = FacesContext.getCurrentInstance();
		String diretorioImg = getDiretorioReal("/img/");
		
        if(mensagem.getMensTexto().length() <= 0 ){
        	context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERRO!!!","Resposta em Branco!"));
        	return "/cad/mensagensLerEntrada.mir";
        }
        else{
        
        int envia = 0;
        int recebe = 0;
        
        List<Object> obj = mensagemDao.discipulosEnviaRecebeMensagem(mensagemSelecionada.getMensCod()); //[1, 2] retornou        
        Object[] objeto = obj.toArray();//transformar sua List num array de Objeto  
        Object[] objeto2 = (Object[]) objeto[0];
        
        envia = (Integer) objeto2[0];
        recebe = (Integer) objeto2[1];
    
        listaDiscipulos = new ArrayList<Discipulos>();
        listaDiscipulos.addAll(discipuloDao.listarDiscipulador(envia));
        discipuloEnvia = listaDiscipulos.get(0);
        
        listaDiscipulos = new ArrayList<Discipulos>();
        listaDiscipulos.addAll(discipuloDao.listarDiscipulador(recebe));
        discipuloRecebe = listaDiscipulos.get(0);
        
        String mensagemTexto = mensagem.getMensTexto();
	    
	    //Salva Caixa Saida
	    mensagem = new Mensagem();
		mensagem.setMensData(pegaDataAtual());
		mensagem.setDiscipulosByMensDisCod(discipuloRecebe);
		mensagem.setDiscipulosByMensDisCodRecebe(discipuloEnvia);
		mensagem.setMensTexto(mensagemTexto);
		mensagem.setMensLida("N");
		mensagem.setMensCaixa("S");
		mensagemDao.salvar(mensagem);
		
		//Se Tiver Anexos
		if(listFileUploadEnviar.size() > 0){
			for(MensagemAnexoFileUpload anex : listFileUploadEnviar)
			{
				//Salva em Disco o Anexo
				String arquivo = SalvarEmDiscoAnexos(mensagem.getMensCod().toString(), anex.getNomeArquivoSelecionado(),
				anex.getConteudoByte());
				
				mensagemAnexosId = new MensagemanexosId();
				mensagemAnexosId.setMensagemCod(mensagem.getMensCod());
				mensagemAnexosId.setMensagemarquivo(arquivo);
				
				mensagemAnexos = new Mensagemanexos();
				mensagemAnexos.setId(mensagemAnexosId);
				mensagemAnexos.setMensagem(mensagem);
				mensagemAnexosDao.salvar(mensagemAnexos);
			}
		}
		
		//Salva Caixa Entrada
	    mensagem = new Mensagem();
		mensagem.setMensData(pegaDataAtual());
		mensagem.setDiscipulosByMensDisCod(discipuloRecebe);
		mensagem.setDiscipulosByMensDisCodRecebe(discipuloEnvia);
		mensagem.setMensTexto(mensagemTexto);
		mensagem.setMensLida("N");
		mensagem.setMensCaixa("E");
		mensagemDao.salvar(mensagem);
		
		//Se Tiver Anexos
		if(listFileUploadEnviar.size() > 0){
			for(MensagemAnexoFileUpload anex : listFileUploadEnviar)
			{
				//Salva em Disco o Anexo
				String arquivo = SalvarEmDiscoAnexos(mensagem.getMensCod().toString(), anex.getNomeArquivoSelecionado(),
				anex.getConteudoByte());
				
				mensagemAnexosId = new MensagemanexosId();
				mensagemAnexosId.setMensagemCod(mensagem.getMensCod());
				mensagemAnexosId.setMensagemarquivo(arquivo);
				
				mensagemAnexos = new Mensagemanexos();
				mensagemAnexos.setId(mensagemAnexosId);
				mensagemAnexos.setMensagem(mensagem);
				mensagemAnexosDao.salvar(mensagemAnexos);
			}
		}
		
		enviarEmailMensagem(diretorioImg, discipuloSessao.getDiscipulos().getDisnome(), 
				discipuloEnvia.getDisnome(), discipuloEnvia.getDisemail());
		
		listaEnviadas = new ArrayList<Mensagem>();
        listaEnviadas.addAll(mensagemDao.listarCaixaSaida(discipuloSessao.getDiscipulos().getDisCod()));
        
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!","Mensagem enviada com sucesso!"));
        
        return "/cad/mensagensEnviadas.mir";
        }
   
	}
	
	public void setDiscipuloSessao(ApplicationSecurityManager discipuloSessao) {
		this.discipuloSessao = discipuloSessao;
	}

	public ApplicationSecurityManager getDiscipuloSessao() {
		return discipuloSessao;
	}
	
	public void setDiscipulos(Discipulos discipulos) {
		this.discipulos = discipulos;
	}

	public Discipulos getDiscipulos() {
		return discipulos;
	}
	
	public Celulas getCelulas() {
		return celulas;
	}

	public void setCelulas(Celulas celulas) {
		this.celulas = celulas;
	}

	public void setGeracoes(Geracoes geracoes) {
		this.geracoes = geracoes;
	}

	public Geracoes getGeracoes() {
		return geracoes;
	}
	
	public Mensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}
	
	public List<Discipulos> getListaDiscipulos() {
		return listaDiscipulos;
	}

	public void setListaDiscipulos(List<Discipulos> listaDiscipulos) {
		this.listaDiscipulos = listaDiscipulos;
	}
	
	public List<Geracoes> getListaGeracoes() {
		return listaGeracoes;
	}

	public void setListaGeracoes(List<Geracoes> listaGeracoes) {
		this.listaGeracoes = listaGeracoes;
	}

	public void setDiscipuloSelecionados(Discipulos[] discipuloSelecionados) {
		DiscipuloSelecionados = discipuloSelecionados;
	}

	public Discipulos[] getDiscipuloSelecionados() {
		return DiscipuloSelecionados;
	}
	
	public boolean isDtM12() {
		return tabM12;
	}

	public void setDtM12(boolean tabM12) {
		this.tabM12 = tabM12;
	}
	
	public boolean isEnviarMensagemSelecionados() {
		return EnviarMensagemSelecionados;
	}

	public void setEnviarMensagemSelecionados(boolean enviarMensagemSelecionados) {
		EnviarMensagemSelecionados = enviarMensagemSelecionados;
	}
	
	public List<Mensagem> getListaRecebidas() {
		return listaRecebidas;
	}

	public void setListaRecebidas(List<Mensagem> listaRecebidas) {
		this.listaRecebidas = listaRecebidas;
	}

	public List<Mensagem> getListaEnviadas() {
		return listaEnviadas;
	}

	public void setListaEnviadas(List<Mensagem> listaEnviadas) {
		this.listaEnviadas = listaEnviadas;
	}

	public Mensagem getMensagemSelecionada() {
		return mensagemSelecionada;
	}

	public void setMensagemSelecionada(Mensagem mensagemSelecionada) {
		this.mensagemSelecionada = mensagemSelecionada;
	}
	
	public boolean isResponder() {
		return responder;
	}

	public void setResponder(boolean responder) {
		this.responder = responder;
	}
	
	public Discipulos getDiscipulador() {
		return discipulador;
	}
	
	public void setDiscipulador(Discipulos discipulador) {
		this.discipulador = discipulador;
	}
	
	public int getQtdMensagem() {
		listaRecebidas = new ArrayList<Mensagem>();
        listaRecebidas.addAll(mensagemDao.listarMensagensRecebidas(discipuloSessao.getDiscipulos()));
        qtdMensagem = listaRecebidas.size();
		return qtdMensagem;
	}

	public void setQtdMensagem(int qtdMensagem) {
		this.qtdMensagem = qtdMensagem;
	}
	
	public List<MensagemAnexoFileUpload> getListFileUpload() {
		return listFileUpload;
	}

	public void setListFileUpload(List<MensagemAnexoFileUpload> listFileUpload) {
		this.listFileUpload = listFileUpload;
	}

	public MensagemAnexoFileUpload getMensagemAnexo() {
		return mensagemAnexo;
	}

	public void setMensagemAnexo(MensagemAnexoFileUpload mensagemAnexo) {
		this.mensagemAnexo = mensagemAnexo;
	}
	
	public MensagemAnexoFileUpload getMensagemAnexoSelecionada() {
		return mensagemAnexoSelecionada;
	}

	public void setMensagemAnexoSelecionada(
			MensagemAnexoFileUpload mensagemAnexoSelecionada) {
		this.mensagemAnexoSelecionada = mensagemAnexoSelecionada;
	}
	
	public int getTipoEnvio() {
		return tipoEnvio;
	}

	public void setTipoEnvio(int tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}
	
	public List<Discipulos> getListaDiscipulador() {
		return listaDiscipulador;
	}

	public void setListaDiscipulador(List<Discipulos> listaDiscipulador) {
		this.listaDiscipulador = listaDiscipulador;
	}
	
	public List<Discipulos> getListaDiscipulosPraCima() {
		return listaDiscipulosPraCima;
	}

	public void setListaDiscipulosPraCima(List<Discipulos> listaDiscipulosPraCima) {
		this.listaDiscipulosPraCima = listaDiscipulosPraCima;
	}
	
	public boolean isComboGeracao() {
		return comboGeracao;
	}

	public void setComboGeracao(boolean comboGeracao) {
		this.comboGeracao = comboGeracao;
	}

	public boolean isTabView() {
		return tabView;
	}

	public void setTabView(boolean tabView) {
		this.tabView = tabView;
	}

	public boolean isTabDiscipulador() {
		return tabDiscipulador;
	}

	public void setTabDiscipulador(boolean tabDiscipulador) {
		this.tabDiscipulador = tabDiscipulador;
	}

	public boolean isTabM12() {
		return tabM12;
	}

	public void setTabM12(boolean tabM12) {
		this.tabM12 = tabM12;
	}

	public boolean isTabMensagem() {
		return tabMensagem;
	}

	public void setTabMensagem(boolean tabMensagem) {
		this.tabMensagem = tabMensagem;
	}

	public boolean isTabAnexos() {
		return tabAnexos;
	}

	public void setTabAnexos(boolean tabAnexos) {
		this.tabAnexos = tabAnexos;
	}
	
	public boolean isBtnEnviarDiscipulador() {
		return btnEnviarDiscipulador;
	}

	public void setBtnEnviarDiscipulador(boolean btnEnviarDiscipulador) {
		this.btnEnviarDiscipulador = btnEnviarDiscipulador;
	}

	public boolean isBtnEnviarAmbos() {
		return btnEnviarAmbos;
	}

	public void setBtnEnviarAmbos(boolean btnEnviarAmbos) {
		this.btnEnviarAmbos = btnEnviarAmbos;
	}
	
	public Mensagemanexos getMensagemAnexos() {
		return mensagemAnexos;
	}

	public void setMensagemAnexos(Mensagemanexos mensagemAnexos) {
		this.mensagemAnexos = mensagemAnexos;
	}

	public MensagemanexosId getMensagemAnexosId() {
		return mensagemAnexosId;
	}

	public void setMensagemAnexosId(MensagemanexosId mensagemAnexosId) {
		this.mensagemAnexosId = mensagemAnexosId;
	}
	
	public List<Discipulos> getListaEnviarDiscipuladorGeracao() {
		return listaEnviarDiscipuladorGeracao;
	}

	public void setListaEnviarDiscipuladorGeracao(
			List<Discipulos> listaEnviarDiscipuladorGeracao) {
		this.listaEnviarDiscipuladorGeracao = listaEnviarDiscipuladorGeracao;
	}
	
	public StreamedContent getFile() throws FileNotFoundException {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}
	
	public String getArquivoSelecionado() {
		return arquivoSelecionado;
	}

	public void setArquivoSelecionado(String arquivoSelecionado) {
		this.arquivoSelecionado = arquivoSelecionado;
	}
	
	public List<MensagemAnexoFileUpload> getListFileUploadEnviar() {
		return listFileUploadEnviar;
	}

	public void setListFileUploadEnviar(
			List<MensagemAnexoFileUpload> listFileUploadEnviar) {
		this.listFileUploadEnviar = listFileUploadEnviar;
	}
	
	public Discipulos getDiscipuloEnvia() {
		return discipuloEnvia;
	}

	public void setDiscipuloEnvia(Discipulos discipuloEnvia) {
		this.discipuloEnvia = discipuloEnvia;
	}
	
	public Discipulos getDiscipuloRecebe() {
		return discipuloRecebe;
	}

	public void setDiscipuloRecebe(Discipulos discipuloRecebe) {
		this.discipuloRecebe = discipuloRecebe;
	}
	
	public Discipulos getDiscipulo() {
		return discipulo;
	}

	public void setDiscipulo(Discipulos discipulo) {
		this.discipulo = discipulo;
	}
	
	public boolean isEnviarEmailMensagem() {
		return enviarEmailMensagem;
	}

	public void setEnviarEmailMensagem(boolean enviarEmailMensagem) {
		this.enviarEmailMensagem = enviarEmailMensagem;
	}
}
