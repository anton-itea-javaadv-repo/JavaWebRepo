package ua.itea.web.hw14p2.lesson14p2hw.service.impl;

import org.springframework.stereotype.Service;
import ua.itea.web.hw14p2.lesson14p2hw.component.CartMapper;
import ua.itea.web.hw14p2.lesson14p2hw.dto.CartDto;
import ua.itea.web.hw14p2.lesson14p2hw.model.CartEntity;
import ua.itea.web.hw14p2.lesson14p2hw.model.ItemEntity;
import ua.itea.web.hw14p2.lesson14p2hw.repository.CartRepository;
import ua.itea.web.hw14p2.lesson14p2hw.repository.ItemRepository;
import ua.itea.web.hw14p2.lesson14p2hw.service.CartService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, ItemRepository itemRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public Optional<CartDto> get(int id) {
        return Optional.ofNullable(cartMapper.entityToDto(cartRepository.findById(id).get()));
    }

    @Override
    public CartDto save(CartDto cart) {
        CartEntity userEntity = cartMapper.dtoToEntity(cart);
        cartRepository.save(userEntity);
        return cartMapper.entityToDto(cartRepository.findById(userEntity.getCart_id()).get());
    }

    @Override
    public CartDto update(CartDto cart, int id) {
        CartEntity cartEntity = cartRepository.findById(id).get();
        if (cartEntity != null) {
            //Сначала удаляем те, которые отсутствуют в списке в дто, чтобы уменьшить количество работы.
            List<ItemEntity> toRemove =
                    cartEntity.getItems().stream()
                            .filter(ie -> cart.getItems().get(ie.getItemId()) == null)
                            .collect(Collectors.toList());
            toRemove.forEach(ie -> {
                cartEntity.getItems().remove(ie);
                itemRepository.delete(ie);
            });
            //Потом обновляем значения у существующих
            cartEntity.getItems().forEach(ie -> ie.setItemCount(cart.getItems().get(ie.getItemId())));
            //Затем добавляем отсутствующие
            Set<Integer> needToAdd = cart.getItems().keySet().stream()
                    .filter(key -> cartEntity.getItems().stream().noneMatch(ie -> ie.getItemId() == key)) //Ух ты ) Прикольно.
                    .collect(Collectors.toSet());
            needToAdd.forEach(itemId -> cartEntity.getItems().add(itemRepository.save(new ItemEntity(itemId, cart.getItems().get(itemId), cartEntity))));
            cartRepository.save(cartEntity);
            return cartMapper.entityToDto(cartRepository.findById(cartEntity.getCart_id()).get());
        }
        return null;
    }

    @Override
    public void delete(int id) {
        CartEntity userEntity = cartRepository.findById(id).get();
        if (userEntity != null) {
            cartRepository.delete(userEntity);
        }
    }
}
