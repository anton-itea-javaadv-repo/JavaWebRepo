package ua.itea.web.hw14p2.lesson14p2hw.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cart_id;
    @OneToMany(mappedBy="cart")
    private Set<ItemEntity> items;

    public CartEntity() {
    }

    public int getCart_id() {
        return cart_id;
    }

    public CartEntity setCart_id(int cart_id) {
        this.cart_id = cart_id;
        return this;
    }

    public Set<ItemEntity> getItems() {
        return items;
    }

    public CartEntity setItems(Set<ItemEntity> items) {
        this.items = items;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartEntity that = (CartEntity) o;
        return cart_id == that.cart_id && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cart_id, items);
    }

    @Override
    public String toString() {
        return "CartEntity{" +
                "cart_id=" + cart_id +
                ", items=" + items +
                '}';
    }
}
