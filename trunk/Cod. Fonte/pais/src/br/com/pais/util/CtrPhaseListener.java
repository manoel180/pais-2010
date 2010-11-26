package br.com.pais.util;

import java.io.IOException;

import javax.el.ELResolver;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;



public class CtrPhaseListener implements PhaseListener {
	private ApplicationSecurityManager usuarioSession = new ApplicationSecurityManager();
	   private  static final long  serialVersionUID = 1L;  
	       
	     public static final String LOGIN_PAGE = "/login.xhtml";  
	     public static final String LOGIN_PAGE_FACES = "/login.mir";  
	       
	    @Override  
	     public void afterPhase(PhaseEvent event) {  
	         FacesContext facesContext = event.getFacesContext();  
	           
	         // Verifica se é a pagina de login  
	         if ( facesContext.getViewRoot().getViewId().equals( LOGIN_PAGE ) ) {  
	             return;  
	         }  
	           
	         // Obtem uma referencia para o autenticador na sessão  
	         ELResolver elResolver = facesContext.getApplication().getELResolver();  
	       //  LoginHandler login =  (LoginHandler) elResolver.getValue( facesContext.getELContext(), null, "loginHandler" );  
	           
	         if ( usuarioSession.getDiscipulos() == null ) {  
	               	               
	             // Redireciona para o Login  
	             NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();  
	             navigationHandler.handleNavigation( facesContext, null, "login" );  
	               
	             // Renderiza a Pagina  
	             facesContext.renderResponse();  
	   
	         }   
	   
	     }  
	   
	    @Override  
	     public void beforePhase(PhaseEvent event) {  
	   
	         ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();   
	         HttpServletRequest request = (HttpServletRequest) context.getRequest();  
	           
	         if(!request.getRequestURL().toString().endsWith(LOGIN_PAGE_FACES) && context.getSession(false) == null){  
	           
	             try {  
	                 context.redirect(request.getContextPath() + LOGIN_PAGE_FACES);  
	             } catch (IOException e) {}  
	               
	         }   
	   
	     }  
	   
	     @Override  
	     public PhaseId getPhaseId() {  
	         return PhaseId.RESTORE_VIEW;  
	     }  
	   
	 }  