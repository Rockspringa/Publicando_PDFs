import { RevistasViewComponent } from './revista-components/revistas-view/revistas-view.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './user-components/home/home.component';
import { WindowComponent } from './user-components/window/window.component';
import { ProfileComponent } from './user-components/profile/profile.component';
import { GeneralGuard } from './guards/general/general.guard';
import { UpdateProfileComponent } from './user-components/update-profile/update-profile.component';
import { BuscarPdfComponent } from './revista-components/buscar-pdf/buscar-pdf.component';
import { RevistaFormComponent } from './revista-components/revista-form/revista-form.component';

const routes: Routes = [
  {
    path: 'user',
    component: WindowComponent,
    canActivate: [ GeneralGuard ],
    children: [
      { path: 'home', component: HomeComponent },
      { path: 'buscar', component: BuscarPdfComponent },
      { path: 'update', component: UpdateProfileComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'revistas', component: RevistasViewComponent },
      { path: 'publicar', component: RevistaFormComponent }
    ]
  },
  { path: 'login', component: LoginComponent },
  { path: 'sign-up', component: SignUpComponent },
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
