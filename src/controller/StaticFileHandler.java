package controller;

import http.HttpRequest;
import http.HttpResponse;
import http.StatusCode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

public class StaticFileHandler implements Controller{
    final private String root = "/staticfile";
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        String requestUrl = request.getUrl();
        String contentType = getContentType(requestUrl);
        System.out.println("requestUrl: " + requestUrl);

        if(requestUrl.contains("..")) {
            //보안 위험으로 400 (요청 url을 ../../../한다면 root폴더에 접근이 가능해짐)
            response.setStatus(StatusCode.BAD_REQUEST);
            return;
        }
        Optional<byte[]> bytes = readFile(root + requestUrl);
        if(bytes.isPresent()){
            response.setByteBody(bytes.get());
            response.setStatus(StatusCode.OK);
            response.setContentType(contentType, "UTF-8");
        } else {
            //3. 없으면 400으로 응답
            System.out.println("bad url = " + requestUrl);
            response.setStatus(StatusCode.BAD_REQUEST);
        }
    }


    private String getContentType(String url) {
        if (url.endsWith(".html")) return "text/html";
        if (url.endsWith(".png")) return "image/png";
        if (url.endsWith(".jpg")) return "image/jpeg";
        return "text/plain";
    }

    private Optional<byte[]> readFile(String url) {
        try (InputStream in = this.getClass().getResourceAsStream(url)) {
            if (in == null) {
                return Optional.empty();
            }
            return Optional.of(in.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
            //에러 처리 후에도 return을 해야함
        }
    }


}
