package br.com.bergamin.restWs.controller;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BASIC_HEADER_PREFIX = "@ContentBasic ";
	private static final String SECURED_URL_PREFIX = "web";

	@Override
	public void filter(ContainerRequestContext request) throws IOException {

		if (request.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {

			List<String> authHeader = request.getHeaders().get(AUTHORIZATION_HEADER);
			if (authHeader != null && authHeader.size() > 0) {
				String authToken = authHeader.get(0);
				authToken = authToken.replaceFirst(BASIC_HEADER_PREFIX, "");
				String strDecoded = Base64.decodeAsString(authToken);
				org.apache.tomcat.util.codec.binary.Base64.decodeBase64(authToken);
				StringTokenizer tokenizer = new StringTokenizer(strDecoded, ":");

				String username = tokenizer.nextToken();
				String pass = tokenizer.nextToken();

				if ("bergTeste".equals(username) && "teste".equals(pass)) {
					return;
				}
			}
		}
		Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("Erro de autenticacao!")
				.build();
		request.abortWith(unauthorizedStatus);

	}

}
