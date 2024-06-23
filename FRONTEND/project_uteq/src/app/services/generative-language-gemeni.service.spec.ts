import { TestBed } from '@angular/core/testing';

import { GenerativeLanguageGemeniService } from './generative-language-gemeni.service';

describe('GenerativeLanguageGemeniService', () => {
  let service: GenerativeLanguageGemeniService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GenerativeLanguageGemeniService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
