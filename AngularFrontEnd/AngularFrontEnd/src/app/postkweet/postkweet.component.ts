import {Component, Input, OnInit} from '@angular/core';
import {RestService} from "../rest.service";
import {JwtService} from "../jwt.service";
import {WebsocketService} from "../websocket.service";

@Component({
  selector: 'app-postkweet',
  templateUrl: './postkweet.component.html',
  styleUrls: ['./postkweet.component.css']
})
export class PostkweetComponent implements OnInit {

  @Input() kweetdata = {
    text:"",
    author: 0
  };

  constructor(public rest:RestService, public jwt:JwtService, private websocketService: WebsocketService) {

  }

  ngOnInit() {
  }

  public postkweet(){
    this.kweetdata.author = this.jwt.getCurretnUser.userid;
    this.rest.postkweet(this.kweetdata);
  }

}
