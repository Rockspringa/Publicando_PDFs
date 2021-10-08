import { Administrador } from 'src/app/model/users/admin.model';
import { Suscriptor } from './../model/users/suscriptor.model';
import { UserType } from 'src/app/model/users/user-type.model';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ButtonClasses, InputClasses } from '../model/html/form-classes.model';
import { UsuarioService } from '../services/users/usuario.service';
import { Editor } from '../model/users/editor.model';
import { Usuario } from '../model/usuario.model';
import { HttpErrorResponse } from '@angular/common/http';
import { VariablesService } from '../services/global/variables.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  
  readonly options = UserType;
  formSignUp!: FormGroup;

  constructor(private formBuilder: FormBuilder, private usuarioService: UsuarioService,
    private functions: VariablesService) { }

  ngOnInit(): void {
    this.formSignUp = this.formBuilder.group({
      username: ['', [Validators.pattern("^[\\w ]{1,40}$"), Validators.required]],
      password: ['', [Validators.pattern("^[\\w ]{1,40}$"), Validators.required]],
      nombre: ['', [Validators.pattern("^[\\w ]{1,40}$")]],
      descripcion: ['', [Validators.pattern("^[\\w ]{1,150}$")]],
      gustos: ['', [Validators.pattern("^[\\w ]{1,150}$")]],
      hobbies: ['', [Validators.pattern("^[\\w ]{1,150}$")]],
      foto: ['', [Validators.pattern("^[\\w ]{1,40}$")]],
      tipo: [null]
    });
  }

  crearCuenta(): void {
    let user: Suscriptor | Editor | Administrador;

    switch (this.formSignUp.controls.tipo.value) {
      case UserType.SUSCRIPTOR:
        user = new Suscriptor('', '');
        break;

      case UserType.EDITOR:
        user = new Editor('', '');
        break;
        
      case UserType.ADMINISTRADOR:
        user = new Administrador('', '');
        break;
      
      default:
        alert('Los valores aceptados fueron corrompidos.');
        return;
    }
    
    user = Object.assign(user, this.formSignUp.value);
    this.usuarioService.create(user).subscribe(
      (data: {}) => this.functions.userLogged(data),
      (error: HttpErrorResponse) => this.functions.errorInLogin(error)
    );
    this.formSignUp.reset();
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
    if (this.formSignUp.valid)
      return ButtonClasses.VALID;
    else if (this.formSignUp.pristine)
      return ButtonClasses.DEFAULT;
    else
      return ButtonClasses.ERROR;
  }

}
