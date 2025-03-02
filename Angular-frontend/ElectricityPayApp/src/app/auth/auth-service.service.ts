import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  http = inject(HttpClient);
  private loggedIn: boolean = false;
  private baseUrl = 'http://localhost:8080/auth'; 

   // send OTP
   sendOtp(customerId: string): Observable<any> {
    const params = { customerId };
    return this.http.post(`${this.baseUrl}/send-otp`, null, { params });
  }

  validateOtp(customerId: string, otp: string): Observable<any> {
    const params = { customerId, otp };    
    return this.http.post(`${this.baseUrl}/validate-otp`, null, { params }).pipe(
      tap((response: any) => {
          localStorage.setItem('id',response.customerId );
          this.loggedIn = true;
      })
  );
  }
  
  resendOtp(customerId: string): Observable<any> {
    const params = { customerId };
    return this.http.post(`${this.baseUrl}/resend-otp`, null, { params });
  }

  getCustomerData(customerId: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/get-customer/${customerId}`)
  }

   updateWallet(customerId: string, amount: number): Observable<any> {
    const params={amount};
    return this.http.put<any>(`http://localhost:8080/auth/wallet/${customerId}`,{},{params});
  }

  login() {
    this.loggedIn = true;
  }

  logout() {
    localStorage.removeItem('id');
    this.loggedIn = false;
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('id');
  }

}
