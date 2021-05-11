package com.example.demo;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import javax.ws.rs.core.Application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.twoperfect.jaxrs.model.*;
import com.twoperfect.jaxrs.repository.DataRepository;



@SpringBootTest(classes = Application.class)
class TwoPerfectApplicationTests {
	private final DataRepository data = new DataRepository();
	
	@Test
	void TestGetProductByID() {
		Optional<Product> pr = data.getProductById(1);
		assertThat(pr);
	}
	
	@Test
	void TestGetEmployeeByID() {
		Optional<Employee> pr = data.getEmployeeById(1);
		assertThat(pr);
	}
	
	@Test
	void TestGetDescriptionByID() {
		Optional<Description> dp = data.getDescriptionById(1);
		assertThat(dp);
	}
	
	@Test
	void TestGetTicketByID() {
		Optional<Ticket> tk = data.getTicketById(1);
		assertThat(tk);
	}
	
	@Test
	void TestGetClientForTechnician() {
		Optional<Client> client = data.getClientForTechnician(1);
		assertThat(client);
	}

}
