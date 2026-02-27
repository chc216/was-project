package http;

public enum StatusCode {
    OK(200) {
        @Override
        public String responseMessage() {
            return "200 OK\r\n";
        }
    }, BAD_REQUEST(400) {
        @Override
        public String responseMessage() {
            return "400 BAD REQUEST\r\n";
        }
    };
    private int code;
    StatusCode(int code){
        this.code = code;
    }
    public abstract String responseMessage();
}
