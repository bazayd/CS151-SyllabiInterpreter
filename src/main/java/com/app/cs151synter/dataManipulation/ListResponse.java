package com.app.cs151synter.dataManipulation;

import com.app.cs151synter.dataContainers.SyllabusEntity;

import java.util.Arrays;
import java.util.List;

public class ListResponse implements IntentResponse {

    private List<SyllabusEntity> listData;


    public ListResponse(List<SyllabusEntity> listData) {
        this.listData = listData;
    }

    @Override
    public String generateResponse() {
        StringBuilder responseBuilder = new StringBuilder();

        responseBuilder.append("List to help you out:\n");
        for (SyllabusEntity data : listData) {
            responseBuilder.append("- ").append(data.getTitle()).append(": ").append(Arrays.toString(data.getInfo())).append("\n");
        }
        return responseBuilder.toString();

    }
}
