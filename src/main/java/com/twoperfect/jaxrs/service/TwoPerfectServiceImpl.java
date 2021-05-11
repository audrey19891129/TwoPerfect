package com.twoperfect.jaxrs.service;

import java.util.ArrayList;
import java.util.Collection;
import javax.ws.rs.core.Response;
import com.twoperfect.jaxrs.exceptions.CustomException;
import com.twoperfect.jaxrs.model.*;
import com.twoperfect.jaxrs.model.Package;
import com.twoperfect.jaxrs.repository.DataRepository;

public class TwoPerfectServiceImpl implements TwoPerfectService {

	private final DataRepository repository;
	
	public TwoPerfectServiceImpl(DataRepository repository) {
		this.repository = repository;
	}
	
	// CLIENT

	@Override
	public Response getClientById(int id) {
		Client client = repository.getClientById(id).orElseThrow(CustomException::new);
		return Response.ok(client).build();
	}
	
	@Override
	public Response getClientForTechnician(int id) {
		Client client = repository.getClientForTechnician(id).orElseThrow(CustomException::new);
		return Response.ok(client).build();
	}
	

	@Override
	public Response getClientByPhone(String phone) {
		Client client = repository.getClientByPhone(phone).orElseThrow(CustomException::new);
		return Response.ok(client).build();
	}
	
	@Override
	public Response getClientByEmail(String email) {
		Client client = repository.getClientByEmail(email).orElseThrow(CustomException::new);
		return Response.ok(client).build();
	}
	
	// ADDRESS

	@Override
	public Collection<Address> getAddressesByClientId(int id) {
		return repository.getAddressesByClientId(id);
	}

	@Override
	public Response getAddressById(int id) {
		Address address = repository.getAddressById(id).orElseThrow(CustomException::new);
		return Response.ok(address).build();
	}

	@Override
	public Response createAddress(Address address) {
		Integer a = repository.createAddress(address).orElseThrow(CustomException::new);
		return Response.ok(a).build();
	}
	
	// PACKAGE

	@Override
	public Collection<Package> getPackagesByClientId(int id) {
		return repository.getPackagesByClientId(id);
	}

	@Override
	public Response createPackage(Package pack) {
		Integer p = repository.createPackage(pack).orElseThrow(CustomException::new);
		return Response.ok(p).build();
	}

	@Override
	public Response modifyPackage(Package pack) {
		Package p = repository.modifyPackage(pack).orElseThrow(CustomException::new);
		return Response.ok(p).build();
	}
	
	// ENTRY

	@Override
	public Collection<Entry> getEntriesByPackageId(int id) {
		return repository.getEntriesByPackageId(id);
	}

	@Override
	public Response getEntryById(int id) {
		Entry entry = repository.getEntryById(id).orElseThrow(CustomException::new);
		return Response.ok(entry).build();
	}

	@Override
	public Response createEntry(Entry entry) {
		Integer i = repository.createEntry(entry).orElseThrow(CustomException::new);
		return Response.ok(i).build();
	}

	@Override
	public Response modifyEntry(Entry entry) {
		Entry e = repository.modifyEntry(entry).orElseThrow(CustomException::new);
		return Response.ok(e).build();
	}

	@Override
	public Response deleteEntry(Entry entry) {
		Entry e = repository.deleteEntry(entry).orElseThrow(CustomException::new);
		return Response.ok(e).build();
	}
	
	// PRODUCT

	@Override
	public Collection<Product> getProducts() {
		return repository.getProducts();
	}

	@Override
	public Response getProductById(int id) {
		Product product = repository.getProductById(id).orElseThrow(CustomException::new);
		return Response.ok(product).build();
	}
	
	// EMPLOYEE
	
	@Override
	public Collection<Intervention> getNewInterventions(int id, ArrayList<Intervention> compare) {
		return repository.getNewInterventions(id, compare);
	}

	@Override
	public Response getEmployeeById(int id) {
		Employee employe = repository.getEmployeeById(id).orElseThrow(CustomException::new);
		return Response.ok(employe).build();
	}

	@Override
	public Response getEmployeeLogin(String username, String password) {
		Employee employe = repository.getEmployeeLogin(username, password).orElseThrow(CustomException::new);
		return Response.ok(employe).build();
	}

