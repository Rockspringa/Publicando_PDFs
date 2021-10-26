import { Administrador } from 'src/app/model/users/admin.model';
import { Editor } from 'src/app/model/users/editor.model';
import { Suscriptor } from 'src/app/model/users/suscriptor.model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
import { Usuario } from '../../model/usuario.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  readonly serverUrl = "http://localhost:8080/PublishPDF/usuario";

  constructor(private http: HttpClient) { }

  private doTwoParams(data: Usuario | Suscriptor | Editor | Administrador | null): {} {
    const headers = new HttpHeaders().append('Content-Type', 'text/plain');
    if (data) {
      let params = new HttpParams().append('user', data.username)
        .append('pass', data.password || '').append('type', data.type);

      return { headers, params };
    } else {
      return { headers }
    }
  }

  findOne(data: Suscriptor | Editor | Administrador): Observable<Suscriptor | Editor | Administrador> {
    return this.http.get<Suscriptor | Editor | Administrador>(`${this.serverUrl}/read`, this.doTwoParams(data));
  }

  logIn(data: Usuario): Observable<Suscriptor | Editor | Administrador> {
    return this.http.post<Suscriptor | Editor | Administrador>(`${this.serverUrl}/read`, null, this.doTwoParams(data));
  }

  authenticate(): Observable<void> {
    return this.http.post<void>(`${this.serverUrl}/authenticate`, null, this.doTwoParams(null));
  }

  create(data: Suscriptor | Editor | Administrador): Observable<Suscriptor | Editor | Administrador> {
    return this.http.post<Suscriptor | Editor | Administrador>(this.serverUrl, data);
  }

  patch(data: Suscriptor | Editor | Administrador): Observable<void> {
    return this.http.post<void>(`${this.serverUrl}/`, data);
  }

  updatePhoto(data: FormData, user: Suscriptor | Editor | Administrador): Observable<void> {
    return this.http.post<void>(`${this.serverUrl}/photo/${user.username}`, data);
  }

  getPhoto(user: Suscriptor | Editor | Administrador): Observable<any> {
    return this.http.get<any>(`${this.serverUrl}/photo/${user.username}`, this.doTwoParams(null));
  }

  getTags(user: Suscriptor): Observable<string[][]> {
    return this.http.get<string[][]>(`${this.serverUrl}/tags`,
        { params: new HttpParams().append('user', user.username) });
  }

}
