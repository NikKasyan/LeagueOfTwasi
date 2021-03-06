package at.saith.twasi.lot.lol.data.exception;

public class InvalidAPIKeyException extends RuntimeException {


    private static final long serialVersionUID = -1324769216445927543L;
    private String apikey;

    public InvalidAPIKeyException(String apikey) {
        this.apikey = apikey;
    }
    
    public String getMessage() {
        return "The APIKEY(" + apikey + ") is invalid!";
    }
}
