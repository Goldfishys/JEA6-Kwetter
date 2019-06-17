import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {catchError, tap} from "rxjs/operators";
import {__param} from "tslib";
import {Observable} from "rxjs";
import {Url} from "url";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  endpoint = 'http://localhost:8080/javaee8-essentials-archetype/kwetter';
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };
  constructor(private httpClient: HttpClient, private myRoute: Router) { }

  public get loggedIn(): boolean{
    return localStorage.getItem('access_token') !==  null;
  }

  public get getCurretnUser(): any{
    let currentUser:any = {};
    currentUser.userid = localStorage.getItem("UserID");
    currentUser.username = localStorage.getItem("Username");
    return currentUser;
  }

  login(username, password): Observable<any> {
    console.log("logging in for: " + username + " - " + password);
    let bodyparam = JSON.stringify([username, password])

    return this.httpClient.post<any>(this.endpoint +  "/login", bodyparam, this.httpOptions).pipe(
      tap(token => {
        if(token != null) {
          console.log(token);
          localStorage.setItem('access_token', token.token);
          localStorage.setItem('UserID', token.userid);
          localStorage.setItem('Username', token.username);
          this.myRoute.navigate(["/"]);
        }
      }));
  }

  logout() {
    localStorage.removeItem('access_token');
    localStorage.removeItem('UserID');
    localStorage.removeItem('Username');
  }
}
