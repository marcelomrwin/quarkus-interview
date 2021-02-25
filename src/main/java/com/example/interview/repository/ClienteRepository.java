package com.example.interview.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.example.interview.model.Cliente;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;

@ApplicationScoped
public class ClienteRepository implements PanacheRepositoryBase<Cliente, Long> {

	public List<Cliente> findByNomeContains(String nome, int pageIndex, int pageSize) {
		return find("lower(nome) like %lower(?1)%", nome).page(Page.of(pageIndex, pageSize)).list();
	}
}
