import { Comentario } from './../../model/revista/comentario.model';
import { HttpErrorResponse } from '@angular/common/http';
import { SuscripcionService } from './../../services/suscripciones/suscripcion.service';
import { Revista } from 'src/app/model/revista/revista.model';
import { ActivatedRoute, Router } from '@angular/router';
import { VariablesService } from './../../services/global/variables.service';
import { Suscriptor } from 'src/app/model/users/suscriptor.model';
import { Component, OnInit } from '@angular/core';
import { Suscripcion } from 'src/app/model/revista/suscripcion.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ComentariosService } from 'src/app/services/comentarios/comentarios.service';

@Component({
  selector: 'app-revista',
  templateUrl: './revista.component.html',
  styleUrls: ['./revista.component.css']
})
export class RevistaComponent implements OnInit {

  suscribirse: boolean = false;
  cambio: number = 0;
  like: string = "der btn btn-secondary";
  readonly link = "/suscriptor/suscribirse";
  readonly linkProfile = "/suscriptor/profile";

  inputs!: Function;
  button!: Function;
  formComentar!: FormGroup;

  suscripcion!: Suscripcion;
  user!: Suscriptor;
  revista: Revista = {
    id: 0,
    editor: "Cargando...",
    nombre: "Cargando...",
    categoria: "Cargando...",
    fechaPublicacion: new Date()
  };
  indexes: number[] = [];

  constructor(private functions: VariablesService, private suscripcionService: SuscripcionService,
    private route: ActivatedRoute, private formBuilder: FormBuilder,
    private comentariosService: ComentariosService, private router: Router) {
  }

  ngOnInit(): void {
    this.inputs = this.functions.getInputClasses;
    this.button = this.functions.getButtonClasses;

    this.formComentar = this.formBuilder.group({
      comentario: ['', [Validators.pattern("^[\\w \\n,.]{1,150}$"), Validators.required]]
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
          this.revista = revista;

          for (let i = 1; i <= (this.revista.numeros ?? 0); i++) {
            this.indexes.push(i);
          }
        }, (error: HttpErrorResponse) => {
          alert(`No se pudo encontrar la revista solicitada.\nError ${error.status}: ${error.message}`);
          console.log(error);
        }
      )
      
      this.suscripcionService.getDateLiked(this.suscripcion).subscribe(
        (liked: boolean) => {
          this.like = (liked) ? "der btn btn-primary" : this.like;
        },
        (error: HttpErrorResponse) => {
          console.log(error);
        }
      );
    } else {
      alert('La visualizacion de revistas esta disponible solo para suscriptores');
      this.router.navigate(['/user/home']);
    }
  }

  comentar() {
    let comentario: Comentario = {
      revista: this.revista.id,
      suscriptor: this.user.username,
      comentario: this.formComentar.controls.comentario.value,
      fecha: new Date()
    }

    this.comentariosService.create(comentario).subscribe(
      (data: void) => {
        this.cambio++;
        this.formComentar.reset();
      }, (error: HttpErrorResponse) => {
        alert(`No se publicar el comentario ingresado.\nError ${error.status}: ${error.message}`);
        console.log(error);
        this.formComentar.reset();
      }
    )
  }

  darLike() {
    this.suscripcionService.setLike(this.suscripcion).subscribe(
      (liked: boolean) => {
        if (liked) {
          this.like = "der btn btn-primary";
          this.revista.meGustas = (this.revista.meGustas ?? 0) + 1;
        } else {
          this.like = "der btn btn-secondary";
          this.revista.meGustas = (this.revista.meGustas ?? 1) - 1;
        }
      }, (error: HttpErrorResponse) => {
        alert(`No se pudo dar like a la revista.\nError ${error.status}: ${error.message}`);
        console.log(error);
      }
    )
  }

  redirect(): void {
    this.router.navigate([ '/suscriptor/suscribirse', this.revista.id ])
  }

}
