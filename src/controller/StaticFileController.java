package controller;

import http.HttpBody;
import http.HttpRequest;
import http.HttpResponse;
import http.StatusCode;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class StaticFileController implements Controller{
    final private String root = System.getProperty("user.dir") + "/src/staticfile";
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        String url = request.getUrl();
        String contentType = getContentType(url);
        String filePath = root + request.getUrl();
        System.out.println("request path : " + filePath);

        if(url.contains("..")) {
            //보안 위험으로 400 (요청 url을 ../../../한다면 root폴더에 접근이 가능해짐)
            response.setStatus(StatusCode.BAD_REQUEST);
            return;
        }

        //1. 파일이 존재하는지 확인
        File file = new File(filePath);
        if(file.exists() && file.isFile()){
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                response.setByteBody(bytes);
                response.setStatus(StatusCode.OK);
                response.setContentType(contentType, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();

                response.setStatus(StatusCode.BAD_REQUEST);
            }
        } else {
            //3. 없으면 400으로 응답
            System.out.println("bad url = " + filePath);
            response.setStatus(StatusCode.BAD_REQUEST);
        }
    }

    private String getContentType(String url) {
        if (url.endsWith(".html")) return "text/html";
        if (url.endsWith(".png")) return "image/png";
        if (url.endsWith(".jpg")) return "image/jpeg";
        return "text/plain";
    }


}
