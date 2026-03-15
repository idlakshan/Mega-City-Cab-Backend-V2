package lk.icbt.megacity.service;


import lk.icbt.megacity.dto.BookingDTO;
import lk.icbt.megacity.dto.request.BookingStatusUpdateRequestDTO;

import java.util.List;
import java.util.Map;

public interface BookingService {
    int saveBooking(BookingDTO bookingDTO);

    List<BookingDTO> getAllBookings();

    List<BookingDTO> getBookingsByUserId(int userId);

    int getTotalBookings();

    double getTotalRevenue();

    List<BookingDTO> getBookingsByStatus(String status);

    Map<String, Integer> getBookingCountsLast7Days();

    List<BookingDTO> getBookingsDetailsByUserId(int userId);

    int getTotalBookingsByUserId(int userId);

    double getTotalSpendingByUserId(int userId);

    String getActiveSinceByUserId(int userId);

    String getFavoriteLocationByUserId(int userId);

    String updateBookingStatus(BookingStatusUpdateRequestDTO requestDTO);
}
