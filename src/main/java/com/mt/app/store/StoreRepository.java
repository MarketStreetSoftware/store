package com.mt.app.store;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StoreRepository {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private DynamoDBMapper dbMapper;

  public List<Store> readAll() {

    log.trace("Entering readAll()");
    PaginatedList<Store> results = dbMapper.scan(Store.class, new DynamoDBScanExpression());
    results.loadAllResults();
    return results;
  }

  public Optional<Store> read(String name) {

    log.trace("Entering read() with {}", name);
    return Optional.ofNullable(dbMapper.load(Store.class, name));
  }

  public void save(Store store) {

    log.trace("Entering save() with {}", store);
    dbMapper.save(store);
  }

  public void delete(String name) {

    dbMapper.delete(new Store().withName(name), new DynamoDBMapperConfig(SaveBehavior.CLOBBER));
  }
}
