package com.baozistore.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/** Define os dados recebidos pela API para cadastrar um pedido. */
public record PedidoRequest(
        @NotNull @Positive Long clienteId,
        @NotNull @Positive Long produtoId,
        @NotNull @Positive Integer quantidade
) {
}
