package com.dev.blogs.model;

import java.util.HashSet;
import java.util.Set;

public class Provider {
	public static final String TABLE_NAME = "providers";
	
	public static final String ID_COLUMN = "id";
	public static final String NAME_COLUMN = "name";
	
	private Long id;
    private String name;
    private Set<Item> items = new HashSet<Item>();
 
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
 
    public Set<Item> getItems() {
        return items;
    }
 
    public void setItems(Set<Item> items) {
        this.items = items;
    }
    
    public void addItem(Item item) {
    	if (items == null) {
    		items = new HashSet<Item>();
    	}
    	items.add(item);
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Provider that = (Provider) obj;
		if (!name.equals(that.name)) return false;
		return true;
    }
    
    @Override
    public String toString() {
    	return "Provider[id=" + this.id + ", name=" + this.name + "]";
    }
}