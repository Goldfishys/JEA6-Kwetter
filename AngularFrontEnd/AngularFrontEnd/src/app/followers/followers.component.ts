import {Component, Input, OnInit} from '@angular/core';
import {RestService} from "../rest.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-followers',
  templateUrl: './followers.component.html',
  styleUrls: ['./followers.component.css']
})
export class FollowersComponent implements OnInit {

  public userid:number;
  public followers:any = [];

  constructor(public rest:RestService, private route: ActivatedRoute) {
    route.queryParams.subscribe(params => {
      this.userid = params.userid;
    })
    this.getFollowers();
  }

  ngOnInit() {

  }

  public getFollowers(){
    this.rest.getFollowers(this.userid).subscribe((data:{})=>{
      console.log("getting followers 2k");
      console.log(data);
      console.log(data[0].username)
      this.followers = data;
    });
  }
}
