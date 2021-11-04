package ua.itea.web.hw14p2.lesson14p2hw.component;

import org.springframework.stereotype.Component;
import ua.itea.web.hw14p2.lesson14p2hw.dto.CartDto;
import ua.itea.web.hw14p2.lesson14p2hw.model.CartEntity;
import ua.itea.web.hw14p2.lesson14p2hw.model.ItemEntity;
import ua.itea.web.hw14p2.lesson14p2hw.repository.ItemRepository;

import java.util.*;

@Component
public class CartMapper {
    private final ItemRepository itemRepository;

    public CartMapper(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public CartDto entityToDto(CartEntity entity) {
        if (entity == null) {
            return null;
        }
        Map<Integer, Integer> items = new LinkedHashMap<>();
        if (entity.getItems() != null) {
            entity.getItems().forEach(ie -> items.put(ie.getItemId(), ie.getItemCount()));
        }
        return new CartDto()
                .setId(entity.getCart_id())
                .setItems(items);
    }

    public CartEntity dtoToEntity(CartDto dto) {
        if (dto == null) {
            return null;
        }
        CartEntity cartEntity = new CartEntity().setCart_id(dto.getId());
        Map<Integer, Integer> items = dto.getItems();
        Set<ItemEntity> itemsSet = new LinkedHashSet<>();
        cartEntity.setItems(itemsSet);
        if (items != null) {
            items.entrySet().forEach(ie -> {
                ItemEntity itemEntityByItemId = itemRepository.getItemEntityByItemId(ie.getKey());
                if (itemEntityByItemId != null) {
                    itemEntityByItemId.setItemCount(ie.getValue());
                    ItemEntity saved = itemRepository.save(itemEntityByItemId);
                    itemsSet.add(saved);
                } else {
                    ItemEntity newItemEntity = new ItemEntity(ie.getKey(), ie.getValue(), cartEntity);
                    ItemEntity saved = itemRepository.save(newItemEntity);
                    itemsSet.add(saved);
                }
            });
        }
        return cartEntity;
    }
}
