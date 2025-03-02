import { Component, OnInit } from '@angular/core';
import {
  DueBillsService,
  ElectricityBillDTO,
} from '../due-bills/due-bills.service';
import {
  PaymentRequest,
  PaymentServiceService,
} from './payment-service.service';
import { MatTabsModule } from '@angular/material/tabs';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-bill-payment',
  standalone: true,
  imports: [
    MatTabsModule,
    RouterLink,
    DatePipe,
    CurrencyPipe,
    ReactiveFormsModule,
    CommonModule,
    FormsModule,
  ],
  templateUrl: './bill-payment.component.html',
  styleUrl: './bill-payment.component.css',
})
export class BillPaymentComponent implements OnInit {
  billIds!: number[];
  bills: ElectricityBillDTO[] = [];
  totalAmountAfterDiscount: number = 0;
  totalAmount: number = 0;
  totalUnits: number = 0;
  paymentCardForm!: FormGroup;
  todayDate!: string;
  beforeDue: number = 0;
  otpForm!: FormGroup;
  transactionId!: string[];
  wallet!: number;
  cardNumber!: string;
  isPaymentValid: boolean = false;
  isloader: boolean = false;
  customerName!: string;
  customerId!: string;
  email!: string;
  ismainpage: boolean = true;
  constructor(
    private paymentService: PaymentServiceService,
    private router: Router,
    private route: ActivatedRoute
  ) {}
  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const arrayString = params.get('number');
      this.billIds = arrayString ? arrayString.split(',').map(Number) : [];
    });
    this.todayDate = new Date().toISOString().split('T')[0];
    this.getBills();
    this.paymentCardForm = new FormGroup({
      billIds: new FormControl(this.billIds, [Validators.required]),
      cardNumber: new FormControl('', [
        Validators.required,
        Validators.minLength(16),
        Validators.maxLength(16),
        Validators.pattern(/^\d{16}$/),
      ]),
      cardExpireDate: new FormControl('', [
        Validators.required,
        this.expiryDateValidator,
      ]),
      cardCvv: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(3),
        Validators.pattern('^[0-9]*$'),
      ]),
      payAmount: new FormControl(this.totalAmountAfterDiscount, [
        Validators.required,
      ]),
      transactionMethod: new FormControl('', [Validators.required]),
    });

    this.otpForm = new FormGroup({
      otp: new FormControl('', Validators.required),
    });
  }

  getBills() {
    this.beforeDue = 0;
    this.paymentService.getBillsByIds(this.billIds).subscribe({
      next: (data: ElectricityBillDTO[]) => {
        this.bills = data;
        this.bills.map((bill) => {
          this.wallet = bill.wallet;
          this.customerName = bill.customerName;
          this.email = bill.email;
          this.customerId = bill.customerId;
          this.totalAmount += bill.totalAmount;
          if (bill.dueDate < this.todayDate) {
            this.totalAmountAfterDiscount += bill.totalAmount * 0.95;
          } else if (bill.dueDate >= this.todayDate) {
            this.beforeDue++;
            this.totalAmountAfterDiscount += bill.totalAmount * 0.95 * 0.95;
          }
          this.totalUnits += bill.units;
          this.ismainpage = false;
        });
      },
      error: (error) => {
        console.error('Error fetching bills', error);
        this.ismainpage = false;
      },
    });
  }

  validateCard() {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You want to make this payment',
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes',
    }).then((result) => {
      if (result.isConfirmed) {
        this.paymentCardForm.value.payAmount =
        this.totalAmountAfterDiscount.toFixed(2);
        this.paymentCardForm.value.billIds = this.billIds;
        this.paymentCardForm.value.transactionMethod = 'CARD';
        this.isloader = true;
        const paymentRequest: PaymentRequest = this.paymentCardForm.value;
        this.paymentService.validateCard(paymentRequest).subscribe({
          next: (response) => {
            this.isPaymentValid = true;
            this.isloader = false;
            this.cardNumber = response.cardNumber;
            this.transactionId = response.transactionId;
            Swal.fire(`${response.message}`);
            this.paymentCardForm.reset();
          },

          error: (error: HttpErrorResponse) => {
            this.isloader = false;
            this.isPaymentValid = false;
            let errorMessage: string;
            if (error.status === 400) {
              errorMessage = error.error.message.join(', ');
            } else {
              errorMessage = 'An unexpected error occurred. Please try again.';
            }
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: `${errorMessage}`,
              timer: 2500,
            });
            this.paymentCardForm.reset();
          },
        });
      }
    });
  }

  validateOtp() {
    console.log(this.otpForm.value);
    const otp = this.otpForm.get('otp')?.value;
    this.isloader = true;
    this.paymentService
      .validateOtp(otp, this.cardNumber, this.transactionId)
      .subscribe({
        next: (response) => {
          this.isPaymentValid = false;
          this.isloader = false;
          Swal.fire({
            title: 'Good job!',
            text: `${response.message}`,
            icon: 'success',
          });

          this.paymentCardForm.reset();
          this.otpForm.reset();
          this.router.navigate(['/history']);
        },
        error: (error) => {
          this.otpForm.value.otp = ' ';
          this.isPaymentValid = false;
          this.isloader = false;
          this.paymentCardForm.reset();
          this.otpForm.reset();
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: `${error.error}`,
            timer: 2500,
          });
        },
      });
  }

  handleWalletPayment() {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You want to make this payment',
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes',
    }).then((result) => {
      if (result.isConfirmed) {
        this.paymentCardForm.value.payAmount =
          this.totalAmountAfterDiscount * 0.9;
        this.paymentCardForm.value.billIds = this.billIds;
        this.paymentCardForm.value.transactionMethod = 'Wallet';
        const paymentRequest: PaymentRequest = this.paymentCardForm.value;
        this.isloader = true;
        const customerId = localStorage.getItem('id') || '';
        this.paymentService
          .processPayment(paymentRequest, customerId)
          .subscribe({
            next: (response) => {
              this.isloader = false;
              Swal.fire({
                title: 'Good job!',
                text: `${response.message}`,
                icon: 'success',
              });
              this.router.navigate(['/duebills']);
            },
            error: (error) => {
              this.isloader = false;
              Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: `${error.error}`,
                timer: 2500,
              });
            },
          });
      }
    });
  }

  expiryDateValidator(control: any) {
    if (!control.value) return null;
    const [month, year] = control.value.split('/');
    const isValidFormat = /^\d{2}\/\d{4}$/.test(control.value);
    const now = new Date();
    const expDate = new Date(+year, +month - 1);
    const expireYearLimit = now.getFullYear()+6;
    
    if (!isValidFormat || expDate < now || month > 12 || year > expireYearLimit) {
      return { invalidDate: true };
    }
    return null;
  }

  allowOnlyNumbers(event: KeyboardEvent) {
    const inputChar = String.fromCharCode(event.charCode);
    if (!/^\d$/.test(inputChar)) {
      event.preventDefault();
    }
  }

  canclePayment() {
    alert('payment canceled');
  }
}
