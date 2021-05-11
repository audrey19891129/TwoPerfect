package HibernateTesting;
import java.util.List;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator; 
 
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.twoperfect.jaxrs.model.*;

class CumputedDates {
	   String startDate;
	   String endDate;
}

public class HibernateTest {
	
	private static SessionFactory factory; 
	
	 /* Method to CREATE an employee in the database */
	   public Integer addEmployee(Employee employee){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      Integer employeeID = null;
	      
	      try {
	         tx = session.beginTransaction();
	         //Employee employee = new Employee(0, fname, lname, "1", "2", "3", "4", "5");
	         employeeID = (Integer) session.save(employee); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return employeeID;
	   }
	   
	   /* Method to  READ all the employees */
	   public void listEmployees( ){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         List employees = session.createQuery("FROM Employee").list(); 
	         for (Iterator iterator = employees.iterator(); iterator.hasNext();){
	            Employee employee = (Employee) iterator.next(); 
	            System.out.print("First Name: " + employee.getFirstname()); 
	            System.out.print("  Last Name: " + employee.getLastname()); 
	         }
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	   }
	   
	   
	   public void updateEmployee(Integer EmployeeID, String lastname ){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         Employee employee = (Employee)session.get(Employee.class, EmployeeID); 
	         employee.setLastname(lastname);
			 session.update(employee); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	   }
	   
	   /* Method to DELETE an employee from the records */
	   public void deleteEmployee(Integer EmployeeID){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         Employee employee = (Employee)session.get(Employee.class, EmployeeID); 
	         session.delete(employee); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	   }
	
	
	   public static void setDates(CumputedDates cd) {
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		   Calendar cal = Calendar.getInstance();
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
	   
	   
	   
	   public static void main(String[] args) {
		   
		   
	      /*
	      try {
	         factory = new Configuration().configure().buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
	      
	      HibernateTest ME = new HibernateTest();

	      System.out.println("LIST OF EMPLOYEES: ");
	      ME.listEmployees();
	      System.out.println();
	      System.out.println();

	      
	      Employee employee = new Employee("Zara", "Ali", "phone", "email", "title", "username", "password");
	      Integer empID1 = ME.addEmployee(employee);
	      System.out.println("NEW EMPLOYEE: " + empID1);
	      
	      //ME.updateEmployee(6, "Gisele");
	      //ME.deleteEmployee(6);
	      
	      System.out.println("LIST OF EMPLOYEES: ");
	      ME.listEmployees();
	      System.out.println();
	      System.out.println();
	      
*/
		   
	   }

}


