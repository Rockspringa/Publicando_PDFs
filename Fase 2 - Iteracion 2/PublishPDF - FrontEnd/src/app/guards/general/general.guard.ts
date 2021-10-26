import { VariablesService } from 'src/app/services/global/variables.service';
import { UsuarioService } from '../../services/users/usuario.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GeneralGuard implements CanActivate {

  constructor(private route: Router, private usuarioService: UsuarioService, private funciones: VariablesService) { }

  redirect(autorizado: boolean, mensaje: string = ""): void {
    if (!autorizado) {
      this.route.navigate([ '/login' ]);
      alert(`No se pudo comprobar que hayas iniciado sesion.` + mensaje);
    }
  }

  canActivate(
        route: ActivatedRouteSnapshot, state: RouterStateSnapshot
      ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let autorizado: boolean = false;

    if (localStorage.getItem('usuario') && this.funciones.user) {
      autorizado = true;/*
      this.usuarioService.authenticate().subscribe(
        (value: void) => {
          this.redirect(this.cookieService.check('autorizado'));
        }, (error: HttpErrorResponse) => {
          this.redirect(false, `\nError ${ error.status }: ${ error.message }`);
        }
      );*/
    }
    this.redirect(autorizado);
    return autorizado;
  }

}
