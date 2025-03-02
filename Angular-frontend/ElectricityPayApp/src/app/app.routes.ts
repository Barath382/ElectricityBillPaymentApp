import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { DashboardComponent } from './dashboard/dashboard/dashboard.component';
import { AuthGuard } from './auth/auth-guard.guard';
import { LayoutComponent } from './layout/layout.component';
import { DueBillsComponent } from './due-bills/due-bills.component';
import { AllBillsComponent } from './all-bills/all-bills.component';
import { BillPaymentComponent } from './bill-payment/bill-payment.component';
import { WalletComponent } from './wallet/wallet.component';
import { HistoryComponent } from './history/history.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    {
        path: '',
        component: LayoutComponent, 
        children: [
            { path: 'dashboard', component: DashboardComponent,canActivate: [AuthGuard] },
            {path : 'duebills' , component: DueBillsComponent, canActivate:[AuthGuard] },
            {path : 'allbills' , component: AllBillsComponent, canActivate:[AuthGuard] },
            {path:'payment/:number', component:BillPaymentComponent, canActivate:[AuthGuard]},
            {path:'wallet', component:WalletComponent, canActivate:[AuthGuard]},
            {path:'history', component:HistoryComponent, canActivate:[AuthGuard]},
        ]
      },
    
    { path: '**', component: PageNotFoundComponent },
];
