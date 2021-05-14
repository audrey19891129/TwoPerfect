package com.twoperfect.jaxrs.repository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import com.twoperfect.jaxrs.exceptions.CustomException;
import com.twoperfect.jaxrs.exceptions.CustomExceptionMapper;
import com.twoperfect.jaxrs.model.*;
import com.twoperfect.jaxrs.model.Package;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

class CumputedDates {
	   String startDate;
	   String endDate;
	   String today;
}

@Repository
public class DataRepository {
	
	public static SessionFactory factory;

	public static void openConnection(){
		try {
	         factory = new Configuration().configure().buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
	}
	
	public static void closeConnecion() {
		factory.close();
	}
	
	
	public static void setDates(CumputedDates cd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cd.today = sdf.format(cal.getTime());
	    cal.add(Calendar.DAY_OF_MONTH, -7);
	    cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
	    cal.clear(Calendar.MINUTE);
	    cal.clear(Calendar.SECOND);
	    cal.clear(Calendar.MILLISECOND);
	    cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
	    cd.startDate = sdf.format(cal.getTime());
	    cal.add(Calendar.WEEK_OF_YEAR, 5);
	    cal.add(Calendar.DAY_OF_MONTH, -1);
	    cd.endDate = sdf.format(cal.getTime());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Intervention> getNewInterventions(int id, ArrayList<Intervention> compare){
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String today = sdf.format(cal.getTime());
		ArrayList<Intervention> todaysInterventions = null;
		
		tx = session.beginTransaction();
		todaysInterventions = (ArrayList<Intervention>) session.createQuery("from Intervention where technicianId = :technicianId and date = :date")
        .setParameter("technicianId", id)
        .setParameter("date", today)
        .list();
        tx.commit();
        
        ArrayList<Intervention> newI = new ArrayList<Intervention>();
        boolean bool = false;

        for(Intervention I : todaysInterventions) {
        	for(Intervention i : compare) {
        		if(i.id == I.id) {
        			bool = true;
        		}	
        	}
        	if(!bool) {
        		newI.add(I);
        	}
        }
		return newI;
	}
	
	public Optional<Communication> getCommById(int id){
		Communication comm = null;
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    
	      try {
	         tx = session.beginTransaction();
	         comm = (Communication)session.get(Communication.class, id); 
	         tx.commit();
	         CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no communication with id["+id+"].\"}");
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return Optional.ofNullable(comm);
	}
	
	
public Optional<Integer> createCommunication(Communication comm) {
		
	 Integer i = null;
	 openConnection();
	 Session session = factory.openSession();
     Transaction tx = null;
     
     try {
        tx = session.beginTransaction();
        i = (Integer) session.save(comm); 
        tx.commit();
        CustomExceptionMapper.setMessage("{0}");
     } catch (HibernateException e) {
        if (tx!=null) tx.rollback();
        e.printStackTrace(); 
     } finally {
        session.close(); 
        closeConnecion();
     }
     return Optional.ofNullable(i);
	}
	
	
	
	//============================COORDINATES=============================
	
	public Optional<Coordinates> getCoordinates(int techId){
		Coordinates c = null;
		openConnection();
		 Session session = factory.openSession();
	     Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         c = (Coordinates)session.get(Coordinates.class, techId); 
	         tx.commit();
	         CustomExceptionMapper.setMessage("{\"ERROR\":\"there are no coordinates associated to technician with id = [ "+techId+" ] \"}");
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close();
	         closeConnecion();
	      }
		
	      return Optional.ofNullable(c);
	}
	
	public Optional<Coordinates> setCoordinates(Coordinates c){
		Coordinates C = null;
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
		      
		      try {
		         tx = session.beginTransaction();
		         int id = c.getTechId();
		         C = (Coordinates)session.get(Coordinates.class, id); 
		         C.setLatitude(c.getLatitude());
		         C.setLongitude(c.getLongitude());
				 session.update(C); 
		         tx.commit();
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		         closeConnecion();
		      }
		
	      return Optional.ofNullable(C);
	}
	
	//=========================EMPLOYEE====================================
	
	public Optional<Employee> getEmployeeById(int id){

		Employee emp = null;
		openConnection();
		 Session session = factory.openSession();
	     Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         emp = (Employee)session.get(Employee.class, id); 
	         tx.commit();
	         CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no employee with id = [ "+id+" ] \"}");
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close();
	         closeConnecion();
	      }
		
