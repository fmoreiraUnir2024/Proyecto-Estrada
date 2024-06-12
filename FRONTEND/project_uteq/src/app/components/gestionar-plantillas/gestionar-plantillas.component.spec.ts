import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarPlantillasComponent } from './gestionar-plantillas.component';

describe('GestionarPlantillasComponent', () => {
  let component: GestionarPlantillasComponent;
  let fixture: ComponentFixture<GestionarPlantillasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GestionarPlantillasComponent]
    });
    fixture = TestBed.createComponent(GestionarPlantillasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
