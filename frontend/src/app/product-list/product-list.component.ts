import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {catchError, Observable, of} from 'rxjs';
import {BASE_URL} from '../APIService';
import {IProductType} from '../../Mock';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-product-list',
  imports: [
    NgForOf
  ],
  templateUrl: './product-list.component.html',
  standalone: true,
  styleUrl: './product-list.component.css'
})
export class ProductListComponent implements OnInit {
  dataSource: IProductType[]

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit() {
    return this.getData().subscribe(res => { this.dataSource = res });
  }


  getData(): Observable<IProductType[]> {
    console.log('fetching')
    const headers = new HttpHeaders({ 'Access-Control-Allow-Origin': '*' });
    const response = this.http.get<IProductType[]>(`${BASE_URL}`, {
      headers: headers
    })
      .pipe(
        catchError
          (this.handleError<[]>('getData', []))
      );

    console.log('response= ', response);
    return response;
  }
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error('Error is=', error); // log to console instead
      return of(result as T);
    };
  }

  onEdit(e) {
    // console.log('Editing', e);
    // console.log(JSON.stringify(e));
    this.router.navigate(['add', { data: JSON.stringify(e) }])
    // document.getElementById('').values()
    // console.log(document.getElementById('code'))
  }

  onDelete(e) {
    console.log('deleting', e)
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      "Access-Control-Allow-Methods": "DELETE, POST, GET, OPTIONS"
    });

    this.http.delete(`${BASE_URL + e}`, {
      headers: headers
    }).subscribe(
      () => {this.getData().subscribe(res => { this.dataSource = res })},
    )
  }

}

export interface PeriodicElement {
  category: string;
  code: number;
  name: number;
  price: string;
}
