package br.com.pais.relatorio.imp;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.StateManager;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.relatorio.RelatorioDao;

import com.mysql.jdbc.Connection;

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

	@Override
	public void gerarRelatorioGeracoes(int Discipulador) {
		String jasper = "";
		String pathSubRelatorio = "";
		jasper = getDiretorioReal("/jasper/geracoes/geracoesm12.jasper");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("logo", getDiretorioReal("/img/logoRelatorio.png"));
		params.put("discipulador", Discipulador);

		try {
			gerarRelatorioPDF(jasper, params);
		} catch (Exception e) {

			System.err.println(e.getMessage());
		}
		
	}
	
	private static Connection getConnection() throws ClassNotFoundException, SQLException {  
        String driver = "com.mysql.jdbc.Driver";  
        String url = "jdbc:mysql://pais12.com/wwwpais_sistema";  
        String user = "wwwpais_sistema";  
        String password = "06112218";  
        Class.forName(driver);  
        Connection con = (Connection) DriverManager.getConnection(url, user, password);  
        return con;  
    }
	
	private void gerarRelatorioPDF(String nome, Map params) {

		
		try {

			 
			byte[] pdf = JasperRunManager.runReportToPdf(nome,
					params,getConnection());

			//Captura uma instancia da página
			FacesContext faces = FacesContext.getCurrentInstance();
			//Captura a respostas do contexto e set os tipo de retorno. 
			HttpServletResponse response = (HttpServletResponse) faces
					.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setContentLength(pdf.length);
			response.setHeader("Content-disposition", "inline");

			response.setHeader("Cache-Control", "cache, must-revalidate");
			response.setHeader("Pragma", "public");
			ServletOutputStream out = response.getOutputStream();

			out.write(pdf);
			//
			StateManager stateManager = (StateManager) faces.getApplication()
					.getStateManager();
			stateManager.saveSerializedView(faces);
			faces.responseComplete();
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new FacesException(e);
		}
	}

}
