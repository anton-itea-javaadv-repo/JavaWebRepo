package ua.itea.web.hw14p2.lesson14p2hw.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cartitem")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int itemId;
    private int itemCount;
    @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)
    private CartEntity cart;

    public ItemEntity() {
    }

    public ItemEntity(int itemId, int itemCount, CartEntity cart) {
        this.itemId = itemId;
        this.itemCount = itemCount;
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public ItemEntity setId(int id) {
        this.id = id;
        return this;
    }

    public int getItemId() {
        return itemId;
    }

    public ItemEntity setItemId(int itemId) {
        this.itemId = itemId;
        return this;
    }

    public int getItemCount() {
        return itemCount;
    }

    public ItemEntity setItemCount(int itemCount) {
        this.itemCount = itemCount;
        return this;
    }

    public CartEntity getCart() {
        return cart;
    }

    public ItemEntity setCart(CartEntity cart) {
        this.cart = cart;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity that = (ItemEntity) o;
        return id == that.id && itemId == that.itemId && itemCount == that.itemCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemId, itemCount);
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", itemCount=" + itemCount +
                '}';
    }
}
