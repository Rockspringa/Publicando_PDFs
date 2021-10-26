import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { RevistasService } from './../../services/revistas/revistas.service';
import { Revista } from '../../model/revista/revista.model';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Editor } from 'src/app/model/users/editor.model';
import { VariablesService } from 'src/app/services/global/variables.service';

@Component({
  selector: 'app-revista-form',
  templateUrl: './revista-form.component.html',
  styleUrls: ['./revista-form.component.css']
})
export class RevistaFormComponent implements OnInit {

  num: string = 'hid';
  categorias: string[] = [];
  etiquetas: string[] = [];
  revista!: Revista;
  user!: Editor;

  inputs!: Function;
  selects!: Function;
  button!: Function;
  formRevista!: FormGroup;

  constructor(private formBuilder: FormBuilder, private functions: VariablesService,
      private revistasService: RevistasService, private router: Router) { }

  ngOnInit(): void {
    const tmp = this.functions.getUserLogged();
    
    if (tmp instanceof Editor)
      this.user = tmp;
    
    this.revistasService.findAllCategories().subscribe(
      (categorias: string[]) => this.categorias = categorias,
      (error) => console.log(error)
    )

    this.inputs = this.functions.getInputClasses;
    this.selects = this.functions.getSelectClasses;
    this.button = this.functions.getButtonClasses;

    this.formRevista = this.formBuilder.group({
      editor: [this.user.username],
      nombre: ['', [Validators.pattern("^[\\w ]{1,20}$"), Validators.required]],
      descripcion: ['', [Validators.pattern("^[\\w ]{0,150}$")]],
      fechaPublicacion: ['', [Validators.required]],
      categoria: [null, [Validators.nullValidator, Validators.required]],
      costeMes: [0, [Validators.pattern("^[\\d]{1,4}(\\.[\\d]{1,2})?$"), Validators.required]]
    });
  }

  subirRevista() {
    this.revista = this.formRevista.value;
    this.revista.etiquetas = this.etiquetas;
    this.num = '';

    this.revistasService.publish(this.revista).subscribe(
      (publicada: boolean) => {
        if (publicada) {
          alert(`Su revista ${this.revista.nombre} ya se publico.`);
        } else {
          alert('La revista no se logro publicar, vuelva a intentarlo mas tarde.');
          this.router.navigate([ '/editor/revistas' ]);
        }
      }, (error: HttpErrorResponse) => {
        alert(`No se pudo publicar la revista.\nError ${error.status}: ${error.message}`);
        console.log(error);
        this.router.navigate([ '/editor/revistas' ])
      }
    );
  }

  changeTags(tags: string[]): void {
    this.etiquetas = tags;
  }

}
