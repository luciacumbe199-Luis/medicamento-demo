package medicamentos;

public class Medicamento {
    private final int id;
    private final String nome;
    private String estado;
    private int quantidade;
    private final String farmacia;

    public Medicamento(int id, String nome, String estado, int quantidade, String farmacia) {
        this.id = id;
        this.nome = nome;
        this.estado = estado;
        this.quantidade = quantidade;
        this.farmacia = farmacia;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEstado() { return estado; }
    public int getQuantidade() { return quantidade; }
    public String getFarmacia() { return farmacia; }

    public boolean reservar() {
        if (this.quantidade > 0) {
            this.quantidade--; // Diminui o stock em 1
            if (this.quantidade == 0) {
                this.estado = "Esgotado";
            } else if (this.quantidade < 5) {
                this.estado = "Em Baixo Stock";
            }
            return true; 
        }
        return false; 
    }
}
