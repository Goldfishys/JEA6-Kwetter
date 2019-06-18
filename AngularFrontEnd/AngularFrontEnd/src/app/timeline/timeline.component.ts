import {Component, OnInit} from '@angular/core';
import {RestService} from "../rest.service";
import {ActivatedRoute, Router} from "@angular/router";
import {JwtService} from "../jwt.service";
import {WebsocketService} from "../websocket.service";

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {

  kweets: any[] = [];

  constructor(public rest: RestService, private jwt: JwtService, private router: Router, private websocketService: WebsocketService) {
    this.router.routeReuseStrategy.shouldReuseRoute = function () {
      return false;
    };
      this.getTimeLine();
      this.SubscribeToUpdates();
  }

  ngOnInit() {

  }

  public getTimeLine() {
    this.kweets = new Array();
    if (this.jwt.loggedIn) {
      this.rest.getTimeLine(this.jwt.getCurretnUser.userid).subscribe((data: any[]) => {
          console.log(data)
          this.kweets = data;
        }
      )
    }
    ;
  }

  private addNewKweet(msg: any){
    console.log(msg);
    msg.id = msg.ID;
    this.kweets.splice(0, 0, msg);
  }

  private SubscribeToUpdates() {
    if (this.jwt.loggedIn) {
      console.log("Starting websocket connection")
      this.websocketService.createConnection();
      this.websocketService.subject.subscribe(
        msg => this.addNewKweet(msg), // Called whenever there is a message from the server.
        err => console.log('Error occured: ' + err), // Called if at any point WebSocket API signals some kind of error.
        () => console.log('Connection closed') // Called when connection is closed (for whatever reason).
      );
    }
  }
}
