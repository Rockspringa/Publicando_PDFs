import { Editor } from 'src/app/model/users/editor.model';
import { Revista } from 'src/app/model/revista/revista.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RevistasService {

  readonly serverUrl = "http://localhost:8080/PublishPDF/revista";

  constructor(private http: HttpClient) { }

  publish(revista: Revista): Observable<boolean> {
    return this.http.post<boolean>(this.serverUrl, revista);
  }

  postNumeroWithoutId(data: FormData): Observable<void> {
    return this.http.post<void>(`${this.serverUrl}/archivo/`, data);
  }

  postNumero(data: FormData, revista: Revista): Observable<void> {
    return this.http.post<void>(`${this.serverUrl}/archivo/${revista.id}`, data);
  }

  viewNumero(revista: Revista, numero: number): Observable<string> {
    return this.http.get<string>(`${this.serverUrl}/archivo/`,
        { params: new HttpParams().append('revista', revista.id).append('numero', numero) });
  }

  downloadNumero(revista: Revista, numero: number): Observable<string> {
    return this.http.get<string>(`${this.serverUrl}/archivo/`,
        { params: new HttpParams().append('revista', revista.id)
            .append('numero', numero)
            .append('descargar', true) });
  }

  findAllCoincidences(criterioBusqueda: string): Observable<Revista[]> {
    return this.http.get<Revista[]>(`${this.serverUrl}/buscar`,
        { params: new HttpParams().append('criterio', criterioBusqueda) });
  }

  findOne(revista: number): Observable<Revista> {
    return this.http.get<Revista>(`${this.serverUrl}/`,
        { params: new HttpParams().append('revista', revista) });
  }

  findCreadas(username: string): Observable<Revista[]> {
    return this.http.get<Revista[]>(`${this.serverUrl}/creadas`,
        { params: new HttpParams().append('user', username) });
  }

  findAllCategories(): Observable<string[]> {
    return this.http.get<string[]>(`${this.serverUrl}/categorias`,
        { params: new HttpParams().append('wildcard', 1) });
  }

  findAllTags(): Observable<string[]> {
    return this.http.get<string[]>(`${this.serverUrl}/tags`,
        { params: new HttpParams().append('wildcard', '1') });
  }

}
