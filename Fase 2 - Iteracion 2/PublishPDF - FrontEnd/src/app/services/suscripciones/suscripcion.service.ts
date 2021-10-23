import { Suscripcion } from './../../model/suscripcion.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Revista } from 'src/app/model/revista.model';

@Injectable({
  providedIn: 'root'
})
export class SuscripcionService {

  readonly serverUrl = "http://localhost:8080/PublishPDF/suscripciones";

  constructor(private http: HttpClient) { }

  findAll(username: string): Observable<Revista[]> {
    return this.http.get<Revista[]>(this.serverUrl, { params: new HttpParams().append('user', username) });
  }

  findOne(suscripcion: Suscripcion): Observable<Revista> {
    return this.http.post<Revista>(this.serverUrl, suscripcion);
  }

}
