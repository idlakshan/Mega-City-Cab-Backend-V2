package lk.icbt.megacity.service;

import com.lowagie.text.DocumentException;
import lk.icbt.megacity.dto.BookingDTO;

import java.io.IOException;

public interface InvoiceService {
    byte[] generateInvoice(BookingDTO booking) throws IOException, DocumentException;
}
