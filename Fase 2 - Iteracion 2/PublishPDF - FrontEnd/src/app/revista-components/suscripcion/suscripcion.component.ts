import { SuscripcionService } from './../../services/suscripciones/suscripcion.service';
import { RevistasService } from './../../services/revistas/revistas.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Suscripcion } from './../../model/revista/suscripcion.model';
import { Revista } from './../../model/revista/revista.model';
import { Suscriptor } from './../../model/users/suscriptor.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { VariablesService } from './../../services/global/variables.service';

@Component({
  selector: 'app-suscripcion',
  templateUrl: './suscripcion.component.html',
  styleUrls: ['./suscripcion.component.css']
})
export class SuscripcionComponent implements OnInit {

  user!: Suscriptor;
  suscripcion!: Suscripcion;
  revista: Revista = {
    id: 0,
    editor: "Cargando...",
    nombre: "Cargando...",
    categoria: "Cargando...",
    fechaPublicacion: new Date()
  };

  inputs!: Function;
  button!: Function;
  formSuscribirse!: FormGroup;

  constructor(private route: ActivatedRoute, private functions: VariablesService, private router: Router,
      private formBuilder: FormBuilder, private revistasService: RevistasService,
      private suscripcionService: SuscripcionService) { }

  ngOnInit(): void {
    this.inputs = this.functions.getRadioClasses;
    this.button = this.functions.getButtonClasses;

    this.formSuscribirse = this.formBuilder.group({
      fecha: ['', [Validators.required]],
      mensual: ['', [Validators.required]]
    });

    let usuario = this.functions.getUserLogged();
    let id: number = +(this.route.snapshot.paramMap.get('id') ?? 0);

    if (usuario instanceof Suscriptor && !isNaN(id)) {
      this.user = usuario;

      this.suscripcion = {
        revista: id,
        suscriptor: this.user.username
      }

      this.suscripcionService.findOne(this.suscripcion).subscribe(
        (revista: Revista) => {
          if (revista.meGustasActivos !== undefined) {
            alert('Ya esta suscrito a la revista.');
            this.router.navigate([ '/suscriptor/revista', id ]);
          }
        }
      )

      this.revistasService.findOne(id).subscribe(
        (revista: Revista) => {
          this.revista = revista;

          if (!(revista.suscripcionesActivas ?? true)) {
            alert('Las suscripciones no estan activas.');
            this.router.navigate([ '/suscriptor/revista', id ]);
          }
        }, (error: HttpErrorResponse) => {
          alert(`No se pudo encontrar la revista solicitada.\nError ${error.status}: ${error.message}`);
          console.log(error);
        }
      )
    } else {
      alert('La visualizacion de revistas esta disponible solo para suscriptores');
      this.router.navigate(['/user/home']);
    }
  }

  suscribirse() {
    this.suscripcion.fechaSuscripcion = this.formSuscribirse.controls.fecha.value;
    this.suscripcion.mensual = this.formSuscribirse.controls.mensual.value;

    this.suscripcionService.create(this.suscripcion).subscribe(
      (suscrito: boolean) => {
        if (suscrito)
          this.router.navigate([ '/suscriptor/revista', this.revista.id ]);
        else
          alert('No se logro suscribir a la revista, vuelva a intentarlo.');
      }, (error: HttpErrorResponse) => {
        alert(`No se logro suscribir a la revista, vuelva a intentarlo.\n`
            + `Error ${error.status}: ${error.message}`);
        console.log(error);
      }, () => this.formSuscribirse.reset()
    )
  }

}
