package controller;

import request.HttpRequest;
import response.HttpResponse;

public class DummyController implements Controller{
    @Override
    public void service(HttpRequest request, HttpResponse response) {
    }
}
