package ua.itea.web.hw14.lesson14hw.dto;

import java.util.Map;
import java.util.Objects;

public class CartDto {
    private int id;
    private Map<Integer, Integer> items;

    public CartDto() {
    }

    public int getId() {
        return id;
    }

    public CartDto setId(int id) {
        this.id = id;
        return this;
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public CartDto setItems(Map<Integer, Integer> items) {
        this.items = items;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDto cartDto = (CartDto) o;
        return id == cartDto.id && Objects.equals(items, cartDto.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, items);
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", items=" + items +
                '}';
    }
}
