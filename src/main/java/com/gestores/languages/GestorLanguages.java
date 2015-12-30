package com.gestores.languages;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.gestor.caching.Dao;
import com.gestor.caching.MessageResource;

public class GestorLanguages {
    //Se utiliza para crear el archivo properties
	
	private Dao dao;
	private List<MessageResource> messageResources;
	private String rutaInicial;
	private String sufijo;
	 
    public GestorLanguages() {
		dao = new Dao();
		messageResources = dao.findAllMessageResource();
		rutaInicial="..\\Languages\\src\\main\\resources\\com\\translates\\";
		
	}

	public void createFileProperties(String language){
    	sufijo = getSufix(language,"_");
    	
    	String ruta=rutaInicial+"messages"+sufijo+language+".properties";

    	FileWriter archivoConfig = null;
        FileInputStream fileConfig =null;
        Properties propConfig =new Properties();
        try {
            //Se crea el archivo en disco
            archivoConfig = new FileWriter(ruta);
            //Se carga el archivo a un File para poder usar el método Load
            fileConfig =new FileInputStream(ruta);
            //Con el método load cargamos el objeto properties
            propConfig.load(fileConfig);
            //Ahora agregamos información al properties mediante un Hashmap
            propConfig.putAll(getConfigProperties(language));
            //Salvamos los datos en el properties y listo
            propConfig.store(new FileOutputStream(ruta),"Config");
            archivoConfig.close();
        } catch (IOException ex) {
            Logger.getLogger(GestorLanguages.class.getName()).log(Level.SEVERE, null, ex);
        }

	}

	public HashMap<String, String> getConfigProperties(String language){
    	
    	if(language.equals(""))
			 language="en";
    	
        HashMap<String, String> map= new HashMap<String, String>();

        for (MessageResource messageResource : messageResources) {
			if(messageResource.getLocale().equals(language))
				map.put(messageResource.getCode(), messageResource.getText());
		}
        
        return map;
    }
    
    public void createFileJson(String language){
    	 sufijo = getSufix(language,"-");
		 String ruta = rutaInicial+"locale"+sufijo+language+".json";		
		 try {
			 FileWriter file = new FileWriter(ruta);
			 file.write(getConfigJson(language).toString());
			 file.flush();
			 file.close();

		 } catch (IOException e) {
			e.printStackTrace();
		 }
	 }
    
	 public JSONObject getConfigJson(String language){
		 if(language.equals(""))
			 language="en";
		
		JSONObject obj = new JSONObject();

		List<String> id_project = new ArrayList<String>();
		String id_proj = messageResources.get(0).getProyect();
		id_project.add(messageResources.get(0).getProyect());
		for (MessageResource messageResource : messageResources) {
			if(!messageResource.getProyect().equals(id_proj)){
				id_proj = messageResource.getProyect();
				id_project.add(id_proj);
				}
		}
		
		for (String id : id_project) {
			JSONObject obj2 = new JSONObject();
			for (MessageResource messageResource : messageResources) {
				if((id.equals(messageResource.getProyect()))&&(language.equals(messageResource.getLocale()))){
					obj2.put(messageResource.getCode(), messageResource.getText());
				}					
			}
			obj.put(id,obj2);	
		}
       return obj;
	}
	 
	private String getSufix(String language, String suf) {
		if(language.equals(""))
			return "";
    	else
    		return suf;
	}
	
    public static void main(String[] args){
    	
    	GestorLanguages g = new GestorLanguages();

        g.createFileProperties("");
        g.createFileProperties("en");
        g.createFileProperties("es");
        g.createFileProperties("fr");
        
        g.createFileJson("");
        g.createFileJson("en");
        g.createFileJson("es");
        g.createFileJson("fr");
    }
}