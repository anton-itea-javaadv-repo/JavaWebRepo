package ua.itea.web.hw14p2.lesson14p2hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.itea.web.hw14p2.lesson14p2hw.model.CartEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
}
