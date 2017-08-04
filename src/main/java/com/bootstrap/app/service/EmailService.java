package com.bootstrap.app.service;

import com.bootstrap.app.model.Greeting;

import java.util.concurrent.Future;

public interface EmailService {

    Boolean send(Greeting greeting);

    void sendAsync(Greeting greeting);

    Future<Boolean> sendAsyncWithResult(Greeting greeting);
}
