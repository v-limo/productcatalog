import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {BASE_URL} from '../APIService';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-product-add',
  imports: [
    ReactiveFormsModule,
    NgForOf
  ],
  templateUrl: './product-add.component.html',
  standalone: true,
  styleUrl: './product-add.component.css'
})
export class ProductAddComponent implements OnInit {

  disabled: boolean = false;

  keyValue: Array<Object> = [];

  productForm: FormGroup;
  formData = {};
  defaultValue;

  constructor(private formBuilder: FormBuilder, private route: ActivatedRoute, private http: HttpClient, private router: Router) {
    this.productForm = this.formBuilder.group({
      name: ['', Validators.required],
      category: ['', Validators.required],
      code: ['', Validators.required],
      price: ['', Validators.required],
      key: ['', Validators.required],
      value: ['', Validators.required],
      details: this.formBuilder.array([
          this.formBuilder.group({
            key: [''],
            value: ['']
          })
      ])
    })
    if (this.route.snapshot.params['data'] != null)
    this.formData =  JSON.parse(this.route.snapshot.params['data']);

}
  onClick(key, value): void {
    (this.productForm.get('details') as FormArray).push(
        this.formBuilder.group({
          key: [''],
          value: ['']
        })
    );
  }

  onChange() {
    this.disabled = false;
  }

  onSubmit(data): void {
    console.log('form data 1', this.formData);
    // console.log('id =', this.formData["id"])
    const productDetail = {
      name: data.value.name,
      category: data.value.category,
      code: data.value.code,
      price: data.value.price,
      details: data.value.details,
      id: this.formData['id']
    }
    console.log('form data 2', productDetail);
    if(this.formData['id'] == undefined) {
        this.http.post(`${BASE_URL}`, productDetail).subscribe(
        (response) => console.log(response),
        (error) => console.log(error)
      )
    }
    else {
      this.http.put(`${BASE_URL + this.formData['id']}`, productDetail).subscribe(
        (response) => console.log(response),
        (error) => console.log(error)
      )
    }
    this.router.navigate([''])
  }

  ngOnInit(): void {

    if(this.formData['details'] != null) {
      while ((this.productForm.get('details') as any).controls.length < this.formData['details'].length) {
        this.onClick('', '');
      }
    }

    console.log(this.formData)
    if (this.formData != null) {
        this.productForm.patchValue({
        code: this.formData['code'],
        name: this.formData['name'],
        category: this.formData['category'],
        price: this.formData['price'],
        details: this.formData['details']
      });
    }
  }

}
// this.router.routerState.snapshot

