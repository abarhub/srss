import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LineClampComponent } from './line-clamp.component';

describe('LineClampComponent', () => {
  let component: LineClampComponent;
  let fixture: ComponentFixture<LineClampComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LineClampComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LineClampComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
