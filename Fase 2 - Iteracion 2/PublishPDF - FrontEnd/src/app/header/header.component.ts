import { VariablesService } from './../services/global/variables.service';
import { ClickleableOption } from '../model/html/option.model';
import { Component, OnInit, Input, OnChanges } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnChanges {

  @Input() ejecutar!: boolean;
  link: string = '/user/home';
  headOpts!: ClickleableOption[];
  profOpts!: ClickleableOption[];

  constructor(private variables: VariablesService) { }

  ngOnChanges(): void {
    if (this.ejecutar) {
      const user = this.variables.user;
      this.headOpts = user.headerOptions;
      this.profOpts = user.menuOptions;
    }
  }

}
