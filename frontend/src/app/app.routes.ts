import {Routes} from '@angular/router';
import {ProductListComponent} from './product-list/product-list.component';
import {ProductAddComponent} from './product-add/product-add.component';

export const routes: Routes = [
  {path: '', component: ProductListComponent},
  {path: 'add', component: ProductAddComponent, data: {}}
];
