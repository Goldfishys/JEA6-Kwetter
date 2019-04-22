import { Injectable } from '@angular/core';
import {CanActivate, CanActivateChild} from '@angular/router';
import {JwtService} from "./jwt.service";

@Injectable()
export class JwtGuard implements CanActivate, CanActivateChild  {

  constructor(private jwt:JwtService) {}

  canActivate() {
    console.log("checking loggedin");
    console.log(this.jwt.loggedIn);
    return this.jwt.loggedIn;
  }

  canActivateChild(): boolean {
    console.log("checking loggedin for child");
    console.log(this.jwt.loggedIn);
    return this.jwt.loggedIn;
  }
}
