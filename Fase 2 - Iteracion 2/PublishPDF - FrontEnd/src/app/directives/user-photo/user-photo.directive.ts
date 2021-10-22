import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appUserPhoto]'
})
export class UserPhotoDirective {

  readonly URL: string = "/assets/default_photo.png";

  constructor(private elemRef: ElementRef) {  }

  @HostListener('error')
  defaultImage() {
    const elem = this.elemRef.nativeElement;
    elem.src = this.URL;
  }

}
