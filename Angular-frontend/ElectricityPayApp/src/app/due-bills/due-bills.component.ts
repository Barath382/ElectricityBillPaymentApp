import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { DueBillsService, ElectricityBillDTO } from './due-bills.service';
import {
  NgbAlert,
  NgbAlertModule,
  NgbTooltipModule,
} from '@ng-bootstrap/ng-bootstrap';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { debounceTime, Subject, tap } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-due-bills',
  standalone: true,
  imports: [
    CommonModule,
    NgbTooltipModule,
    DatePipe,
    RouterLink,
    FormsModule,
    CurrencyPipe,
    NgbAlertModule,
    FormsModule
  ],
  encapsulation: ViewEncapsulation.None,
  templateUrl: './due-bills.component.html',
  styleUrl: './due-bills.component.css',
})
export class DueBillsComponent {
  todayDate: any;
  duebills: ElectricityBillDTO[] = [];
  customerId: any;
  selectedBills: number[] = [];
  payTotalAmount: number = 0;
  totalAmount: number = 0;
  isPayBtn: boolean = false;
  isChecked: boolean = false;
  isloader = true;


  type!: string;
  private _message$ = new Subject<string>();
  successMessage = '';
  @ViewChild('selfClosingAlert', { static: false })
  selfClosingAlert: NgbAlert = new NgbAlert();

  constructor(
    private electricityBillService: DueBillsService,
    private router: Router
  ) {
    
    this._message$
      .pipe(
        takeUntilDestroyed(),
        tap((message) => (this.successMessage = message)),
        debounceTime(2000)
      )
      .subscribe(() => this.selfClosingAlert?.close());
  }

  ngOnInit(): void {
    this.todayDate = new Date().toISOString().split('T')[0];
    this.customerId = localStorage.getItem('id');
    this.getDueBills();
  }

  getDueBills(): void {
    this.electricityBillService
      .getDueBillsByCustomerId(this.customerId)
      .subscribe({
        next: (data: ElectricityBillDTO[]) => {
          this.isloader = false;
          this.duebills = data;
          console.log(data);
          
          this.duebills.map((bill) => {
            this.totalAmount += bill.totalAmount;
          });
        },
        error: (error: any) => {
          this.isloader = false;
        },
      });
  }

  handleSelectedBills(event: any, bill: ElectricityBillDTO) {
    if (event.target.checked) {
      this.isChecked = true;
      this.selectedBills.push(bill.billId);
      this.payTotalAmount += bill.totalAmount;
    } else {
      this.selectedBills = this.selectedBills.filter(
        (selectedBill) => selectedBill !== bill.billId
      );
      this.isChecked = false
      this.payTotalAmount -= bill.totalAmount;
    }
    if (this.selectedBills.length > 0) {
      this.isPayBtn = true;
    }
  }

  setSelectedBill() {
    if (this.selectedBills.length > 0) {
      const arrayString = this.selectedBills.join(',');  
      this.router.navigate(['/payment',arrayString]);
    } else {
      this.type = 'danger';
      this._message$.next('Select Bills For Proceeding Payment');
    }
  }
}
