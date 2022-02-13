import { TestBed } from '@angular/core/testing';

import { RssServiceService } from './rss-service.service';

describe('RssServiceService', () => {
  let service: RssServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RssServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