	      return Optional.ofNullable(emp);
	}

	@SuppressWarnings("unchecked")
	public Optional<Employee> getEmployeeLogin(String username, String password) {
		 Employee emp = null;
		 openConnection();
		 Session session = factory.openSession();
	     Transaction tx = null;

	      try {
	         tx = session.beginTransaction();
	         emp = (Employee)session.createQuery("from Employee where username = :username AND password = :password")
	         .setParameter("username", username)
	         .setParameter("password", password)
	         .uniqueResult();
	         tx.commit();
	         CustomExceptionMapper.setMessage("{\"ERROR\":\"Check your username AND password.\"}");
	         
	         if(emp != null && emp.title.contentEquals("technicien")) {
	        	 
    		    CumputedDates cd = new CumputedDates();
			    setDates(cd);
	        	 
			    tx = session.beginTransaction();
				List<Availability> availabilities = (List<Availability>)session.createQuery("from Availability where ( startDate <= :date and endDate >= :date ) AND employeeId = :employeeId ")
	        			 .setParameter("date", cd.today)
	        			 .setParameter("employeeId", emp.getId())
	        			 .list();
	        	         tx.commit();
	        	         emp.setAvailability((ArrayList<Availability>)availabilities);
		         
    	         tx = session.beginTransaction();
		         List<Holiday>holidays = session.createQuery("from Holiday where startDate >= :startDate and startDate <= :endDate and employeeId= :employeeId")
		    			 .setParameter("employeeId", emp.getId())
		    			 .setParameter("startDate", cd.startDate)
		    			 .setParameter("endDate", cd.endDate)
		    			 .list();
		    	         tx.commit(); 
		    	         emp.setHolidays((ArrayList<Holiday>)holidays);
		    	         
		    	         
    	         SQLite sqlite = new SQLite();
    	         
			         tx = session.beginTransaction();
					 List<Description> descriptions = session.createQuery("from Description").list();
			         tx.commit();
			         sqlite.setDescriptions((ArrayList<Description>)descriptions);
    	         

		 	         tx = session.beginTransaction();
		 			 List<Intervention> interventions = session.createQuery("from Intervention where technicianId = :technicianId AND ( date >= :startDate AND date <= :endDate )")
		 	         .setParameter("technicianId", emp.getId())
		 	         .setParameter("startDate", cd.startDate)
		 	         .setParameter("endDate", cd.endDate)
		 	         .list();
		 	         tx.commit();
		 	         sqlite.setInterventions((ArrayList<Intervention>) interventions);
		 	         
		 	         ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		 	         for(Intervention i : interventions) {
		 	        	tx = session.beginTransaction();
		 		        Ticket ticket = (Ticket)session.get(Ticket.class, i.ticketId); 
		 		        tx.commit();
		 		        CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no ticket with id["+i.ticketId+"].\"}");
		 		        tickets.add(ticket);
		 	         }
		 	         sqlite.setTickets(tickets);
		 	         
		 	         ArrayList<Address> addresses = new ArrayList<Address>();
		 	         ArrayList<Client> clients = new ArrayList<Client>();
		 	         ArrayList<Integer> A = new ArrayList<>();
		 	         ArrayList<Integer> C = new ArrayList<>();
		 	         
		 	         // Have Fun Audrey
//		 	         HashMap<Integer, Address> intAdd = new HashMap<>();
//		 	         ArrayList<Address> test = (ArrayList<Address>) intAdd.values();
		 	        
		 	         for(Ticket t : tickets) {
		 	        	
		 	        	if(!A.contains(t.addressId)) { 
			 	        	tx = session.beginTransaction();
					        Address address = (Address)session.get(Address.class, t.addressId); 
					        tx.commit();
					        CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no address with id["+t.addressId+"].\"}");
					        addresses.add(address);
					        A.add(t.addressId);
		 	        	}
		 	        	
		 	        	if(!C.contains(t.clientId)) {
		 	        		tx = session.beginTransaction();
		 			        Client client = (Client)session.get(Client.class, t.clientId); 
		 			        tx.commit();
		 			        CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no client with id [ "+t.clientId+" ]\".}");
		 			        clients.add(client);
		 			        C.add(t.clientId);
		 	        	}
		 	         }
		 	         sqlite.setAddresses(addresses);
		 	         sqlite.setClients(clients);
		 	         
    	         emp.setSqlite(sqlite);
	         }
	         
	         
	      } catch (HibernateException e) {
	         if (tx!=null)tx.rollback();
        	 e.printStackTrace();
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return Optional.ofNullable(emp);
	}

	public Collection<Employee> getEmployeesByStatus(String status) {
		openConnection();
		 Session session = factory.openSession();
	     Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
			 @SuppressWarnings("unchecked")
			 List<Employee> employees = session.createQuery("from Employee where status = :status")
	         .setParameter("status", status)
	         .list();
	         tx.commit();
	         return employees;
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close();
	         closeConnecion();
	      }
	      return null;
	}

	
	public Collection<Employee> getEmployees() {
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    
	      try {
	         tx = session.beginTransaction();
	         @SuppressWarnings("unchecked")
			List<Employee>  employees = session.createQuery("FROM Employee where title = :title")
			.setParameter("title", "technicien")
			.list(); 
	         tx.commit();
	         return employees;
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return null;
	}
	
	
	
	
	
		//=========================CLIENT====================================
	
	
	// CLIENT CONSTRUCTOR
	    @SuppressWarnings("unchecked")
		public Client clientConstructor(Client client) {

	    	Client cli = client;
			Session session = factory.openSession();
		    Transaction tx = null;
			if(cli == null) {return null;}
			else {
				openConnection();
			      try {
			    	  
			    	  //STEP 1 : ADDRESSES
			         tx = session.beginTransaction();
			         ArrayList<Address> addresses = (ArrayList<Address>) session.createQuery("from Address where clientId = :clientId")
			         .setParameter("clientId", client.getId())
			         .list();
			         tx.commit();
			         cli.setAddresses(addresses);
			         
			         //STEP 2 : PACKAGES
			         tx = session.beginTransaction();
			         ArrayList<Package> packages  = (ArrayList<Package>) session.createQuery("from Package where clientId = :clientId")
			         .setParameter("clientId",  client.getId())
			         .list();
			         tx.commit();
			         
			         //STEP 3 :  ENTRIES
			         for(Package pack: packages) {
							tx = session.beginTransaction();
							ArrayList<Entry> entries = (ArrayList<Entry>)session.createQuery("from Entry where packageId = :packageId")
					         .setParameter("packageId", pack.getId())
					         .list();
					         tx.commit();
							for(Entry entry: entries) {
								
								 //STEP 4 : PRODUCT
								 tx = session.beginTransaction();
								 Optional<Product> pr = Optional.ofNullable((Product)session.get(Product.class, entry.getProductId())); 
						         tx.commit();
						         CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no product with id["+entry.getProductId()+"].\"}");
								if(pr.isPresent()) {
									entry.setProduct(pr.get());
								}
							}
							pack.setEntries(entries);
						}
			         cli.setPackages(packages);
			         
			         //STEP 5 : TICKETS
			         tx = session.beginTransaction();
			         ArrayList<Ticket> tickets = (ArrayList<Ticket>) session.createQuery("from Ticket where clientId = :clientId order by creationDate DESC")
			         .setParameter("clientId", cli.getId())
			         .list();
			         tx.commit();
						for(Ticket ticket : tickets) {
							tx = session.beginTransaction();
							//STEP 6 : DESCRIPTION
							Optional<Description> dp = Optional.ofNullable((Description)session.get(Description.class, ticket.getDescriptionId())); 
					        tx.commit();
					        CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no description with id["+ticket.getDescriptionId()+"].\"}");
					        if(dp.isPresent())
					        	ticket.setDescription(dp.get());
					        
					        //STEP 7 : ADDRESS
							tx = session.beginTransaction();
							Optional<Address> ad = Optional.ofNullable((Address)session.get(Address.class, ticket.getAddressId())); 
					        tx.commit();
					        CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no address with id["+ticket.getAddressId()+"].\"}");
					        if(ad.isPresent())
								ticket.setAddress(ad.get());
					       
					        //STEP 8 : INTERVENTIONS
					        tx = session.beginTransaction();
							ArrayList<Intervention> interventions = (ArrayList<Intervention>) session.createQuery("from Intervention where ticketId = :ticketId order by date DESC")
							         .setParameter("ticketId", ticket.getId())
							         .list();
							         tx.commit();
							ticket.setInterventions(interventions);
						}
					cli.setTickets(tickets);
			      } catch (HibernateException e) {
				         if (tx!=null) tx.rollback();
				         e.printStackTrace(); 
				      } finally {
				         session.close();   
				         closeConnecion();
				      }
			}
			return cli;
		}

		public Optional<Client> getClientById(int id) {
			Client C = null;
			Client client = null;
			openConnection();
			 Session session = factory.openSession();
		     Transaction tx = null;
		      
		      try {
		         tx = session.beginTransaction();
		         client = (Client)session.get(Client.class, id); 
		         tx.commit();
		         CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no client with id [ "+id+" ]\".}");
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		         closeConnecion();
		         if(client != null)
		         C = clientConstructor(client);
		      }
			
		      return Optional.ofNullable(C);
		}
		
		public Optional<Client> getClientForTechnician(int id) {
			Client client = null;
			openConnection();
			 Session session = factory.openSession();
		     Transaction tx = null;
		      
		      try {
		         tx = session.beginTransaction();
		         client = (Client)session.get(Client.class, id); 
		         tx.commit();
		         CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no client with id [ "+id+" ]\".}");
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		         closeConnecion();
		      }
			
		      return Optional.ofNullable(client);
		}


		public Optional<Client> getClientByPhone(String phone) {
			Client CLIENT = null;
			Client base = null;
			openConnection();
			 Session session = factory.openSession();
		     Transaction tx = null;
		      
		      try {
		         tx = session.beginTransaction();
		         base = (Client)session.createQuery("from Client where phone = :phone")
		         .setParameter("phone", phone)
		         .uniqueResult();
		         tx.commit();
		         CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no client with phone["+phone+"].\"}");
		         if(base != null)
		         CLIENT = clientConstructor(base);
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close();   
		         closeConnecion();
		      }
		      return Optional.ofNullable(CLIENT);
		}
		
		public Optional<Client> getClientByEmail(String email) {
			Client CLIENT = null;
			Client base = null;
			openConnection();
			 Session session = factory.openSession();
		     Transaction tx = null;
		      
		      try {
		         tx = session.beginTransaction();
		         base = (Client)session.createQuery("from Client where email = :email")
		         .setParameter("email", email)
		         .uniqueResult();
		         tx.commit();
		         CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no client with email [ "+email+" ].\"}");
		         if(base != null)
		         CLIENT = clientConstructor(base);
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		         closeConnecion();
		      }
		      return Optional.ofNullable(CLIENT);
		}
		
		//=========================ADDRESS====================================
		
		public Collection<Address> getAddressesByClientId(int id) {
			 openConnection();
			 Session session = factory.openSession();
		     Transaction tx = null;
		      
		      try {
		         tx = session.beginTransaction();
				 @SuppressWarnings("unchecked")
				 List<Address>  addresses = session.createQuery("from Address where clientId = :clientId")
		         .setParameter("clientId", id)
		         .list();
		         tx.commit();
		         return addresses;
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		         closeConnecion();
		      }
		      return null;
			
		}


		public Optional<Address> getAddressById(int id) {
		        Address add = null;
				openConnection();
				 Session session = factory.openSession();
			     Transaction tx = null;
			      
			      try {
			         tx = session.beginTransaction();
			         add = (Address)session.get(Address.class, id); 
			         tx.commit();
			         CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no address with id["+id+"].\"}");
			      } catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			      } finally {
			         session.close(); 
			         closeConnecion();
			      }
				
			      return Optional.ofNullable(add);
		}


		public Optional<Integer> createAddress(Address address) {
			  Integer addressId = null;
			  openConnection();
			  Session session = factory.openSession();
		      Transaction tx = null;
		      
		      try {
		         tx = session.beginTransaction();
		         addressId = (Integer) session.save(address); 
		         tx.commit();
		         CustomExceptionMapper.setMessage("{0}");
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		         closeConnecion();
		      }
		      return Optional.ofNullable(addressId);
		}
		
		
		
		//=========================PACKAGE====================================
		
		public Collection<Package> getPackagesByClientId(int id) {
			openConnection();
			Session session = factory.openSession();
		    Transaction tx = null;
		    
		      try {
		         tx = session.beginTransaction();
		         @SuppressWarnings("unchecked")
				 List<Package> packages = session.createQuery("from Package where clientId = :clientId")
		         .setParameter("clientId", id)
		         .list();
		         tx.commit();
		         return packages;
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		         closeConnecion();
		      }
		      return null;
		}


		public Optional<Integer> createPackage(Package pack) {
			  Integer packageId = null;
			  openConnection();
			  Session session = factory.openSession();
		      Transaction tx = null;
		      
		      try {
		         tx = session.beginTransaction();
		         packageId = (Integer) session.save(pack); 
		         tx.commit();
		         CustomExceptionMapper.setMessage("{\"id\":\"0\"}");
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close();  
		         closeConnecion();
		      }
		      return Optional.ofNullable(packageId);
		}


		public Optional<Package> modifyPackage(Package pack) {
			Session session = factory.openSession();
		    Transaction tx = null;
		    Package P = null;
		      
		      try {
		         tx = session.beginTransaction();
		         int id = pack.getId();
		         P = (Package)session.get(Package.class, id); 
		         P.setAddressId(pack.getAddressId());
		         P.setEnd(pack.getEnd());
		         P.setStart(pack.getStart());
				 session.update(P); 
		         tx.commit();
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		         closeConnecion();
		      }
		      return Optional.ofNullable(P);
		}
		
		
		
		//=========================ENTRY====================================
		
		public Collection<Entry> getEntriesByPackageId(int id) {

			openConnection();
			Session session = factory.openSession();
		    Transaction tx = null;
		    
		      try {
		         tx = session.beginTransaction();
		         @SuppressWarnings("unchecked")
				 List<Entry> entries = session.createQuery("from Entry where packageId = :packageId")
		         .setParameter("packageId", id)
		         .list();
		         tx.commit();
		         return entries;
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		         closeConnecion();
		      }
		      return null;
		}


		public Optional<Entry> getEntryById(int id) {
			Entry entry = null;
			openConnection();
			Session session = factory.openSession();
		    Transaction tx = null;
		    
		      try {
		         tx = session.beginTransaction();
				 entry = (Entry)session.get(Entry.class, id); 
		         tx.commit();
		         CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no entry with id["+id+"].\"}");
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		         closeConnecion();
		      }
		      return Optional.ofNullable(entry);
		}


		public Optional<Integer> createEntry(Entry entry) {
			  Integer entryId = null;
			  openConnection();
			  Session session = factory.openSession();
		      Transaction tx = null;
		      
		      try {
		         tx = session.beginTransaction();
		         entryId = (Integer) session.save(entry); 
		         tx.commit();
		         CustomExceptionMapper.setMessage("{0}");
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		         closeConnecion();
		      }
		      return Optional.ofNullable(entryId);
		}


		public Optional<Entry> modifyEntry(Entry entry) {
			Session session = factory.openSession();
		    Transaction tx = null;
		    Entry E = null;
		      
		      try {
		         tx = session.beginTransaction();
		         int id = entry.getId();
		         E = (Entry)session.get(Entry.class, id); 
		         E.setQuantity(entry.getQuantity());
				 session.update(E); 
		         tx.commit();
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		         closeConnecion();
		      }
		      return Optional.ofNullable(E);
		}


		public Optional<Entry> deleteEntry(Entry entry) {
			Entry E = null;
			Session session = factory.openSession();
		    Transaction tx = null;
		      
		      try {
		         tx = session.beginTransaction();
		         E = (Entry)session.get(Entry.class, entry.getId()); 
		         session.delete(E); 
		         tx.commit();
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		         closeConnecion();
		      }
		      return Optional.ofNullable(E);
		}
		
		
		
		
		//=========================PRODUCT====================================
		
			public Collection<Product> getProducts() {
				openConnection();
				Session session = factory.openSession();
			    Transaction tx = null;
			    
			      try {
			         tx = session.beginTransaction();
			         @SuppressWarnings("unchecked")
					 List<Product>  products = session.createQuery("FROM Product").list(); 
			         tx.commit();
			         return products;
			      } catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			      } finally {
			         session.close(); 
			         closeConnecion();
			      }
			      return null;
			}


			public Optional<Product> getProductById(int id) {
				Product product = null;
				openConnection();
				Session session = factory.openSession();
			    Transaction tx = null;
			    
			      try {
			         tx = session.beginTransaction();
					 product = (Product)session.get(Product.class, id); 
			         tx.commit();
			         CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no product with id["+id+"].\"}");
			      } catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			      } finally {
			         session.close(); 
			         closeConnecion();
			      }
			      return Optional.ofNullable(product);
			}
		

			
		
		
	//AVAILABILITY
	
		public Collection<Availability> getAvailabilityByEmpId(int id) {
			openConnection();
			Session session = factory.openSession();
		    Transaction tx = null;
		    
		      try {
		         tx = session.beginTransaction();
		         @SuppressWarnings("unchecked")
				 List<Availability> avail = session.createQuery("from Availability where employeeId = :employeeId")
		         .setParameter("employeeId", id)
		         .list();
		         tx.commit();
		         return avail;
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		         closeConnecion();
		      }
		      return null;
		}

		
		public Collection<Availability> getAvailabilityByDay(String day) {
			openConnection();
			Session session = factory.openSession();
		    Transaction tx = null;
		    
		      try {
		         tx = session.beginTransaction();
		         @SuppressWarnings("unchecked")
				 List<Availability> avail = session.createQuery("from Availability where day = :day")
		         .setParameter("day", day)
		         .list();
		         tx.commit();
		         return avail;
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		         closeConnecion();
		      }
		      return null;
		}
	
	
	//TICKET
	
	public Optional<Ticket> getTicketById(int id) {
		Ticket ticket = null;
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    
	      try {
	         tx = session.beginTransaction();
	         ticket = (Ticket)session.get(Ticket.class, id); 
	         tx.commit();
	         CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no ticket with id["+id+"].\"}");
	         
	         if(ticket != null) {
		         tx = session.beginTransaction();
		         Address address = (Address)session.get(Address.class, ticket.addressId); 
		         tx.commit();
		         CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no address with id["+ticket.addressId+"].\"}");
		         ticket.setAddress(address);
	         }

	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return Optional.ofNullable(ticket);
	}

	
	public Collection<Ticket> getTicketsByClientId(int id) {
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    
	      try {
	         tx = session.beginTransaction();
	         @SuppressWarnings("unchecked")
			 List<Ticket> avail = session.createQuery("from Ticket where clientId = :clientId order by creationDate DESC")
	         .setParameter("clientId", id)
	         .list();
	         tx.commit();
	         return avail;
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return null;
	}
	
	public Collection<Ticket> getTicketsByStatus(String status) {
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    
	      try {
	         tx = session.beginTransaction();
	         @SuppressWarnings("unchecked")
			 List<Ticket> avail = session.createQuery("from Ticket where status = :status")
	         .setParameter("status", status)
	         .list();
	         tx.commit();
	         return avail;
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return null;
	}

	
	public Optional<Integer> createTicket(Ticket ticket) {
		  Integer ticketId = null;
		  openConnection();
		  Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         ticketId = (Integer) session.save(ticket); 
	         tx.commit();
	         CustomExceptionMapper.setMessage("{0}");
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return Optional.ofNullable(ticketId);
	}

	
	public Optional<Ticket> modifyTicket(Ticket ticket) {
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    Ticket T = null;
	      
	      try {
	         tx = session.beginTransaction();
	         int id = ticket.getId();
	         T = (Ticket)session.get(Ticket.class, id); 
	         T.setStatus(ticket.getStatus());
	         T.setClosingDate(ticket.getClosingDate());
			 session.update(T); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return Optional.ofNullable(T);
	}

	
	public Collection<Ticket> getAllTickets(){
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    
	      try {
	         tx = session.beginTransaction();
	         @SuppressWarnings("unchecked")
			 List<Ticket> list = session.createQuery("from Ticket order by creationDate DESC")
	         .list();
	         tx.commit();
	         return list;
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return null;
	}
	
	//INTERVENTION
	
	public Optional<Intervention> getInterventionById(int id) {
		Intervention intervention = null;
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    
	      try {
	         tx = session.beginTransaction();
	         intervention = (Intervention)session.get(Intervention.class, id); 
	         tx.commit();
	         CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no intervention with id["+id+"].\"}");
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return Optional.ofNullable(intervention);
	}

	
	public Collection<Intervention> getInterventionsByEmpId(int id) {
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    CumputedDates cd = new CumputedDates();
	    setDates(cd);

	    // where startDate <= :date and endDate >= :date and startTime <= :startTime and endTime >= :endTime and day=:day")
	      try {
	         tx = session.beginTransaction();
	         @SuppressWarnings("unchecked")
			 List<Intervention> inter = session.createQuery("from Intervention where technicianId = :technicianId AND ( date >= :startDate AND date <= :endDate )")
	         .setParameter("technicianId", id)
	         .setParameter("startDate", cd.startDate)
	         .setParameter("endDate", cd.endDate)
	         .list();
	         tx.commit();
	         return inter;
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return null;
	}
	
	public Collection<Intervention> getInterventionsByTicketId(int id){
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    
	      try {
	         tx = session.beginTransaction();
	         @SuppressWarnings("unchecked")
			 List<Intervention> inter = session.createQuery("from Intervention where ticketId = :ticketId order by punchOut DESC")
	         .setParameter("ticketId", id)
	         .list();
	         tx.commit();
	         return inter;
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return null;
	}
	
	public Collection<Intervention> getInterventionsByStatus(String status) {
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    
	      try {
	         tx = session.beginTransaction();
	         @SuppressWarnings("unchecked")
			 List<Intervention> inter = session.createQuery("from Intervention where status = :status")
	         .setParameter("status", status)
	         .list();
	         tx.commit();
	         return inter;
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return null;
	}

	public Optional<Employee> updateTechnician(Employee employee){
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    Employee E = null;
	      
	      try {
	         tx = session.beginTransaction();
	         int id = employee.getId();
	         E = (Employee)session.get(Employee.class, id); 
	         E.setStatus(employee.status);
			 session.update(E); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return Optional.ofNullable(E);
	}
	
	
	public Optional<Intervention> modifyIntervention(Intervention intervention) {
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    Intervention I = null;
	      
	      try {
	         tx = session.beginTransaction();
	         int id = intervention.getId();
	         I = (Intervention)session.get(Intervention.class, id); 
	         I.setComment(intervention.comment);
	         I.setPunchIn(intervention.punchIn);
	         I.setPunchOut(intervention.punchOut);
	         I.setStatus(intervention.status);
			 session.update(I); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return Optional.ofNullable(I);
	}

	
	public Optional<Intervention> deleteIntervention(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Optional<Integer> createIntervention(Intervention intervention) {
		 
		  System.out.println(intervention.toString());
		
		  Integer interventionId = null;
		  openConnection();
		  Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         interventionId = (Integer) session.save(intervention); 
	         tx.commit();
	         CustomExceptionMapper.setMessage("{0}");
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return Optional.ofNullable(interventionId);
	}
	
	
	//HOLIDAY
	
	public Collection<Holiday> getHolidaysByEmpId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Collection<Holiday> getHolidaysByDate(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	public Collection<Description> getDescriptions() {
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    
	      try {
	         tx = session.beginTransaction();
	         @SuppressWarnings("unchecked")
			 List<Description> inter = session.createQuery("from Description").list();
	         tx.commit();
	         return inter;
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	         //comment
	      }
	      return null;
	}

	public Optional<Description> getDescriptionById(int id) {
		Description description = null;
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    
	      try {
	         tx = session.beginTransaction();
	         description = (Description)session.get(Description.class, id); 
	         tx.commit();
	         CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no description with id["+id+"].\"}");
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return Optional.ofNullable(description);
	}
	
	//SEARCH AVAILABLE TECHNICIAN 
	
	/*
		    SELECT * FROM u915933203_twoperfect.Availability where 
			startDate <= "" and
			endDate >= "" and
			startTime <= "20:00:00" and
			endTime >= "21:00:00" and
			day="mercredi";
	 */
	
	
	
	@SuppressWarnings("unchecked")
	public Collection<Employee> getAvailableEmployees(String day, String date, String startTime, String endTime) {
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    ArrayList<Employee> employees = new ArrayList<Employee>();
	    ArrayList<Employee> firstSort = new ArrayList<Employee>();
	    ArrayList<Employee> finalList = new ArrayList<Employee>();
	    Boolean bool = false;
	    
	      try {
	         tx = session.beginTransaction();
	         
	         //STEP 1 : GET AVAIL
			 List<Availability> availabilities = session.createQuery("from Availability where startDate <= :date and endDate >= :date and startTime <= :startTime and endTime >= :endTime and day=:day")
			 .setParameter("date", date)
			 .setParameter("startTime", startTime)
			 .setParameter("endTime", endTime)
			 .setParameter("day", day)
			 .list();
	         tx.commit();
	         
	         //STEP 2 : GET LIST OF EMPLOYEES
	         for(Availability avail : availabilities) {
	        	 CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no employee with id = [ "+ avail.getEmployeeId() +" ] \"}");
	        	 System.out.println("get employees");
	        	 tx = session.beginTransaction();
		         Employee emp = (Employee)session.get(Employee.class, avail.getEmployeeId()); 
		         tx.commit();
		         employees.add(emp);
	         }

	         //STEP 3 : GET VACATIONS FITTING DATE 
	         for(Employee e : employees) {
	        	 
	        	 tx = session.beginTransaction();
		         List<Holiday>holidays = session.createQuery("from Holiday where startDate <= :date and endDate >= :date and employeeId= :employeeId")
		    			 .setParameter("employeeId", e.getId())
		    			 .setParameter("date", date)
		    			 .list();
		    	         tx.commit();
		    	         
    	         if(holidays.isEmpty())
    	        	 firstSort.add(e);
	         }   

    	   // STEP 4 : SORT INTERVENTIONS FOR SCHEDULED DATE FOR EACH TECH
	         for(Employee E : firstSort) {
    	         tx = session.beginTransaction();
    	         List<Intervention>interventions = session.createQuery("from Intervention where date = :date and technicianId= :technicianId")
		    			 .setParameter("technicianId", E.getId())
		    			 .setParameter("date", date)
		    			 .list();
		    	         tx.commit();
		    	 
		    	 if(interventions.isEmpty()) {
		    		 finalList.add(E);
		    		 System.out.println(E.getId() + " HAS NO PLANNED INTERVENTION FOR TODAY"); 
		    	 }
		    	 else {        
		    		 
	    	         for(int i = 0; i < interventions.size() && bool==false; i++) {
	    	        	 
	    	        	 Intervention next = interventions.get(i);
	    	        	 SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	    	        	 Date timeEndNext = format.parse(next.getClientAvailEnd());
	    	        	 Date timeStartNext = format.parse(next.getClientAvailStart());
    	        		 Date timeStartNew = format.parse(startTime);
    	        		 Date timeEndNew = format.parse(endTime);
    	        		 LocalTime NextEnd = LocalTime.parse(next.getClientAvailEnd());
    	        		 
    	        		 LocalTime NextPOut = LocalTime.parse(next.getPunchOut());
    	        		 
    	        		 LocalTime NextStart = LocalTime.parse(next.getClientAvailStart());
    	        		 LocalTime NewStart = LocalTime.parse(startTime);
    	        		 LocalTime NewEnd = LocalTime.parse(endTime);
    	        		 long difference = 0;
	    	        	 
    	        		 if(interventions.size() == 1 || ( (i > 0) && (i == interventions.size() -1)) ) {  // <======= There is only 1 intervention planned for today or intervention is last of the day
    	        			 
    	        			 System.out.println(E.getId() + " HAS 1 PLANNED INTERVENTION FOR TODAY OR INTERVENTION IS LAST FOR TODAY"); 
    	        			 
    	        			 if(NewStart.compareTo(NextEnd) > 0 || ( !(next.getPunchOut().contentEquals("00:00:00")) && NewStart.compareTo(NextPOut) > 0)) {  // <======= if start of new intervention is set after end of previous
 	    	        			
 	    	        			System.out.println("start of new intervention is set after end of previous");

 		    	        		 difference = timeStartNew.getTime() - timeEndNext.getTime();
 	    	        			
 		    	        		 if((difference / (30 * 60 * 1000) % 24) >= 1) { // <==== there is at least 30 minutes between end of next intervention and start of new
 		        					 finalList.add(E);
 		        					 bool = true;
 		        				 }
 	    	        		 }
    	        			 else {
    	        				 // check if there is room to place new intervention before [next]
    	        				 
    	        				 if(NewEnd.compareTo(NextStart) < 0) { // <========= new intervention ends before start of next
    	        					 
    	        					 System.out.println("new intervention ends before start of next");
    	        					 
    	        					 difference = timeStartNext.getTime() - timeEndNew.getTime();
    	 	    	        			
     		    	        		 if((difference / (30 * 60 * 1000) % 24) >= 1) { // <==== there is at least 30 minutes between end of next intervention and start of new
     		        					 finalList.add(E);
     		        					 bool = true;
     		        				 }
    	        				 }
    	        				 
    	        			 }
    	        		 }
    	        		 else { // <======== there are more than 1 intervention planned for today
    	        			 
    	        			 System.out.println(E.getId() + " HAS MORE THAN 1 PLANNED INTERVENTION FOR TODAY"); 
    	        			 
    	        			 if(i > 0) {  // <========== If intervention is not the first in the list
    	        			 
    	        				 System.out.println("intervention is not the first in the list"); 
    	        				
    	        				 Intervention previous = interventions.get(i - 1); 
		    	        		 
		    	        		 Date timeEndPrevious = format.parse(previous.getClientAvailEnd());
		    	        		 LocalTime PrevEnd = LocalTime.parse(previous.getClientAvailEnd());
		    	        		 LocalTime PrevPOut = LocalTime.parse(previous.getPunchOut());
		    	        		 difference = timeStartNext.getTime() - timeEndPrevious.getTime();
		    	        	
		    	        		 if((difference / (60 * 60 * 1000) % 24) >= 2) {  // <========== If there is at least 2 hours between end of previous intervention and start of next
		    	        			 
		    	        			 System.out.println("there is at least 3 hours between end of previous intervention and start of next");

		    	        			 if(((NewStart.compareTo(PrevEnd) >= 0) || ( !(previous.getPunchOut().contentEquals("00:00:00")) && NewStart.compareTo(PrevPOut) > 0)) && (NewEnd.compareTo(NextStart) <=0)) {
		    	        				
		    	        				 System.out.println("new intervention fits between previous and next");

		    	    	        		 long start = timeStartNew.getTime() - timeEndPrevious.getTime();
		    	    	        		 long end =  timeStartNext.getTime() - timeEndNew.getTime();
		    	        				 
		    	        				 
		    	        				 if(((start / (60 * 60 * 1000) % 24) >= 1 ) && (( end / (60 * 60 * 1000) % 24) >= 1 )) { // <==== there is at least 1 hour between (end of new intervention and start of next) and (start of new and end of previous)
		    	        					 finalList.add(E);
		    	        					 bool = true;
		    	        				 }
		    	        			 }
		    	        		 }
    	        			 }
    	        		 }
	    	         }
	    	         bool = false;
		    	 }
	         }
    	        		
	         System.out.println(finalList);
	         return finalList; 
	         
	      } catch (HibernateException | ParseException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return null;
	}
	
	
	
	
	
	
	/*
	@SuppressWarnings("unchecked")
	public Collection<Employee> getAvailableEmployees(String day, String date, String startTime, String endTime) {
		openConnection();
		Session session = factory.openSession();
	    Transaction tx = null;
	    ArrayList<Employee> employees = new ArrayList<Employee>();
	    ArrayList<Employee> firstSort = new ArrayList<Employee>();
	    ArrayList<Employee> finalList = new ArrayList<Employee>();
	    Boolean bool = false;
	    
	      try {
	         tx = session.beginTransaction();
	         
	         //STEP 1 : GET AVAIL
			 List<Availability> availabilities = session.createQuery("from Availability where startDate <= :date and endDate >= :date and startTime <= :startTime and endTime >= :endTime and day=:day")
			 .setParameter("date", date)
			 .setParameter("startTime", startTime)
			 .setParameter("endTime", endTime)
			 .setParameter("day", day)
			 .list();
	         tx.commit();
	         
	         //STEP 2 : GET LIST OF EMPLOYEES
	         for(Availability avail : availabilities) {
	        	 CustomExceptionMapper.setMessage("{\"ERROR\":\"there is no employee with id = [ "+ avail.getEmployeeId() +" ] \"}");
	        	 System.out.println("get employees");
	        	 tx = session.beginTransaction();
		         Employee emp = (Employee)session.get(Employee.class, avail.getEmployeeId()); 
		         tx.commit();
		         employees.add(emp);
	         }

	         //STEP 3 : GET VACATIONS FITTING DATE 
	         for(Employee e : employees) {
	        	 
	        	 tx = session.beginTransaction();
		         List<Holiday>holidays = session.createQuery("from Holiday where startDate <= :date and endDate >= :date and employeeId= :employeeId")
		    			 .setParameter("employeeId", e.getId())
		    			 .setParameter("date", date)
		    			 .list();
		    	         tx.commit();
		    	         
    	         if(holidays.isEmpty())
    	        	 firstSort.add(e);
	         }   

    	   // STEP 4 : SORT INTERVENTIONS FOR SCHEDULED DATE FOR EACH TECH
	         for(Employee E : firstSort) {
    	         tx = session.beginTransaction();
    	         List<Intervention>interventions = session.createQuery("from Intervention where date = :date and technicianId= :technicianId")
		    			 .setParameter("technicianId", E.getId())
		    			 .setParameter("date", date)
		    			 .list();
		    	         tx.commit();
		    	 
		    	 if(interventions.isEmpty()) {
		    		 finalList.add(E);
		    		 System.out.println(E.getId() + " HAS NO PLANNED INTERVENTION FOR TODAY"); 
		    	 }
		    	 else {        
		    		 
	    	         for(int i = 0; i < interventions.size() && bool==false; i++) {
	    	        	 
	    	        	 Intervention next = interventions.get(i);
	    	        	 SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	    	        	 Date timeEndNext = format.parse(next.getClientAvailEnd());
	    	        	 Date timeStartNext = format.parse(next.getClientAvailStart());
    	        		 Date timeStartNew = format.parse(startTime);
    	        		 Date timeEndNew = format.parse(endTime);
    	        		 LocalTime NextEnd = LocalTime.parse(next.getClientAvailEnd());
    	        		 LocalTime NextStart = LocalTime.parse(next.getClientAvailStart());
    	        		 LocalTime NewStart = LocalTime.parse(startTime);
    	        		 LocalTime NewEnd = LocalTime.parse(endTime);
    	        		 long difference = 0;
	    	        	 
    	        		 if(interventions.size() == 1 || ( (i > 0) && (i == interventions.size() -1)) ) {  // <======= There is only 1 intervention planned for today or intervention is last of the day
    	        			 
    	        			 System.out.println(E.getId() + " HAS 1 PLANNED INTERVENTION FOR TODAY OR INTERVENTION IS LAST FOR TODAY"); 
    	        			 
    	        			 if(NewStart.compareTo(NextEnd) > 0) {  // <======= if start of new intervention is set after end of previous
 	    	        			
 	    	        			System.out.println("start of new intervention is set after end of previous");

 		    	        		 difference = timeStartNew.getTime() - timeEndNext.getTime();
 	    	        			
 		    	        		 if((difference / (60 * 60 * 1000) % 24) >= 1) { // <==== there is at least 1 hour between end of next intervention and start of new
 		        					 finalList.add(E);
 		        					 bool = true;
 		        				 }
 	    	        		 }
    	        			 else {
    	        				 // check if there is room to place new intervention before [next]
    	        				 
    	        				 if(NewEnd.compareTo(NextStart) < 0) { // <========= new intervention ends before start of next
    	        					 
    	        					 System.out.println("new intervention ends before start of next");
    	        					 
    	        					 difference = timeStartNext.getTime() - timeEndNew.getTime();
    	 	    	        			
     		    	        		 if((difference / (60 * 60 * 1000) % 24) >= 1) { // <==== there is at least 1 hour between end of next intervention and start of new
     		        					 finalList.add(E);
     		        					 bool = true;
     		        				 }
    	        				 }
    	        				 
    	        			 }
    	        		 }
    	        		 else { // <======== there are more than 1 intervention planned for today
    	        			 
    	        			 System.out.println(E.getId() + " HAS MORE THAN 1 PLANNED INTERVENTION FOR TODAY"); 
    	        			 
    	        			 if(i > 0) {  // <========== If intervention is not the first in the list
    	        			 
    	        				 System.out.println("intervention is not the first in the list"); 
    	        				
    	        				 Intervention previous = interventions.get(i - 1); 
		    	        		 
		    	        		 Date timeEndPrevious = format.parse(previous.getClientAvailEnd());
		    	        		 LocalTime PrevEnd = LocalTime.parse(previous.getClientAvailEnd());
		    	        		 difference = timeStartNext.getTime() - timeEndPrevious.getTime();
		    	        	
		    	        		 if((difference / (60 * 60 * 1000) % 24) >= 3) {  // <========== If there is at least 3 hours between end of previous intervention and start of next
		    	        			 
		    	        			 System.out.println("there is at least 3 hours between end of previous intervention and start of next");

		    	        			 if((NewStart.compareTo(PrevEnd) >= 0) && (NewEnd.compareTo(NextStart) <=0)) {
		    	        				
		    	        				 System.out.println("new intervention fits between previous and next");

		    	    	        		 long start = timeStartNew.getTime() - timeEndPrevious.getTime();
		    	    	        		 long end =  timeStartNext.getTime() - timeEndNew.getTime();
		    	        				 
		    	        				 
		    	        				 if(((start / (60 * 60 * 1000) % 24) >= 1 ) && (( end / (60 * 60 * 1000) % 24) >= 1 )) { // <==== there is at least 1 hour between (end of new intervention and start of next) and (start of new and end of previous)
		    	        					 finalList.add(E);
		    	        					 bool = true;
		    	        				 }
		    	        			 }
		    	        		 }
    	        			 }
    	        		 }
	    	         }
	    	         bool = false;
		    	 }
	         }
    	        		
	         System.out.println(finalList);
	         return finalList; 
	         
	      } catch (HibernateException | ParseException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	         closeConnecion();
	      }
	      return null;
	}

	*/
	
	
	
}
