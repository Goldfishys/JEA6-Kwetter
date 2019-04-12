import {Component, Input, OnInit} from '@angular/core';
import {RestService} from "../rest.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-postkweet',
  templateUrl: './postkweet.component.html',
  styleUrls: ['./postkweet.component.css']
})
export class PostkweetComponent implements OnInit {

  @Input() kweetdata = {
    text:"",
    author:1
  };

  constructor(public rest:RestService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.kweetdata.author = 1;
  }

  public postkweet(){
    this.rest.postkweet(this.kweetdata).subscribe((err) => {
      console.log(err);
    });
  }

}
