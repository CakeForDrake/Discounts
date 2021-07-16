package com.exadel.sandbox.team5;

import com.exadel.sandbox.team5.dto.OrderDto;
import com.exadel.sandbox.team5.service.OrderService;
import com.exadel.sandbox.team5.service.QRCodeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;
    private final QRCodeService qrCodeService;

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @GetMapping
    public List<OrderDto> getAll() {
        return orderService.getAll();
    }

    @PutMapping("/{id}")
    public OrderDto update(@PathVariable Long id, @RequestBody OrderDto order) {
        order.setId(id);
        return orderService.update(order);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }


    @ApiOperation("Create order from register user and return QR code with link")
    @PostMapping(value = "/create/{discountId}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] create(@PathVariable String discountId) {
        return qrCodeService.generateQRCode(orderService.createOrder(discountId));
    }

    @ApiOperation("Checks link if unique code of employee exists in database and promocode has not expired")
    @GetMapping(value = "/validate/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public String validateQRCode(@PathVariable String uuid) {
        return qrCodeService.validateQR(uuid);
    }
}
