import {Component, Input, OnInit} from '@angular/core';
import {RestService} from "../rest.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-recentkweets',
  templateUrl: './recentkweets.component.html',
  styleUrls: ['./recentkweets.component.css']
})
export class RecentkweetsComponent implements OnInit {
  public userid:number;
  public recentkweets:any = [];
  public following:any = [];
  public followers:any = [];

  constructor(public rest:RestService, private route: ActivatedRoute, private router: Router) {
    route.queryParams.subscribe(params => {
      this.userid = params.userid;
      console.log("userid in rk: " + params.userid)
    })
  }

  ngOnInit() {
    console.log("userid: " + this.userid);
    this.getRecentKweets();
  }

  private getRecentKweets(){
    this.rest.getRecentKweets(this.userid).subscribe((data: {}) =>{
      console.log(data);
      this.recentkweets = data;
      });
  }

  private getFollowers(){

  }

  private getFollowing(){

  }
}
