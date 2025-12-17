import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Import CommonModule
import { CustomerService } from '../services/customer.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-customers',
  standalone: true, // Mark as standalone
  imports: [CommonModule], // Import CommonModule for *ngIf, *ngFor
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {
  customers: any;

  constructor(private customerService: CustomerService, private router: Router) { }

  ngOnInit(): void {
    this.customerService.getCustomers().subscribe({
      next: (data) => {
        this.customers = data;
      },
      error: (err) => {
        console.error("Error fetching customers:", err);
      }
    });
  }

  getOrders(c: any) {
    this.router.navigateByUrl("/bills/" + c.id);
  }
}
