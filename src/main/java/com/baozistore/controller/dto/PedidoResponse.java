package com.baozistore.controller.dto;

import com.baozistore.model.Pedido;

/** Define os dados de pedido enviados pela API sem expor as entidades relacionadas. */
public record PedidoResponse(Long id, Long clienteId, Long produtoId, Integer quantidade) {

    public static PedidoResponse from(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getProduto().getId(),
                pedido.getQuantidade()
        );
    }
}
