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
public class PaymentRequest {
    private String merchantName;

    private String billOrInvoiceNo;

    private BigDecimal amount;

    private String customerWalletNo;

    private String customerPin;
}
