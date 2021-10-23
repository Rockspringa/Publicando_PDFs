import { VariablesService } from '../../services/global/variables.service';
import { Administrador } from 'src/app/model/users/admin.model';
import { Editor } from 'src/app/model/users/editor.model';
import { Suscriptor } from 'src/app/model/users/suscriptor.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  user!: Suscriptor | Editor | Administrador;
  rec: string = "invisible";

  constructor(private variables: VariablesService) {
  }

  ngOnInit(): void {
    this.user = this.variables.getUserLogged();
    if (this.user instanceof Suscriptor) {
      this.rec = "container";
    }
  }

}
