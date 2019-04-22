import {Component, OnDestroy, OnInit} from '@angular/core';
import {RestService} from "../rest.service";
import {JwtService} from "../jwt.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-startpagina',
  templateUrl: './startpagina.component.html',
  styleUrls: ['./startpagina.component.css']
})
export class StartpaginaComponent implements OnInit {

  constructor(public rest: RestService, public jwt: JwtService, private router: Router) {
  }

  ngOnInit() {
  }

  logout(){
    this.jwt.logout();

  }
}
