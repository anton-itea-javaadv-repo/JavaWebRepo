package ua.itea.web.hw13.component;

import org.springframework.stereotype.Component;
import ua.itea.web.hw13.model.CategoryEntity;
import ua.itea.web.hw13.model.ProductEntity;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.List;

@Component
public class ProductEntityManager {
    private EntityManager em =
            Persistence.createEntityManagerFactory("ironstore").createEntityManager();

    public ProductEntity getProductById(int id) {
        return em.find(ProductEntity.class, id);
    }

    public List<ProductEntity> getProducts() {
        //https://stackoverflow.com/a/38337408
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> cq = cb.createQuery(ProductEntity.class);
        Root<ProductEntity> rootEntry = cq.from(ProductEntity.class);
        CriteriaQuery<ProductEntity> all = cq.select(rootEntry);
        TypedQuery<ProductEntity> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    public List<ProductEntity> getProductsByCategory(String categoryName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> cq = cb.createQuery(ProductEntity.class);
        Metamodel m = em.getMetamodel();
        EntityType<ProductEntity> productEntityType = m.entity(ProductEntity.class);
        Root<ProductEntity> product = cq.from(ProductEntity.class);
        Join<ProductEntity, CategoryEntity> category = product.join(
                productEntityType.getSet("categories", CategoryEntity.class));
        category.on(cb.equal(category.get("name"), categoryName));
        CriteriaQuery<ProductEntity> allByCategory = cq.select(product);
        TypedQuery<ProductEntity> allByCategoryQuery = em.createQuery(allByCategory);
        return allByCategoryQuery.getResultList();
    }
}
