import { Routes } from '@angular/router';
import { CustomersComponent } from './customers/customers.component';
import { ProductsComponent } from './products/products.component';
import { BillsComponent } from './bills/bills.component';

export const routes: Routes = [
    { path: 'customers', component: CustomersComponent },
    { path: 'products', component: ProductsComponent },
    { path: 'bills/:customerId', component: BillsComponent },
    { path: '', redirectTo: '/customers', pathMatch: 'full' }
];
