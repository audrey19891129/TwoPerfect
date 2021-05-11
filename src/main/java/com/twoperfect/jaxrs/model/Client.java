package com.twoperfect.jaxrs.model;

import java.util.ArrayList;

public class Client {

	int id;
	public String lastname;
	public String firstname;
	public String phone;
	public String email;
	public ArrayList<Address>addresses;
	public ArrayList<Package>packages;
	public ArrayList<Ticket> tickets;
	
	
	public Client() {
		this.addresses = new ArrayList<Address>();
		this.packages = new ArrayList<Package>();
		this.tickets = new ArrayList<Ticket>();
	}
	
	public Client(int id, String lastname, String firstname, String phone, String email) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.phone = phone;
		this.email = email;
		this.addresses = new ArrayList<Address>();
		this.packages = new ArrayList<Package>();
		this.tickets = new ArrayList<Ticket>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(ArrayList<Address> addresses) {
		this.addresses = addresses;
	}
	

	public ArrayList<Package> getPackages() {
		return packages;
	}

	public void setPackages(ArrayList<Package> packages) {
		this.packages = packages;
	}

	public ArrayList<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + ", phone=" + phone
				+ ", email=" + email + ", addresses=" + addresses + ", packages=" + packages + ", tickets=" + tickets
				+ "]";
	}
}
