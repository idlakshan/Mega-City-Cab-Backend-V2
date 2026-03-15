package lk.icbt.megacity.service.impl;

import lk.icbt.megacity.dto.PaymentDTO;
import lk.icbt.megacity.entity.Booking;
import lk.icbt.megacity.entity.Payment;
import lk.icbt.megacity.repo.BookingRepo;
import lk.icbt.megacity.repo.PaymentRepo;
import lk.icbt.megacity.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;
    private final BookingRepo bookingRepo;

    @Override
    public void savePayment(PaymentDTO dto) {
        Payment payment = new Payment();
        Booking booking = bookingRepo.findById(dto.getBookingId()).orElseThrow();

        payment.setBooking(booking);
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setPaymentStatus(dto.getPaymentStatus());
        payment.setPaymentDate(dto.getPaymentDate());

        paymentRepo.save(payment);
    }

    @Override
    public Map<String, Double> getPaymentHistoryByUserId(Integer userId) {
        List<Payment> payments = paymentRepo.findPaymentsByUserId(userId);

        Map<String, Double> paymentHistory = new LinkedHashMap<>();

        for (Payment payment : payments) {
            if (payment.getPaymentDate() != null) {
                String date = payment.getPaymentDate().toLocalDate().toString();
                paymentHistory.put(date,
                        paymentHistory.getOrDefault(date, 0.0) + payment.getAmount());
            }
        }

        return paymentHistory;
    }
}
