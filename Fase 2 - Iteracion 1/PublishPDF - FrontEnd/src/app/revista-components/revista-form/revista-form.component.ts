import { Revista } from './../../model/revista.model';
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
  inputs!: Function;
  button!: Function;
  formRevista!: FormGroup;
  user!: Editor;

  constructor(private formBuilder: FormBuilder, private functions: VariablesService) { }

  ngOnInit(): void {
    const tmp = this.functions.getUserLogged();
    
    if (tmp instanceof Editor)
      this.user = tmp;

    this.inputs = this.functions.getInputClasses;
    this.button = this.functions.getButtonClasses;

    this.formRevista = this.formBuilder.group({
      editor: [this.user.username],
      nombre: ['', [Validators.pattern("^[\\w ]{1,20}$"), Validators.required]],
      descripcion: ['', [Validators.pattern("^[\\w ]{1,150}$")]],
      fecha: ['', [Validators.required]],
      categoria: ['', [Validators.pattern("^[\\w ]{1,20}$"), Validators.required]]
    });
  }

  subirRevista() {
    const prueba: Revista = this.formRevista.value;
    this.num = '';
    console.log(prueba);
  }

}
