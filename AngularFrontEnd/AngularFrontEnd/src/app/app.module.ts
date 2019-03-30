import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { KweetComponent } from './kweet/kweet.component';
import { StartpaginaComponent } from './startpagina/startpagina.component';
import {AppRoutingModule} from "./app-routing.module";
import { TimelineComponent } from './timeline/timeline.component';



@NgModule({
  declarations: [
    AppComponent,
    KweetComponent,
    StartpaginaComponent,
    TimelineComponent
  ],
  imports: [
    AppRoutingModule,
    FormsModule,
    BrowserModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
