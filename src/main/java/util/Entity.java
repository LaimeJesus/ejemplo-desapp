package util;

import java.io.Serializable;

public class Entity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2070168481754732045L;
	private Integer id;
	
	
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer newId) {
		this.id = newId;
	}	
}
