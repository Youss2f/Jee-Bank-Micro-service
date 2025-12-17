import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InventoryService {
  private host = "http://localhost:8888";

  constructor(private http: HttpClient) { }

  public getProducts(): Observable<any> {
    return this.http.get(this.host + "/api/products");
  }
}
