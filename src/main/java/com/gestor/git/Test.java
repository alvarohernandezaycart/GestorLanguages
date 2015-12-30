package com.gestor.git;

import java.io.IOException;
import org.eclipse.jgit.api.errors.GitAPIException;
 
/**
 *
 * @author Mario Pérez Esteso
 *
 */
 
public class Test {
    
    public static void main(String[] args) throws IOException, GitAPIException {
    	String current = (new java.io.File( "." ).getCanonicalPath());
        String localPath = current+"/src/main/resources/com/translates";
        
        System.out.println(localPath);
        
        String remotePath = "https://github.com/alvarohernandezaycart/TraduccionesAura.git";
        GitControl gc = new GitControl(localPath, remotePath);

        gc.cloneRepo();

        gc.addToRepo1("");
        gc.addToRepo1("-fr");
        gc.addToRepo1("-es");
        gc.addToRepo1("-en");
        gc.addToRepo2("");
        gc.addToRepo2("_en");
        gc.addToRepo2("_es");
        gc.addToRepo2("_fr");
        gc.commitToRepo("Modified");

        gc.pushToRepo();
        //Pull
        gc.pullFromRepo();
    }
    
}
