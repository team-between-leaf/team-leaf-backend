package com.team.leaf.user.account.repository;

import com.team.leaf.user.account.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<ShippingAddress, Long> {

    Optional<ShippingAddress> findByAccountIdAndAddress(Long userId, String address);

    List<ShippingAddress> findAllByAccountId(Long userId);

}