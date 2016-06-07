package com.dev.blogs.model;

import java.util.HashSet;
import java.util.Set;

public class Warehouse {
	public static final String ALIAS_TABLE_NAME = "w";
	public static final String TABLE_NAME = "warehouses";
	public static final String ID_COLUMN = "id";
	public static final String ADDRESS_COLUMN = "address";
	
	private Long id;
    private String address;
    private Set<Item> items = new HashSet<Item>();
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getAddress() {
        return address;
    }
 
    public void setAddress(String address) {
        this.address = address;
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
		Warehouse that = (Warehouse) obj;
		if (!address.equals(that.address)) return false;
		return true;
	}
    
    @Override
    public String toString() {
    	return "Warehouse[id=" + this.id + ", address=" + this.address + "]";
    }
}