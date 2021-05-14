package com.twoperfect.jaxrs.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import com.twoperfect.jaxrs.model.*;
import com.twoperfect.jaxrs.model.Package;

@Path("services")
@Service
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface TwoPerfectService {

	
	//CLIENT

	@Path("/client/{id}")
	@GET
	public Response getClientById(@PathParam("id")int id);
	
	@Path("/ClientForTechnician/{id}")
	@GET
	public Response getClientForTechnician(@PathParam("id")int id);
	
	
	@Path("/clientByPhone/{phone}")
	@GET
	public Response getClientByPhone(@PathParam("phone")String phone);
	
	@Path("/clientByEmail/{email}")
	@GET
	public Response getClientByEmail(@PathParam("email")String email);
	
	
	//ADDRESS
	
	@Path("/addressByClientId/{id}")
	@GET
	public Collection<Address> getAddressesByClientId(@PathParam("id")int id);
	
	@Path("/address/{id}")
	@GET
	public Response getAddressById(@PathParam("id")int id);
	
	@Path("/address")
	@POST
	public Response createAddress(Address address);
	
	
	//PACKAGE
	
	@Path("/package/{id}")
	@GET
	public Collection<Package> getPackagesByClientId(@PathParam("id")int id);
	
	@Path("/package")
	@POST
	public Response createPackage(Package pack);
	
	@Path("/package")
	@PUT
	public Response modifyPackage(Package pack);
	
	
	//ENTRY
	
	@Path("/entry/packageId/{id}")
	@GET
	public Collection<Entry> getEntriesByPackageId(@PathParam("id")int id);
	
	@Path("/entry/{id}")
	@GET
	public Response getEntryById(@PathParam("id")int id);
	
	@Path("/entry")
	@POST
	public Response createEntry(Entry entry);
	
	@Path("/entry")
	@PUT
	public Response modifyEntry(Entry entry);
	
	@Path("/entry")
	@DELETE
	public Response deleteEntry(Entry entry);
	
	
	//PRODUCT
	
	@Path("/product")
	@GET
	public Collection<Product> getProducts();
	
	@Path("/product/{id}")
	@GET
	public Response getProductById(@PathParam("id")int id);

	
	//EMPLOYEE
	
	@Path("/employee/{id}")
	@GET
	public Response getEmployeeById(@PathParam("id")int id);
	
	@Path("/employee/{username}/{password}")
	@GET
	public Response getEmployeeLogin(@PathParam("username")String username, @PathParam("password")String password);
	
	@Path("/employee/byStatus/{status}")
	@GET
	public Collection<Employee> getEmployeesByStatus(@PathParam("status")String status);
	
	@Path("/employee")
	@GET
	public  Collection<Employee> getEmployees();
	
	@Path("/employee")
	@PUT
	public  Response updateTechnician(Employee employee);
	
	
	//AVAILABILITY
	
	@Path("/availability/byEmployee/{id}")
	@GET
	public Collection<Availability> getAvailabilityByEmpId(@PathParam("id")int id);
	
	@Path("/availability/byDay/{day}")
	@GET
	public Collection<Availability> getAvailabilityByDay(@PathParam("day")String day);
	
	
	//HOLIDAY
	
	@Path("/holiday/byEmployee/{id}")
	@GET
	public Collection<Holiday> getHolidaysByEmpId(@PathParam("id")int id);
	
	@Path("/holiday/byDate/{date}")
	@GET
	public Collection<Holiday> getHolidaysByDate(@PathParam("date")String date);
	
	
	//TICKET
	
	@Path("/ticket/{id}")
	@GET
	public Response getTicketById(@PathParam("id")int id);
	
	@Path("/ticket/byClient/{id}")
	@GET
	public Collection<Ticket> getTicketsByClientId(@PathParam("id")int id);
	
	@Path("/ticket/byStatus/{status}")
	@GET
	public Collection<Ticket> getTicketsByStatus(@PathParam("status")String status);
	
	@Path("/ticket")
	@POST
	public Response createTicket(Ticket ticket);
	
	@Path("/ticket")
	@PUT
	public Response modifyTicket(Ticket ticket);
	
	@Path("/ticket")
	@GET
	public Collection<Ticket> getAllTickets();
	
	
	//INTERVENTION
	
	@Path("/intervention/{id}")
	@GET
	public Response getInterventionById(@PathParam("id")int id);
	
	@Path("/intervention/byEmployee/{id}")
	@GET
	public Collection<Intervention> getInterventionsByEmpId(@PathParam("id")int id);
	
	@Path("/intervention/byTicket/{id}")
	@GET
	public Collection<Intervention> getInterventionsByTicketId(@PathParam("id")int id);
	
	@Path("/intervention/byStatus/{status}")
	@GET
	public Collection<Intervention> getInterventionsByStatus(@PathParam("status")String status);
	
	@Path("/intervention")
	@POST
	public Response createIntervention(Intervention intervention);
	
	@Path("/intervention")
	@PUT
	public Response modifyIntervention(Intervention intervention);
	
	@Path("/intervention/{id}")
	@DELETE
	public Response deleteIntervention(@PathParam("id")int id);
	
	@Path("/NEW_interventions/{id}")
	@POST
	public Collection<Intervention> getNewInterventions(@PathParam("id")int id, ArrayList<Intervention> compare);

	
	//COORDINATES
	
	@Path("/coordinates/{techId}")
	@GET
	public Response getCoordinates(@PathParam("techId")int techId);
	
	@Path("/coordinates")
	@PUT
	public Response setCoordinates(Coordinates c);
	
	
	
	
	//DESCRIPTION
	
	@Path("/description")
	@GET
	public Collection<Description> getDescriptions();
	
	@Path("/description/{id}")
	@GET
	public Response getDescriptionById(@PathParam("id")int id);
	
	
	//TO-DO : COMMUNICATIONS
	
	@Path("/communication/{id}")
	@GET
	public Response getCommById(@PathParam("id")int id);
	
	@Path("/communication")
	@POST
	public Response createCommunication(Communication comm);
	
	
	// SEARCH EMPLOYEES BY AVAILABILITY
	@Path("/empSearch/{day}/{date}/{startTime}/{endTime}")
	@GET
	public Collection<Employee> getAvailableEmployees(@PathParam("day")String day, @PathParam("date")String date, @PathParam("startTime")String startTime, @PathParam("endTime")String endTime);

	
	
}
