import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HeaderComponent } from './header/header.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { WindowComponent } from './user-components/window/window.component';
import { HomeComponent } from './user-components/home/home.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { ProfileComponent } from './user-components/profile/profile.component';
import { UpdateProfileComponent } from './user-components/update-profile/update-profile.component';
import { UserPhotoDirective } from './directives/user-photo/user-photo.directive';
import { PdfCardComponent } from './revista-components/pdf-card/pdf-card.component';
import { BuscarPdfComponent } from './revista-components/buscar-pdf/buscar-pdf.component';
import { CardContainerComponent } from './revista-components/card-container/card-container.component';
import { RevistaFormComponent } from './revista-components/revista-form/revista-form.component';
import { RevistasViewComponent } from './revista-components/revistas-view/revistas-view.component';
import { NumeroFormComponent } from './revista-components/numero-form/numero-form.component';
import { RevistaComponent } from './revista-components/revista/revista.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    HeaderComponent,
    WindowComponent,
    SignUpComponent,
    ProfileComponent,
    UpdateProfileComponent,
    UserPhotoDirective,
    PdfCardComponent,
    BuscarPdfComponent,
    CardContainerComponent,
    RevistaFormComponent,
    RevistasViewComponent,
    NumeroFormComponent,
    RevistaComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [ ],
  bootstrap: [AppComponent]
})
export class AppModule { }
