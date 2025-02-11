package org.bookstoreecommerce.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.bookstoreecommerce.DTO.PaymentRequest;
import org.bookstoreecommerce.DTO.PaymentResponse;
import org.bookstoreecommerce.enums.PaymentStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderService orderService;

    @Value("${stripe.secret-key}")
    private  String stripeApiKey;

    public PaymentResponse createPaymentSession(PaymentRequest paymentRequest) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        Map<String, String> metadata = new HashMap<>();
        metadata.put("order_id", paymentRequest.getOrderId());

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(paymentRequest.getCurrency())
                                                .setUnitAmount(paymentRequest.getAmount()*100)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Book Purchase")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:8080/cancel")
                .putAllMetadata(metadata)
                .build();

        Session session = Session.create(params);

        return PaymentResponse.builder()
                .sessionId(session.getUrl())
                .checkoutUrl(session.getUrl())
                .build();

    }

    public Boolean processPaymentAndUpdateOrder(String sessionId) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        Session session = Session.retrieve(sessionId);

        Map<String, String> paymentDetails = new HashMap<>();
        paymentDetails.put("payment_status", session.getPaymentStatus());
        paymentDetails.put("payment_id", session.getPaymentIntent());
        paymentDetails.put("amount", session.getAmountTotal().toString());
        paymentDetails.put("order_id", session.getMetadata().get("order_id"));

        Long orderId = Long.parseLong(session.getMetadata().get("order_id"));

        if (session.getPaymentStatus().equals("paid")) {
            return orderService.updatePaymentDetails(orderId, paymentDetails.get("payment_id"), PaymentStatus.COMPLETED);
        }

        return false;
    }


}
