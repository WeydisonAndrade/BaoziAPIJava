package com.baozistore.controller;

import com.baozistore.model.Produto;
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

/** Expõe os endpoints REST de criação, consulta, listagem e exclusão de produtos. */
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;
    private final PedidoRepository pedidoRepository;

    public ProdutoController(ProdutoRepository repository, PedidoRepository pedidoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto criar(@Valid @RequestBody Produto produto) {
        produto.setId(null);
        return repository.save(produto);
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @GetMapping
    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
        if (pedidoRepository.existsByProduto_Id(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Produto possui pedidos e não pode ser excluído");
        }
        repository.deleteById(id);
    }
}
