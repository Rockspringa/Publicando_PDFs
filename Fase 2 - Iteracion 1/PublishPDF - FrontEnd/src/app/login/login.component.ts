import { Administrador } from 'src/app/model/users/admin.model';
import { Editor } from 'src/app/model/users/editor.model';
import { Suscriptor } from 'src/app/model/users/suscriptor.model';
import { HttpErrorResponse } from '@angular/common/http';
import { VariablesService } from './../services/global/variables.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ButtonClasses, InputClasses } from '../model/html/form-classes.model';
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

  constructor(private formBuilder: FormBuilder, private usuarioService: UsuarioService,
    private functions: VariablesService) {
  }

  ngOnInit(): void {
    this.formLogin = this.formBuilder.group({
      username: ['', [Validators.pattern("^[\\w ]{1,40}$"), Validators.required]],
      password: ['', [Validators.pattern("^[\\w ]{1,40}$"), Validators.required]]
    });
  }

  iniciarSesion(): void {
    this.cargando = "cargando";
    const user = Object.assign(new Usuario('', ''), this.formLogin.value);
    this.usuarioService.logIn(user).subscribe(
      (data: {}) => {
        this.functions.userLogged(data)
        this.cargando = "cargando invisible";
      },
      (error: HttpErrorResponse) => {
        this.functions.errorInLogin(error);
        this.formLogin.reset();
      }
    );
  }

  getClasses(input: any): string {
    if (input.pristine)
      return InputClasses.DEFAULT;
    else if (input.invalid)
      return InputClasses.ERROR;
    else
      return InputClasses.VALID;
  }

  getButtonClasses(): string {
    if (this.formLogin.valid)
      return ButtonClasses.VALID;
    else if (this.formLogin.pristine)
      return ButtonClasses.DEFAULT;
    else
      return ButtonClasses.ERROR;
  }

}
