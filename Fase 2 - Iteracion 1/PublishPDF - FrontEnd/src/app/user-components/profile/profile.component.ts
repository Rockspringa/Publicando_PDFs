import { Component, OnInit } from '@angular/core';
import { Administrador } from 'src/app/model/users/admin.model';
import { Editor } from 'src/app/model/users/editor.model';
import { Suscriptor } from 'src/app/model/users/suscriptor.model';
import { Usuario } from 'src/app/model/usuario.model';
import { VariablesService } from 'src/app/services/global/variables.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  readonly link: string = "/user/update";
  user!: Suscriptor | Editor | Administrador;

  foto!: string;

  constructor(private functions: VariablesService) { }

  ngOnInit(): void {
    this.user = this.functions.getUserLogged();
    this.foto = Usuario.url_foto + this.user.username + "?wildcard=1";
  }

}
