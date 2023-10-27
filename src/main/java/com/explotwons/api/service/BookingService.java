package com.explotwons.api.service;

import com.explotwons.api.entity.Booking;
import com.explotwons.api.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {

        booking.setCreatedDate(new Date());
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long bookingId, Booking updatedBooking) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);

        if (!optionalBooking.isPresent()) {
            throw new EntityNotFoundException("No booking found for the given ID");
        }

        Booking existingBooking = optionalBooking.get();

        // Update the fields that can be changed
        existingBooking.setBookingDate(updatedBooking.getBookingDate());
        existingBooking.setNumberOfPersons(updatedBooking.getNumberOfPersons());

        // Here, we're assuming that an experience can't be changed once booked.
        // If that's not the case, you'd update the experience as well.

        return bookingRepository.save(existingBooking);
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Booking getBookingByReference(String reference) {
        return bookingRepository.findByReferenceNumber(reference);
    }

    public Booking CheckIn(String reference){
        Booking optionalBooking = bookingRepository.findByReferenceNumber(reference);

        if (optionalBooking == null) {
            throw new EntityNotFoundException("No booking found for the given ID");
        }

        optionalBooking.setStatus("USED");

        return optionalBooking;

    }
}
