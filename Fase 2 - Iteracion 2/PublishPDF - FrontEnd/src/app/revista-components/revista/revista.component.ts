import { HttpErrorResponse } from '@angular/common/http';
import { SuscripcionService } from './../../services/suscripciones/suscripcion.service';
import { Suscripcion } from './../../model/suscripcion.model';
import { Revista } from 'src/app/model/revista.model';
import { ActivatedRoute, Router } from '@angular/router';
import { VariablesService } from './../../services/global/variables.service';
import { Suscriptor } from 'src/app/model/users/suscriptor.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-revista',
  templateUrl: './revista.component.html',
  styleUrls: ['./revista.component.css']
})
export class RevistaComponent implements OnInit {

  user!: Suscriptor;
  revista!: Revista;
  indexes: number[] = [];

  constructor(private functions: VariablesService, private suscripcionService: SuscripcionService,
      private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    let usuario = this.functions.getUserLogged();
    if (usuario instanceof Suscriptor) {
      this.user = usuario;

      let suscripcion: Suscripcion = {
        revista: +(this.route.snapshot.paramMap.get('id') ?? 0),
        suscriptor: this.user.username
      }

      this.suscripcionService.findOne(suscripcion).subscribe(
        (revista: Revista) => {
          this.revista = revista;

          for (let i = 1; i <= (this.revista.numeros ?? 0); i++) {
            this.indexes.push(i);
          }
        }, (error: HttpErrorResponse) => {
          alert(`No se pudo encontrar la revista solicitada.\nError ${error.status}: ${error.statusText}`);
          console.log(error);
        }
      )
      
    } else {
      alert('La visualizacion de revistas esta disponible solo para suscriptores');
      this.router.navigate([ '/user/home' ]);
    }
  }

}
