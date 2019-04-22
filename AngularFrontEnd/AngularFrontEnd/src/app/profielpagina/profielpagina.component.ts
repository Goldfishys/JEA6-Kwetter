import {Component, OnDestroy, OnInit} from '@angular/core';
import {RestService} from "../rest.service";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-profielpagina',
  templateUrl: './profielpagina.component.html',
  styleUrls: ['./profielpagina.component.css']
})
export class ProfielpaginaComponent implements OnInit,OnDestroy {
  public userid:number;
  public profiledetails:any;
  public following:any = [];
  navigationSubscription;

  constructor(public rest:RestService, private route: ActivatedRoute, private router: Router) {
    this.navigationSubscription = this.router.events.subscribe((e: any) => {
      if (e instanceof NavigationEnd) {
        this.initialiseProfile();
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

  private initialiseProfile() {
    this.route.params.subscribe(params => {
      this.userid = +params['id'];
      console.log("userid pp: " + this.userid);
    });
    this.getFollowing();
    this.getProfileDetails();
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
