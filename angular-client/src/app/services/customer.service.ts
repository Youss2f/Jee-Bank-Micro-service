import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private host = "http://localhost:8888";

  constructor(private http: HttpClient) { }

  public getCustomers(): Observable<any> {
    return this.http.get(this.host + "/api/customers");
  }
}
