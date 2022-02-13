package org.srss.webapp.model;

import java.time.LocalDateTime;
import java.util.StringJoiner;

public class FeedMessage {

    private String title;
    private String description;
    private String link;
    private String author;
    private String guid;
    private LocalDateTime datePublication;
    private boolean html;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public LocalDateTime getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDateTime datePublication) {
        this.datePublication = datePublication;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FeedMessage.class.getSimpleName() + "[", "]")
                .add("title='" + title + "'")
                .add("description='" + description + "'")
                .add("link='" + link + "'")
                .add("author='" + author + "'")
                .add("guid='" + guid + "'")
                .add("datePublication=" + datePublication)
                .add("html=" + html)
                .toString();
    }
}
