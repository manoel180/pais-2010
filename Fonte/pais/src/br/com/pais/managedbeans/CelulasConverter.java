/*
 * Copyright 2009 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import br.com.pais.dao.impl.CelulaDaoImp;
import br.com.pais.entities.Celulas;

public class CelulasConverter implements Converter {

    private static List<Celulas> celulasDB;

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
    	celulasDB = new ArrayList<Celulas>();
    	celulasDB.addAll(new CelulaDaoImp().todos());
    	
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);

                for (Celulas c : celulasDB) {
                    if (c.getCelCod()== number) {
                        return c;
                    }
                }
                
            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Célula não é válido"));
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null) {
            return null;
        } else {
            return String.valueOf(((Celulas) value).getCelCod());
        }
    }
}
