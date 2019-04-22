import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {StartpaginaComponent} from "./startpagina/startpagina.component";
import {TimelineComponent} from "./timeline/timeline.component";
import {SearchkweetsComponent} from "./searchkweets/searchkweets.component";
import {ProfielpaginaComponent} from "./profielpagina/profielpagina.component";
import {RecentkweetsComponent} from "./recentkweets/recentkweets.component";
import {FollowersComponent} from "./followers/followers.component";
import {LoginComponent} from "./login/login.component";

const routes: Routes = [
  {
    path: '',
    component: StartpaginaComponent,
    children: [
      {
        path: '',
        component: TimelineComponent
      },
      {
        path: "searchkweets",
        component: SearchkweetsComponent
      }
    ]
  },
  {
    path: "profile/:id",
    component: ProfielpaginaComponent,
    runGuardsAndResolvers: "pathParamsChange",
    children:[
      {
        path:"",
        component: RecentkweetsComponent,
        runGuardsAndResolvers: "pathParamsChange"
      },
      {
        path:"followers",
        component:FollowersComponent,
        runGuardsAndResolvers: "pathParamsChange"
      }
    ]
  },
  {
    path: 'login',
    component: LoginComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: "reload"})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
