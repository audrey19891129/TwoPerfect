package com.twoperfect.jaxrs.model;

public class Product {

	public int id;
	public String type;
	public String subtype;
	public String description;
	
	public Product() {};
	
	public Product(int id, String type, String subtype, String description) {
		super();
		this.id = id;
		this.type = type;
		this.subtype = subtype;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", type=" + type + ", subtype=" + subtype + ", description=" + description + "]";
	}
	
	
}
