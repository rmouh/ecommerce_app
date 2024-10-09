package com.example.e_commerce.Controllers;

import com.example.e_commerce.Models.PaymentRequest;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    public PaymentController() {
        Stripe.apiKey = "sk_test_51Q5ofE04DVlvplqiymnHjjQMGNh1BDR5vI8CWcxEMAUnnMq5hYuzrw7V1BzJcEMdF8MASwhyCLjZ9xgwBXBpfBUE00mqmF1dun"; // Assurez-vous que la clé est correcte
    }

    @PostMapping("/create-session")
    public Map<String, String> createCheckoutSession(@RequestBody PaymentRequest paymentRequest) throws Exception {
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/payment-success") // URL de redirection en cas de succès
                .setCancelUrl("http://localhost:8080/payment-failure") // URL de redirection en cas d'échec
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(paymentRequest.getQuantity())
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("eur") // Monnaie
                                                .setUnitAmount(paymentRequest.getAmount()) // Montant en cents
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(paymentRequest.getProductName()) // Nom du produit
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        Session session = Session.create(params);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("sessionId", session.getId()); // Retourner l'ID de session
        return responseData;
    }
}
