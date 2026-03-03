package com.ai_project.doc_auditor_service.service;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class IngestionService {
    public static  final Logger log= LoggerFactory.getLogger(IngestionService.class);
    private  final VectorStore vesctorStore;

    public IngestionService(VectorStore vesctorStore){
        this.vesctorStore=vesctorStore;
    }

    public void ingestPdf(Resource pdf){
        log.info("Starting ingestion of file : {}",pdf.getFilename());
        var pdfReader = new PagePdfDocumentReader(pdf);
        var splitter = new TokenTextSplitter();

        List<Document> documentList = pdfReader.get();
        List<Document> chunks = splitter.apply(documentList);

        vesctorStore.accept(chunks);


    }
}
