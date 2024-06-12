import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegistrationComponent } from './components/registration/registration.component';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CreateProjectComponent } from './components/create-project/create-project.component';
import { ProjectDataEntryComponent } from './components/project-data-entry/project-data-entry.component';
import { StyleGuideComponent } from './components/style-guide/style-guide.component';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { GrammarReviewComponent } from './components/grammar-review/grammar-review.component';
import { ProjectManagementComponent } from './components/project-management/project-management.component';
import {  GestionarPlantillasComponent } from './components/gestionar-plantillas/gestionar-plantillas.component'
const routes: Routes = [
  { path: 'register', component: RegistrationComponent },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'create-project', component: CreateProjectComponent },
  { path: 'project-data-entry', component: ProjectDataEntryComponent },
  { path: 'style-guide', component: StyleGuideComponent },
  { path: 'feedback', component: FeedbackComponent },
  { path: 'plantillas', component: GestionarPlantillasComponent },
  { path: 'grammar-review', component: GrammarReviewComponent },
  { path: 'project-management', component: ProjectManagementComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
