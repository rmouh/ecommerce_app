package com.example.e_commerce.Services;

import com.example.e_commerce.Models.Payment;
import com.example.e_commerce.Models.PaymentDto;
import com.example.e_commerce.Repositories.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PaymentService {
/*
    @Autowired
    private PaymentRepository paymentRepository;

    // Inject Stripe secret key from application properties
    @Value("${stripe.api.key}")
    private String stripeApiKey;


    // Initialize the Stripe API key in the constructor
    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        Stripe.apiKey = "sk_test_51Q5ofE04DVlvplqiymnHjjQMGNh1BDR5vI8CWcxEMAUnnMq5hYuzrw7V1BzJcEMdF8MASwhyCLjZ9xgwBXBpfBUE00mqmF1dun";
        this.paymentRepository = paymentRepository;
        //Stripe.apiKey = System.getenv("STRIPE_API_KEY");;  // Initialize Stripe with the API key
    }

    public PaymentIntent createPaymentIntent(PaymentDto paymentInfoRequest) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfoRequest.getAmount());
        params.put("currency", paymentInfoRequest.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        //params.put("user", paymentInfoRequest.)

        return PaymentIntent.create(params);
    }

    public ResponseEntity<String> stripePayment(String userEmail) throws Exception {
        Payment payment = paymentRepository.findByUserEmail(userEmail);

        if (payment == null) {
            throw new Exception("Payment information is missing");
        }

        // Example logic: set a dummy amount, adjust as needed
        payment.setAmount(100.00); // Set an appropriate amount or use paymentInfoRequest.getAmount() if applicable
        paymentRepository.save(payment);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    */

}
