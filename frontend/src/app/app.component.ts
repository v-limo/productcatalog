import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {TopBarComponent} from './top-bar/top-bar.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, TopBarComponent],
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.css'
})
export class AppComponent {
}
