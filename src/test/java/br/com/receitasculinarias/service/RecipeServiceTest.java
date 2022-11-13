package br.com.receitasculinarias.service;

import br.com.receitasculinarias.domain.dto.RecipeCreateRequest;
import br.com.receitasculinarias.domain.dto.RecipeUpdateRequest;
import br.com.receitasculinarias.domain.entity.Recipe;
import br.com.receitasculinarias.domain.entity.Section;
import br.com.receitasculinarias.repository.RecipesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {
    @Mock
    private RecipesRepository repository;
    @InjectMocks
    private RecipeService service;

    @Test
    public void shouldReturnListOfRecipeResponse() {
        var listSection = List.of(new Section());
        var recipe1 = new Recipe(1, "suco de acerola", listSection);
        var recipe2 = new Recipe(2, "macarrão", listSection);
        var recipes = List.of(recipe1, recipe2);
        var recipePage = new PageImpl<>(recipes);
        when(repository.findAll(any(Pageable.class))).thenReturn(recipePage);
        PageRequest pageRequest = PageRequest.of(0, 50);


        var listRecipeResponse = service.listAll(pageRequest);


        verify(repository).findAll(any(Pageable.class));
        assertEquals(2, listRecipeResponse.size());
        assertEquals(recipe1.getName(), listRecipeResponse.get(0).getName());
        assertEquals(recipe2.getName(), listRecipeResponse.get(1).getName());
    }

    @Test
    public void shouldReturnResponseStatusExceptionWhenPageIsLarge() {
        final var expectedMessage = "page size must be between 1 and 50";
        var pageRequest = PageRequest.of(0, 100);


        var actualException = assertThrows(ResponseStatusException.class, () -> service.listAll(pageRequest));


        assertEquals(HttpStatus.BAD_REQUEST, actualException.getStatus());
        assertEquals(expectedMessage, actualException.getReason());
    }

    @Test
    public void shouldReturnRecipeCreated() {
        final var expectedMessage = "suco de acerola";
        var recipe = new Recipe();
        when(repository.save(any(Recipe.class))).thenReturn(recipe);

        var recipeCreateRequest = RecipeCreateRequest.builder()
                .name("suco de acerola")
                .build();

        service.create(recipeCreateRequest);

        var recipeCaptor = ArgumentCaptor.forClass(Recipe.class);
        verify(repository).save(recipeCaptor.capture());

        assertEquals(expectedMessage, recipeCaptor.getValue().getName());

    }

    @Test
    public void shouldReturnRecipeById() {
        var recipe = new Recipe(1, "suco de acerola", List.of(new Section()));
        when(repository.findById(recipe.getId())).thenReturn(Optional.of(recipe));

        var response = service.findById(recipe.getId());

        assertEquals(recipe.getId(), response.getId());
        assertEquals(recipe.getName(), response.getName());
    }

    @Test
    public void shouldReturnResponseStatusExceptionWhenRecipeByIdNotFound() {
        final var expectedMessage = "Recipe not found by id " + any(String.class);
        var recipe = new Recipe();

        var response = assertThrows(ResponseStatusException.class, () -> service.findById(recipe.getId()));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals(expectedMessage, response.getReason());
    }

    @Test
    public void shouldReturnRecipeUpdated() {
        final var expectedMessage = "suco de acerola";
        var recipe = new Recipe(1, "suco de manga", List.of(new Section()));
        when(repository.save(any())).thenReturn(recipe);
        when(repository.findById(any())).thenReturn(Optional.of(recipe));
        var recipeUpdateRequest = RecipeUpdateRequest.builder()
                .name("suco de acerola")
                .section(List.of(new Section()))
                .build();

        service.update(recipe.getId(), recipeUpdateRequest);

        var recipeCaptor = ArgumentCaptor.forClass(Recipe.class);
        verify(repository).save(recipeCaptor.capture());
        verify(repository).findById(1);
        assertEquals(expectedMessage, recipeCaptor.getValue().getName());

    }

    @Test
    public void shouldReturnResponseStatusExceptionWhenRecipeUpdateByIdNotFound() {
        final var expectedMessage = "Recipe not found by id " + any(String.class);
        var recipe = new Recipe();
        var recipeUpdateRequest = new RecipeUpdateRequest();

        var response = assertThrows(ResponseStatusException.class, () -> service.update(recipe.getId(), recipeUpdateRequest));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals(expectedMessage, response.getReason());

    }

    @Test
    public void shouldReturnRecipeDeletedById() {
        var recipe = new Recipe(1, "suco de manga", List.of(new Section()));
        when(repository.findById(1)).thenReturn(Optional.of(recipe));

        var response = service.delete(recipe.getId());

        verify(repository, times(1)).delete(recipe);
        assertEquals(recipe.getId(), response.getId());
        assertEquals(recipe.getName(), response.getName());
    }

    @Test
    public void shouldReturnResponseStatusExceptionWhenRecipeDeletedByIdNotFound() {
        final var expectedMessage = "Recipe not found by id " + any(String.class);
        var recipe = new Recipe();

        var response = assertThrows(ResponseStatusException.class, () -> service.delete(recipe.getId()));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals(expectedMessage, response.getReason());

    }
}
