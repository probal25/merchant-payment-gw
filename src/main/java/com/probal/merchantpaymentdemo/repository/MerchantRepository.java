package com.probal.merchantpaymentdemo.repository;

import com.probal.merchantpaymentdemo.domain.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Optional<Merchant> findByMerchantCode(String merchantCode);
}
