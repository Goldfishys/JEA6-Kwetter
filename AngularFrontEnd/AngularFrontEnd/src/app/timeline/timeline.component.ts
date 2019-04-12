import { Component, OnInit } from '@angular/core';
import {RestService} from "../rest.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {

  kweets:any = [];

  constructor(public rest:RestService, private route: ActivatedRoute, private router: Router) {
    this.getTimeLine();
  }


  ngOnInit() {
  }

  public getTimeLine(){
    this.kweets= [];
    this.rest.getTimeLine(1).subscribe((data: {}) =>{
      console.log(data)
      this.kweets = data;
    });
  }


}
