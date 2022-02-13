package org.srss.webapp.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.srss.webapp.dto.FluxDTO;
import org.srss.webapp.dto.ListFluxDTO;
import org.srss.webapp.dto.ListRssDTO;
import org.srss.webapp.dto.RssDTO;
import org.srss.webapp.model.Feed;
import org.srss.webapp.model.RssSite;
import org.srss.webapp.util.ParserFeed;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.util.stream.Collectors.toSet;

@Service
public class ReadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadService.class);

    @Value("${config-file}")
    private String configFile;

    private List<RssSite> listeRss;

    @PostConstruct
    public void init() throws IOException {
        reloadListSites();
    }

    public ListFluxDTO read(int id) {

        var siteOpt=getSite(id);

        if(siteOpt.isPresent()){
            var site=siteOpt.get();
            return getFlux(site);
        } else {
            return new ListFluxDTO();
        }


    }

    public ListRssDTO listeRss() {

        ListRssDTO listRssDTO = new ListRssDTO();
        List<RssDTO> liste = new ArrayList<>();
        listRssDTO.setList(liste);

        if(!CollectionUtils.isEmpty(listeRss)){
            for(var rss:listeRss){
                RssDTO rssDTO = new RssDTO();
                rssDTO.setId(rss.getId());
                rssDTO.setTitle(rss.getTitle());
                rssDTO.setUrl(rss.getUrl());
                liste.add(rssDTO);
            }
        }

        return listRssDTO;
    }

    private void reloadListSites() throws IOException {
        Path p = Paths.get(configFile);
        Properties properties = new Properties();
        properties.load(Files.newBufferedReader(p));

        List<RssSite> liste = new ArrayList<>();

        Set<Object> set = properties.keySet();
        if (set != null) {
            var set2 = set.stream()
                    .filter(x -> x instanceof String)
                    .map(x -> (String) x)
                    .filter(x -> x.startsWith("rss"))
                    .collect(toSet());

            var i = 1;
            do {
                final String name = "rss" + i;
                final String nameId = name + ".id";
                final String nameTitle = name + ".title";
                final String nameUrl = name + ".url";
                if (set2.contains(nameId)) {
                    String s = properties.getProperty(nameId);
                    int id = Integer.parseInt(s);
                    var title = properties.getProperty(nameTitle);
                    var url = properties.getProperty(nameUrl);
                    if (id > 0 && StringUtils.hasText(title) && StringUtils.hasText(url)) {
                        RssSite rssSite = new RssSite();
                        rssSite.setId(id);
                        rssSite.setTitle(title);
                        rssSite.setUrl(url);
                        liste.add(rssSite);
                    }
                } else {
                    break;
                }
                i++;
            } while (true);
        }

        this.listeRss=liste;
    }

    private Optional<RssSite> getSite(int id){
        if(id>0&&!CollectionUtils.isEmpty(listeRss)){
            return listeRss.stream()
                    .filter(x -> x.getId()==id)
                    .findFirst();
        } else {
            return Optional.empty();
        }
    }

    private ListFluxDTO getFlux(RssSite site) {
        String url = site.getUrl();
        LOGGER.info("call {}", url);
        if(StringUtils.hasText(url)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders requestHeaders = new HttpHeaders();
            //requestHeaders.set("Accept", "text/html;charset=utf-8");
            //requestHeaders.set("Accept", "application/rss+xml;charset=utf-8, application/atom+xml;charset=utf-8");
            //requestHeaders.setContentType(new MediaType("application", "rss+xml", StandardCharsets.UTF_8));
            //requestHeaders.setContentType(new MediaType("application", "atom+xml", StandardCharsets.UTF_8));
            HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,requestEntity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                var res = response.getBody();
                res=corrigeEncodageReponse(res);
                ParserFeed parserFeed = new ParserFeed();
                Feed feed = parserFeed.read(new StringReader(res));
                var liste = new ListFluxDTO();
                if (feed != null && !CollectionUtils.isEmpty(feed.getMessages())) {
                    liste.setListe(new ArrayList<>());
                    for (var msg : feed.getMessages()) {
                        var fluxDTO = new FluxDTO();
                        fluxDTO.setTitle(msg.getTitle());
                        fluxDTO.setAuthor(msg.getAuthor());
                        fluxDTO.setDescription(msg.getDescription());
                        fluxDTO.setGuid(msg.getGuid());
                        fluxDTO.setLink(msg.getLink());
                        fluxDTO.setDatePublication(msg.getDatePublication());
                        fluxDTO.setHtml(msg.isHtml());
                        liste.getListe().add(fluxDTO);
                    }
                }
                return liste;
            } else {
                LOGGER.error("Erreur pour lire le flux");
            }
        } else {
            LOGGER.error("Erreur : url vide");
        }
        return null;
    }

    private String corrigeEncodageReponse(String res) {
        if(res!=null){
            if(res.contains("Ã©")||res.contains("Ã")||res.contains("Ãª")){
                return new String(res.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
            } else {
                return res;
            }
        } else {
            return res;
        }
    }
}
