package br.com.receitasculinarias.service;

import br.com.receitasculinarias.domain.dto.RecipeCreateRequest;
import br.com.receitasculinarias.domain.dto.RecipeResponse;
import br.com.receitasculinarias.domain.entity.Recipe;
import br.com.receitasculinarias.repository.RecipesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipesRepository repository;

    public List<RecipeResponse> listAll() {
        List<Recipe> recipes = repository.findAll();

        return recipes.stream().map(RecipeResponse::new).toList();
    }

    public List<RecipeResponse> create(List<RecipeCreateRequest> createRequest) {
        List<Recipe> createRecipes = createRequest.stream().map(request -> Recipe.builder().name(request.getName()).section(request.getSection()).build()).toList();

        List<Recipe> recipesSaved = repository.saveAll(createRecipes);
        return recipesSaved.stream().map(RecipeResponse::new).toList();

    }

    public RecipeResponse create(RecipeCreateRequest createRequest) {
        Recipe createRecipe = Recipe.builder().name(createRequest.getName()).section(createRequest.getSection()).build();

        Recipe recipeSave = repository.save(createRecipe);
        return new RecipeResponse(recipeSave);
    }

    public RecipeResponse findById(Integer id) {
        var findRecipe = repository.findById(id).get();

        return new RecipeResponse(findRecipe);
    }

    public List<RecipeResponse> findAllByName(String name) {
        var findRecipes = repository.findAllByName(name);
        return findRecipes.stream().map(RecipeResponse::new).toList();
    }
}
