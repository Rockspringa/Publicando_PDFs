import { Component, Input, OnInit } from '@angular/core';
import { Revista } from 'src/app/model/revista/revista.model';

@Component({
  selector: 'app-card-container',
  templateUrl: './card-container.component.html',
  styleUrls: ['./card-container.component.css']
})
export class CardContainerComponent implements OnInit {

  @Input() revistas: Revista[] = []
  
  constructor() { }

  ngOnInit(): void {
  }

}
