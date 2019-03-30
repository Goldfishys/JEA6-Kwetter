import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  endpoint = 'http://localhost:8080/javaee8-essentials-archetype/kwetter';
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      'Access-Control-Allow-Origin': "*"
    })
  };

  constructor(private http: HttpClient) {

  }

  private extractData(res: Response) {
    let body = res;
    return body || { };
  }

  searchKweets(searchTerm): Observable<any> {
    return this.http.get(this.endpoint + '/kweet/search/' + searchTerm).pipe(
      map(this.extractData));
  }

  getTimeLine(id): Observable<any>{
    return this.http.get(this.endpoint + '/kweet/timeline/' + id).pipe(
      map(this.extractData));
  }

  getKweet(id): any {
    return this.http.get(this.endpoint + '/kweet/' + id).pipe(
      map(this.extractData));
  }

  getUser(id): any {
    return this.http.get(this.endpoint + '/user/' + id).pipe(
      map(this.extractData));
  }

  private handleError<T> (operation = 'operation', result?: T) {
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
