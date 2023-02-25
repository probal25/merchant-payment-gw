package com.probal.merchantpaymentdemo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.probal.merchantpaymentdemo.common.EncryptionUtils;
import com.probal.merchantpaymentdemo.domain.entity.Merchant;
import com.probal.merchantpaymentdemo.domain.entity.MerchantConfig;
import com.probal.merchantpaymentdemo.domain.entity.MerchantPaymentRequest;
import com.probal.merchantpaymentdemo.domain.request.CommonRequest;
import com.probal.merchantpaymentdemo.domain.request.ExternalPaymentRequest;
import com.probal.merchantpaymentdemo.domain.request.PaymentRequest;
import com.probal.merchantpaymentdemo.repository.MerchantConfigRepository;
import com.probal.merchantpaymentdemo.repository.MerchantPaymentRequestRepository;
import com.probal.merchantpaymentdemo.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final MerchantRepository merchantRepository;

    private final MerchantConfigRepository merchantConfigRepository;

    private final PasswordEncoder passwordEncoder;

    private final MerchantPaymentRequestRepository merchantPaymentRequestRepository;

    public String encryptPayload() throws Exception {
        String payload = "";
        ExternalPaymentRequest request = generateRequest();
        try {
            payload = toJson(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String encryptedValue = EncryptionUtils.encrypt(payload);
        return encryptedValue;
    }

    public void initiateRequest(CommonRequest request) {
        ExternalPaymentRequest paymentRequest = decryptPayload(request.getData());
        validateRequest(paymentRequest);
        Merchant merchant = validateMerchant(paymentRequest);
        initiateMerchantPaymentRequest(paymentRequest, merchant);
        PaymentRequest requestPayment = new PaymentRequest();
        requestPayment.setMerchantName(merchant.getMerchantName());
        requestPayment.setBillOrInvoiceNo(paymentRequest.getBillOrInvoiceNo());
    }

    private ExternalPaymentRequest decryptPayload(String data) {
        ExternalPaymentRequest request = null;
        try {
            request = EncryptionUtils.decryptPayload(data, ExternalPaymentRequest.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return request;
    }

    private ExternalPaymentRequest generateRequest() {
        ExternalPaymentRequest paymentRequest = new ExternalPaymentRequest();
        paymentRequest.setMerchantCode("123456");
        paymentRequest.setPassword("mySecretPassword");
        paymentRequest.setSecretKey("mySecretKey");
        paymentRequest.setTransactionTrackingNo("ABCDE12345");
        paymentRequest.setAmount(BigDecimal.valueOf(5000));
        paymentRequest.setBillOrInvoiceNo("INV-2023-001");
        paymentRequest.setApprovedUrl("https://example.com/payment/approved");
        paymentRequest.setCancelUrl("https://example.com/payment/cancelled");
        paymentRequest.setDeclineUrl("https://example.com/payment/declined");
        paymentRequest.setDescription("Payment for purchase of item X.");
        paymentRequest.setSessionId(generateSessionId());
        return paymentRequest;
    }

    private void initiateMerchantPaymentRequest(ExternalPaymentRequest paymentRequest, Merchant merchant) {
        MerchantPaymentRequest request = new MerchantPaymentRequest();
        request.setAmount(paymentRequest.getAmount());
        request.setMerchantCode(paymentRequest.getMerchantCode());
        request.setBillOrInvoiceNo(paymentRequest.getBillOrInvoiceNo());
        request.setDescription(paymentRequest.getDescription());
        request.setSessionId(paymentRequest.getSessionId());
        request.setApprovedUrl(paymentRequest.getApprovedUrl());
        request.setDeclineUrl(paymentRequest.getDeclineUrl());
        request.setCancelUrl(paymentRequest.getCancelUrl());
        request.setMerchantIdentity(merchant.getMerchantIdentity());
        request.setCurrency(paymentRequest.getCurrency());
        request.setLanguage(paymentRequest.getLanguage());

        merchantPaymentRequestRepository.save(request);
    }

    private void validateRequest(ExternalPaymentRequest paymentRequest) {
        // todo- check for session id
    }

    private Merchant validateMerchant(ExternalPaymentRequest request) {
        String merchantCode = request.getMerchantCode();
        Merchant merchant = merchantRepository.findByMerchantCode(merchantCode).orElse(null);

        if (Objects.isNull(merchant))
            throw new IllegalArgumentException("No merchant found by this code");

        if (!passwordEncoder.matches(request.getPassword(), merchant.getPin()))
            throw new IllegalArgumentException("Password Does not match");

        MerchantConfig config = merchantConfigRepository.findByMerchantIdentity(merchant.getMerchantIdentity()).orElse(null);

        if (Objects.isNull(config))
            throw new IllegalArgumentException("No config found");

        if (!request.getSecretKey().equals(config.getMerchantSecretKey()))
            throw new IllegalArgumentException("Secret key does not match");

        return merchant;
    }

    private String toJson(ExternalPaymentRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(request);
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
