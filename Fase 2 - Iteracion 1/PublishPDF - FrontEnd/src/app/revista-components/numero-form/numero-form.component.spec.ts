import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NumeroFormComponent } from './numero-form.component';

describe('NumeroFormComponent', () => {
  let component: NumeroFormComponent;
  let fixture: ComponentFixture<NumeroFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NumeroFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NumeroFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
