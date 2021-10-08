import { VariablesService } from 'src/app/services/global/variables.service';
import { CookieService } from 'ngx-cookie-service';
import { UsuarioService } from '../../services/users/usuario.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GeneralGuard implements CanActivate {

  constructor(private route: Router, private usuarioService: UsuarioService,
      private cookieService: CookieService, private funciones: VariablesService) { }

  redirect(autorizado: boolean): void {
    if (!autorizado)
      this.route.navigate([ '/login' ]);
  }

  canActivate(
        route: ActivatedRouteSnapshot, state: RouterStateSnapshot
      ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const existCookie = this.cookieService.check('logeado');
    let autorizado: boolean = false;

    if (existCookie) {
      autorizado = true;/*
      this.usuarioService.authenticate().subscribe(
        data => {
          console.log(data);
          this.redirect(this.cookieService.check('autorizado'));
        }, (error: HttpErrorResponse) => {
          alert(`Error ${ error.status }: ${ error.message }`);
          this.redirect(false);
        }
      );*/
    }

    return autorizado;
  }

}
