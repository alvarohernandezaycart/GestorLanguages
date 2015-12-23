package com.codinko.caching;


import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.cache.annotation.EnableCaching;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 

@ManagedBean(name="language")
@SessionScoped
public class DatabaseDrivenResourceBundle /*extends ResourceBundle*/implements Serializable {

	private static final String PREFIX_NOT_FOUND = "???";
	
	@Autowired
	private IDao dao;

	private ApplicationContext context1;

	public DatabaseDrivenResourceBundle() {
		context1 = new ClassPathXmlApplicationContext("spring-config.xml");
		dao = (IDao)context1.getBean("DAO");
	}

//	protected Object handleGetObject(String key) {
// 
//		MessageResource messageResource = dao.findByKey(key, FacesContext.getCurrentInstance()
//						.getViewRoot().getLocale().getLanguage());
//		messageResource = dao.findByKey(key, FacesContext.getCurrentInstance()
//				.getViewRoot().getLocale().getLanguage());
//		
//		if (messageResource != null) {
//			return messageResource.getText();
//		}
//		return new StringBuilder(PREFIX_NOT_FOUND).append(key).append(PREFIX_NOT_FOUND).toString();
//	}
//
//	public void changeLanguage(String language) {
//	        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(language));
//	}
//
//	public Enumeration<String> getKeys() {
//		return Collections.enumeration(dao.findByLocale(FacesContext.getCurrentInstance().getViewRoot()
//						.getLocale().getLanguage()));
//	}
	
	 public void changeLanguage(String language) {
	      //  locale = new Locale(language);
	        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(language));
	 }

}