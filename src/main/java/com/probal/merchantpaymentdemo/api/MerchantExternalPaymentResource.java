package com.probal.merchantpaymentdemo.api;


import com.probal.merchantpaymentdemo.domain.request.CommonRequest;
import com.probal.merchantpaymentdemo.domain.request.ExternalPaymentRequest;
import com.probal.merchantpaymentdemo.domain.request.PaymentRequest;
import com.probal.merchantpaymentdemo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment/external")
public class MerchantExternalPaymentResource {

    private final PaymentService service;

    @ResponseBody
    @GetMapping("/encrypt")
    public ResponseEntity<String> encryptPayload() throws Exception {
        String data = service.encryptPayload();
        return ResponseEntity.ok().body(data);
    }

    @ResponseBody
    @PostMapping("/initiate-request")
    public ResponseEntity<ExternalPaymentRequest> initiateRequest(@RequestBody CommonRequest commonRequest) throws Exception {
        return null;
    }

    @GetMapping("/initiate")
    public String initiateMerchantPayment(Model model) {
        PaymentRequest request = new PaymentRequest();
        request.setMerchantName("Pipex");
        request.setBillOrInvoiceNo("DW16554165465");
        request.setAmount(BigDecimal.valueOf(200));
        model.addAttribute("request", request);
        return "initial_form";
    }

    @ResponseBody
    @PostMapping("/process")
    private PaymentRequest processSubmission(@ModelAttribute("request") PaymentRequest request) {
        return request;
    }
}
