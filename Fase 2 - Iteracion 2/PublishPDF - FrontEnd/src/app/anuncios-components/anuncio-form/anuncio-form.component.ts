import { VariablesService } from './../../services/global/variables.service';
import { Administrador } from 'src/app/model/users/admin.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AnuncioType } from './../../model/anuncio/anuncioType';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-anuncio-form',
  templateUrl: './anuncio-form.component.html',
  styleUrls: ['./anuncio-form.component.css']
})
export class AnuncioFormComponent implements OnInit {

  file!: File;
  formAnuncio!: FormGroup;
  user!: Administrador;
  foto: string | ArrayBuffer = "";

  inputs!: Function;
  button!: Function;

  tipo!: AnuncioType;

  readonly types = AnuncioType;

  constructor(private formBuilder: FormBuilder, private functions: VariablesService) { }

  ngOnInit(): void {
    if (this.functions.user instanceof Administrador)
      this.user = this.functions.user;

    this.inputs = this.functions.getInputClasses;
    this.button = this.functions.getButtonClasses;

    this.formAnuncio = this.formBuilder.group({
      administrador: [this.user.username],
      costoDia: ['0', [Validators.pattern("^[\\d]{1,4}(\\.[\\d]{1,2})?$"), Validators.required]],
      texto: ['', [Validators.pattern("^[\\w ]{1,150}$"), Validators.required]],
      video: ['', [Validators.pattern("^[\\w ]{1,150}$")]]
    });
  }

  subirAnuncio() {

  }
  
}
