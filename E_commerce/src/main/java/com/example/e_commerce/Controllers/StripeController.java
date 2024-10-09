package com.example.e_commerce.Controllers;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping("/api/stripe")
public class StripeController {
    // Inject Stripe secret key from application properties
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    // Initialiser Stripe avec la clé API dans le constructeur
    public StripeController() {
        Stripe.apiKey = "sk_test_51Q5ofE04DVlvplqiymnHjjQMGNh1BDR5vI8CWcxEMAUnnMq5hYuzrw7V1BzJcEMdF8MASwhyCLjZ9xgwBXBpfBUE00mqmF1dun";
    }

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestParam("price") double price,
                                                     @RequestParam("productName") String productName,
                                                     @RequestParam("currency") String currency) {
        Map<String, String> responseData = new HashMap<>();
        try {
            // Créer les paramètres de la session de paiement
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:3000/payment-success")
                    .setCancelUrl("http://localhost:3000/payment-failure")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(currency)
                                                    .setUnitAmount((long) (price * 100)) // Montant en centimes
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(productName)
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .setQuantity(1L)
                                    .build()
                    )
                    .build();

            // Créer la session Stripe
            Session session = Session.create(params);
            responseData.put("sessionId", session.getId());
        } catch (Exception e) {
            responseData.put("error", e.getMessage());
        }

        return responseData;
    }
}


