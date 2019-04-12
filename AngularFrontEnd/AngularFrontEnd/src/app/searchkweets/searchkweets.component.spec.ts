import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchkweetsComponent } from './searchkweets.component';

describe('SearchkweetsComponent', () => {
  let component: SearchkweetsComponent;
  let fixture: ComponentFixture<SearchkweetsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchkweetsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchkweetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
