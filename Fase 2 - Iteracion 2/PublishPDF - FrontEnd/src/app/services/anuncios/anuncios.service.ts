import { Observable } from 'rxjs';
import { Anuncio } from './../../model/anuncio/anuncio.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AnunciosService {

  readonly serverUrl: string = "http://localhost:8080/PublishPDF/anuncio";

  constructor(private http: HttpClient) { }

  findAll(username: string): Observable<Anuncio[]> {
    return this.http.get<Anuncio[]>(this.serverUrl,
        { params: new HttpParams().append('user', username) });
  }

  create(anuncio: Anuncio): Observable<boolean> {
    return this.http.post<boolean>(`${this.serverUrl}/`, anuncio);
  }

}
