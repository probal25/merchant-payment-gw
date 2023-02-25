package com.probal.merchantpaymentdemo.repository;

import com.probal.merchantpaymentdemo.domain.entity.MerchantConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantConfigRepository extends JpaRepository<MerchantConfig, Long> {
    Optional<MerchantConfig> findByMerchantIdentity(String identity);
}
