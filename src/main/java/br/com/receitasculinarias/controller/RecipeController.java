package br.com.receitasculinarias.controller;

import br.com.receitasculinarias.domain.dto.RecipeCreateRequest;
import br.com.receitasculinarias.domain.dto.RecipeResponse;
import br.com.receitasculinarias.domain.dto.RecipeUpdateRequest;
import br.com.receitasculinarias.service.RecipeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService service;
    private final static String Id = "/id/{id}";
    private final static String Name = "/name/{name}";
   @ApiImplicitParams({
           @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query"),
           @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query"),
           @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query")
   })
    @GetMapping
    public ResponseEntity<List<RecipeResponse>> listAll (@PageableDefault @ApiIgnore Pageable pageable) {
        List<RecipeResponse> recipes = service.listAll(pageable);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping(Id)
    public ResponseEntity<RecipeResponse> findById (@PathVariable Integer id) {
        RecipeResponse recipe = service.findById(id);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping(Name)
    public ResponseEntity<List<RecipeResponse>> findAllByName (@PathVariable String name) {
        List<RecipeResponse> recipes = service.findAllByName(name);
        return ResponseEntity.ok(recipes);
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> create (@RequestBody @Valid RecipeCreateRequest createRequest, UriComponentsBuilder uriBuilder) {
        RecipeResponse newRecipe = service.create(createRequest);
        URI uri = uriBuilder.path("/receitas/{id}").buildAndExpand(newRecipe.getId()).toUri();

        return ResponseEntity.created(uri).body(newRecipe);
    }
    @PutMapping(Id)
    public ResponseEntity<RecipeResponse> update (@PathVariable Integer id, @RequestBody @Valid RecipeUpdateRequest updateRequest) {
        RecipeResponse updateRecipe = service.update(id, updateRequest);

        return ResponseEntity.ok(updateRecipe);
    }
    @DeleteMapping(Id)
    public ResponseEntity<RecipeResponse> delete (@PathVariable Integer id) {
        RecipeResponse deleteRecipe = service.delete(id);

        return ResponseEntity.ok(deleteRecipe);
    }
}
