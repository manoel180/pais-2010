package br.com.pais.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.pais.dao.RelatorioDao;

@SuppressWarnings("deprecation")
public class RelatorioDaoImp implements RelatorioDao {
	
	@Override
	public void gerarProtocoloDinheiro(List<Protocolo> listaProtocolo) {
		String jasper = "";
		if(listaProtocolo.get(0).getDiscipulador().equals("FINANCEIRO")){
			jasper = getDiretorioReal("/jasper/financeiro/protocoloFinanceiroDinheiro.jasper");
		}
		else{
			jasper = getDiretorioReal("/jasper/protocolo/protocoloDinheiro.jasper");
		}
		String nomeRelatorioProtocolo = listaProtocolo.get(0).getProtocolo();
        String path = getDiretorioReal("//relatorio/protocolo/"+nomeRelatorioProtocolo.concat(".pdf")+"");
		
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		
		try {
			// Parametros do relatorios
			Map<String, Object> parametros = new HashMap<String, Object>();
			//parametros.put("SUBREPORT_DIR", getDiretorioReal("/jasper/") + "/");
			
			// Preenche a Lista
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaProtocolo);
			
			//Mando o jasper gerar o relatorio. Nesse caso passo o map
			JasperPrint impressao = JasperFillManager.fillReport(jasper, parametros, ds);
			
			// Grava o relatorio em disco em pdf	
			JasperManager.printReportToPdfFile(impressao, path);
			// Redireciona para o pdf gerado
			response.sendRedirect("/pais/relatorio/protocolo/"+nomeRelatorioProtocolo.concat(".pdf")+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void gerarProtocoloCheque(List<Protocolo> listaProtocolo) {
		String jasper = "";
		String pathSubRelatorio = "";
		if(listaProtocolo.get(0).getDiscipulador().equals("FINANCEIRO")){
			jasper = getDiretorioReal("/jasper/financeiro/protocoloFinanceiroCheque.jasper");
			pathSubRelatorio = getDiretorioReal("/jasper/financeiro/cheques.jasper");
		}
		else{
			jasper = getDiretorioReal("/jasper/protocolo/protocoloCheque.jasper");
			pathSubRelatorio = getDiretorioReal("/jasper/protocolo/cheques.jasper");
		}
		
		String nomeRelatorioProtocolo = listaProtocolo.get(0).getProtocolo();
        String path = getDiretorioReal("//relatorio/protocolo/"+nomeRelatorioProtocolo.concat(".pdf")+"");
		
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		
		try {
			// Parametros do relatorios
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("SUBREPORT_DIR", pathSubRelatorio);
			
			//$P{SUBREPORT_DIR} + "cheques.jasper"
			//new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource($F{listacheques})
			
			// Preenche a Lista
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaProtocolo);
			
			//Mando o jasper gerar o relatorio. Nesse caso passo o map
			JasperPrint impressao = JasperFillManager.fillReport(jasper, parametros, ds);
			
			// Grava o relatorio em disco em pdf	
			JasperManager.printReportToPdfFile(impressao, path);
			// Redireciona para o pdf gerado
			response.sendRedirect("/pais/relatorio/protocolo/"+nomeRelatorioProtocolo.concat(".pdf")+"");
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERRO!",e.getMessage()));
		}
	}
	
	@Override
	public void gerarProtocoloDinheiroCheque(List<Protocolo> listaProtocolo) {
		String jasper = "";
		String pathSubRelatorio = "";
		if(listaProtocolo.get(0).getDiscipulador().equals("FINANCEIRO")){
			jasper = getDiretorioReal("/jasper/financeiro/protocoloFinanceiroDinheiroCheque.jasper");
			pathSubRelatorio = getDiretorioReal("/jasper/financeiro/cheques.jasper");
		}
		else{
			jasper = getDiretorioReal("/jasper/protocolo/protocoloDinheiroCheque.jasper");
			pathSubRelatorio = getDiretorioReal("/jasper/protocolo/cheques.jasper");
		}
		
		String nomeRelatorioProtocolo = listaProtocolo.get(0).getProtocolo();
        String path = getDiretorioReal("//relatorio/protocolo/"+nomeRelatorioProtocolo.concat(".pdf")+"");
		
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		
		try {
			// Parametros do relatorios
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("SUBREPORT_DIR", pathSubRelatorio);
			
			//$P{SUBREPORT_DIR} + "cheques.jasper"
			//new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource($F{listacheques})
			
			// Preenche a Lista
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaProtocolo);
			
			//Mando o jasper gerar o relatorio. Nesse caso passo o map
			JasperPrint impressao = JasperFillManager.fillReport(jasper, parametros, ds);
			
			// Grava o relatorio em disco em pdf	
			JasperManager.printReportToPdfFile(impressao, path);
			// Redireciona para o pdf gerado
			response.sendRedirect("/pais/relatorio/protocolo/"+nomeRelatorioProtocolo.concat(".pdf")+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getDiretorioReal(String diretorio) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return session.getServletContext().getRealPath(diretorio);
	}

}
