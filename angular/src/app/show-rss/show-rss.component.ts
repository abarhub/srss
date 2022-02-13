import {Component, OnInit} from '@angular/core';
import {RssServiceService} from "../api/rss-service.service";
import {ListFlux} from "../model/list-flux";
import {ListRss} from "../model/list-rss";

@Component({
  selector: 'app-show-rss',
  templateUrl: './show-rss.component.html',
  styleUrls: ['./show-rss.component.scss']
})
export class ShowRssComponent implements OnInit {

  listFlux: ListFlux = new ListFlux();

  listRss: ListRss = new ListRss();

  constructor(private rssServiceService: RssServiceService) {
  }

  ngOnInit(): void {


    this.rssServiceService.getListRss()
      .subscribe((list: ListRss) => {
        console.log("listRss", list);
        this.listRss = list;
        if (this.listRss && this.listRss.list && this.listRss.list.length > 0) {
          const id = this.listRss.list[0].id;
          if (id > 0) {
            this.selectFluxRss(id);
          }
        }
      });
  }

  onOptionsSelected(event: Event) {

    console.log("onChange", event);

    const element = event.currentTarget as HTMLSelectElement;
    const value = element.value;

    console.log("value", value);
    if (value) {
      let valueNumber = parseInt(value);
      this.selectFluxRss(valueNumber);
    }
  }

  private selectFluxRss(id: number) {
    console.log("selectFluxRss", id);
    this.rssServiceService.getRss(id)
      .subscribe((list: ListFlux) => {
        console.log("liste", list);
        this.listFlux = list;
      });
  }
}
