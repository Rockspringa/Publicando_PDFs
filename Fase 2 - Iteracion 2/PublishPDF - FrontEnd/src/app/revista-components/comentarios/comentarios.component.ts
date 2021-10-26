import { HttpErrorResponse } from '@angular/common/http';
import { ComentariosService } from './../../services/comentarios/comentarios.service';
import { Comentario } from './../../model/revista/comentario.model';
import { Component, Input, OnChanges, OnInit } from '@angular/core';

@Component({
  selector: 'app-comentarios',
  templateUrl: './comentarios.component.html',
  styleUrls: ['./comentarios.component.css']
})
export class ComentariosComponent implements OnInit, OnChanges {

  @Input() cambio!: number;
  @Input() revista!: number;
  comentarios: Comentario[] = [];

  constructor(private comentariosService: ComentariosService) { }

  ngOnInit(): void {
  }

  ngOnChanges(): void {
    this.fillComentarios();
  }

  private fillComentarios(): void {
    this.comentariosService.findAll(this.revista).subscribe(
      (data: Comentario[]) => this.comentarios = data,
      (error: HttpErrorResponse) => {
        alert(`No se pudo conseguir los comentarios de la revista.\nError ${error.status}: ${error.message}`);
        console.log(error);
      }
    )
  }

}
