package com.example.interview.resource;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.example.interview.model.Cidade;
import com.example.interview.repository.CidadeRepository;

import io.quarkus.panache.common.Page;

@Path("/cidades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CidadeResource {

	@Inject
	private CidadeRepository repository;

	@GET
	public List<Cidade> findAll(@QueryParam("page") @DefaultValue("0") int page,
			@QueryParam("size") @DefaultValue("20") int size) {
		return repository.findAll().page(Page.of(page, size)).list();
	}

	@POST
	@Transactional
	public Response create(Cidade cidade, @Context UriInfo uriInfo) {
		repository.persist(cidade);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(cidade.getId().toString()).build()).build();
	}

	@GET
	@Path("/nome/{name}")
	public List<Cidade> findByName(@PathParam("name") String name, @QueryParam("page") @DefaultValue("0") int page,
			@QueryParam("size") @DefaultValue("20") int size) {
		return repository.findByNome(name, page, size);
	}

	@GET
	@Path("/estado/{estado}")
	public List<Cidade> findByEstate(@PathParam("estado") String estado,
			@QueryParam("page") @DefaultValue("0") int page, @QueryParam("size") @DefaultValue("20") int size) {
		return repository.findByEstado(estado, page, size);
	}

}
