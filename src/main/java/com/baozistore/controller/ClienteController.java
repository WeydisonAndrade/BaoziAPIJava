package com.baozistore.controller;

import com.baozistore.model.Cliente;
import com.baozistore.repository.ClienteRepository;
import com.baozistore.repository.PedidoRepository;
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

/** Expõe os endpoints REST de criação, consulta, listagem e exclusão de clientes. */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository repository;
    private final PedidoRepository pedidoRepository;

    public ClienteController(ClienteRepository repository, PedidoRepository pedidoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente criar(@Valid @RequestBody Cliente cliente) {
        cliente.setId(null);
        return repository.save(cliente);
    }

    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @GetMapping
    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
        if (pedidoRepository.existsByCliente_Id(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cliente possui pedidos e não pode ser excluído");
        }
        repository.deleteById(id);
    }
}
