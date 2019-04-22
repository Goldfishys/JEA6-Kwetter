import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {AppComponent} from './app.component';
import {KweetComponent} from './kweet/kweet.component';
import {StartpaginaComponent} from './startpagina/startpagina.component';
import {AppRoutingModule} from "./app-routing.module";
import {TimelineComponent} from './timeline/timeline.component';
import {SearchkweetsComponent} from './searchkweets/searchkweets.component';
import {PostkweetComponent} from './postkweet/postkweet.component';
import {ProfielpaginaComponent} from './profielpagina/profielpagina.component';
import {RecentkweetsComponent} from './recentkweets/recentkweets.component';
import {FollowersComponent} from './followers/followers.component';
import {LoginComponent} from './login/login.component';
import {JwtModule} from '@auth0/angular-jwt';
import {JwtGuard} from "./JwtGuard";
import {JwtService} from "./jwt.service";

@NgModule({
  declarations: [
    AppComponent,
    KweetComponent,
    StartpaginaComponent,
    TimelineComponent,
    SearchkweetsComponent,
    PostkweetComponent,
    ProfielpaginaComponent,
    RecentkweetsComponent,
    FollowersComponent,
    LoginComponent
  ],
  imports: [
    AppRoutingModule,
    FormsModule,
    BrowserModule,
    HttpClientModule
  ],
  providers: [
    JwtService,
    JwtGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
