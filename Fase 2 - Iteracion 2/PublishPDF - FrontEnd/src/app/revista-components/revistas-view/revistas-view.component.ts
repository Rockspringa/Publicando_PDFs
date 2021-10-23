import { HttpErrorResponse } from '@angular/common/http';
import { Revista } from 'src/app/model/revista.model';
import { SuscripcionService } from './../../services/suscripciones/suscripcion.service';
import { VariablesService } from 'src/app/services/global/variables.service';
import { Component, OnInit } from '@angular/core';
import { Administrador } from 'src/app/model/users/admin.model';
import { Editor } from 'src/app/model/users/editor.model';
import { Suscriptor } from 'src/app/model/users/suscriptor.model';

@Component({
  selector: 'app-revistas-view',
  templateUrl: './revistas-view.component.html',
  styleUrls: ['./revistas-view.component.css']
})
export class RevistasViewComponent implements OnInit {

  readonly link = '/editor/publicar';
  
  user!: Suscriptor | Editor | Administrador;
  revistas!: Revista[];

  constructor(private functions: VariablesService, private suscripcionesService: SuscripcionService) { }

  ngOnInit(): void {
    this.user = this.functions.getUserLogged();
    this.getRevistas();
  }

  isEditor(user: Suscriptor | Editor | Administrador): boolean {
    return user instanceof Editor;
  }

  private getRevistas() {
    if (this.user instanceof Suscriptor) {
      this.getSuscripciones();
    } else
      this.revistas = [];
  }

  private getSuscripciones() {
    this.suscripcionesService.findAll(this.user.username).subscribe(
      (revistas: Revista[]) => {
        this.revistas = revistas;
      }, (error: HttpErrorResponse) => {
        this.revistas = [];
        alert(`No se pudieron recuperar sus sucripciones.\nError ${error.status}: ${error.statusText}`);
        console.log(error);
      }
    )
  }

}
