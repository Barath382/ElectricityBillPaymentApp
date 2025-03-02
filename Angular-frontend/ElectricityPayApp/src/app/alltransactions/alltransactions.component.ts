import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { TransactionService } from '../transactions/transaction.service';
import { InvoiceService } from '../invoice.service';

@Component({
  selector: 'app-alltransactions',
  standalone: true,
  imports: [RouterLink,CommonModule,FormsModule,NgbTooltipModule],
  templateUrl: './alltransactions.component.html',
  styleUrl: './alltransactions.component.css'
})
export class AlltransactionsComponent {
  @Input() allTransactionDetails :any;
  accordionItems: any;
  customerId: string;
  constructor(private successfulTransactionService: TransactionService,private invoiceService: InvoiceService){
    this.customerId = localStorage.getItem('id')||'';
  }

  toggleItem(index: number) {
    this.allTransactionDetails[index].isOpen = !this.allTransactionDetails[index].isOpen;
  }


  @Output() messageEvent = new EventEmitter<string>(); 

  generateInvoice(transactionId : string) {
    this.messageEvent.emit(transactionId);
  }
}
