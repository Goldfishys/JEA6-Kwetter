import {Component, OnDestroy, OnInit} from '@angular/core';
import {RestService} from "../rest.service";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-profielpagina',
  templateUrl: './profielpagina.component.html',
  styleUrls: ['./profielpagina.component.css']
})
export class ProfielpaginaComponent implements OnInit,OnDestroy {
  public userid:number;
  public profiledetails:any;
  public following:any = [];
  userSubscription: Subscription;

  constructor(public rest:RestService, private route: ActivatedRoute, private router: Router) {
    this.router.routeReuseStrategy.shouldReuseRoute = function(){
      return false;
    };

    this.router.events.subscribe((evt) => {
      if (evt instanceof NavigationEnd) {
        this.router.navigated = false;
        window.scrollTo(0, 0);
      }
    });

    this.userSubscription = route.queryParams.subscribe(params => {
      this.userid = params.userid;
    })
    this.getFollowing();
    this.getProfileDetails();
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.userSubscription.unsubscribe()
  }

  public getProfileDetails(){
    this.rest.getProfile(this.userid).subscribe((data:any)=> {
      console.log(data);
      this.profiledetails = data;
      this.getUsername();
    });
  }

  public getUsername(){
    this.rest.getUser(this.userid).subscribe((data:any)=> {
      this.profiledetails.username = data.username;
    });
  }

  public getFollowing(){
    this.rest.getFollowing(this.userid).subscribe((data:{})=>{
      console.log("getting following");
      console.log(data);
      this.following = data;
    });
  }
}
