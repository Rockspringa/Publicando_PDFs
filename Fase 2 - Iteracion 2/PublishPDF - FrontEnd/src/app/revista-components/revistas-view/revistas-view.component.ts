import { RevistasService } from './../../services/revistas/revistas.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Revista } from 'src/app/model/revista/revista.model';
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

  constructor(private functions: VariablesService, private suscripcionesService: SuscripcionService,
      private revistasService: RevistasService) { }

  ngOnInit(): void {
    this.user = this.functions.user;
    this.getRevistas();
  }

  isEditor(user: Suscriptor | Editor | Administrador): boolean {
    return user instanceof Editor;
  }

  private getRevistas() {
    if (this.user instanceof Suscriptor) {
      this.getSuscripciones();
    } else if (this.user instanceof Editor) {
      this.getCreados();
    } else
      this.revistas = [];
  }

  private getCreados() {
    this.revistasService.findCreadas(this.user.username).subscribe(
      (revistas: Revista[]) => {
        this.revistas = revistas;
      }, (error: HttpErrorResponse) => {
        this.revistas = [];
        alert(`No se pudieron recuperar sus revistas creadas.\nError ${error.status}: ${error.message}`);
        console.log(error);
      }
    );
  }

  private getSuscripciones() {
    this.suscripcionesService.findAll(this.user.username).subscribe(
      (revistas: Revista[]) => {
        this.revistas = revistas;
      }, (error: HttpErrorResponse) => {
        this.revistas = [];
        alert(`No se pudieron recuperar sus sucripciones.\nError ${error.status}: ${error.message}`);
        console.log(error);
      }
    )
  }

}
