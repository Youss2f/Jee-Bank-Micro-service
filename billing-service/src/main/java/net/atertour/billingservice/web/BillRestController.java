package net.atertour.billingservice.web;

import jakarta.validation.Valid;
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
    public ResponseEntity<Bill> createBill(@Valid @RequestBody BillRequest billRequest) {
        Bill bill = billService.createBill(billRequest.getCustomerId(), billRequest.getProductItems());
        return ResponseEntity.ok(bill);
    }

    @GetMapping("/fullBill/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id) {
        return billService.getBill(id);
    }

    @GetMapping("/byCustomer/{customerId}")
    public java.util.List<Bill> getBillsByCustomer(@PathVariable(name = "customerId") Long customerId) {
        return billService.getBillsByCustomerId(customerId);
    }
}
