package br.com.pais.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import br.com.pais.dao.RelatorioDao;
import br.com.pais.relatorio.Protocolo;

@SuppressWarnings("deprecation")
public class RelatorioDaoImp implements RelatorioDao {
	
	@SuppressWarnings({ "static-access" })
	@Override
	public void gerarProtocoloDinheiro(List<Protocolo> listaProtocolo) {
		String jasper = getDiretorioReal("/jasper/protocoloDinheiro.jasper");
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
			JasperManager manager = new JasperManager();
			manager.printReportToPdfFile(impressao, path);
			// Redireciona para o pdf gerado
			response.sendRedirect("/pais/relatorio/protocolo/"+nomeRelatorioProtocolo.concat(".pdf")+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void gerarProtocoloCheque(List<Protocolo> listaProtocolo) {
		String jasper = getDiretorioReal("/jasper/protocoloCheque.jasper");
		String nomeRelatorioProtocolo = listaProtocolo.get(0).getProtocolo();
        String path = getDiretorioReal("//relatorio/protocolo/"+nomeRelatorioProtocolo.concat(".pdf")+"");
        String pathSubRelatorio = getDiretorioReal("/jasper/").concat("\\");
		
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
			JasperManager manager = new JasperManager();
			manager.printReportToPdfFile(impressao, path);
			// Redireciona para o pdf gerado
			response.sendRedirect("/pais/relatorio/protocolo/"+nomeRelatorioProtocolo.concat(".pdf")+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void gerarProtocoloDinheiroCheque(List<Protocolo> listaProtocolo) {
		String jasper = getDiretorioReal("/jasper/protocoloDinheiroCheque.jasper");
		String nomeRelatorioProtocolo = listaProtocolo.get(0).getProtocolo();
        String path = getDiretorioReal("//relatorio/protocolo/"+nomeRelatorioProtocolo.concat(".pdf")+"");
        String pathSubRelatorio = getDiretorioReal("/jasper/").concat("\\");
		
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
			JasperManager manager = new JasperManager();
			manager.printReportToPdfFile(impressao, path);
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
