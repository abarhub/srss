import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ListFlux} from "../model/list-flux";
import {Observable} from "rxjs";
import {ListRss} from "../model/list-rss";

@Injectable({
  providedIn: 'root'
})
export class RssServiceService {

  private rssUrl = 'api/read';

  private listRssUrl = 'api/list-rss';

  constructor(private http: HttpClient) {
  }

    getRss(id: number): Observable<ListFlux> {
    return this.http.get<ListFlux>(this.rssUrl+"/"+id);
  }

  getListRss(): Observable<ListRss> {
    return this.http.get<ListRss>(this.listRssUrl);
  }
}
