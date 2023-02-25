package com.probal.merchantpaymentdemo.repository;

import com.probal.merchantpaymentdemo.domain.entity.MerchantPaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantPaymentRequestRepository extends JpaRepository<MerchantPaymentRequest, Long> {
}
