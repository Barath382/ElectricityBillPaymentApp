import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TransactionService } from './transaction.service';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InvoiceService } from '../invoice.service';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [RouterLink,CommonModule,FormsModule,NgbTooltipModule],
  templateUrl: './transactions.component.html',
  styleUrl: './transactions.component.css'
})
export class TransactionsComponent {
  @Input() transactionSuccessDetails :any;
  accordionItems: any;
  customerId!:string;
  constructor(private successfulTransactionService: TransactionService,private invoiceService: InvoiceService){
    this.customerId = localStorage.getItem('id')||'';
  }

  toggleItem(index: number) {
    this.transactionSuccessDetails[index].isOpen = !this.transactionSuccessDetails[index].isOpen;
  }

  @Output() messageEvent = new EventEmitter<string>(); 

  generateInvoice(transactionId : string) {
    this.messageEvent.emit(transactionId);
  }

}
