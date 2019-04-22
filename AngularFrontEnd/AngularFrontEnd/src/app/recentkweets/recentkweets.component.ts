import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {RestService} from "../rest.service";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-recentkweets',
  templateUrl: './recentkweets.component.html',
  styleUrls: ['./recentkweets.component.css']
})
export class RecentkweetsComponent implements OnInit,OnDestroy {
  public userid:number;
  public recentkweets:any = [];
  public following:any = [];
  public followers:any = [];
  navigationSubscription;

  constructor(public rest:RestService, private route: ActivatedRoute, private router: Router) {
    this.navigationSubscription = this.router.events.subscribe((e: any) => {
      if (e instanceof NavigationEnd) {
        this.initialiseRecentKweets();
      }
    });
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }

  private initialiseRecentKweets(){
    this.route.params.subscribe(params => {
      this.userid = +params['id'];
      console.log("userid pp: " + this.userid);
    });
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
