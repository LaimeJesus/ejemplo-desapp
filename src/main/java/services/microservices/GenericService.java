package services.microservices;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import repositories.generics.GenericRepository;

public class GenericService<T> implements Serializable {

    private static final long serialVersionUID = -6540963495078524186L;

    private GenericRepository<T> repository;

    public GenericRepository<T> getRepository() {
        return this.repository;
    }

    public void setRepository(final GenericRepository<T> repository) {
        this.repository = repository;
    }

    @Transactional
    public void delete(final T object) {
        this.getRepository().delete(object);
    }

    @Transactional(readOnly = true)
    public List<T> retriveAll() {
        return this.getRepository().findAll();
    }

    @Transactional
    public void save(final T object) {
        this.getRepository().save(object);
    }

    @Transactional
    public void update(final T object) {
        this.getRepository().update(object);
    }
    
    
    /**
     * 
     * @param id
     * @return El objeto con Id = id
     */
    @Transactional
    public T findById(Integer id) {
    	return this.getRepository().findById(id);
    }
    
    
    @Transactional
    public void deleteAll() {
    	this.getRepository().deleteAll();
    	
    }
    
    @Transactional
    public Integer count() {
    	return this.getRepository().count();
    }
    
    @Transactional
    public T getByExample(T example) {
    	List<T> possible = this.retriveAll();
    	for (T current : possible) {
    		if (current.equals(example)) {
    			return current;
    		}
    	}
    	return null;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}