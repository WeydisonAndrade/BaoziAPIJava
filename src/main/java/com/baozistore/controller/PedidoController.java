package com.baozistore.controller;

import com.baozistore.controller.dto.PedidoRequest;
import com.baozistore.controller.dto.PedidoResponse;
import com.baozistore.model.Cliente;
import com.baozistore.model.Pedido;
import com.baozistore.model.Produto;
import com.baozistore.repository.ClienteRepository;
import com.baozistore.repository.PedidoRepository;
import com.baozistore.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/** Expõe os endpoints REST de criação, consulta, listagem e exclusão de pedidos. */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse criar(@Valid @RequestBody PedidoRequest request) {
        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setProduto(produto);
        pedido.setQuantidade(request.quantidade());
        return PedidoResponse.from(pedidoRepository.save(pedido));
    }

    @GetMapping("/{id}")
    public PedidoResponse buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(PedidoResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    @GetMapping
    public List<PedidoResponse> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(PedidoResponse::from)
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado");
        }
        pedidoRepository.deleteById(id);
    }
}
