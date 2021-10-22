import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-revistas-view',
  templateUrl: './revistas-view.component.html',
  styleUrls: ['./revistas-view.component.css']
})
export class RevistasViewComponent implements OnInit {

  readonly link = '/user/publicar';

  constructor() { }

  ngOnInit(): void {
  }

}
