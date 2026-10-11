package com.baozistore.repository;

import com.baozistore.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

/** Fornece operações de persistência e consulta para clientes. */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
