package com.codinko.caching;
 
import java.util.Collection;
 
public interface IDao {
	
	public MessageResource findByKey(String key, String locale);

	public Collection<String> findByLocale(String locale);
}