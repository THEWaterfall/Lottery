package waterfall.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractDAO<PK extends Serializable, T> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Class<T> entityClass;
	
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void merge(T entity) {
		getSession().merge(entity);
	}
	
	public void save(T entity) {
		getSession().save(entity);
	}
	
	public void update(T entity) {
		getSession().update(entity);
	}
	
	public void remove(T entity) {
		getSession().remove(entity);
	}
	
	public T findById(PK id) {
		return getSession().get(entityClass, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll() {	
		Query<T> query = getSession().createQuery("FROM " + entityClass.getName());
		List<T> entityList = query.list();
		
		return entityList;
	}
}
