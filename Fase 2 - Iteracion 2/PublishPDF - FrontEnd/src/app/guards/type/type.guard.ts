import { UserType } from 'src/app/model/users/user-type.model';
import { Administrador } from './../../model/users/admin.model';
import { Editor } from './../../model/users/editor.model';
import { Suscriptor } from './../../model/users/suscriptor.model';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateChild, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { VariablesService } from 'src/app/services/global/variables.service';
import { UsuarioService } from 'src/app/services/users/usuario.service';

@Injectable({
  providedIn: 'root'
})
export class TypeGuard implements CanActivateChild {
  
  constructor(private route: Router, private usuarioService: UsuarioService, private funciones: VariablesService) { }

  redirect(autorizado: boolean, mensaje: string = ""): void {
    if (!autorizado) {
      alert(`No tienes permiso para ver esta seccion.` + mensaje);
      this.route.navigate([ '/user/home' ]);
    }
  }

  canActivateChild(
    childRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    
      try {
        const user: Suscriptor | Editor | Administrador = this.funciones.user;

        let autorizado: boolean = false;
        
        if (user instanceof Suscriptor && state.url.includes('suscriptor'))
          autorizado = true;
        else if (user instanceof Editor && state.url.includes('editor'))
          autorizado = true;
        else if (user instanceof Administrador && state.url.includes('admin'))
          autorizado = true;

        this.redirect(autorizado);
        return autorizado;
      } catch (error) {
        return false;
      }
  }
  
}
