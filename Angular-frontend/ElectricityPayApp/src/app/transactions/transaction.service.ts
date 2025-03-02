import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  http = inject(HttpClient);
  private transactionUrl = 'http://localhost:8080/transaction';
  constructor() { }

  getAllSuccessfullTransaction(customerId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.transactionUrl}/successful-transactions/${customerId}`)
  }
}
