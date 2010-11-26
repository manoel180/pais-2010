/**
 * 
 */
package br.com.pais.util;

import java.text.MessageFormat;
import java.util.GregorianCalendar;

/**
 * @author Manoel
 * 
 */
public class Data_Hora {

	String hora = "";
	

	@SuppressWarnings("deprecation")
	public String getSaudacao() {
		java.util.Calendar data = GregorianCalendar.getInstance();
		String saudacao = "Boa Noite";
		if (data.getTime().getHours() >= 4 && data.getTime().getHours() <= 11) {
			saudacao = "Bom dia";
		} else {
			if (data.getTime().getHours() >= 12
					&& data.getTime().getHours() <= 17) {
				saudacao = "Boa tarde";
			}

		}

		return saudacao;
	}


	public String getDia() {

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(calendar.getTime());
		String[] diaSemana = new String[] { "", "Domingo", "Segunda-feira",
				"Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira",
				"Sábado" };
		return diaSemana[calendar.get(GregorianCalendar.DAY_OF_WEEK)];

	}


	@SuppressWarnings("deprecation")
	public String getHora() {
		//hora= "";
		java.util.Calendar data = GregorianCalendar.getInstance();
		return String.valueOf(MessageFormat.format("{0}:{1}:{2}", data.getTime().getHours(), data.getTime().getMinutes(), data.getTime().getSeconds()));

	}

	/**
	 * @param hora the hora to set
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}
}
