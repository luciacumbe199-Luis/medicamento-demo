/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package medicamentos;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaMedicamentos extends JFrame {

    private JTextField campoPesquisa;
    private JTextArea areaResultados;
    private JTextField campoReserva;
    private final ArrayList<Medicamento> listaMedicamentos;

    public TelaMedicamentos() {
        listaMedicamentos = new ArrayList<>();

      
        listaMedicamentos.add(new Medicamento(1, "Paracetamol 500mg", "Disponível", 15, "Farmácia Moderna (Alto Maé)"));
        listaMedicamentos.add(new Medicamento(2, "Paracetamol 500mg", "Últimas Unidades!", 1, "Farmácia de Moçambique (Baixa)"));
        listaMedicamentos.add(new Medicamento(3, "Ibuprofeno 400mg", "Esgotado", 0, "Farmácia do Povo (Polana)"));

        criarInterface();
    }

    private void criarInterface() {
        setTitle("MediMaputo - Sistema de Reservas Distribuído");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(new Color(244, 247, 246));

       
        JPanel cabecalho = new JPanel(new GridLayout(2, 1, 5, 5));
        cabecalho.setOpaque(false);
        JLabel titulo = new JLabel("MediMaputo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(23, 107, 80));
        JLabel descricao = new JLabel("Pesquisa e Reserva Segura de Medicamentos", SwingConstants.CENTER);
        cabecalho.add(titulo);
        cabecalho.add(descricao);

        
        JPanel painelPesquisa = new JPanel(new BorderLayout(10, 10));
        painelPesquisa.setOpaque(false);
        campoPesquisa = new JTextField();
        campoPesquisa.setFont(new Font("Arial", Font.PLAIN, 16));
        JButton botaoPesquisar = new JButton("Pesquisar");
        botaoPesquisar.setBackground(new Color(23, 107, 80));
        botaoPesquisar.setForeground(Color.WHITE);
        painelPesquisa.add(new JLabel("Nome do Medicamento: "), BorderLayout.WEST);
        painelPesquisa.add(campoPesquisa, BorderLayout.CENTER);
        painelPesquisa.add(botaoPesquisar, BorderLayout.EAST);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(areaResultados);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados da Pesquisa"));

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.setOpaque(false);
        centro.add(painelPesquisa, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);

        
        JPanel painelReserva = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelReserva.setOpaque(false);
        painelReserva.setBorder(BorderFactory.createTitledBorder("Efetuar Reserva de Medicamento (TTL: 24h)"));
        campoReserva = new JTextField(5);
        campoReserva.setFont(new Font("Arial", Font.PLAIN, 16));
        JButton botaoReservar = new JButton("Reservar Item (Simular Transação)");
        botaoReservar.setBackground(new Color(200, 50, 50));
        botaoReservar.setForeground(Color.WHITE);
        painelReserva.add(new JLabel("Digite o ID do Medicamento:"));
        painelReserva.add(campoReserva);
        painelReserva.add(botaoReservar);

        painelPrincipal.add(cabecalho, BorderLayout.NORTH);
        painelPrincipal.add(centro, BorderLayout.CENTER);
        painelPrincipal.add(painelReserva, BorderLayout.SOUTH);

        add(painelPrincipal);

       
        botaoPesquisar.addActionListener(e -> pesquisar());
        campoPesquisa.addActionListener(e -> pesquisar());
        botaoReservar.addActionListener(e -> reservar());

        areaResultados.setText("Bem-vindo ao MediMaputo!\nPesquise por 'Paracetamol' ou 'Ibuprofeno' para testar.");
    }

    private void pesquisar() {
        String nome = campoPesquisa.getText().trim().toLowerCase();
        if (nome.isEmpty()) {
            areaResultados.setText("Por favor, digite o nome do medicamento.");
            return;
        }

        // PARADIGMA FUNCIONAL: Uso de Stream API (filter, sorted, collect)
        java.util.List<Medicamento> encontrados = listaMedicamentos.stream()
                .filter(med -> med.getNome().toLowerCase().contains(nome))
                .sorted(java.util.Comparator.comparing(Medicamento::getNome))
                .collect(java.util.stream.Collectors.toList());

        if (encontrados.isEmpty()) {
            areaResultados.setText("Medicamento não encontrado nas farmácias parceiras.");
            return;
        }

        // Transformação funcional dos resultados em texto usando map e joining
        String resultado = encontrados.stream()
                .map(med -> String.format(
                        "ID: [%d] | %s%n" +
                        "Farmácia: %s%n" +
                        "Estado: %s | Quantidade disponível: %d%n" +
                        "--------------------------------------------------%n",
                        med.getId(),
                        med.getNome(),
                        med.getFarmacia(),
                        med.getEstado(),
                        med.getQuantidade()
                ))
                .collect(java.util.stream.Collectors.joining());

        areaResultados.setText(resultado);
    }
    // ... (todo o código do método reservar que já tens)


    private void reservar() {
        // ... (todo o código do método reservar que já tens)
    }

    public static void main(String[] args) {
        // Inicia o Servidor HTTP para o Paradigma Distribuído
        ServidorMedicamentos.iniciarServidor();

        // Abre a interface gráfica
        SwingUtilities.invokeLater(() -> {
            new TelaMedicamentos().setVisible(true);
        });
    }
} // <- Esta é a última chave que fecha a classe TelaMedicamentos
