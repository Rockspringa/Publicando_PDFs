import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuscarPdfComponent } from './buscar-pdf.component';

describe('BuscarPdfComponent', () => {
  let component: BuscarPdfComponent;
  let fixture: ComponentFixture<BuscarPdfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BuscarPdfComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BuscarPdfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
