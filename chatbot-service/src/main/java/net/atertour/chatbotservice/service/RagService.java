package net.atertour.chatbotservice.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore; // Check if SimpleVectorStore implements this directly or how it's typed
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate; // Correct import for M4 might vary
import org.springframework.ai.embedding.EmbeddingModel; // Updated from EmbeddingClient

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RagService {

    @Value("${app.rag.document-path}")
    private Resource pdfResource;

    private final ChatClient chatClient;
    private final SimpleVectorStore vectorStore;

    // In 0.8.1, EmbeddingClient is used. SimpleVectorStore needs it.
    public RagService(ChatClient chatClient, EmbeddingModel embeddingModel) {
        this.chatClient = chatClient;
        this.vectorStore = new SimpleVectorStore(embeddingModel);
    }

    @PostConstruct
    public void init() {
        if (pdfResource.exists()) {
            PagePdfDocumentReader reader = new PagePdfDocumentReader(pdfResource);
            List<Document> documents = reader.get();
            // Split
            TokenTextSplitter splitter = new TokenTextSplitter();
            List<Document> chunks = splitter.apply(documents);
            // Store
            vectorStore.add(chunks);
            System.out.println("RAG: PDF loaded and embedded.");
        } else {
            System.out.println("RAG: PDF not found at " + pdfResource);
        }
    }

    public String ask(String query) {
        // Retrieve
        List<Document> similarDocuments = vectorStore.similaritySearch(query);
        String context = similarDocuments.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n"));

        // Generate
        String promptText = """
                You are an helpful AI assistant. Answer the question based ONLY on the following context.

                Context:
                {context}

                Question:
                {question}
                """;

        // Using ChatClient fluent API (typical in recent Spring AI)
        return chatClient.prompt()
                .user(u -> u.text(promptText).param("context", context).param("question", query))
                .call()
                .content();
    }
}
