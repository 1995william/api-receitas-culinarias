package br.com.receitasculinarias.domain.dto;

import br.com.receitasculinarias.domain.entity.Section;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCreateRequest {
    @NotNull
    private String name;
    @NotNull
    private List<Section> section;
}
