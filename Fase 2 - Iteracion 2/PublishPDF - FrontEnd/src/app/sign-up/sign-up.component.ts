import { Administrador } from 'src/app/model/users/admin.model';
import { Suscriptor } from './../model/users/suscriptor.model';
import { UserType } from 'src/app/model/users/user-type.model';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UsuarioService } from '../services/users/usuario.service';
import { Editor } from '../model/users/editor.model';
import { HttpErrorResponse } from '@angular/common/http';
import { VariablesService } from '../services/global/variables.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  
  readonly options = UserType;
  formSignUp!: FormGroup;
  file!: File;
  foto!: string | ArrayBuffer;

  inputs!: Function;
  button!: Function;

  constructor(private router: Router, private formBuilder: FormBuilder, private usuarioService: UsuarioService,
    private functions: VariablesService) { }

  ngOnInit(): void {
    console.log(this.options);
    this.inputs = this.functions.getInputClasses;
    this.button = this.functions.getButtonClasses;

    this.formSignUp = this.formBuilder.group({
      username: ['', [Validators.pattern("^[\\w ]{1,40}$"), Validators.required]],
      password: ['', [Validators.pattern("^[\\w ]{1,40}$"), Validators.required]],
      nombre: ['', [Validators.pattern("^[\\w ]{1,40}$")]],
      descripcion: ['', [Validators.pattern("^[\\w ]{1,150}$")]],
      gustos: ['', [Validators.pattern("^[\\w ]{1,150}$")]],
      hobbies: ['', [Validators.pattern("^[\\w ]{1,150}$")]],
      tipo: [null]
    });
  }

  crearCuenta(): void {
    let user: Suscriptor | Editor | Administrador;

    switch (this.formSignUp.controls.tipo.value) {
      case UserType.SUSCRIPTOR:
        user = new Suscriptor('', '');
        break;

      case UserType.EDITOR:
        user = new Editor('', '');
        break;
        
      case UserType.ADMINISTRADOR:
        user = new Administrador('', '');
        break;
      
      default:
        alert('Los valores aceptados fueron corrompidos.');
        return;
    }
    
    user = Object.assign(user, this.formSignUp.value);
    this.usuarioService.create(user).subscribe(
      (data: {
          username: string,
          nombre: string,
          type: string
        }) => {
          this.functions.setGlobalUser(data);
          this.uploadPhoto(user);
        },
      (error: HttpErrorResponse) => {
        this.functions.errorInLogin(error);
        this.router.navigateByUrl('/login', { skipLocationChange: true }).then(() => {
          this.router.navigate([ '/sign-up' ]);
        }); 
      }
    );
  }

  private uploadPhoto(user: Suscriptor | Editor | Administrador) {
    const imgData = new FormData();

    if (this.file) {
      const filename = this.file.name.split('.');
      const extension = filename[filename.length - 1];

      imgData.append('file', this.file, user.username + "_foto." + extension);

      this.usuarioService.updatePhoto(imgData, user).subscribe(
        (data: void) => {
        }, (error: HttpErrorResponse) => {
          alert(`No se pudo subir la imagen.\nError ${error.status}: ${error.statusText}`);
          console.log(error);
        }
      )
    }
  }

  changePhoto(event: any) {
    if (event.target.files.length === 0) {
      return;
    }

    this.file = event.target.files[0];

    if (this.file.type.includes('image')) {
      var reader = new FileReader();
      reader.readAsDataURL(this.file);

      reader.onload = () => {
        this.foto = reader.result || '';
      }
    }
  }

}
