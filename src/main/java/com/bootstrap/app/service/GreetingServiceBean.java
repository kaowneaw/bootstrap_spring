package com.bootstrap.app.service;

import com.bootstrap.app.model.Greeting;
import com.bootstrap.app.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GreetingServiceBean implements GreetingService {

    @Autowired
    private GreetingRepository greetingRepository;

    @Override
    public Collection<Greeting> findAll() {
        Collection<Greeting> greetings = greetingRepository.findAll();

        return greetings;
    }

    @Override
    @Cacheable( value = "greetings", key = "#id")
    public Greeting findOne(Long id) {
        return greetingRepository.findOne(id);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut( value = "greetings", key = "#result.id")
    public Greeting create(Greeting greet) {
        if(greet.getId() != null){
            //Cannot create Greeting
            return null;
        }

       Greeting greeting =  greetingRepository.save(greet);

        return greeting;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut( value = "greetings", key = "#greeting.id")
    public Greeting update(Greeting greeting) {
        Greeting greetingToUpdate = findOne(greeting.getId());
        if (greetingToUpdate == null) {
            // Cannot update Greeting that hasn't been persisted
            return null;
        }

        greetingToUpdate.setText(greeting.getText());
        Greeting updatedGreeting = greetingRepository.save(greetingToUpdate);

        return updatedGreeting;
    }

    @Override
    @CacheEvict( value = "greetings", key = "#id")
    public void delete(Long id) {
        greetingRepository.delete(id);
    }

    @Override
    @CacheEvict(value = "greetings", allEntries = true)
    public void evictCache() {

    }
}
