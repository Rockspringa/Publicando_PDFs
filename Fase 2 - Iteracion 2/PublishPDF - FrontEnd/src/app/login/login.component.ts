import { Suscriptor } from 'src/app/model/users/suscriptor.model';
import { Editor } from 'src/app/model/users/editor.model';
import { Administrador } from 'src/app/model/users/admin.model';
import { HttpErrorResponse } from '@angular/common/http';
import { VariablesService } from './../services/global/variables.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Usuario } from '../model/usuario.model';
import { UsuarioService } from '../services/users/usuario.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  cargando: string = "cargando invisible";
  formLogin!: FormGroup;

  inputs!: Function;
  button!: Function;

  constructor(private formBuilder: FormBuilder, private usuarioService: UsuarioService,
    private functions: VariablesService) {
    this.functions.logoutUser();
  }

  ngOnInit(): void {
    this.inputs = this.functions.getInputClasses;
    this.button = this.functions.getButtonClasses;

    this.formLogin = this.formBuilder.group({
      username: ['', [Validators.pattern("^[\\w ]{1,40}$"), Validators.required]],
      password: ['', [Validators.pattern("^[\\w ]{1,40}$"), Validators.required]]
    });
  }

  iniciarSesion(): void {
    this.cargando = "cargando";
    const user = Object.assign(new Usuario('', ''), this.formLogin.value);
    this.usuarioService.logIn(user).subscribe(
      (data: Suscriptor | Editor | Administrador) => {
        let user: Suscriptor | Editor | Administrador;
        const json = JSON.stringify(data);

        if (json.includes("_tags"))
          user = new Suscriptor('', '');

        else if (json.includes("_revistas"))
          user = new Editor('', '');

        else
          user = new Administrador('', '');

        user = Object.assign(user, data);
        this.cargando = "cargando invisible";
        this.functions.setGlobalUser(user);
      },
      (error: HttpErrorResponse) => {
        this.functions.errorInLogin(error);
        this.formLogin.reset();
        this.cargando = "cargando invisible";
      }
    );
  }

}
