import { RevistasService } from './../../services/revistas/revistas.service';
import { Revista } from '../../model/revista/revista.model';
import { Component, OnInit } from '@angular/core';
import { VariablesService } from 'src/app/services/global/variables.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-buscar-pdf',
  templateUrl: './buscar-pdf.component.html',
  styleUrls: ['./buscar-pdf.component.css']
})
export class BuscarPdfComponent implements OnInit {

  inputs!: Function;
  button!: Function;
  formBuscar!: FormGroup;
  revistas: Revista[] = [];

  constructor(private formBuilder: FormBuilder, private functions: VariablesService,
      private revistasService: RevistasService) { }

  ngOnInit(): void {
    this.inputs = this.functions.getInputClasses;
    this.button = this.functions.getButtonClasses;

    this.formBuscar = this.formBuilder.group({
      criterio: ['', [Validators.pattern("^[\\w ]{1,20}$"), Validators.required]]
    });
  }

  buscarRevista() {
    let criterio: string = this.formBuscar.controls.criterio.value;

    this.revistasService.findAllCoincidences(criterio).subscribe(
      (data: Revista[]) => {
        this.revistas = data;
      }, (error: HttpErrorResponse) => {
        alert(`No se pudo conseguir coincidencias.\nError ${error.status}: ${error.message}`);
        console.log(error);
      }
    )
  }

}
