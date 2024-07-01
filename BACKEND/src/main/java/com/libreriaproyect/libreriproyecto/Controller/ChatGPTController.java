package com.libreriaproyect.libreriproyecto.Controller;

import com.libreriaproyect.libreriproyecto.entidades.ia.entrada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("openia/v1")
@CrossOrigin(origins = "*")
public class ChatGPTController {


    @Autowired
    private WebClient.Builder webClientBuilder;

    @PostMapping("/completions/")
    public Mono<ResponseEntity<byte[]>> getChatCompletion(@RequestBody entrada ggggdatos) {
        String cleanedHtmlContent = cleanUnicodeCharacters(ggggdatos.getDoc());
        String prompt = createPrompt(cleanedHtmlContent);
        WebClient webClient = webClientBuilder.baseUrl("https://api.openai.com/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + " ")
                .build();

        return webClient.post()
                .uri("/chat/completions")
                .body(Mono.just(new ChatGPTRequest(prompt)), ChatGPTRequest.class)
                .retrieve()
                .bodyToMono(ChatGPTResponse.class)
                .flatMap(response -> {
                    String latexContent = response.getChoices().get(0).getMessage().getContent().replaceAll("[\\u200B\\u200D\\u200C\\uFEFF\\u2060]", "");
                    return processLatexContent(latexContent);
                })
                .onErrorResume(this::handleError);
    }
    @PostMapping("/fuentes/")
    public Mono<ResponseEntity<String>> getChatFuentes(@RequestBody entrada ggggdatos) {
        String prompt = analisisFuentes(ggggdatos.getDoc(), ggggdatos.getPlan());
        WebClient webClient = webClientBuilder.baseUrl("https://api.openai.com/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + " ")
                .build();

        return webClient.post()
                .uri("/chat/completions")
                .body(Mono.just(new ChatGPTRequest(prompt)), ChatGPTRequest.class)
                .retrieve()
                .bodyToMono(ChatGPTResponse.class)
                .map(response -> ResponseEntity.ok(response.getChoices().get(0).getMessage().getContent()))
                .onErrorResume(this::handleErrosr);
    }
    private Mono<ResponseEntity<String>> handleErrosr(Throwable ex) {
        String errorMessage;
        if (ex instanceof WebClientResponseException.TooManyRequests) {
            errorMessage = "Too Many Requests: " + ex.getMessage();
        } else if (ex instanceof WebClientResponseException) {
            WebClientResponseException we = (WebClientResponseException) ex;
            errorMessage = "Error: " + we.getStatusCode() + " - " + we.getResponseBodyAsString();
        } else {
            errorMessage = "Internal Server Error: " + ex.getMessage();
        }
        ex.printStackTrace();
        return Mono.just(ResponseEntity.status(500).body(errorMessage));
    }
    private String analisisFuentes(String miTexto, String Fuente_de_conocimiento) {
        String enun = "Dado el siguiente título de mi proyecto y un resumen breve, junto con un texto de un artículo o revista, por favor, analiza el contenido y proporciona tres ideas o puntos importantes que podrían ser útiles para incorporar en mi documento o hacer referencia.\n";
        String mitxto =  miTexto;
        String nota="Texto del Artículo/Revista: "+ Fuente_de_conocimiento;
        return enun + " " + mitxto +" "+nota;
    }

    @PostMapping("/mermaid/")
    public Mono<ResponseEntity<String>> mermaid(@RequestBody entrada ggggdatos) {
        String prompt = analisisMermaid(ggggdatos.getDoc());
        WebClient webClient = webClientBuilder.baseUrl("https://api.openai.com/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + " ")
                .build();

        return webClient.post()
                .uri("/chat/completions")
                .body(Mono.just(new ChatGPTRequest(prompt)), ChatGPTRequest.class)
                .retrieve()
                .bodyToMono(ChatGPTResponse.class)
                .map(response -> ResponseEntity.ok(response.getChoices().get(0).getMessage().getContent()))
                .onErrorResume(this::handleErrosr);
    }
    private String analisisMermaid(String miTexto) {
        String enun = " Dado el siguiente texto que describe un proyecto, por favor genera únicamente el código Mermaid para un diagrama de flujo que represente las etapas y el flujo de trabajo del proyecto. Asegúrate de que la respuesta contenga exclusivamente el código Mermaid sin introducciones, explicaciones o cualquier otro texto.\n";
        String mitxto =  "Texto del Proyecto:"+miTexto;
        return enun + " " + mitxto +" Solo retorname el codigo mermaid no quiero nigunda palabra o simbolo de tu parte, solo quiero lo que te pido no mas palabras de intro tuyas ni resumenes" ;
    }
    private String createPrompt(String htmlContent) {
        String enun = "Por favor, convierte el siguiente HTML a LaTeX, asegurando que la estructura del documento sea correcta y que todos los caracteres especiales, incluidos los caracteres invisibles como espacios sin separación (U+200B), estén correctamente manejados o eliminados. Además, evita usar entornos no estándar como `justify` a menos que estén explícitamente definidos en el código. Utiliza comandos y estructuras de LaTeX apropiados para elementos como títulos, párrafos, énfasis y listas. Incluye enlaces como hiperreferencias utilizando el paquete `hyperref`, y asegúrate de que todas las llaves abiertas tengan su cierre correspondiente. La respuesta debe contener exclusivamente el código LaTeX generado, sin ningún mensaje adicional.\n";
        String html = " HTML: [" + htmlContent + "]";
        String consejo = " Consejo: Utiliza el paquete `hyperref` para los enlaces y `graphicx` para las imágenes. Recuerda utilizar `\\textbf{}` para texto en negrita, `\\textit{}` para texto en cursiva, y estructuras como `\\section{}`, `\\subsection{}` para organización de secciones, según se presenta en el documento de LaTeX proporcionado.";
        String nota="Nota: Si es necesario justificar el texto, por favor proporciona las instrucciones específicas o el código necesario para definir un entorno `justify` utilizando el paquete `ragged2e`, o asegúrate de que el texto esté justificado de acuerdo con las configuraciones predeterminadas de LaTeX. Utiliza el paquete `fontenc` con la opción `[T1]` y el paquete `inputenc` con la opción `[utf8]` para asegurar la correcta codificación y representación de caracteres.";
        return enun + " " + html + " " + consejo+" "+nota;
    }

    private Mono<ResponseEntity<byte[]>> processLatexContent(String latexContent) {
        try {
            Path tempFile = createTempFile(latexContent);
            compileLatexToPdf(tempFile);
            byte[] pdfBytes = readPdfFile(tempFile);
            cleanupTempFiles(tempFile);

            return Mono.just(ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Mono.just(ResponseEntity.status(500).body(("Internal Server Error: " + e.getMessage()).getBytes()));
        }
    }

    private Path createTempFile(String content) throws IOException {
        Path tempFile = Files.createTempFile("document", ".tex");
        Files.write(tempFile, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
        return tempFile;
    }
    private String cleanUnicodeCharacters(String content) {
        return content.replaceAll("[^\\x00-\\x7F]", ""); // Elimina todos los caracteres no ASCII
    }
    private void compileLatexToPdf(Path tempFile) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("pdflatex", "-output-directory", tempFile.getParent().toString(), tempFile.toString());
        Process process = pb.start();

        // Capturar la salida estándar y de error del proceso
        captureProcessOutput(process);

        if (!process.waitFor(580, TimeUnit.SECONDS)) { // Incrementa el tiempo de espera a 180 segundos
            process.destroy();
            throw new IOException("Proceso pdflatex tomó demasiado tiempo y fue terminado.");
        }
    }

    private void captureProcessOutput(Process process) {
        new Thread(() -> {
            try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.err.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private byte[] readPdfFile(Path tempFile) throws IOException {
        Path pdfFile = tempFile.resolveSibling(tempFile.getFileName().toString().replace(".tex", ".pdf"));
        return Files.readAllBytes(pdfFile);
    }

    private void cleanupTempFiles(Path tempFile) throws IOException {
        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(tempFile.resolveSibling(tempFile.getFileName().toString().replace(".tex", ".pdf")));
    }

    private Mono<ResponseEntity<byte[]>> handleError(Throwable ex) {
        String errorMessage;
        if (ex instanceof WebClientResponseException.TooManyRequests) {
            errorMessage = "Too Many Requests: " + ex.getMessage();
        } else if (ex instanceof WebClientResponseException) {
            WebClientResponseException we = (WebClientResponseException) ex;
            errorMessage = "Error: " + we.getStatusCode() + " - " + we.getResponseBodyAsString();
        } else {
            errorMessage = "Internal Server Error: " + ex.getMessage();
        }
        ex.printStackTrace();
        return Mono.just(ResponseEntity.status(500).body(errorMessage.getBytes()));
    }

    static class ChatGPTRequest {
        private String model;
        private ChatGPTMessage[] messages;

        public ChatGPTRequest(String prompt) {
            this.model = "gpt-4";
            this.messages = new ChatGPTMessage[]{new ChatGPTMessage("user", prompt)};
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public ChatGPTMessage[] getMessages() {
            return messages;
        }

        public void setMessages(ChatGPTMessage[] messages) {
            this.messages = messages;
        }
    }

    static class ChatGPTMessage {
        private String role;
        private String content;

        public ChatGPTMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    static class ChatGPTResponse {
        private List<Choice> choices;

        public List<Choice> getChoices() {
            return choices;
        }

        public void setChoices(List<Choice> choices) {
            this.choices = choices;
        }

        static class Choice {
            private ChatGPTMessage message;

            public ChatGPTMessage getMessage() {
                return message;
            }

            public void setMessage(ChatGPTMessage message) {
                this.message = message;
            }
        }
    }
}
