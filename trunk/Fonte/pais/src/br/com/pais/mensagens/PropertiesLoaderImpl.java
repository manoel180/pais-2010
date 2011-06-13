package br.com.pais.mensagens;

public class PropertiesLoaderImpl {  
    
    private static PropertiesLoader loader = new PropertiesLoader();  
      
    public static String getValor(String chave){  
            return loader.getValor(chave);  
    }  
} 
