import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PdfCardComponent } from './pdf-card.component';

describe('PdfCardComponent', () => {
  let component: PdfCardComponent;
  let fixture: ComponentFixture<PdfCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PdfCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PdfCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
