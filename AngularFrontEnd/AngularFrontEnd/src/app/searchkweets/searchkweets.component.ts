import {Component, Input, OnInit} from '@angular/core';
import {RestService} from "../rest.service";
import {ActivatedRoute, Router} from "@angular/router";
import {applySourceSpanToExpressionIfNeeded} from "@angular/compiler/src/output/output_ast";

@Component({
  selector: 'app-searchkweets',
  templateUrl: './searchkweets.component.html',
  styleUrls: ['./searchkweets.component.css']
})
export class SearchkweetsComponent implements OnInit {
  kweets:any = [];

  constructor(public rest:RestService, private route: ActivatedRoute, private router: Router) {
    route.queryParams.subscribe(params => {
      console.log(params.searchterm);
      this.searchKweets(params.searchterm);
    })
  }


  ngOnInit() {
  }

  public searchKweets(searchTerm){
    this.kweets= [];
    console.log("Term: " + searchTerm);
    this.rest.searchKweets(searchTerm).subscribe((data: {}) =>{
      console.log(data);
      this.kweets = data;
    });
  }
}
