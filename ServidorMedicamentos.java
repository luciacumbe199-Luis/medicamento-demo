/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package medicamentos;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ServidorMedicamentos {

    public static void iniciarServidor() {
        try {
            // Cria um servidor HTTP na porta 8080 (Paradigma Distribuído)
            HttpServer servidor = HttpServer.create(new InetSocketAddress(8080), 0);
            
            // Endpoint para receber atualizações de stock das farmácias via rede
            servidor.createContext("/api/stock", (HttpExchange conexao) -> {
                String resposta = "[SERVIDOR CENTRAL] Dados de stock recebidos com sucesso via rede!";
                
                // Configura a resposta HTTP
                conexao.sendResponseHeaders(200, resposta.getBytes().length);
                try (OutputStream os = conexao.getResponseBody()) {
                    os.write(resposta.getBytes());
                }
                
                System.out.println("[REDE] Requisição HTTP recebida de uma farmácia remota.");
            });

            servidor.setExecutor(null); // Cria um executor padrão
            servidor.start();
            System.out.println("[SERVIDOR] Plataforma Central MediMaputo a correr na porta 8080...");

        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor distribuído: " + e.getMessage());
        }
    }
}