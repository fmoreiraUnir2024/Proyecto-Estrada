import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CreateProjectComponent } from './components/create-project/create-project.component';
import { ProjectDataEntryComponent } from './components/project-data-entry/project-data-entry.component';
import { StyleGuideComponent } from './components/style-guide/style-guide.component';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { GrammarReviewComponent } from './components/grammar-review/grammar-review.component';
import { ProjectManagementComponent } from './components/project-management/project-management.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    RegistrationComponent,
    LoginComponent,
    DashboardComponent,
    CreateProjectComponent,
    ProjectDataEntryComponent,
    StyleGuideComponent,
    FeedbackComponent,
    GrammarReviewComponent,
    ProjectManagementComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
