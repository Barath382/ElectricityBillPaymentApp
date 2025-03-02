import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  http = inject(HttpClient);
  constructor() { }

  downloadInvoice(transactionId: string) {
    const url = `http://localhost:8080/invoice/generate/${transactionId}`;
    return this.http.get(url, { responseType: 'blob' });
  }

  downloadInvoiceByBillId(billId: number) {
    const url = `http://localhost:8080/invoice/generate-bill/${billId}`;
    return this.http.get(url, { responseType: 'blob' });
  }
}
