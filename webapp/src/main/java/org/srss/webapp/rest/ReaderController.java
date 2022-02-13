package org.srss.webapp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.srss.webapp.dto.ListFluxDTO;
import org.srss.webapp.dto.ListRssDTO;
import org.srss.webapp.service.ReadService;

@RestController
public class ReaderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReaderController.class);

    @Autowired
    private ReadService readService;

    @GetMapping("/api/read/{id}")
    public ListFluxDTO read(@PathVariable("id") int id){
        LOGGER.info("read");
        return readService.read(id);
    }
    @GetMapping("/api/list-rss")
    public ListRssDTO listRss() {
        LOGGER.info("listRss");
        return readService.listeRss();
    }

}
