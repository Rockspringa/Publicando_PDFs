import { Component, OnInit } from '@angular/core';
import { Suscriptor } from 'src/app/model/users/suscriptor.model';
import { Editor } from 'src/app/model/users/editor.model';
import { Administrador } from 'src/app/model/users/admin.model';
import { VariablesService } from 'src/app/services/global/variables.service';

@Component({
  selector: 'app-window',
  templateUrl: './window.component.html',
  styleUrls: ['./window.component.css']
})
export class WindowComponent implements OnInit {

  ejecutar: boolean = false;
  user!: Suscriptor | Editor | Administrador;

  constructor(private variables: VariablesService) {
  }

  ngOnInit(): void {
    this.user = this.variables.user;
    this.ejecutar = true;

  }

}
