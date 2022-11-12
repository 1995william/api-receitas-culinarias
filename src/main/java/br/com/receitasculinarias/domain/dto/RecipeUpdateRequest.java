package br.com.receitasculinarias.domain.dto;

import br.com.receitasculinarias.domain.entity.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeUpdateRequest {
    @NotNull(message = "name must be defined")
    private String name;
    @NotNull(message = "section must be defined")
    private List<Section> section;
}
