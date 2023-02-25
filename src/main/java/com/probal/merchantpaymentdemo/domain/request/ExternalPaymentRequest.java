package com.probal.merchantpaymentdemo.domain.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExternalPaymentRequest {
    private String merchantCode;
    private String password;
    private String secretKey;
    private String transactionTrackingNo;
    private BigDecimal amount;
    private String billOrInvoiceNo;
    private String approvedUrl;
    private String cancelUrl;
    private String declineUrl;
    private String language;
    private String currency;
    private String description;
    private String sessionId;
}
