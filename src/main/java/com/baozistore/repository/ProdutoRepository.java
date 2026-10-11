package com.baozistore.repository;

import com.baozistore.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

/** Fornece operações de persistência e consulta para produtos. */
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
