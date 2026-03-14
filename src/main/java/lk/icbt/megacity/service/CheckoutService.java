package lk.icbt.megacity.service;

import lk.icbt.megacity.dto.request.StripeCheckoutRequestDTO;

public interface CheckoutService {
    int processCheckout(StripeCheckoutRequestDTO request);
}
