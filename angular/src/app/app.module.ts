import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ShowRssComponent } from './show-rss/show-rss.component';
import {HttpClientModule} from "@angular/common/http";
import { ExempleComponent } from './exemple/exemple.component';

import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import { LineClampComponent } from './line-clamp/line-clamp/line-clamp.component';

registerLocaleData(localeFr, 'fr');

@NgModule({
  declarations: [
    AppComponent,
    ShowRssComponent,
    ExempleComponent,
    LineClampComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {



}
