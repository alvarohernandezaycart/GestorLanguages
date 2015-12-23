package com.codinko.caching;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component("DAO")
public class Dao implements IDao{

	private Session currentSession;
	private Transaction currentTransaction;
	
	public Session openCurrentSession() {
		currentSession = getSessionFactory().openSession();
		return currentSession;
	}

	public Session openCurrentSessionwithTransaction() {
		currentSession = getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}
	
	public void closeCurrentSession() {
		currentSession.close();
	}
	
	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}
	
	private static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}
	@Cacheable(value = "handleGetObject")
	public MessageResource findByKey(String key, String locale) {
		System.out.println("entro para : " + "\nKey: " + key + "\nlolace: " + locale);
		openCurrentSessionwithTransaction();
		Criteria criteria = getCurrentSession().createCriteria(MessageResource.class);
		criteria.add(Restrictions.eq("code", key));
		criteria.add(Restrictions.eq("locale", locale));
		MessageResource messageResource = (MessageResource) criteria.list().get(0);
		closeCurrentSessionwithTransaction();
		return messageResource; 
	}

	@SuppressWarnings("unchecked")
	public Collection<String> findByLocale(String locale) {
		Criteria criteria = getCurrentSession().createCriteria(MessageResource.class);
		criteria.add(Restrictions.eq("locale", locale));
		Collection<String> messageResource = (Collection<String>) criteria.list().get(0);
		return messageResource; 
	}
	
	public List<MessageResource> findAllMessageResource() {
		@SuppressWarnings("unchecked")
		List<MessageResource> messageResources = (List<MessageResource>)  Dao.getSessionFactory().openSession().createQuery("from MessageResource").list();
		return messageResources;
	}
}