package controller;

import http.HttpRequest;
import http.HttpResponse;

public class DummyController implements Controller{
    @Override
    public void service(HttpRequest request, HttpResponse response) {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}
