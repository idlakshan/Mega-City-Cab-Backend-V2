package lk.icbt.megacity.service.impl;

import lk.icbt.megacity.dto.PaymentDTO;
import lk.icbt.megacity.entity.Booking;
import lk.icbt.megacity.entity.Payment;
import lk.icbt.megacity.repo.BookingRepo;
import lk.icbt.megacity.repo.PaymentRepo;
import lk.icbt.megacity.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
