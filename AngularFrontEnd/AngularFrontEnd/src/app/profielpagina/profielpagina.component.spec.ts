import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfielpaginaComponent } from './profielpagina.component';

describe('ProfielpaginaComponent', () => {
  let component: ProfielpaginaComponent;
  let fixture: ComponentFixture<ProfielpaginaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfielpaginaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfielpaginaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
