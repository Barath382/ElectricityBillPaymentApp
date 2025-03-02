import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BehaviorSubject, Observable} from 'rxjs';

export interface ElectricityBillDTO {
  billId: number;
  customerId:string;
  customerName:string;
  email:string;
  wallet:number;
  units: number;
  billDate: string;
  dueDate: string;
  durationOfBill: string;
  billType: string;
  status: string;
  totalAmount:number;
}

@Injectable({
  providedIn: 'root'
})
export class DueBillsService {

  http = inject(HttpClient);
  constructor() { }

  
  private baseUrl = 'http://localhost:8080/bills';

  getAllBillsByCustomerId(customerId: string): Observable<ElectricityBillDTO[]> {
    const params = {customerId};
    return this.http.get<ElectricityBillDTO[]>(`${this.baseUrl}/customer`,{params})
  }

  getDueBillsByCustomerId(customerId: string): Observable<ElectricityBillDTO[]> {
    const params = {customerId};
    return this.http.get<ElectricityBillDTO[]>(`${this.baseUrl}/due`,{params})
  }


}
