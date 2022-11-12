package br.com.receitasculinarias.domain.entity;

import lombok.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Section implements Serializable {
    private String name;
    private List<String> content;

}
