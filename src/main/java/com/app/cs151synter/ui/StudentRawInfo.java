package com.app.cs151synter.ui;

import com.app.cs151synter.customExceptions.BadPDFFormatException;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.util.LinkedList;
import java.util.List;

public class StudentRawInfo {
    private List<PDDocument> pdfs = new LinkedList<>();

    public void addPDFSyllabus (PDDocument pdf) throws IllegalArgumentException {
        if (!isValidPDF(pdf))
            throw new BadPDFFormatException();
    }

    private boolean isValidPDF (PDDocument pdf) {
        return pdf != null;
    }
}
