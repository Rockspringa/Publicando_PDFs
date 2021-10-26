import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Revista } from 'src/app/model/revista/revista.model';
import { Suscripcion } from 'src/app/model/revista/suscripcion.model';

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

  create(suscripcion: Suscripcion): Observable<boolean> {
    return this.http.post<boolean>(`${this.serverUrl}/`, suscripcion);
  }

  getDateLiked(suscripcion: Suscripcion): Observable<boolean> {
    return this.http.get<boolean>(`${this.serverUrl}/like`,
        {
          params: new HttpParams().append('user', suscripcion.suscriptor).append('revista', suscripcion.revista)
        });
  }

  setLike(suscripcion: Suscripcion): Observable<boolean> {
    return this.http.post<boolean>(`${this.serverUrl}/like`, suscripcion);
  }

}
