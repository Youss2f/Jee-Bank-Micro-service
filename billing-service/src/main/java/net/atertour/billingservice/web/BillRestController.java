package net.atertour.billingservice.web;

import net.atertour.billingservice.dto.BillRequest;
import net.atertour.billingservice.entities.Bill;
import net.atertour.billingservice.service.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bills")
public class BillRestController {
    private final BillService billService;

    public BillRestController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody BillRequest billRequest) {
        try {
            Bill bill = billService.createBill(billRequest.getCustomerId(), billRequest.getProductItems());
            return ResponseEntity.ok(bill);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
