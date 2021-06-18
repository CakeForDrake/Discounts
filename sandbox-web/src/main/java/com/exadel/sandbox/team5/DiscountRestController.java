package com.exadel.sandbox.team5;

import com.exadel.sandbox.team5.entity.BookingList;
import com.exadel.sandbox.team5.entity.Discount;
import com.exadel.sandbox.team5.entity.Review;
import com.exadel.sandbox.team5.service.DiscountService;
import com.exadel.sandbox.team5.service.ReviewService;
import com.exadel.sandbox.team5.service.impl.BookingListServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discounts")
@RequiredArgsConstructor
public class DiscountRestController {

    private final DiscountService service;
    private final ReviewService reviewService;
    private final BookingListServiceImpl bookingListService;

    @GetMapping("/{id}")
    public Discount getDiscount(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/all")
    public List<Discount> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Discount save(@RequestBody Discount entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public Discount update(@PathVariable Long id, @RequestBody Discount entity) {
        entity.setId(id);
        return service.update(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{discountId}/reviews")
    public List<Review> getReviewsByDiscount(@PathVariable Long discountId) {
        return reviewService.getReviewsByDiscount(discountId);
    }

    @PostMapping("/{discountId}/order")
    public BookingList saveOrder(@PathVariable Long discountId) {
        return bookingListService.createOrder(discountId);
    }

    @GetMapping("/{discountId}/{promoCode}/validate")
    public BookingList invalidatePromoCode(@PathVariable Long discountId, @PathVariable String promoCode) {
        return bookingListService.invalidatePromoCode(discountId, promoCode);
    }


}

