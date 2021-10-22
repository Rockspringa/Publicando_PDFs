import { Revista } from './../../model/revista.model';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-pdf-card',
  templateUrl: './pdf-card.component.html',
  styleUrls: ['./pdf-card.component.css']
})
export class PdfCardComponent implements OnInit {

  @Input() revista!: Revista;

  constructor() { }

  ngOnInit(): void {
  }

}
