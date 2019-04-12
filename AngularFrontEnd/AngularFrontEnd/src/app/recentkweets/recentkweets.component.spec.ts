import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecentkweetsComponent } from './recentkweets.component';

describe('RecentkweetsComponent', () => {
  let component: RecentkweetsComponent;
  let fixture: ComponentFixture<RecentkweetsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecentkweetsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecentkweetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
