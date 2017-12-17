package com.mt.app.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.amazonaws.util.StringUtils.isNullOrEmpty;

@Service
public class StoreService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private StoreRepository repository;

  public Optional<Store> read(String name) {

    log.trace("Entering read() with {}", name);
    return repository.read(name);
  }

  public Optional<Store> create(Store store) {

    log.trace("Entering create() with {}", store);
    if (repository.read(store.getName()).isPresent()) {
      log.warn("Store {} not found", store.getName());
      return Optional.empty();
    }
    repository.save(store);
    return Optional.of(store);
  }

  public Optional<Store> replace(Store newStoreData) {

    log.trace("Entering replace() with {}", newStoreData);
    Optional<Store> existingStore = repository.read(newStoreData.getName());
    if (!existingStore.isPresent()) {
      log.warn("Store {} not found", newStoreData.getName());
      return Optional.empty();
    }
    Store store = existingStore.get();
    store.setAddress(newStoreData.getAddress());
    store.setPhoneNumber(newStoreData.getPhoneNumber());
    repository.save(store);
    return Optional.of(store);
  }

  public Optional<Store> update(Store newStoreData) {

    log.trace("Entering update() with {}", newStoreData);
    Optional<Store> existingStore = repository.read(newStoreData.getName());
    if (!existingStore.isPresent()) {
      log.warn("Store {} not found", newStoreData.getName());
      return Optional.empty();
    }
    Store store = existingStore.get();
    if (!isNullOrEmpty(newStoreData.getAddress())) {
      store.setAddress(newStoreData.getAddress());
    }
    if (!isNullOrEmpty(newStoreData.getPhoneNumber())) {
      store.setPhoneNumber(newStoreData.getPhoneNumber());
    }
    repository.save(store);
    return Optional.of(store);
  }

  public boolean delete(String name) {

    log.trace("Entering delete() with {}", name);
    if (!repository.read(name).isPresent()) {
      log.warn("Store {} not found", name);
      return false;
    }
    repository.delete(name);
    return true;
  }

  public List<Store> list() {

    log.trace("Entering list()");
    return repository.readAll();
  }
}
