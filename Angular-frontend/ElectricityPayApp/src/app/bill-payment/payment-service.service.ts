import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { ElectricityBillDTO } from '../due-bills/due-bills.service';

export interface PaymentRequest {
  billIds: number[];
  cardNumber: string;
  cardExpireDate: string;
  cardCvv: number;
  wallet: string;
  payAmount: number;
  transactionMethod: 'CARD' | 'CASH' | 'Wallet';
}

@Injectable({
  providedIn: 'root'
})

export class PaymentServiceService {

  private billUrl = 'http://localhost:8080/bills';
  private paymentUrl = 'http://localhost:8080/payment-request';

  http = inject(HttpClient)
  
  getBillsByIds(billIds: number[]): Observable<ElectricityBillDTO[]> {
    const params = {billIds}
    return this.http.get<ElectricityBillDTO[]>(`${this.billUrl}/bill`, {params});
  }

  processPayment(paymentRequest: PaymentRequest, customerId: string): Observable<any> {  
      const params = {customerId};
    return this.http.post<any>(`${this.paymentUrl}/validate-wallet`, paymentRequest,{params});
  }

  validateCard(paymentRequest: PaymentRequest): Observable<any> {
      console.log(paymentRequest);
      
    return this.http.post<any>(`${this.paymentUrl}/validate-card`, paymentRequest).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => error);
      })
    );
  }

  validateOtp(otp: string, cardNumber: string, transactionId : string[]): Observable<any> {
    const params = { otp: otp,cardNumber: cardNumber,transactionId: transactionId };
    return this.http.post(`${this.paymentUrl}/validate-otp`, {}, {params});
  }

  isAuthenticatedForPayment():boolean{
    return !!localStorage.getItem('payIds')
  }

}