package com.example.interview.resource;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.example.interview.model.Cliente;
import com.example.interview.repository.ClienteRepository;

import io.quarkus.panache.common.Page;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

	@Inject
	private ClienteRepository repository;

	@GET
	public List<Cliente> findAll(@QueryParam("page") @DefaultValue("0") int page,
			@QueryParam("size") @DefaultValue("20") int size) {
		return repository.findAll().page(Page.of(page, size)).list();
	}

	@GET
	@Path("/{id}")
	public Response getOne(@PathParam("id") Long id) {
		Optional<Cliente> opt = repository.findByIdOptional(id);
		if (opt.isPresent())
			return Response.ok(opt.get()).build();

		return Response.status(Status.NOT_FOUND).entity("Cliente Id " + id).build();

	}

	@POST
	@Transactional
	public Response create(Cliente cliente, @Context UriInfo uriInfo) {
		repository.persist(cliente);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(cliente.getId().toString()).build()).build();
	}

	@GET
	@Path("/nome/{nome}")
	public List<Cliente> findByName(@PathParam("nome") String nome, @QueryParam("page") @DefaultValue("0") int page,
			@QueryParam("size") @DefaultValue("20") int size) {
		return repository.findByNomeContains(nome, page,size);
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		repository.deleteById(id);
		return Response.ok().build();
	}

	@PUT
	@Transactional
	public Response updateName( Cliente cliente, @Context UriInfo uriInfo) {

		Optional<Cliente> optCliente = repository.findByIdOptional(cliente.getId());
		if (optCliente.isPresent()) {

			Cliente savedCliente = optCliente.get();
			savedCliente.setNome(cliente.getNome());
			repository.persist(savedCliente);

			return Response.created(uriInfo.getAbsolutePathBuilder().path(savedCliente.getId().toString()).build()).build();
		}

		return Response.status(Status.NOT_FOUND).entity("Cliente Id " + cliente.getId()).build();
	}

}
