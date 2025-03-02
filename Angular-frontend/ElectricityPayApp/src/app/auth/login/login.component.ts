import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { AuthServiceService } from '../auth-service.service';
import { Subject } from 'rxjs';
import { debounceTime, tap } from 'rxjs/operators';
import { NgbAlert, NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule, NgbTooltipModule, NgbAlertModule],
  templateUrl: './login.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  isLoading: boolean = false;
  isShowOtpField: boolean = false;
  customer!: Customer;

  type!: string;
  private _message$ = new Subject<string>();
  successMessage = '';
  @ViewChild('selfClosingAlert', { static: false })
  selfClosingAlert: NgbAlert = new NgbAlert();

  constructor(private authService: AuthServiceService, private router: Router) {
    this._message$
      .pipe(
        takeUntilDestroyed(),
        tap((message) => (this.successMessage = message)),
        debounceTime(2000)
      )
      .subscribe(() => this.selfClosingAlert?.close());
  }

  ngOnInit(): void {
    this.customer = {
      customerId: '',
      customerOtp: '',
    };
  }

  otpSent: boolean = false;
  isOtpExpired: boolean = false;
  isShowResend: boolean = false;
  minutes: number = 2;
  seconds: number = 0;
  private interval: any;

  sendOtp() {
    this.isLoading = true;
    this.authService.sendOtp(this.customer.customerId).subscribe({
      next: (response) => {
        this.type = 'success';
        this._message$.next(response.message);
        this.isShowOtpField = true;
        this.isLoading = false;
        this.startTimer();
        this.otpSent = true;
      },
      error: (err) => {
        this.isLoading = false;
        this.type = 'danger';
        this._message$.next(`Customer not found`);
        console.error('Error sending OTP:', err.status);
      },
    });
  }

  validateOtp() {
    this.isLoading = true;
    this.authService
      .validateOtp(this.customer.customerId, this.customer.customerOtp)
      .subscribe({
        next: (response) => {
          this.isLoading = false;
          this.type = 'success';
          this._message$.next('Login successful');
          this.router.navigate(['/dashboard']);
        },
        error: (err) => {
          this.isLoading = false;
          console.error('Error validating OTP:', err.status);
          if (err.status === 401) {
            this.type = 'warnin';
            this._message$.next('Invalid or expired OTP');
          } else {
            this.type = 'danger';
            this._message$.next('Invalid or expired OTP');
          }
        },
      });
    this.isLoading = false;
  }

  resendOtp() {
    this.isLoading = true;
    this.customer.customerOtp=" ";
    this.authService.resendOtp(this.customer.customerId).subscribe({
      next: (response) => {
        this.type = 'success';
        this._message$.next(response.message);
        this.isLoading = false;
        this.resetTimer();
      },
      error: (err) => {
        this.type = 'danger';
        this._message$.next('Error resending OTP');
        this.isLoading = false;
      },
    });
  }

  startTimer() {
    this.isOtpExpired = false;
    this.minutes = 3;
    this.seconds = 0;

    this.interval = setInterval(() => {
      if (this.seconds === 0) {
        if (this.minutes === 0) {
          this.isShowResend = true;
          this.isOtpExpired = true;
          clearInterval(this.interval);
          this.changeSuccessMessage('OTP Expired');
        } else {
          this.minutes--;
          this.seconds = 59;
        }
      } else {
        this.seconds--;
      }
    }, 1000);
  }

  public changeSuccessMessage(msg: string) {
    this.type = 'danger';
    this._message$.next(msg);
  }

  resetTimer() {
    this.isShowResend = false;
    clearInterval(this.interval);
    this.startTimer();
  }
}

class Customer {
  customerId!: string;
  customerOtp!: string;
}
