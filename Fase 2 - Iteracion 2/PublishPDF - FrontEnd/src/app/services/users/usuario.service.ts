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

  public findOne(data: Suscriptor | Editor | Administrador): Observable<{}> {
    return this.http.get<{}>(`${this.serverUrl}/read`, this.doTwoParams(data));
  }

  public logIn(data: Usuario): Observable<{
        username: string,
        nombre: string,
        type: string
      }> {
    return this.http.post<{
        username: string,
        nombre: string,
        type: string
      }>(`${this.serverUrl}/read`, null, this.doTwoParams(data));
  }

  public authenticate(): Observable<void> {
    return this.http.post<void>(`${this.serverUrl}/authenticate`, null, this.doTwoParams(null));
  }

  public create(data: Suscriptor | Editor | Administrador): Observable<{
        username: string,
        nombre: string,
        type: string
      }> {
    return this.http.post<{
        username: string,
        nombre: string,
        type: string
      }>(this.serverUrl, data);
  }

  public patch(data: Suscriptor | Editor | Administrador): Observable<void> {
    return this.http.post<void>(`${this.serverUrl}/`, data);
  }

  public updatePhoto(data: FormData, user: Suscriptor | Editor | Administrador): Observable<void> {
    return this.http.post<void>(`${this.serverUrl}/archivos/${user.username}`, data);
  }

  public getPhoto(user: Suscriptor | Editor | Administrador): Observable<any> {
    return this.http.get<any>(`${this.serverUrl}/archivos/${user.username}`, this.doTwoParams(null));
  }

}
