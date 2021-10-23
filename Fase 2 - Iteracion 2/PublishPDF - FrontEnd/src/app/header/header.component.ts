import { ClickleableOption } from '../model/html/option.model';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  link: string = '/user/home';
  @Input('header-options') headOpts!: ClickleableOption[];
  @Input('profile-options') profOpts!: ClickleableOption[];

  constructor() { }

  ngOnInit(): void {
  }

  doNothing() {
    console.log(open);
  }

}
