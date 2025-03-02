import { TestBed } from '@angular/core/testing';

import { DueBillsService } from './due-bills.service';

describe('DueBillsService', () => {
  let service: DueBillsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DueBillsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
