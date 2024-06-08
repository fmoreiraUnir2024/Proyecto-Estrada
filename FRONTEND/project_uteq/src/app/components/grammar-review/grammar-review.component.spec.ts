import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GrammarReviewComponent } from './grammar-review.component';

describe('GrammarReviewComponent', () => {
  let component: GrammarReviewComponent;
  let fixture: ComponentFixture<GrammarReviewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GrammarReviewComponent]
    });
    fixture = TestBed.createComponent(GrammarReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
