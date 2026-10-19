/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package medicamentos;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Medicamentos extends JFrame {

    private JTextField campoPesquisa;
    private JTextArea areaResultados;
    private JTextField campoReserva;
    private final ArrayList<Medicamento> listaMedicamentos;

    public Medicamentos() {
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

        StringBuilder resultado = new StringBuilder();
        boolean encontrado = false;

        for (Medicamento med : listaMedicamentos) {
            if (med.getNome().toLowerCase().contains(nome)) {
                resultado.append(String.format("ID: [%d] | %s\n", med.getId(), med.getNome()));
                resultado.append(String.format("Farmácia: %s\n", med.getFarmacia()));
                resultado.append(String.format("Estado: %s | Quantidade disponível: %d\n", med.getEstado(), med.getQuantidade()));
                resultado.append("--------------------------------------------------\n");
                encontrado = true;
            }
        }

        if (encontrado) {
            areaResultados.setText(resultado.toString());
        } else {
            areaResultados.setText("Medicamento não encontrado nas farmácias parceiras.");
        }
    }

    private void reservar() {
        try {
            int id = Integer.parseInt(campoReserva.getText().trim());
            Medicamento medEncontrado = null;
            
            for (Medicamento med : listaMedicamentos) {
                if (med.getId() == id) {
                    medEncontrado = med;
                    break;
                }
            }

            if (medEncontrado != null) {
                if (medEncontrado.reservar()) {
                   
                    JOptionPane.showMessageDialog(this, 
                        """
                        SUCESSO: Reserva efetuada com transa\u00e7\u00e3o at\u00f3mica!
                        O medicamento '""" + medEncontrado.getNome() + "' foi bloqueado na " + medEncontrado.getFarmacia() + ".\n\n" +
                        "O evento de expiração iniciou: O código expira em 24 horas (TTL).", 
                        "Reserva Confirmada", JOptionPane.INFORMATION_MESSAGE);
                    pesquisar(); // Atualiza a lista na hora para mostrar o novo stock
                } else {
                    
                    JOptionPane.showMessageDialog(this, """
                                                        FALHA DE CONCORR\u00caNCIA: 
                                                        Outro utilizador acabou de reservar a \u00faltima unidade!
                                                        O sistema ativou o Lock e impediu a reserva dupla.""", 
                        "Erro de Concorrência", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "ID da farmácia/medicamento não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira o ID numérico que aparece nos resultados.", "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Medicamentos().setVisible(true);
        });
    }
}
