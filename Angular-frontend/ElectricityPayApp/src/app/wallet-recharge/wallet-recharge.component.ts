import { CurrencyPipe } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import Swal from 'sweetalert2'
import { AuthServiceService } from '../auth/auth-service.service';

@Component({
  selector: 'app-wallet-recharge',
  standalone: true,
  imports: [RouterLink,CurrencyPipe,FormsModule],
  templateUrl: './wallet-recharge.component.html',
  styleUrl: './wallet-recharge.component.css'
})
export class WalletRechargeComponent{
  constructor(private customerService: AuthServiceService){
  }


  walletBalance:number = 0.0;
  walletAmount: number = 0;
  updateWallet() {
    const customerId = localStorage.getItem('id')||''; 
    if (this.walletAmount > 0) {
      this.customerService.updateWallet(customerId, this.walletAmount).subscribe({
        next:(updatedCustomer: any) => {
          Swal.fire({
            title: "Good job!",
            text: `Wallet updated successfully. New Balance: ${updatedCustomer.wallet}`,
            icon: "success"
          });
        },
        error:(error) => {
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: `Failed to update wallet. Customer not found.`,
          });
        }
    });
    }
  }}

