package com.example.e_commerce.Controllers;


import com.example.e_commerce.Configs.ExtractJWT;
import com.example.e_commerce.Models.PaymentDto;
import com.example.e_commerce.Services.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payment/secure")
public class PaymentController {
    /*
    @Autowired
    private PaymentService paymentService;


    /*
        Attend une requête avec un corps contenant un objet de type PaymentInfoRequest.
            {
          "amount": 1000,
          "currency": "usd",
          "receiptEmail": "example@example.com"
             }

    */
    /*
    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentDto paymentInfoRequest)
            throws StripeException {

        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfoRequest);
        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    @PutMapping("/payment-complete")
    public ResponseEntity<String> stripePaymentComplete(@RequestHeader(value="Authorization") String token)
            throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        return paymentService.stripePayment(userEmail);
    }
    */
}
