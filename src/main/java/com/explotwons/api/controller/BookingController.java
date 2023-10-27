package com.explotwons.api.controller;

import com.explotwons.api.entity.Booking;
import com.explotwons.api.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @PutMapping("/{bookingId}")
    public Booking updateBooking(@PathVariable Long bookingId, @RequestBody Booking booking) {
        return bookingService.updateBooking(bookingId, booking);
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUserId(@PathVariable Long userId) {
        return bookingService.getBookingsByUserId(userId);
    }

    @GetMapping
    public Booking getBookingsByReference(@RequestParam(required = true) String reference) {
        return bookingService.getBookingByReference(reference);
    }

    @PostMapping
    public Booking BookingCheckIn(@RequestParam(required = true) String reference) {
        return bookingService.BookingCheckIn(reference);
    }

}
