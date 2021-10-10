import { Component, Input, OnInit } from '@angular/core';
import { Revista } from 'src/app/model/revista.model';

@Component({
  selector: 'app-card-container',
  templateUrl: './card-container.component.html',
  styleUrls: ['./card-container.component.css']
})
export class CardContainerComponent implements OnInit {

  @Input() revistas: Revista[] = []
  
  constructor() { }

  ngOnInit(): void {
    this.revistas = [
      { id: 1, fecha: new Date(2021, 10, 9), editor: 'Chafa', nombre: 'Avon', categoria: 'ventas' },
      { id: 1, fecha: new Date(2021, 10, 9), editor: 'Chafa', nombre: 'Avon', categoria: 'ventas' },
      { id: 1, fecha: new Date(2021, 10, 9), editor: 'Chafa', nombre: 'Avon', categoria: 'ventas' },
      { id: 1, fecha: new Date(2021, 10, 9), editor: 'Chafa', nombre: 'Avon', categoria: 'ventas' }
    ]
  }

}
