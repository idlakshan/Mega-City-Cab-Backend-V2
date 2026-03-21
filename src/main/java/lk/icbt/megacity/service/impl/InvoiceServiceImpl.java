package lk.icbt.megacity.service.impl;

import com.lowagie.text.DocumentException;
import lk.icbt.megacity.dto.BookingDTO;
import lk.icbt.megacity.dto.CarDTO;
import lk.icbt.megacity.dto.DriverDTO;
import lk.icbt.megacity.dto.PaymentDTO;
import lk.icbt.megacity.service.InvoiceService;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    public byte[] generateInvoice(BookingDTO booking) throws IOException, DocumentException {

        String htmlTemplate = loadHtmlTemplate("templates/invoice-template.html");

        String filledHtml = fillTemplate(htmlTemplate, booking);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(filledHtml);
        renderer.layout();
        renderer.createPDF(baos);

        return baos.toByteArray();
    }

    private String loadHtmlTemplate(String templatePath) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(templatePath)) {
            if (inputStream == null) {
                throw new IOException("Template file not found: " + templatePath);
            }
            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
                return scanner.useDelimiter("\\A").next();
            }
        }
    }

    private String fillTemplate(String template, BookingDTO booking) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String receiptNumber = "INV-" + booking.getBookingId() + "-" + System.currentTimeMillis();
        String receiptDate = LocalDate.now().format(dateFormatter);
        String receiptTime = LocalTime.now().format(timeFormatter);

        CarDTO car = booking.getCar();
        DriverDTO driver = booking.getDriver();
        PaymentDTO payment = booking.getPayment();

        String carImageHtml = "";
        if (car != null && car.getCarImage() != null && !car.getCarImage().isEmpty()) {
            carImageHtml = String.format("""
                <div class="car-image">
                    <img src="%s" alt="%s"/>
                </div>
                """, car.getCarImage(), car.getCarName());
        }

        String paymentStatusClass = payment != null && "Success".equals(payment.getPaymentStatus())
                ? "status-success" : "status-pending";

        return template
                .replace("${receiptNumber}", receiptNumber)
                .replace("${receiptDate}", receiptDate)
                .replace("${receiptTime}", receiptTime)
                .replace("${carImage}", carImageHtml)
                .replace("${bookingId}", String.valueOf(booking.getBookingId()))
                .replace("${customerName}", safeString(booking.getCustomerName()))
                .replace("${customerEmail}", safeString(booking.getCustomerEmail()))
                .replace("${customerPhone}", safeString(booking.getCustomerPhone()))
                .replace("${bookingDateTime}", booking.getBookingDateTime() != null ?
                        booking.getBookingDateTime().format(dateTimeFormatter) : "N/A")
                .replace("${pickupLocation}", safeString(booking.getPickupLocation()))
                .replace("${dropLocation}", safeString(booking.getDropLocation()))
                .replace("${carName}", car != null ? safeString(car.getCarName()) : "N/A")
                .replace("${carNumber}", car != null ? safeString(car.getCarNumber()) : "N/A")
                .replace("${driverName}", driver != null ? safeString(driver.getDriverName()) : "N/A")
                .replace("${driverContact}", driver != null ? safeString(driver.getDriverContact()) : "N/A")
                .replace("${paymentMethod}", payment != null ? safeString(payment.getPaymentMethod()) : "N/A")
                .replace("${paymentDate}", payment != null && payment.getPaymentDate() != null ?
                        payment.getPaymentDate().toString() : "N/A")
                .replace("${paymentStatusClass}", paymentStatusClass)
                .replace("${paymentStatus}", payment != null ? safeString(payment.getPaymentStatus()) : "N/A")
                .replace("${bookingStatus}", safeString(booking.getStatus()))
                .replace("${amount}", payment != null ? String.format("%.2f", payment.getAmount()) : "0.00");
    }

    private String safeString(String value) {
        return value != null ? value : "N/A";
    }
}