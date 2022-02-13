package org.srss.webapp.util;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import org.springframework.util.CollectionUtils;
import org.srss.webapp.model.Feed;
import org.srss.webapp.model.FeedMessage;

import java.io.Reader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class ParserFeed {

    public Feed read(Reader reader) {

        SyndFeedInput input = new SyndFeedInput();
        try {
            SyndFeed feed = input.build(reader);

            String title = feed.getTitle();
            String description = feed.getDescription();
            String link = feed.getLink();
            String language = feed.getLanguage();
            String copyright = feed.getCopyright();
            Date date = feed.getPublishedDate();
            String dateStr = (date != null) ? date.toString() : "";

            Feed feed2 = new Feed(title, link, description, language, copyright, dateStr);

            if (feed.getEntries() != null) {
                for (var entry : feed.getEntries()) {
                    SyndEntry entry2 = (SyndEntry) entry;
                    FeedMessage feedMessage = new FeedMessage();
                    feedMessage.setTitle(entry2.getTitle());
                    feedMessage.setAuthor(entry2.getAuthor());
                    if(entry2.getDescription()!=null) {
                        feedMessage.setDescription(entry2.getDescription().getValue());
                    } else if(!CollectionUtils.isEmpty(entry2.getContents())){
                        var content=entry2.getContents().get(0);
                        if(content instanceof SyndContent){
                            SyndContent syndContent= (SyndContent) content;
                            String value=syndContent.getValue();
                            feedMessage.setDescription(value);
                            if(Objects.equals(syndContent.getType(), "html")){
                                feedMessage.setHtml(true);
                            }
                        }
                    }
                    feedMessage.setGuid(entry2.getUri());
                    feedMessage.setLink(entry2.getLink());
                    Date date2 = entry2.getPublishedDate();
                    LocalDateTime datePublication = null;
                    if (date2 != null) {
                        datePublication=convertToLocalDateTimeViaInstant(date2);
                    }
                    feedMessage.setDatePublication(datePublication);
                    feed2.getMessages().add(feedMessage);
                }
            }

            return feed2;
        } catch (FeedException e) {
            throw new RuntimeException(e);
        }
    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
