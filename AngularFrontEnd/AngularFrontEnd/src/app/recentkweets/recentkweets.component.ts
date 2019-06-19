import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {RestService} from "../rest.service";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-recentkweets',
  templateUrl: './recentkweets.component.html',
  styleUrls: ['./recentkweets.component.css']
})
export class RecentkweetsComponent implements OnInit,OnDestroy {
  @Input() userid:number;
  public recentkweets:any = [];
  public following:any = [];
  public followers:any = [];
  navigationSubscription;

  constructor(public rest:RestService) {

  }

  ngOnInit() {
    this.initialiseRecentKweets();
  }

  ngOnDestroy(): void {
    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }

  private initialiseRecentKweets(){
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
