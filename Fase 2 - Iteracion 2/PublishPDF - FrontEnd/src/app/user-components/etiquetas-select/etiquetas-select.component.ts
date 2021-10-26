import { HttpErrorResponse } from '@angular/common/http';
import { Suscriptor } from 'src/app/model/users/suscriptor.model';
import { UsuarioService } from './../../services/users/usuario.service';
import { VariablesService } from './../../services/global/variables.service';
import { Component, OnInit, Input, OnChanges, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-etiquetas-select',
  templateUrl: './etiquetas-select.component.html',
  styleUrls: ['./etiquetas-select.component.css']
})
export class EtiquetasSelectComponent implements OnInit, OnChanges {

  @Output() tagsChanges = new EventEmitter<string[]>();
  @Input() seleccionar: boolean = false;
  
  tagsEscogidas: string[] = [];
  noEscogidas: string[] = [];
  tags: string[] = [];

  constructor(private functions: VariablesService, private usuarioService: UsuarioService) { }

  ngOnInit(): void {
    const user = this.functions.getUserLogged();

    if (user instanceof Suscriptor) {
      this.usuarioService.getTags(user).subscribe(
        (tags: string[][]) => {
          this.tags = tags[0];
          this.tagsEscogidas = tags[1];
          this.noEscogidas = this.tags.filter(tag => !this.tagsEscogidas.includes(tag));
        }, (error: HttpErrorResponse) => {
          console.log(error);
        }
      )
    }
  }

  ngOnChanges(): void {
    this.noEscogidas = this.tags.filter(tag => !this.tagsEscogidas.includes(tag));
  }

  agregarTag(tag: string): void {
    if (!this.tagsEscogidas.includes(tag))
      this.tagsEscogidas.push(tag);
    
    const index = this.noEscogidas.indexOf(tag);
    if (index != -1)
      this.noEscogidas.splice(index, 1);

    this.tagsChanges.emit(this.tagsEscogidas);
  }
  
  eliminarTag(tag: string): void {
    if (!this.noEscogidas.includes(tag))
      this.noEscogidas.push(tag);
    
    const index = this.tagsEscogidas.indexOf(tag);
    if (index != -1)
      this.tagsEscogidas.splice(index, 1);
      
    this.tagsChanges.emit(this.tagsEscogidas);
  }

}
