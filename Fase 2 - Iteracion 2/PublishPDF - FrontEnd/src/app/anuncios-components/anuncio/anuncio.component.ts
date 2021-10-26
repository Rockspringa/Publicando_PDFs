import { HttpErrorResponse } from '@angular/common/http';
import { AnunciosService } from './../../services/anuncios/anuncios.service';
import { Anuncio } from './../../model/anuncio/anuncio.model';
import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-anuncio',
  templateUrl: './anuncio.component.html',
  styleUrls: ['./anuncio.component.css']
})
export class AnuncioComponent implements OnInit {

  @Input() username!: string;
  anuncios: Anuncio[] = [];
  timeLeft: number = 15;
  index: number = 0;

  constructor(private sanitizer: DomSanitizer, private anunciosService: AnunciosService) {
    setInterval(() => {
      if (this.timeLeft > 0) {
        this.timeLeft--;
      } else {
        if (this.index < this.anuncios.length) {
          this.index++;
        }
        this.timeLeft = 15;
      }
    }, 1000);
  }

  ngOnInit(): void {
    this.anunciosService.findAll(this.username).subscribe(
      (data: Anuncio[]) => this.anuncios = data,
      (error: HttpErrorResponse) => console.log(error)
    )
  }

  getVideoSource() {
    return this.sanitizer.bypassSecurityTrustHtml(this.anuncios[this.index].video ?? '');
  }
}
