package com.dev.blogs.model;

import java.util.HashSet;
import java.util.Set;

public class Item {
	public static final String TABLE_NAME = "items";
	public static final String ID_COLUMN = "id";
	public static final String NAME_COLUMN = "name";
	public static final String WAREHOUSE_ID_COLUMN = "warehouse_id";
	
	private Long id;
    private String name;
    private Long warehouse_id;
    private Set<Provider> providers = new HashSet<Provider>();
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public Set<Provider> getProviders() {
        return providers;
    }
 
    public void setProviders(Set<Provider> providers) {
        this.providers = providers;
    }
    
    public Long getWarehouse_id() {
		return warehouse_id;
	}

	public void setWarehouse_id(Long warehouse_id) {
		this.warehouse_id = warehouse_id;
	}

	public String toString() {
    	return "Item[id=" + this.id + ", name=" + this.name + "]";
    }
}