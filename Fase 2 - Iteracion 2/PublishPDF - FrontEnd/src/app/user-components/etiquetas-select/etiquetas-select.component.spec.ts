import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EtiquetasSelectComponent } from './etiquetas-select.component';

describe('EtiquetasSelectComponent', () => {
  let component: EtiquetasSelectComponent;
  let fixture: ComponentFixture<EtiquetasSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EtiquetasSelectComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EtiquetasSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
