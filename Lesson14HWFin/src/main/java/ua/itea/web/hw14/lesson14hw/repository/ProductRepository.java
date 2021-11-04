package ua.itea.web.hw14.lesson14hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.itea.web.hw14.lesson14hw.model.CategoryEntity;
import ua.itea.web.hw14.lesson14hw.model.ProductEntity;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findByCategories(CategoryEntity category);
}
