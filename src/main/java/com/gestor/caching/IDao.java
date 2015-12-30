package com.gestor.caching;
 
import java.util.Collection;
 
public interface IDao {
	
	public MessageResource findByKey(String key, String locale);

	public Collection<String> findByLocale(String locale);
	
	public void persistMessageResource(MessageResource entity);
}