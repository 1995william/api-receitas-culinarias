package br.com.receitasculinarias.domain.dto;

import br.com.receitasculinarias.domain.entity.Recipe;
import br.com.receitasculinarias.domain.entity.Section;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeResponse {
    private Integer id;
    private String name;
    private List<Section> section = new ArrayList<>();

    public RecipeResponse(Recipe recipe) {
        id = recipe.getId();
        name = recipe.getName();
        section.addAll(recipe.getSection());
    }
}
