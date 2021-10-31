package ua.itea.web.hw13.component;

import org.springframework.stereotype.Component;
import ua.itea.web.hw13.model.CategoryEntity;
import ua.itea.web.hw13.model.ProductEntity;
import ua.itea.web.hw13.model.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.List;

@Component
public class UserEntityManager {
    private EntityManager em =
            Persistence.createEntityManagerFactory("ironstore").createEntityManager();

    public UserEntity getUserByLogin(String login) {
        //https://stackoverflow.com/a/38337408
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> rootEntry = cq.from(UserEntity.class);
        // Всё ради поиска по логину вместо id.
        cq.where(cb.equal(rootEntry.get("login"), login));
        CriteriaQuery<UserEntity> cqUserByLogin = cq.select(rootEntry);
        TypedQuery<UserEntity> userByLogin = em.createQuery(cqUserByLogin);
        List<UserEntity> resultList = userByLogin.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    public UserEntity saveUser(UserEntity user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        return em.find(UserEntity.class, user.getId());
    }

    public UserEntity updateUser(UserEntity user, String oldLogin) {
        UserEntity userEntity = getUserByLogin(oldLogin);
        if (userEntity == null) {
            return saveUser(user);
        } else {
            userEntity.setName(user.getName())
                    .setLogin(user.getLogin())
                    .setPassword(user.getPassword())
                    .setGender(user.getGender())
                    .setRegion(user.getRegion())
                    .setComment(user.getComment());
            em.getTransaction().begin();
            UserEntity mergedUserEntity = em.merge(userEntity);
            em.getTransaction().commit();
            return mergedUserEntity;
        }
    }

    public void deleteUser(String login) {
        UserEntity userEntity = getUserByLogin(login);
        if (userEntity != null) {
            em.getTransaction().begin();
            em.remove(userEntity);
            em.getTransaction().commit();
        }
    }
}
