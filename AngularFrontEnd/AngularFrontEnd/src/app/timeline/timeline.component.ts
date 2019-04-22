import {Component, OnInit} from '@angular/core';
import {RestService} from "../rest.service";
import {ActivatedRoute, Router} from "@angular/router";
import {JwtService} from "../jwt.service";

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {

  kweets: any = [];

  constructor(public rest: RestService, private jwt: JwtService, private router: Router) {
    this.router.routeReuseStrategy.shouldReuseRoute = function () {
      return false;
    };
    this.getTimeLine();
  }


  ngOnInit() {
  }

  public getTimeLine() {
    this.kweets = [];
    if (this.jwt.loggedIn) {
      this.rest.getTimeLine(this.jwt.getCurretnUser.userid).subscribe((data: {}) => {
          console.log(data)
          this.kweets = data;
        }
      )
    }
    ;
  }


}
