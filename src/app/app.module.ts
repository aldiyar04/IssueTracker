import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { ProjectsComponent } from './projects/projects.component';
import { SignupComponent } from './signup/signup.component';
import { FormsModule } from '@angular/forms';
import { NotificationModule } from './notification.module';
import { httpInterceptorProviders } from './auth-http-interceptor';
import { IsLoggedInGuard } from './is-logged-in-guard';
import { IsNotLoggedInGuard } from './is-not-logged-in-guard';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ProjectsComponent,
    SignupComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NotificationModule
  ],
  providers: [httpInterceptorProviders, IsLoggedInGuard, IsNotLoggedInGuard],
  bootstrap: [AppComponent]
})
export class AppModule {}
