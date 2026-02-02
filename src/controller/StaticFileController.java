package controller;

import http.HttpBody;
import http.HttpRequest;
import http.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class StaticFileController implements Controller{
    final private String root = "/Users/cheol/dev/java/mini-project/was-server/src/staticfile";
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        String url = request.getUrl();
        String contentType = getContentType(url);
        if(url.contains("..")) {
            //보안 위험으로 400 (요청 url을 ../../../한다면 root폴더에 접근이 가능해짐)
            response.setStatus(400);
            return;
        }

        String filePath = root + request.getUrl();
        //1. 파일이 존재하는지 확인
        File file = new File(filePath);
        if(file.exists() && file.isFile()){
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                response.setByteBody(bytes);
                response.setStatus(200);
                response.setContentType(contentType, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
                response.setStatus(500);
            }
        } else {
            //3. 없으면 400으로 응답
            response.setStatus(400);

        }
    }

    private String getContentType(String url) {
        if (url.endsWith(".html")) return "text/html";
        if (url.endsWith(".png")) return "image/png";
        return "text/plain";
    }


}
