package com.baozistore.repository;

import com.baozistore.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

/** Persiste pedidos e consulta se cliente ou produto estão associados a eles. */
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    boolean existsByCliente_Id(Long clienteId);

    boolean existsByProduto_Id(Long produtoId);
}
