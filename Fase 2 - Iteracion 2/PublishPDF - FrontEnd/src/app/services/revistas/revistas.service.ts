import { Editor } from 'src/app/model/users/editor.model';
import { Revista } from 'src/app/model/revista/revista.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RevistasService {

  readonly uplFile = "http://localhost:8080/PublishPDF/usuario"; // arreglar
  readonly serverUrl = "http://localhost:8080/PublishPDF/revista";

  constructor(private http: HttpClient) { }

  publish(revista: Revista): Observable<Revista> {
    return this.http.post<Revista>(this.serverUrl, revista);
  }

  postFile(data: FormData, user: Editor, revista: Revista): Observable<void> {
    return this.http.post<void>(`${this.uplFile}/archivos/${user.username}/${revista.nombre}`, data);
  }

  findAllCoincidences(criterioBusqueda: string): Observable<Revista[]> {
    return this.http.get<Revista[]>(`${this.serverUrl}/buscar`,
        { params: new HttpParams().append('criterio', criterioBusqueda) });
  }

  findOne(revista: number): Observable<Revista> {
    return this.http.get<Revista>(`${this.serverUrl}/`,
        { params: new HttpParams().append('revista', revista) });
  }

}
