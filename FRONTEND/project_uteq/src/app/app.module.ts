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
import { AuthInterceptor } from './services/AuthInterceptor'; // Importa el interceptor
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { GestionarPlantillasComponent } from './components/gestionar-plantillas/gestionar-plantillas.component'; 

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
    ProjectManagementComponent,
    GestionarPlantillasComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule ,
  
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
