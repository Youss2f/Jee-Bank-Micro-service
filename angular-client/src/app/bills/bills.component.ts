import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { BillingService } from '../services/billing.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-bills',
  standalone: true,
  imports: [CommonModule],
  providers: [DatePipe],
  templateUrl: './bills.component.html',
  styleUrls: ['./bills.component.css']
})
export class BillsComponent implements OnInit {
  bills: any;
  customerId: number = 0;

  constructor(private billingService: BillingService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.customerId = this.route.snapshot.params['customerId'];

    this.billingService.getBillsByCustomer(this.customerId).subscribe({
      next: (data) => {
        this.bills = data;
      },
      error: (err) => {
        console.error("Error fetching bills:", err);
      }
    });
  }
}
