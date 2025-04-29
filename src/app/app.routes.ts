import { Routes } from '@angular/router';
import { LandingPageComponent } from './landing-page/landing-page.component';
import {LoginComponent} from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { HackathonFormComponent } from './admin-dashboard/hackathon-form/hackathon-form.component';

export const routes: Routes = [
   
    {path: '', component: LandingPageComponent},
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegistrationComponent},
    {path: 'admin-dashboard', component: AdminDashboardComponent},
    {path: 'hackathon-form', component: HackathonFormComponent},


    


    
    

   

];