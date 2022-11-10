package br.com.receitasculinarias.controller;

import br.com.receitasculinarias.domain.dto.RecipeCreateRequest;
import br.com.receitasculinarias.domain.dto.RecipeResponse;
import br.com.receitasculinarias.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService service;

    @GetMapping
    public ResponseEntity<List<RecipeResponse>> listAll () {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RecipeResponse> findById (@PathVariable Integer id) {
        var recipe = service.findById(id);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<RecipeResponse>> findAllByName (@PathVariable String name) {
        var recipe = service.findAllByName(name);
        return ResponseEntity.ok(recipe);
    }

    @PostMapping(value = "receitas")
    public ResponseEntity<List<RecipeResponse>> create (@RequestBody @Valid List<RecipeCreateRequest> createRequest, UriComponentsBuilder uriBuilder) {
        var newReceita = service.create(createRequest);

        return ResponseEntity.ok(newReceita);
    }

    @PostMapping(value = "receita")
    public ResponseEntity<RecipeResponse> create (@RequestBody @Valid RecipeCreateRequest createRequest, UriComponentsBuilder uriBuilder) {
        var newReceita = service.create(createRequest);
        URI uri = uriBuilder.path("/receitas/{id}").buildAndExpand(newReceita.getId()).toUri();

        return ResponseEntity.created(uri).body(newReceita);
    }
}
