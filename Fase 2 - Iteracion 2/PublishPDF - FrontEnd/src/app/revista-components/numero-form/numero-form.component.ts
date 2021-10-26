import { RevistasService } from './../../services/revistas/revistas.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Editor } from 'src/app/model/users/editor.model';
import { UsuarioService } from './../../services/users/usuario.service';
import { Component, Input, OnInit } from '@angular/core';
import { Revista } from 'src/app/model/revista/revista.model';

@Component({
  selector: 'app-numero-form',
  templateUrl: './numero-form.component.html',
  styleUrls: ['./numero-form.component.css']
})
export class NumeroFormComponent implements OnInit {

  file!: File;
  selected: boolean = false;
  @Input() revista!: Revista;
  @Input() user!: Editor;

  constructor(private revistasService: RevistasService) { }

  ngOnInit(): void {
  }

  publicarNumero() {
    if (this.file) {
      const pdfData = new FormData();
  
        const filename = this.file.name.split('.');
        const extension = filename[filename.length - 1];
  
        if (extension === 'pdf') {
          pdfData.append('file', this.file, this.revista.nombre + "_1." + extension);
          this.revistasService.postFile(pdfData, this.user, this.revista).subscribe(
            (value: void) => {
              alert('Se ha publicado con exito el nuevo numero');
            }, (error: HttpErrorResponse) => {
              alert(`No se pudo subir la imagen.\nError ${error.status}: ${error.statusText}`);
              console.log(error);
            }
          );
        }
        else
          alert('El archivo que subio no es pdf, por favor, selecciones un archivo pdf.');
    }
  }

  uploadFile(event: any) {
    if (event.target.files.length === 0)
      return;
    this.file = event.target.files[0];
  }

}
