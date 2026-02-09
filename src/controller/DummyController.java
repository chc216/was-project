package controller;

import http.HttpRequest;
import http.HttpResponse;

public class DummyController implements Controller{
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        System.out.println("dummy controller");
        response.setStatus(200);
        response.setContentType("text/plain", "UTF-8");
        String body = "ok";
        response.setByteBody(body.getBytes());
        response.send();
    }
}
