package com.probal.merchantpaymentdemo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "merchant_payment_request")
public class MerchantPaymentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String merchantIdentity;

    private String merchantCode;

    private String transactionTrackingNo;

    private BigDecimal amount;

    private String billOrInvoiceNo;

    private String approvedUrl;

    private String cancelUrl;

    private String declineUrl;

    private String language;

    private String currency;

    private String sessionId;

    private String description;
}
