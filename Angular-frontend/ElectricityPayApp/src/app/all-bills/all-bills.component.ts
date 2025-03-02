import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import {
  DueBillsService,
  ElectricityBillDTO,
} from '../due-bills/due-bills.service';
import { InvoiceService } from '../invoice.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-all-bills',
  standalone: true,
  imports: [CommonModule, NgbTooltipModule, DatePipe, RouterLink, CurrencyPipe,FormsModule],
  templateUrl: './all-bills.component.html',
  styleUrl: './all-bills.component.css',
})
export class AllBillsComponent {

  allBills: ElectricityBillDTO[] = [];
  customerId: any;
  filteredBills: any[] = [];
  filterStatus: string = 'All';
  isloader:boolean = true;
  constructor(private electricityBillService: DueBillsService,private invoiceService: InvoiceService) {}

  ngOnInit(): void {
    this.customerId = localStorage.getItem('id');
    this.getAllBills(this.customerId);
    this.filterBills()
  }

  getAllBills(customerId: string): void {
    this.electricityBillService
      .getAllBillsByCustomerId(this.customerId)
      .subscribe({
        next:(data: ElectricityBillDTO[]) => {
          this.allBills = data;
          this.isloader = false;
        },
        error:(error: any) => {
          this.isloader = false;
        }
  });
  }

  filterBills():any {
    if (this.filterStatus == 'DUE' || this.filterStatus =='PAID') {
      const statusFilterLower = this.filterStatus.toLowerCase(); 
      this.filteredBills = this.allBills.filter(bill => bill.status.toLowerCase() === statusFilterLower);
      return this.filteredBills;
    }
    return this.allBills;
  }

  generateInvoice(billId : number) {
    this.invoiceService
      .downloadInvoiceByBillId(billId)
      .subscribe((response: Blob) => {
        const url = window.URL.createObjectURL(response);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'invoice.pdf';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
      });
  }
}
