import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Administrador } from 'src/app/model/users/admin.model';
import { Editor } from 'src/app/model/users/editor.model';
import { Suscriptor } from 'src/app/model/users/suscriptor.model';

@Injectable({
  providedIn: 'root'
})
export class VariablesService {

  constructor(private router: Router, private cookieService: CookieService) { }

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

  userLogged(dataJson: {}) {
    const data = JSON.stringify(dataJson);
    const user = this.getUserFromJson(dataJson);

    if (data) {
      let dataAbbr = {
        username: user.username,
        nombre: user.nombre,
        type: user.type
      };

      localStorage.setItem('usuario', data);
      this.cookieService.set('logeado', JSON.stringify(dataAbbr));
      this.router.navigate(['/user/home']);
    }

  }

  errorInLogin(error: HttpErrorResponse) {
    let text = "";
    if (error.status === 404) {
      text = "No se encontro el usuario o la contraseña no coincide. ";
    } else {
      text = "Ocurrio un error al leer los datos, vuelva a intentarlo. ";
    }
    alert(text + `Error ${error.status}: ${error.statusText}`);
    console.log(error);
  }

  getUserLogged(): Suscriptor | Editor | Administrador {
    let userJson: string = localStorage.getItem('usuario') || "{}";
    return this.getUserFromJson(JSON.parse(userJson));
  }
}
