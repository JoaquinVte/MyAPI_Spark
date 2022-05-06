package es.ieslavereda.server.model.entity;

public class Factura {

    private int id;
    private int clienteId;
    private float importe;

    public Factura(int id, int clienteId, float importe) {
        this.id = id;
        this.clienteId = clienteId;
        this.importe = importe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }
}
