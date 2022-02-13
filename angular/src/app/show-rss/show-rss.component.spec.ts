import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowRssComponent } from './show-rss.component';

describe('ShowRssComponent', () => {
  let component: ShowRssComponent;
  let fixture: ComponentFixture<ShowRssComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowRssComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowRssComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
