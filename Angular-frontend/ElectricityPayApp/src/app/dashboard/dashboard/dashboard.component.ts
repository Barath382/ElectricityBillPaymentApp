import { Component } from '@angular/core';
import { NavBarComponent } from "../../NavBar/nav-bar/nav-bar.component";
import { RouterLink } from '@angular/router';
import { AuthServiceService } from '../../auth/auth-service.service';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2'
import { DueBillsService } from '../../due-bills/due-bills.service';
import { WalletRechargeComponent } from "../../wallet-recharge/wallet-recharge.component";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [NavBarComponent, RouterLink, CurrencyPipe, FormsModule, CommonModule, WalletRechargeComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent{

  customerData: any={};

  walletAmount:number=0;

  dueBillsCount : number = 0;

  isloader!:boolean;

  constructor(private customerService: AuthServiceService,private electricityBillService: DueBillsService) {
    this.isloader = true;
    const customerId = localStorage.getItem('id');
    if (customerId !== null) {
      this.customerService.getCustomerData(customerId).subscribe({
        next: (response)=>{
          this.customerData = response;
          this.isloader = false;
        },
        error:(error)=>{
          console.log(error.message);
          this.isloader = false
        }
      })
      this.electricityBillService
        .getDueBillsByCustomerId(customerId)
        .subscribe({
          next: (data) => {
           this.dueBillsCount = data.length;
          },
          error: (error: any) => {
          },
        });
    
      }

  }

  }