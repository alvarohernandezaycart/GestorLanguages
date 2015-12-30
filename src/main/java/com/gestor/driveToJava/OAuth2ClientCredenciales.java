/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.driveToJava;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * Esta clase almacena las credenciales y los objetos necesarios en la creacion del objeto GoogleCredential 
 * @author Katherine
 */
public class OAuth2ClientCredenciales {
    
    
    //Instancia de HttpTransport
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    //Instancia de Json Factory
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    //Ruta donde se encuentra almacenada la llave con formato .p12 que se genera en Developers Console en el momento de registrar un proyecto
    private static final String PATH_P12KEY_SERVICE_ACCOUNT = "src/main/java/com/gestor/driveToJava/My Project 1-9d42d5bd381e.p12";
    
    //Cuenta de email que se genera en Developers Console en el momento de registrar un proyecto
    private static final String SERVICE_ACCOUNT_EMAIL = "ah-531@arched-keyword-117508.iam.gserviceaccount.com";
    
    //Nombre de la aplicación tal como está registrada en Developers Console
    private static final String APPLICATION_NAME = "My Project";


    public static HttpTransport getHTTP_TRANSPORT() {
        return HTTP_TRANSPORT;
    }

    public static JsonFactory getJSON_FACTORY() {
        return JSON_FACTORY;
    }

    public static String getPATH_P12KEY_SERVICE_ACCOUNT() {
        return PATH_P12KEY_SERVICE_ACCOUNT;
    }

    public static String getSERVICE_ACCOUNT_EMAIL() {
        return SERVICE_ACCOUNT_EMAIL;
    }

    public static String getAPPLICATION_NAME() {
        return APPLICATION_NAME;
    }
    
}
