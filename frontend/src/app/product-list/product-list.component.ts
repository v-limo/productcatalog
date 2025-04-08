import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {catchError, Observable, of} from 'rxjs';
import {BASE_URL} from '../APIService';
import {IProductType} from '../../Mock';

@Component({
  selector: 'app-product-list',
  imports: [],
  templateUrl: './product-list.component.html',
  standalone: true,
  styleUrl: './product-list.component.css',
})
export class ProductListComponent implements OnInit {

  dataSource: IProductType[] = [];

  constructor(private http: HttpClient, private router: Router) {
  }

  ngOnInit() {
    this.getData().subscribe((response) => {
      this.dataSource = response;
    });
  }

  getData(): Observable<IProductType[]> {
    const headers = new HttpHeaders({'Access-Control-Allow-Origin': '*'});
    return this.http
      .get<IProductType[]>(`${BASE_URL}`, {
        headers: headers,
      })
      .pipe(catchError(this.handleError<[]>('getData', [])));
  }

  private handleError<T>(_operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error('Error is=', error);
      return of(result as T);
    };
  }

  async onEdit(productToEdit: IProductType): Promise<void> {
    await this.router.navigate([
      'add',
      {data: JSON.stringify(productToEdit)},
    ]);
  }

  onDelete(id: number): void {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'DELETE, POST, GET, OPTIONS',
    });

    this.http.delete(`${BASE_URL}/${id}`, {headers}).subscribe(() =>
      this.getData().subscribe((response) => {
        this.dataSource = response;
      })
    );
  }
}
