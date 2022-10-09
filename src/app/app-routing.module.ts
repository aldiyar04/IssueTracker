import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IsLoggedInGuard } from './is-logged-in-guard';
import { IsNotLoggedInGuard } from './is-not-logged-in-guard';
import { LoginComponent } from './login/login.component';
import { ProjectsComponent } from './projects/projects.component';
import { SignupComponent } from './signup/signup.component';

const routes: Routes = [
  {path: '', component: ProjectsComponent, canActivate: [IsNotLoggedInGuard]},
  {path: 'login', component: LoginComponent, canActivate: [IsLoggedInGuard]},
  {path: 'signup', component: SignupComponent, canActivate: [IsLoggedInGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
