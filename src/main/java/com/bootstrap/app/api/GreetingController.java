package com.bootstrap.app.api;

import com.bootstrap.app.model.Greeting;
import com.bootstrap.app.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
public class GreetingController {

    @Autowired
    private GreetingService greetingService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = "/api/greetings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Greeting>> getGreetings() {
        logger.info("> getGreetings");
        Collection<Greeting> greetings = greetingService.findAll();
        logger.info("< getGreetings");
        return new ResponseEntity<Collection<Greeting>>(greetings, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> getGreeting(@PathVariable("id") Long id) {
        logger.info("> getGreeting id:{}", id);
        Greeting greeting = greetingService.findOne(id);

        if (greeting == null) {

            return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
        }
        logger.info("< getGreeting id:{}", id);
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/greetings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting){
        logger.info("> createGreeting");
        Greeting savedGreeting = greetingService.create(greeting);
        logger.info("< createGreeting");
        return new ResponseEntity<Greeting>(savedGreeting, HttpStatus.CREATED);
    }

    @RequestMapping( value = "/api/greetings/{id}" , method = RequestMethod.PUT , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting){
        logger.info("> updateGreeting id:{}", greeting.getId());
        Greeting updateGreeting = greetingService.update(greeting);

        if (updateGreeting == null) {
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("< updateGreeting id:{}", greeting.getId());

        return new ResponseEntity<Greeting>(updateGreeting, HttpStatus.OK);
    }

    @RequestMapping( value = "/api/greetings/{id}", method = RequestMethod.DELETE , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> deleteGreeting(@PathVariable("id") Long id){
        logger.info("> deleteGreeting id:{}", id);
        greetingService.delete(id);
        logger.info("< deleteGreeting id:{}", id);
        return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
    }

}
