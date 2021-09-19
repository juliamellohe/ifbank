package ifbank.Registro;

import java.time.LocalDateTime;

public class Registro {
    private int id;
    private int user_id;
    private String description;
    private float valor;
    private String tipo;
    private LocalDateTime created_at;

    public Registro(int user_id, String description, float valor, String tipo) {
        this.user_id = user_id;
        this.description = description;
        this.valor = valor;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getDescription() {
        return description;
    }

    public float getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return this.valor + ", " + this.description + ", " + this.tipo + ", em: " + this.created_at;
    }
}
