package br.com.bergamin.restWs.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.bergamin.restWs.http.Cotacao;

@Path("/web")
public class AgnosController {

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/getDolar")
	public Cotacao GetDolar() {
		return HtmlRequestService.GetCotacao();
	}

}
