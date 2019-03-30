import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {StartpaginaComponent} from "./startpagina/startpagina.component";

const routes: Routes = [
  {path: '', component: StartpaginaComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
