import {Component, OnInit, OnDestroy} from '@angular/core';
import {RestService} from "../rest.service";
import {JwtService} from "../jwt.service";
import {WebsocketService} from "../websocket.service";

// const SERVER_URL = "ws://localhost:8080/javaee8-essentials-archetype/websocket";

@Component({
  selector: 'app-startpagina',
  templateUrl: './startpagina.component.html',
  styleUrls: ['./startpagina.component.css']
})
export class StartpaginaComponent implements OnInit {

  constructor(public rest: RestService, public jwt: JwtService) {}

  ngOnInit() {

  }
}
