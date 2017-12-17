package com.mt.app.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1")
public class StoreController {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private StoreService service;

  @RequestMapping(path = "/store", method = RequestMethod.GET)
  public ResponseEntity<List<Store>> list() {

    log.trace("Entering list()");
    List<Store> stores = service.list();
    if (stores.isEmpty()) {
      return new ResponseEntity<>(NO_CONTENT);
    }
    return new ResponseEntity<>(stores, OK);
  }

  @RequestMapping(path = "/store/{name}", method = RequestMethod.GET)
  public ResponseEntity<Store> read(@PathVariable String name) {

    log.trace("Entering read() with {}", name);
    return service.read(name)
        .map(store -> new ResponseEntity<>(store, OK))
        .orElse(new ResponseEntity<>(NOT_FOUND));
  }

  @RequestMapping(path = "/store", method = RequestMethod.POST)
  public ResponseEntity<Store> create(@RequestBody @Valid Store store) {

    log.trace("Entering create() with {}", store);
    return service.create(store)
        .map(newStoreData -> new ResponseEntity<>(newStoreData, CREATED))
        .orElse(new ResponseEntity<>(CONFLICT));
  }

  @RequestMapping(path = "/store/{name}", method = RequestMethod.PUT)
  public ResponseEntity<Store> put(@PathVariable String name, @RequestBody Store store) {

    log.trace("Entering put() with {}, {}", name, store);
    return service.replace(store.withName(name))
        .map(newStoreData -> new ResponseEntity<>(newStoreData, OK))
        .orElse(new ResponseEntity<>(NOT_FOUND));
  }

  @RequestMapping(path = "/store/{name}", method = RequestMethod.PATCH)
  public ResponseEntity<Store> patch(@PathVariable String name, @RequestBody Store store) {

    log.trace("Entering patch() with {}, {}", name, store);
    return service.update(store.withName(name))
        .map(newStoreData -> new ResponseEntity<>(newStoreData, OK))
        .orElse(new ResponseEntity<>(NOT_FOUND));
  }

  @RequestMapping(path = "/store/{name}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable String name) {

    log.trace("Entering delete() with {}", name);
    return service.delete(name) ?
        new ResponseEntity<>(NO_CONTENT) :
        new ResponseEntity<>(NOT_FOUND);
  }
}
