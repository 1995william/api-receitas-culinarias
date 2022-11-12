package br.com.receitasculinarias.domain.dto;

import br.com.receitasculinarias.domain.entity.Section;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
@Data
@Builder
public class RecipeCreateRequest {
    @NotNull(message = "name must be defined")
    private String name;
    @NotNull(message = "section must be defined")
    private List<Section> section;
}