	@Override
	public Collection<Employee> getEmployeesByStatus(String status) {
		return repository.getEmployeesByStatus(status);
	}

	@Override
	public Collection<Employee> getEmployees() {
		return repository.getEmployees();
	}
	
	
	@Override
	public Response updateTechnician(Employee employee) {
		Employee e = repository.updateTechnician(employee).orElseThrow(CustomException::new);
		return Response.ok(e).build();
	}
	
	// AVAILABILITY

	@Override
	public Collection<Availability> getAvailabilityByEmpId(int id) {
		return repository.getAvailabilityByEmpId(id);
	}

	@Override
	public Collection<Availability> getAvailabilityByDay(String day) {
		return repository.getAvailabilityByDay(day);
	}
	
	// HOLIDAY

	@Override
	public Collection<Holiday> getHolidaysByEmpId(int id) {
		return repository.getHolidaysByEmpId(id);
	}

	@Override
	public Collection<Holiday> getHolidaysByDate(String date) {
		return repository.getHolidaysByDate(date);
	}
	
	// TICKET

	@Override
	public Response getTicketById(int id) {
		Ticket ticket = repository.getTicketById(id).orElseThrow(CustomException::new);
		return Response.ok(ticket).build();
	}

	@Override
	public Collection<Ticket> getTicketsByClientId(int id) {
		return repository.getTicketsByClientId(id);
	}
	
	@Override
	public Collection<Ticket> getTicketsByStatus(String status) {
		return repository.getTicketsByStatus(status);
	}

	@Override
	public Response createTicket(Ticket ticket) {
		Integer p = repository.createTicket(ticket).orElseThrow(CustomException::new);
		return Response.ok(p).build();
	}

	@Override
	public Response modifyTicket(Ticket ticket) {
		Ticket T = repository.modifyTicket(ticket).orElseThrow(CustomException::new);
		return Response.ok(T).build();
	}
	
	// INTERVENTION

	@Override
	public Response getInterventionById(int id) {
		Intervention intervention = repository.getInterventionById(id).orElseThrow(CustomException::new);
		return Response.ok(intervention).build();
	}

	@Override
	public Collection<Intervention> getInterventionsByEmpId(int id) {
		return repository.getInterventionsByEmpId(id);
	}

	@Override
	public Collection<Intervention> getInterventionsByTicketId(int id) {
		return repository.getInterventionsByTicketId(id);
	}

	@Override
	public Collection<Intervention> getInterventionsByStatus(String status) {
		return repository.getInterventionsByStatus(status);
	}

	@Override
	public Response modifyIntervention(Intervention intervention) {
		Intervention I = repository.modifyIntervention(intervention).orElseThrow(CustomException::new);
		return Response.ok(I).build();
	}

	@Override
	public Response deleteIntervention(int id) {
		Intervention I = repository.deleteIntervention(id).orElseThrow(CustomException::new);
		return Response.ok(I).build();
	}
	
	@Override
	public Response createIntervention(Intervention intervention) {
		Integer a = repository.createIntervention(intervention).orElseThrow(CustomException::new);
		return Response.ok(a).build();
	}
	
	
	// DESCRIPTION

	@Override
	public Collection<Description> getDescriptions() {
		return repository.getDescriptions();
	}

	@Override
	public Response getDescriptionById(int id) {
		Description d = repository.getDescriptionById(id).orElseThrow(CustomException::new);
		return Response.ok(d).build();
	}

	@Override
	public Collection<Employee> getAvailableEmployees(String day, String date, String startTime, String endTime) {
		return repository.getAvailableEmployees(day, date, startTime, endTime);
	}

	@Override
	public Response getCommById(int id) {
		Communication c = repository.getCommById(id).orElseThrow(CustomException::new);
		return Response.ok(c).build();
	}

	@Override
	public Response getCoordinates(int techId) {
		Coordinates c = repository.getCoordinates(techId).orElseThrow(CustomException::new);
		return Response.ok(c).build();
	}

	@Override
	public Response setCoordinates(Coordinates c) {
		Coordinates C = repository.setCoordinates(c).orElseThrow(CustomException::new);
		return Response.ok(C).build();
	}

	@Override
	public Response createCommunication(Communication comm) {
		Integer r = repository.createCommunication(comm).orElseThrow(CustomException::new);
		return Response.ok(r).build();
	}

	

}
