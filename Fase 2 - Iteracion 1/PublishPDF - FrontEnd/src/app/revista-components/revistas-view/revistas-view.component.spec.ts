import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevistasViewComponent } from './revistas-view.component';

describe('RevistasViewComponent', () => {
  let component: RevistasViewComponent;
  let fixture: ComponentFixture<RevistasViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RevistasViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RevistasViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
