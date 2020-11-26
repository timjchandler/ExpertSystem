package home.model;

public class Response {

    private final String value;
    private final String response;
    private int connectionID = -1;

    public Response(String value, String response) {
        this.value = value;
        this.response = response;
    }

    public void setConnectionID(int id) {
        this.connectionID = id;
    }

    public int getConnectionID() {
        return connectionID;
    }

    public String getValue() {
        return value;
    }

    public String getResponse() {
        return response;
    }
}

