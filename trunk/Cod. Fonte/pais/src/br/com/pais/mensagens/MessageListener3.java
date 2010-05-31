package br.com.pais.mensagens;

 import java.util.Iterator;

  import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
  import javax.faces.component.UIViewRoot;
  import javax.faces.context.FacesContext;
  import javax.faces.event.PhaseEvent;
  import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

 
  
  public class MessageListener3 implements PhaseListener {

      public PhaseId getPhaseId() {
          return PhaseId.RENDER_RESPONSE;
      }

      public void beforePhase(PhaseEvent e) {
          FacesContext fc = e.getFacesContext();
          UIViewRoot root = fc.getViewRoot();
          Iterator i = fc.getClientIdsWithMessages();
          while (i.hasNext()) {
              String clientId = (String) i.next();
              UIComponent c = root.findComponent(clientId);
              String fieldRef = 
                  (String) c.getAttributes().get("fieldRef");
              if (fieldRef != null) {
                  Iterator j = fc.getMessages(clientId);
                  while (j.hasNext()) {
                      FacesMessage fm = (FacesMessage) j.next();
                      fm.setDetail(fieldRef + ": " + fm.getDetail());
                  }
              }
          }
      }

      public void afterPhase(PhaseEvent e) {
      }
  }
