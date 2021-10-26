import { RadioClasses, ButtonClasses, InputClasses, SelectClasses } from './../../model/html/form-classes.model';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Injectable, Output, EventEmitter } from '@angular/core';
import { Administrador } from 'src/app/model/users/admin.model';
import { Editor } from 'src/app/model/users/editor.model';
import { Suscriptor } from 'src/app/model/users/suscriptor.model';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class VariablesService {

  user!: Suscriptor | Editor | Administrador;

  constructor(private router: Router) { }

  setGlobalUser(data: Suscriptor | Editor | Administrador) {
    this.user = data;
    localStorage.setItem('usuario', JSON.stringify(data));
    this.router.navigate(['/user/home']);
  }

  getUserFromJson(dataJson: {}): Suscriptor | Editor | Administrador {
    const data = JSON.stringify(dataJson);
    let user: Suscriptor | Editor | Administrador;

    if (data.includes('"_tags":'))
      user = new Suscriptor('', '');

    else if (data.includes('\"_revistas\":'))
      user = new Editor('', '');

    else if (data.includes('\"_anuncios\":'))
      user = new Administrador('', '');

    else
      throw new Error('El string no coincide con ningun tipo de usuario.');

    return Object.assign(user, dataJson);
  }

  logoutUser() {
    if (localStorage.getItem('usuario') && this.user) {
      localStorage.removeItem('usuario');
    }
  }

  errorInLogin(error: HttpErrorResponse) {
    let text = "";
    if (error.status === 404) {
      text = "No se encontro el usuario o la contraseña no coincide. ";
    } else {
      text = "Ocurrio un error al leer los datos, vuelva a intentarlo. ";
    }
    alert(text + `Error ${error.status}: ${error.message}`);
    console.log(error);
  }

  getUserLogged(): Suscriptor | Editor | Administrador {
    let userJson: string = localStorage.getItem('usuario') || "{}";
    return this.getUserFromJson(JSON.parse(userJson));
  }

  getSelectClasses(input: any): string {
    if (input.pristine)
      return SelectClasses.DEFAULT;
    else if (input.invalid)
      return SelectClasses.ERROR;
    else
      return SelectClasses.VALID;
  }

  getRadioClasses(input: any): string {
    if (input.pristine)
      return RadioClasses.DEFAULT;
    else if (input.invalid)
      return RadioClasses.ERROR;
    else
      return RadioClasses.VALID;
  }

  getInputClasses(input: any): string {
    if (input.pristine)
      return InputClasses.DEFAULT;
    else if (input.invalid)
      return InputClasses.ERROR;
    else
      return InputClasses.VALID;
  }

  getButtonClasses(form: FormGroup): string {
    if (form.valid)
      return ButtonClasses.VALID;
    else if (form.pristine)
      return ButtonClasses.DEFAULT;
    else
      return ButtonClasses.ERROR;
  }

}
