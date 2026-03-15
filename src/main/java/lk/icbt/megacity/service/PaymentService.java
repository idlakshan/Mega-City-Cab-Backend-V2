package lk.icbt.megacity.service;

import lk.icbt.megacity.dto.PaymentDTO;

import java.util.Map;

public interface PaymentService {
    void savePayment(PaymentDTO dto);
    Map<String, Double> getPaymentHistoryByUserId(Integer userId);
}
