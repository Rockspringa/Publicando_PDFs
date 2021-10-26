import { Revista } from '../../model/revista/revista.model';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-pdf-card',
  templateUrl: './pdf-card.component.html',
  styleUrls: ['./pdf-card.component.css']
})
export class PdfCardComponent implements OnInit {

  @Input() revista!: Revista;
  revistaLink: string = "/suscriptor/revista/";

  constructor() { }

  ngOnInit(): void {
    this.revistaLink += this.revista.id;
  }

}
