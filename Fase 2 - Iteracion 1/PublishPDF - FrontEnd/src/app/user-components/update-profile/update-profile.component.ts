import { Usuario } from './../../model/usuario.model';
import { UsuarioService } from './../../services/users/usuario.service';
import { VariablesService } from 'src/app/services/global/variables.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Administrador } from 'src/app/model/users/admin.model';
import { Editor } from 'src/app/model/users/editor.model';
import { Suscriptor } from 'src/app/model/users/suscriptor.model';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})
export class UpdateProfileComponent implements OnInit {

  file!: File;
  formUpdate!: FormGroup;
  user!: Suscriptor | Editor | Administrador;
  foto!: string | ArrayBuffer;

  inputs!: Function;
  button!: Function;

  constructor(private formBuilder: FormBuilder, private functions: VariablesService,
        private usuarioService: UsuarioService, private router: Router) { }

  ngOnInit(): void {
    this.user = this.functions.getUserLogged();
    this.foto = Usuario.url_foto + this.user.username;
    this.inputs = this.functions.getInputClasses;
    this.button = this.functions.getButtonClasses;

    this.formUpdate = this.formBuilder.group({
      username: [this.user.username],
      password: ['', [Validators.pattern("^[\\w ]{1,40}$"), Validators.required]],
      nombre: [this.user.nombre, [Validators.pattern("^[\\w ]{1,40}$")]],
      descripcion: [this.user.descripcion, [Validators.pattern("^[\\w ]{1,150}$")]],
      gustos: [this.user.gustos, [Validators.pattern("^[\\w ]{1,150}$")]],
      hobbies: [this.user.hobbies, [Validators.pattern("^[\\w ]{1,150}$")]]
    });
  }

  private uploadPhoto() {
    const imgData = new FormData();

    if (this.file) {
      const filename = this.file.name.split('.');
      const extension = filename[filename.length - 1];

      imgData.append('file', this.file, this.user.username + "_foto." + extension);

      this.usuarioService.updatePhoto(imgData, this.user).subscribe(
        (data: void) => {
        }, (error: HttpErrorResponse) => {
          alert(`No se pudo actualizar la imagen.\nError ${error.status}: ${error.statusText}`);
          console.log(error);
        }
      )
    }
  }

  changePhoto(event: any) {
    if (event.target.files.length === 0)
      return;

    this.file = event.target.files[0];

    if (this.file.type.includes('image')) {
      var reader = new FileReader();
      reader.readAsDataURL(this.file);

      reader.onload = () => {
        this.foto = reader.result || '';
      }
    }
  }

  updateCuenta() {
    let user: Suscriptor | Editor | Administrador = this.user;
    user = Object.assign(user, this.formUpdate.value);

    this.usuarioService.patch(user).subscribe(
      (data: void) => {
        this.user.nombre = this.formUpdate.controls['nombre'].value;
        this.user.descripcion = this.formUpdate.controls['descripcion'].value;
        this.user.gustos = this.formUpdate.controls['gustos'].value;
        this.user.hobbies = this.formUpdate.controls['hobbies'].value;

        this.functions.setGlobalUser(JSON.parse(JSON.stringify(this.user)));

        this.uploadPhoto();
        this.router.navigate([ '/user/profile' ]);
      }, (error: HttpErrorResponse) => {
        alert(`No se pudo actualizar la informacion.\nError ${error.status}: ${error.statusText}`);
        console.log(error);

        this.router.navigateByUrl('/user/profile', { skipLocationChange: true }).then(() => {
          this.router.navigate([ '/user/update' ]);
        }); 
      }
    )
  }

}
