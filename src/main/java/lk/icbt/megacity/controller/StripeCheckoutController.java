package lk.icbt.megacity.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lk.icbt.megacity.dto.request.StripeCheckoutRequestDTO;
import lk.icbt.megacity.service.*;
import lk.icbt.megacity.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/checkout")
@RequiredArgsConstructor

public class StripeCheckoutController {

    private final CheckoutService checkoutService;

    @Value("${stripe.secret-key}")
    private String stripeApiKey;

    @PostMapping("/create-session")
    public ResponseEntity<ResponseUtil> createCheckoutSession(@RequestBody StripeCheckoutRequestDTO request) {
        try {
            Stripe.apiKey = stripeApiKey;

            long amountInCents = (long) (request.getAmount() * 100);

            // Create Stripe session
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .addLineItem(SessionCreateParams.LineItem.builder()
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency(request.getCurrency())
                                            .setUnitAmount(amountInCents)
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName("Ride Booking")
                                                            .build())
                                            .build())
                            .setQuantity(1L)
                            .build())
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(request.getSuccessUrl())
                    .setCancelUrl(request.getCancelUrl())
                    .build();

            Session session = Session.create(params);


            int bookingId = checkoutService.processCheckout(request);

            Map<String, Object> response = new HashMap<>();
            response.put("id", session.getId());
            response.put("bookingId", bookingId);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseUtil(201, "Payment session created successfully", response));

        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseUtil(502, "Stripe service error", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseUtil(500, "Internal server error", null));
        }
    }
}
