package com.domain.system.repository.postgresql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.system.models.postgresql.CartItem;
import com.domain.system.models.postgresql.CartItemID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemID> {

}
