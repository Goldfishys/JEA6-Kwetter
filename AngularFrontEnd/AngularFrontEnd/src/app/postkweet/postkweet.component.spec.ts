import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PostkweetComponent } from './postkweet.component';

describe('PostkweetComponent', () => {
  let component: PostkweetComponent;
  let fixture: ComponentFixture<PostkweetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PostkweetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PostkweetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
