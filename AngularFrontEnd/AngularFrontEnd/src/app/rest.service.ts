import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {map, catchError, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  endpoint = 'http://localhost:8080/javaee8-essentials-archetype/kwetter';
  headers_object = new HttpHeaders().set("Authorization", "Bearer " + localStorage.getItem("access_token"));
  httpOptions = {
    headers: this.headers_object
  };

  constructor(private http: HttpClient) {

  }

  private extractData(res: Response) {
    let body = res;
    return body || {};
  }

  postkweet(kweet): Observable<any> {
    console.log(kweet);
    return this.http.post<any>(this.endpoint + '/kweet', JSON.stringify(kweet), this.httpOptions).pipe(
      tap((kweet) => console.log(`added kweet w/ id=${kweet.id}`)),
      catchError(this.handleError<any>('postkweet'))
    );
  }

  searchKweets(searchTerm): Observable<any> {
    console.log("st: " + searchTerm);
    return this.http.get(this.endpoint + '/kweet/search/' + searchTerm).pipe(
      map(this.extractData));
  }

  getTimeLine(id): Observable<any> {
    console.log("token is:");
    console.log(localStorage.getItem('access_token'));
    return this.http.get(this.endpoint + '/kweet/timeline/' + id, this.httpOptions).pipe(
      map(this.extractData));
  }

  getKweet(id): Observable<any> {
    return this.http.get(this.endpoint + '/kweet/' + id).pipe(
      map(this.extractData));
  }

  getUser(id): Observable<any> {
    return this.http.get(this.endpoint + '/user/' + id).pipe(
      map(this.extractData));
  }

  getProfile(userid): Observable<any> {
    return this.http.get(this.endpoint + '/profile/' + userid).pipe(
      map(this.extractData));
  }

  getRecentKweets(userid): Observable<any> {
    return this.http.get(this.endpoint + '/kweet/recentkweets/' + userid).pipe(
      map(this.extractData));
  }

  getFollowers(userid): Observable<any> {
    return this.http.get(this.endpoint + '/user/' + userid + '/followers').pipe(
      map(this.extractData));
  }

  getFollowing(userid): Observable<any> {
    return this.http.get(this.endpoint + '/user/' + userid + '/following').pipe(
      map(this.extractData));
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
