package com.ubereats.ubereatsclone.repositories;

import com.ubereats.ubereatsclone.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
