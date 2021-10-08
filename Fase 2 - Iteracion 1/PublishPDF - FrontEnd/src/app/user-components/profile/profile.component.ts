import { Component, OnInit } from '@angular/core';
import { Administrador } from 'src/app/model/users/admin.model';
import { Editor } from 'src/app/model/users/editor.model';
import { Suscriptor } from 'src/app/model/users/suscriptor.model';
import { VariablesService } from 'src/app/services/global/variables.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  editar: boolean = false;
  user!: Suscriptor | Editor | Administrador;

  constructor(private variables: VariablesService) { }

  ngOnInit(): void {
    this.user = this.variables.getUserLogged();
  }

}
