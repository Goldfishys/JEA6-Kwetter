import { Component, OnInit } from '@angular/core';
import {RestService} from "../rest.service";
import {JwtService} from "../jwt.service";
import {Router, Routes} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public statusMessage;

  constructor(public rest:RestService, public jwt:JwtService, private router:Router) { }

  ngOnInit() {
  }

  public Login(username, password){
    console.log("found the login :) username: " + username + " - password: " + password);

    if(username != "" && password != ""){
      this.jwt.login(username,password).subscribe((result) => {
        if(result != null) {
          this.statusMessage = "";
        }else{
          this.statusMessage = "Incorrect username/password combination";
        }
      });
    }else{
      this.statusMessage = "Please fill in both fields";
    }
  }
}
