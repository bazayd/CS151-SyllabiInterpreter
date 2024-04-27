package dataManipulation;

import dataContainers.SyllabusEntities;

import javax.swing.plaf.synth.SynthLabelUI;
import java.util.Arrays;
import java.util.List;

public class ListResponse implements IntentResponse {
    private List<SyllabusEntities> listData;

    public ListResponse(List<SyllabusEntities> listData) {
        this.listData = listData;
    }

    @Override
    public String generateResponse() {
        StringBuilder responseBuilder = new StringBuilder();

        responseBuilder.append("List to help you out:\n");
        for (SyllabusEntities data : listData) {
            responseBuilder.append("- ").append(data.getTitle()).append(": ").append(Arrays.toString(data.getInfo())).append("\n");
        }
        return responseBuilder.toString();
    }
}
