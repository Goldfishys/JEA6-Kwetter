import {Component, Input, OnInit} from '@angular/core';
import {RestService} from "../rest.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-followers',
  templateUrl: './followers.component.html',
  styleUrls: ['./followers.component.css']
})
export class FollowersComponent implements OnInit {

  @Input() userid:number;
  public followers:any = [];

  constructor(public rest:RestService) {

  }

  ngOnInit() {
    this.getFollowers();
  }

  public getFollowers(){
    this.rest.getFollowers(this.userid).subscribe((data:{})=>{
      this.followers = data;
    });
  }
}
