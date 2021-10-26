import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Comentario } from './../../model/revista/comentario.model';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ComentariosService {

  readonly serverUrl: string = "http://localhost:8080/PublishPDF/comentarios";

  constructor(private http: HttpClient) { }

  findAll(revistaId: number): Observable<Comentario[]> {
    return this.http.get<Comentario[]>(this.serverUrl,
        { params: new HttpParams().append('revista', revistaId) });
  }

  create(comentario: Comentario): Observable<void> {
    return this.http.post<void>(`${this.serverUrl}/`, comentario);
  }

}
