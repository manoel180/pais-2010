/**
 * 
 */
package br.com.pais.managedbeans;

import javax.faces.event.ActionEvent;

import org.primefaces.model.StreamedContent;

import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Estadocivil;
import br.com.pais.exception.ValidarCPFException;
import br.com.pais.exception.ValidarTEException;
import br.com.pais.util.ValidarCPF;
import br.com.pais.util.ValidarTituloEleitor;





/**
 * @author manoel
 */

public class DiscipuloBean {

	protected boolean editar = true; 
	
	protected Discipulos discipulos = new Discipulos();
	protected Estadocivil estadocivil= new Estadocivil();
	
	
	protected DiscipuloDao discipuloDao = new DiscipuloDaoImp();
	private StreamedContent image;  
	
	       
	   public void isCasado(){ 
		   if (estadocivil.getEstCod()== 2){
			   editar = false;
		   }else{
			   editar = true;
			   discipulos.setDisconjuge(null);
			   }
			   
	   }
	 
	   
	   
	public void validarcpf(ActionEvent ev){
		try {
			if(ValidarCPF.validarCPF(getDiscipulos().getDisCpf()) == false){		
				
				discipulos.setDisCpf(null);
			}
		} catch (ValidarCPFException e) {
			// TODO Auto-generated catch block
			discipulos.setDisCpf(null);	
		}
	}
	
	public void validartituloeleitor(ActionEvent ev){
		try {
			 int qtdCompleta = 0;
			 String zeros = "";
			   if (getDiscipulos().getDisTitEleitor().length()>0){
				   qtdCompleta = 12 - getDiscipulos().getDisTitEleitor().length();
				for (int i = 0; i < qtdCompleta; i++) {
					zeros += "0";
				}discipulos.setDisTitEleitor(zeros+getDiscipulos().getDisTitEleitor());
				if(ValidarTituloEleitor.validarTE(getDiscipulos().getDisTitEleitor()) == false){		
					discipulos.setDisTitEleitor(null);
				}
			}
		}
				catch (ValidarTEException e) {
					// TODO Auto-generated catch block
					discipulos.setDisTitEleitor(null);	
				}
		}
	
	public void salvar(){
		discipuloDao.salvar(discipulos);
		
	}
			
	
	
	/**
	 * GETTERS and SETTERS
	 */
	

	/**
	 * @return the discipulos
	 */
	public Discipulos getDiscipulos() {
		return discipulos;
	}

	/**
	 * @param discipulos the discipulos to set
	 */
	public void setDiscipulos(Discipulos discipulos) {
		this.discipulos = discipulos;
	}

	/**
	 * @return the image
	 */
	public StreamedContent getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(StreamedContent image) {
		this.image = image;
	}

	/**
	 * @return the editar
	 */
	public boolean isEditar() {
		return editar;
	}

	/**
	 * @param editar the editar to set
	 */
	public void setEditar(boolean editar) {
		this.editar = editar;
	}



	/**
	 * @return the estadocivil
	 */
	public Estadocivil getEstadocivil() {
		return estadocivil;
	}



	/**
	 * @param estadocivil the estadocivil to set
	 */
	public void setEstadocivil(Estadocivil estadocivil) {
		this.estadocivil = estadocivil;
	}

	
	}
