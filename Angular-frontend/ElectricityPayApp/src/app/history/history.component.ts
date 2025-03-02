import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InvoiceService } from '../invoice.service';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { TransactionsComponent } from "../transactions/transactions.component";
import { AlltransactionsComponent } from "../alltransactions/alltransactions.component";
import { TransactionService } from '../transactions/transaction.service';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule, TransactionsComponent, AlltransactionsComponent,NgbTooltipModule],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent {
  transactionSuccessDetails :any;
  allTransactionDetails :any;
  accordionItems: any;
  customerId!:string;
  searchTerm: string = '';
  isloading:boolean = true;
  constructor(private successfulTransactionService: TransactionService,private invoiceService: InvoiceService){}
  ngOnInit(): void {
    this.customerId = localStorage.getItem('id') || '';
    this.successfulTransactionService.getAllSuccessfullTransaction(this.customerId).subscribe(
      {
        next: (response)=>{
          this.allTransactionDetails = response;
          console.log(response);
          
          this.transactionSuccessDetails = response.filter((item:any)=>{
            return item.transactionStatus =='SUCCESS';
          });
          this.isloading = false;
        },
        error: (error)=>{
          console.log(error);
          this.isloading = false;
        }
      }
    )
  }

  toggleItem(index: number) {
    this.transactionSuccessDetails[index].isOpen = !this.transactionSuccessDetails[index].isOpen;
  }

  generateInvoice(transactionId : string) {
    this.invoiceService
      .downloadInvoice(transactionId)
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
