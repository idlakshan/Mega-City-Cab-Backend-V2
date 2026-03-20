package lk.icbt.megacity.controller;

import lk.icbt.megacity.dto.BookingDTO;
import lk.icbt.megacity.service.BookingService;
import lk.icbt.megacity.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final BookingService bookingService;
    private final InvoiceService invoiceService;

    @GetMapping("/generate")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<byte[]> generateInvoice(
            @RequestParam String bookingId
    ) {
        try {

            Integer id = Integer.parseInt(bookingId);
            BookingDTO booking = bookingService.getBookingById(id);
            byte[] pdfContents = invoiceService.generateInvoice(booking);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "invoice-" + bookingId + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfContents);

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}