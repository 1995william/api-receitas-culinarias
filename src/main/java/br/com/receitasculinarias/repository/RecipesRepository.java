package br.com.receitasculinarias.repository;

import br.com.receitasculinarias.domain.entity.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipesRepository extends PagingAndSortingRepository<Recipe, Integer> {
    @Query(value = "SELECT * FROM recipe WHERE name ILIKE %?1%", nativeQuery = true)
    List<Recipe> findAllByName(String name);
}
