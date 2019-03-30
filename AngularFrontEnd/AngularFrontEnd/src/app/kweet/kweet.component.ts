import {Component, Input, OnInit} from '@angular/core';
import { RestService } from '../rest.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-kweet',
  templateUrl: './kweet.component.html',
  styleUrls: ['./kweet.component.css']
})
export class KweetComponent implements OnInit {
  @Input() kweetid: number;
  kweet:any;

  constructor(public rest:RestService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.getKweet();
  }

  public getKweet(){
    console.log("starting to get kweet")
    this.rest.getKweet(this.kweetid).subscribe((data: any) => {
      this.kweet = data;
      this.getUsername();
      console.log(data.text);
    });
  }

  public getUsername(): any{
    this.rest.getUser(this.kweet.author).subscribe((data: any) => {
      this.kweet.username = data.username;
      console.log(data.username)
    })
  }
}
