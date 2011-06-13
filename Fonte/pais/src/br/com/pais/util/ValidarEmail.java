package br.com.pais.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarEmail {
	public static boolean validarEmail(String email){ 
	      Pattern p = Pattern.compile(".+@.+\\.[a-z]+"); 
	      Matcher m = p.matcher(email);  
	  
	      //check whether match is found   
	      boolean matchFound = m.matches();  
	  
	      if (matchFound){
	    	  return true;
	      }else{
	    	  return false;
	      }
	}
}
