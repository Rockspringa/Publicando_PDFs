import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevistaFormComponent } from './revista-form.component';

describe('RevistaFormComponent', () => {
  let component: RevistaFormComponent;
  let fixture: ComponentFixture<RevistaFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RevistaFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RevistaFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
