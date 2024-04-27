package dataManipulation;

public class ParagraphResponse implements IntentResponse {
    private String paragraphResponse;

    public ParagraphResponse(String paragraphResponse) {
        this.paragraphResponse = paragraphResponse;
    }

    @Override
    public String generateResponse() {
        return paragraphResponse;
    }
}
