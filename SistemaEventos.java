/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package medicamentos;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SistemaEventos {
    
    // Lista de ouvintes (listeners) registados para o evento STOCK_REPOSTO
    private static final List<Consumer<String>> ouvintesStockReposto = new ArrayList<>();

    // Regista um novo ouvinte interessado no evento STOCK_REPOSTO
    public static void subscreverStockReposto(Consumer<String> acao) {
        ouvintesStockReposto.add(acao);
    }

    // Publica o evento STOCK_REPOSTO (Dispara a ação para todos os subscritores)
    public static void publicarStockReposto(String nomeMedicamento, String farmacia, int novaQuantidade) {
        String mensagem = String.format("[EVENTO] STOCK_REPOSTO -> A farmácia '%s' repôs %d unidades de '%s'!", 
                farmacia, novaQuantidade, nomeMedicamento);
        
        System.out.println(mensagem); // Mostra na consola
        
        // Notifica cada ouvinte registado
        for (Consumer<String> ouvinte : ouvintesStockReposto) {
            ouvinte.accept(mensagem);
        }
    }
}