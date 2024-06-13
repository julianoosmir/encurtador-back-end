package org.example.encurtador.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.encurtador.dto.ShortenUrlRequest;
import org.example.encurtador.entity.URLEntity;
import org.example.encurtador.repository.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Optional;

@Service
public class URLService {

    @Autowired
    private URLRepository repository;

    public URLEntity findByShortURL(String shortURL) {
        Optional<URLEntity> obj = repository.findByShortURL(shortURL);
        return obj.orElse(null);
    }


    public URLEntity insert(ShortenUrlRequest shortenUrlRequest) {
        String shorturl;
        do {
            shorturl = RandomStringUtils.randomAlphanumeric(5, 10);
        } while (repository.existsByShortURL(shorturl));

        return repository.save(new URLEntity(null,shortenUrlRequest.url(),shorturl));
    }

    public List<URLEntity> findAll() {
        return repository.findAll();
    }

    public Void delete(Integer id) {
        repository.deleteById(id);
        return null;
    }
}
