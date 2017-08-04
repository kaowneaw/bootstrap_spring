package com.bootstrap.app.service;

import com.bootstrap.app.model.Greeting;

import java.util.Collection;

public interface GreetingService {

    Collection<Greeting> findAll();

    Greeting findOne(Long id);

    Greeting create(Greeting greet);

    Greeting update(Greeting greet);

    void delete(Long id);

    void evictCache();
}
