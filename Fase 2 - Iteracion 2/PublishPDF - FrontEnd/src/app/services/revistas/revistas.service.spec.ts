import { TestBed } from '@angular/core/testing';

import { RevistasService } from './revistas.service';

describe('RevistasService', () => {
  let service: RevistasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RevistasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
