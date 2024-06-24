package com.libreriaproyect.libreriproyecto.Controller;



import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import com.libreriaproyect.libreriproyecto.entidades.ia.entrada;
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
                    " Dado un texto, proporciona cinco referencias \n" +
                            "de artículos científicos o fuentes confiables \n" +
                            "únicamente en un formato JSON limpio. \n" +
                            "Cada elemento del JSON debe incluir los \n" +
                            "siguientes detalles: 'titulo' \n" +
                            "(el titulo del articulo), \n" +
                            "'resumen' (un breve resumen en español \n" +
                            "del contenido del artículo), 'enlace' \n" +
                            "(URL directo al artículo), 'DOI' (el\n" +
                            " Identificador de Objeto Digital, si\n" +
                            " está disponible), 'palabras_clave' \n" +
                            " (una lista de términos clave asociados\n" +
                            " con el artículo), y 'año_publicación' \n" +
                            " (el anio de publicación del artículo,\n" +
                            " revista o página, asegurándote de que \n" +
                            " sea el año en que la fuente fue publicada\n" +
                            " y no fechas históricas mencionadas en el texto)\n" +
                            " . Los artículos deben estar en inglés y ser las publicaciones \n" +
                            " más recientes y relevantes. Prioriza artículos que tengan un \n" +
                            " impacto significativo en su campo. Asegúrate de no incluir\n" +
                            " ningún texto adicional o símbolos fuera de la estructura JSON.\n" +
                            " siempre respeta el nombre de las etiquetas json y no pongas\n" +
                            " simbolos que dañen el json \n" +
                            "\n" +
                "Text: ["+consulta+"]\n";
        String output = textInput(projectId, location, modelName, textPrompt);
        return  output;
    }
    @PostMapping("/ia/planitlla")
    public String  analisisplantilla(@RequestBody entrada datos) throws IOException {
        String projectId = "digital-proton-427302-h0";
        String location = "us-central1";
        String modelName = "gemini-1.0-pro-002";
        String textPrompt =
                "Recibe dos textos: el primero, 'Texto del Documento', es el documento que se desea corregir; el segundo, 'Texto de la Plantilla', contiene los términos o guías que debe seguir el documento. Basado en estos textos, realiza un análisis para determinar si el documento está siguiendo los lineamientos de la plantilla. Verifica la presencia de las secciones requeridas según la plantilla y proporciona un análisis detallando qué secciones están presentes y qué secciones pueden estar faltando o necesitan mejora. La respuesta debe ser un texto en español que especifique claramente el cumplimiento del documento con la plantilla y las áreas de mejora.\n" +
                        "\n" +
                        "Ejemplo de estructura del prompt:\n" +
                        "```plaintext\n" +
                        "{\n" +
                        "  \"Texto del Documento\": \"["+datos.getDoc()+"]\",\n" +
                        "  \"Texto de la Plantilla\": \"["+datos.getPlan()+"]\"\n" +
                        "}\n" +
                        "\n" +
                        "Ejemplo de respuesta esperada:\n" +
                        "\"El documento revisado muestra un cumplimiento parcial de los lineamientos de la plantilla proporcionada. Las secciones [nombre de la sección] y [nombre de la sección] están bien desarrolladas y alineadas con los requisitos de la plantilla. Sin embargo, falta desarrollar o mejorar las secciones [nombre de la sección faltante] y [nombre de otra sección faltante], las cuales son esenciales según la plantilla. Se recomienda revisar estos aspectos para asegurar una mayor coherencia con los lineamientos establecidos.\"\n" +
                        "";
        String output = textInput(projectId, location, modelName, textPrompt);
        return  output;
    }
    @PostMapping("/alt")
    public String alternativas(@RequestBody String consulta) throws IOException
    {
        String projectId = "digital-proton-427302-h0";
        String location = "us-central1";
        String modelName = "gemini-1.0-pro-002";
        String textPrompt = "Recibe un texto destinado a un proyecto científico y genera tres alternativas de redacción que sigan estrictamente el formato indicado. Cada alternativa debe comenzar con \"AlternativaGPQ\" seguido de un número de secuencia (1, 2, 3) y dos puntos, y después el texto de la alternativa. Las alternativas deben reformular el texto original, manteniendo el mismo significado y contenido, pero variando la estructura y el estilo para adaptarse a diferentes formatos académicos. Asegúrate de que la respuesta contenga exclusivamente las alternativas solicitadas, sin ninguna introducción, ningún título adicional, ningún comentario o análisis, ni uso de Markdown o cualquier otro tipo de formato adicional.\n" +
                "\n" +
                "Texto Original: \"["+consulta+"]\"" +
                "" +
                "Ejemplo de la respuesta esperada:" +
                "no contenido" +

                "Alternativa GPQ 1: []" +
                "Alternativa GPQ 2: []" +
                "Alternativa GPQ 3: []";
        String output = textInput(projectId, location, modelName, textPrompt);
        return  output;
    }
    @PostMapping("/analisis")
    public String  analisis(@RequestBody String consulta) throws IOException {
        String projectId = "digital-proton-427302-h0";
        String location = "us-central1";
        String modelName = "gemini-1.0-pro-002";
        String textPrompt =
                "Analiza el texto proporcionado y ofrece retroalimentación en español, estructurada puramente en formato JSON. La retroalimentación debe incluir dos secciones claramente definidas en el JSON: 'Fortalezas' y 'Áreas para Mejorar', cada una con hasta cinco puntos. \n" +
                        "\n" +
                        "La sección 'Fortalezas' debe incluir objetos con dos atributos: 'Aspecto' (el área donde el texto sobresale) y 'Descripcion' (una breve explicación de por qué es una fortaleza). \n" +
                        "\n" +
                        "La sección 'Áreas para Mejorar' debe incluir objetos con dos atributos: 'Aspecto' (el área que necesita mejora) y 'Descripcion' (sugerencias específicas para mejorar).\n" +
                        "\n" +
                        "Además, incluye un campo 'Puntuación General' que proporciona una puntuación de 1 a 5, siendo 5 excelente, evaluando la calidad general del texto.\n" +
                        "\n" +
                        "Asegúrate de que la respuesta contenga solo formato JSON sin ningún texto adicional o marcadores.\n" +
                        "\n" +
                        "Ejemplo de estructura JSON esperada(no pongas tildes a los atributos y etiquetas del json):\n" +

                        "{\n" +
                        "  \"fortalezas\": [\n" +
                        "    {\n" +
                        "      \"Aspecto\": \"Claridad\",\n" +
                        "      \"Descripcion\": \"El texto está bien redactado y es fácil de entender, incluso para alguien no familiarizado con el tema.\"\n" +
                        "    },\n" +
                        "    // otros puntos...\n" +
                        "  ],\n" +
                        "  \"areasParaMejorar\": [\n" +
                        "    {\n" +
                        "      \"Aspecto\": \"Estilo de Escritura\",\n" +
                        "      \"Descripcion\": \"El texto puede ser más atractivo utilizando un lenguaje y imágenes más vívidos.\"\n" +
                        "    },\n" +
                        "    // otros puntos...\n" +
                        "  ],\n" +
                        "  \"puntuacionGeneral\": 4\n" +
                        "}\n"+
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
