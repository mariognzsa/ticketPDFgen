package com.example.Parcial2.ExamenParcial2E1;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class TemplatePDF {

    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfwriter;
    private Paragraph paragraph;
    private Font fontTitle = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
    private Font fontSubTitle = new Font(Font.FontFamily.COURIER, 18, Font.BOLDITALIC);
    private Font fontText = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL);
    private Font fontHighlighted = new Font(Font.FontFamily.COURIER, 14, Font.BOLD, BaseColor.DARK_GRAY);

    public TemplatePDF(Context context) {
        this.context = context;
    }
    public void openDocument(){
        createFile();
        try {
            document = new Document(PageSize.A4);
            pdfwriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

        }catch (Exception e){
            Log.e("openDocument",e.toString());
        }
    }
    private void createFile(){
        Random random = new Random();
        String filename = "PDF_Ticket_"+random.nextInt(1000)+".pdf";

//        Environment.getExternalStorageDirectory().toString()
        File folder = new File("/root/Documentos/Universidad/ticketPDFgen/documents/");
        if(!folder.exists()) folder.mkdirs();
        pdfFile = new File(folder, filename);
    }
    public void closeDocument(){
        document.close();
    }
    public void addMetaData(String title, String subject, String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }
    public void addHeader(String title, String subTitle, String date){
        try {
            paragraph = new Paragraph();
            addChildParagraph(new Paragraph(title, fontTitle));
            addChildParagraph(new Paragraph(subTitle, fontSubTitle));
            addChildParagraph(new Paragraph("Fecha de creacion: " + date, fontHighlighted));
            paragraph.setSpacingAfter(35);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addHeader",e.toString());
        }
    }
    public void addParagraph(String text){
        try {
            paragraph = new Paragraph(text, fontText);
            paragraph.setSpacingAfter(10);
            paragraph.setSpacingBefore(10);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addParagraph",e.toString());
        }
    }
    private void addChildParagraph(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(childParagraph);
    }
    public void createTable(String[]header, ArrayList<String[]>lines){
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fontText);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int index_col = 0;
            while (index_col < header.length) {
                pdfPCell = new PdfPCell(new Phrase(header[index_col++], fontSubTitle));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfPTable.addCell(pdfPCell);
            }
            for (int index_row = 0; index_row < lines.size(); index_row++) {
                String[] row = lines.get(index_row);
                for (index_col = 0; index_col < header.length; index_col++) {
                    pdfPCell = new PdfPCell(new Phrase(row[index_row], fontText));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(50);
                    pdfPTable.addCell(pdfPCell);
                }
            }
            paragraph.add(pdfPTable);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("createTable",e.toString());
        }
    }
    public void viewPDF(){
        Intent intent = new Intent(context, ViewPDFActivity.class);
        intent.putExtra("path", pdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
