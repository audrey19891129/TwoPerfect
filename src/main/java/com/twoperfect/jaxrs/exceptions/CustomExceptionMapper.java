package com.twoperfect.jaxrs.exceptions;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<CustomException> {
	public static String message;
	
	@Override
	public Response toResponse(CustomException exception) {
		return Response.serverError().entity(message).type(MediaType.APPLICATION_JSON).build();
	}

	
	public static void setMessage(String message) {
		CustomExceptionMapper.message = message;
	}

}
