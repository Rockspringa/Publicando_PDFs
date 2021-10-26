import { HttpErrorResponse } from '@angular/common/http';
import { UsuarioService } from 'src/app/services/users/usuario.service';
import { ActivatedRoute } from '@angular/router';
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
  vista: boolean = false;
  user!: Suscriptor | Editor | Administrador;

  foto: string = "";

  constructor(private functions: VariablesService, private route: ActivatedRoute,
      private usuarioService: UsuarioService) { }

  ngOnInit(): void {
    if (this.route.snapshot.paramMap.has('username')) {
      this.vista = true;
      const username: string = this.route.snapshot.paramMap.get('username') ?? '';
      const usuario = new Editor(username, '');
      this.usuarioService.findOne(usuario).subscribe(
        (data: Suscriptor | Editor | Administrador) => {
          this.user = Object.assign(new Editor('', ''), data);
          if (this.user.foto)
            this.foto = Usuario.url_foto + this.user.username + "?wildcard=1";
        },
        (error: HttpErrorResponse) => {
          alert(`No se pudieron recuperar los datos del editor.\nError ${error.status}: ${error.message}`);
          console.log(error);
        }
      );
    } else {
      this.user = this.functions.user;
      if (this.user.foto)
        this.foto = Usuario.url_foto + this.user.username + "?wildcard=1";
    }
  }

  isInstance(user: Suscriptor | Editor | Administrador) {
    return user instanceof Suscriptor;
  }

}
