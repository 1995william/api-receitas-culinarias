package br.com.receitasculinarias.service;

import br.com.receitasculinarias.domain.dto.RecipeCreateRequest;
import br.com.receitasculinarias.domain.dto.RecipeResponse;
import br.com.receitasculinarias.domain.dto.RecipeUpdateRequest;
import br.com.receitasculinarias.domain.entity.Recipe;
import br.com.receitasculinarias.repository.RecipesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipesRepository repository;

    public List<RecipeResponse> listAll(Pageable pageable) {

        if (pageable.getPageSize() < 1 || pageable.getPageSize() > 50) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page size must be between 1 and 50");
        }

        Page<Recipe> recipes = repository.findAll(pageable);

        return recipes.stream().map(RecipeResponse::new).toList();
    }

    public RecipeResponse create(RecipeCreateRequest createRequest) {
        Recipe createRecipe = Recipe.builder().name(createRequest.getName()).section(createRequest.getSection()).build();

        Recipe recipeSave = repository.save(createRecipe);
        return new RecipeResponse(recipeSave);
    }

    public RecipeResponse findById(Integer id) {
        Recipe searchedRecipe = searchById(id);

        return new RecipeResponse(searchedRecipe);
    }

    public List<RecipeResponse> findAllByName(String name) {
        List<Recipe> findRecipes = repository.findAllByName(name);
        if (findRecipes.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found by name " + name);
        }
        return findRecipes.stream().map(RecipeResponse::new).toList();
    }

    public RecipeResponse update(Integer id, RecipeUpdateRequest createRequest) {
        Recipe searchedRecipe = searchById(id);

        searchedRecipe.setName(createRequest.getName());
        searchedRecipe.setSection(createRequest.getSection());

        Recipe updatedRecipe = repository.save(searchedRecipe);
        return new RecipeResponse(updatedRecipe);
    }

    public RecipeResponse delete(Integer id) {
        Recipe searchedRecipe = searchById(id);
        repository.delete(searchedRecipe);

        return new RecipeResponse(searchedRecipe);
    }
    private Recipe searchById (Integer id) {
       return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found by id " + id));
    }
}
