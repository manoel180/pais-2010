package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.entities.Discipulos;

public class DiscipulosConverter implements Converter{
	
	private static List<Discipulos> discipulosDB;

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		discipulosDB = new ArrayList<Discipulos>();
		discipulosDB.addAll(new DiscipuloDaoImp().todos());
    	
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);

                for (Discipulos e : discipulosDB) {
                    if (e.getDisCod() == number) {
                        return e;
                    }
                }
                
            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Encontro não é válido"));
            }
        }

        return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		if (value == null) {
            return null;
        } else {
            return String.valueOf(((Discipulos) value).getDisCod());
        }
	}

}
