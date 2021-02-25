package com.example.interview.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.example.interview.model.Cidade;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;

@ApplicationScoped
public class CidadeRepository implements PanacheRepositoryBase<Cidade, Long> {

	public List<Cidade> findByNome(String nome, int pageIndex, int pageSize) {
		return find("nome", nome).page(Page.of(pageIndex, pageSize)).list();
	}

	public List<Cidade> findByEstado(String estado, int pageIndex, int pageSize) {
		return find("estado", estado).page(Page.of(pageIndex, pageSize)).list();
	}

}
