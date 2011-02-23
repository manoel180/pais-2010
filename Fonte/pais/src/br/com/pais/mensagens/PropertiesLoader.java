/**
 * 
 */
package br.com.pais.mensagens;

/**
 * @author Cl� dos Souza
 *
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
  
public class PropertiesLoader {  
  
    private Properties props;  
    private String nomeDoProperties = "mensagens.properties";  
  
    protected PropertiesLoader(){  
            props = new Properties();  
            InputStream in = this.getClass().getResourceAsStream(nomeDoProperties);  
            try{  
                    props.load(in);  
                    in.close();  
            }  
            catch(IOException e){e.printStackTrace();}  
    }  
  
    protected String getValor(String chave){  
            return props.getProperty(chave);  
    }  
}  

