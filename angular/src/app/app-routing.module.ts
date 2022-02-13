import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ShowRssComponent} from "./show-rss/show-rss.component";
import {ExempleComponent} from "./exemple/exemple.component";


const routes: Routes = [
  { path: 'show-rss', component: ShowRssComponent },
  { path: 'exemple', component: ExempleComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
