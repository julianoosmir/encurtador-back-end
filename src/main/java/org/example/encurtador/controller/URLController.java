package org.example.encurtador.controller;

import org.example.encurtador.dto.ShortenUrlRequest;
import org.example.encurtador.entity.URLEntity;
import org.example.encurtador.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin("*")
@RestController
public class URLController {
    @Autowired
    private URLService service;

    @PostMapping
    public ResponseEntity<URI> insert(@RequestBody ShortenUrlRequest request) {
        var url  = service.insert(request);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{shortURL}").buildAndExpand(url.getShortURL()).toUri();
        return ResponseEntity.ok(uri);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable  Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping
    public ResponseEntity<List<URLEntity>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("{shorturl}")
    public ResponseEntity<Void> redirect(@PathVariable String shorturl) {

        var url = service.findByShortURL(shorturl);

        if (url == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url.getOriginalURL()));

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
