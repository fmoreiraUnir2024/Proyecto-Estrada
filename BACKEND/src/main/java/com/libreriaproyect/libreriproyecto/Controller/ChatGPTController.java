package com.libreriaproyect.libreriproyecto.Controller;



import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;


@RestController
@RequestMapping("gpt/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatGPTController {


    @PostMapping("/chat")
    public String  chat(@RequestBody String consulta) throws IOException {
        String projectId = "digital-proton-427302-h0";
        String location = "us-central1";
        String modelName = "gemini-1.0-pro-002";
        String textPrompt =
                    "Given a text, provide five references from scientific articles or " +
                        "reliable sources in a clean JSON format only. The JSON should " +
                        "include details such as title, a summary in Spanish, the link," +
                        " the DOI (if available), keywords, and the year of publication." +
                        " The articles should be in English and up-to-date. Prioritize articles" +
                        " that are relevant and have significant impact in their field. Do not" +
                        " include any additional text or symbols outside the JSON structure.\n" +
                        "\n" +
                "Text: ["+consulta+"]\n";
        String output = textInput(projectId, location, modelName, textPrompt);
        return  output;
    }
    public static String textInput(
            String projectId, String location, String modelName, String textPrompt) throws IOException {
        try (VertexAI vertexAI = new VertexAI(projectId, location)) {
            String output;
            GenerativeModel model = new GenerativeModel(modelName, vertexAI);

            GenerateContentResponse response = model.generateContent(textPrompt);
            output = ResponseHandler.getText(response);
            return output;
        }
    }
}
