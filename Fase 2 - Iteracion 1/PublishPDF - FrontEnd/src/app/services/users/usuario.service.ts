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

  readonly serverUrl = "http://localhost:8080/PublishPDF/usuario"

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

  public logIn(data: Usuario): Observable<{}> {
    return this.http.post<{}>(`${this.serverUrl}/read`, null, this.doTwoParams(data));
  }

  public authenticate(): Observable<any> {
    return this.http.post<any>(`${this.serverUrl}/authenticate`, null, this.doTwoParams(null));
  }

  public create(data: Suscriptor | Editor | Administrador): Observable<{}> {
    return this.http.post<{}>(this.serverUrl, data);
  }

}
