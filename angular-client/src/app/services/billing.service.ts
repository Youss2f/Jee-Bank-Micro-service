import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BillingService {
  private host = "http://localhost:8888";

  constructor(private http: HttpClient) { }

  public getBill(id: number): Observable<any> {
    return this.http.get(this.host + "/api/bills/fullBill/" + id);
  }

  public getBillsByCustomer(customerId: number): Observable<any> {
    return this.http.get(this.host + "/api/bills/byCustomer/" + customerId);
  }
}
