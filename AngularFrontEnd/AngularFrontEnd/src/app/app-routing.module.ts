import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {StartpaginaComponent} from "./startpagina/startpagina.component";
import {TimelineComponent} from "./timeline/timeline.component";
import {SearchkweetsComponent} from "./searchkweets/searchkweets.component";
import {ProfielpaginaComponent} from "./profielpagina/profielpagina.component";
import {RecentkweetsComponent} from "./recentkweets/recentkweets.component";
import {FollowersComponent} from "./followers/followers.component";

const routes: Routes = [
  {
    path: '',
    component: StartpaginaComponent,
    children: [
      {
        path: "",
        component: TimelineComponent,
      },
      {
        path: "searchkweets",
        component: SearchkweetsComponent
      }
    ]
  },
  {
    path: "profile",
    component: ProfielpaginaComponent,
    children:[
      {
        path:"",
        component: RecentkweetsComponent
      },
      {
        path:"followers",
        component:FollowersComponent
      }
    ]
  },
  {
    path:"profilerf",
    redirectTo:"profile"
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
