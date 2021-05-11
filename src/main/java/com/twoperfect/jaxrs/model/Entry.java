package com.twoperfect.jaxrs.model;

import java.util.ArrayList;

public class Entry {

	public int id;
	public int packageId;
	public int productId;
	public Product product;
	public int quantity;
	
	public Entry() {}

	

	public Entry(int id, int packageId, int productId, Product product, int quantity) {
		super();
		this.id = id;
		this.packageId = packageId;
		this.productId = productId;
		this.product = product;
		this.quantity = quantity;
	}



	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "Entry [id=" + id + ", packageId=" + packageId + ", productId=" + productId + ", product=" + product
				+ ", quantity=" + quantity + "]";
	}

	
	
	
}
