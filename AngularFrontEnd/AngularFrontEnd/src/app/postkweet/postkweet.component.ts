import {Component, Input, OnInit} from '@angular/core';
import {RestService} from "../rest.service";
import {ActivatedRoute, Router} from "@angular/router";
import {JwtService} from "../jwt.service";

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

  constructor(public rest:RestService, public jwt:JwtService) { }

  ngOnInit() {
  }

  public postkweet(){
    this.kweetdata.author = this.jwt.getCurretnUser.userid;
    this.rest.postkweet(this.kweetdata).subscribe((err) => {
      console.log(err);
    });
  }

}
