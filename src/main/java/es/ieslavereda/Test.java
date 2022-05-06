package es.ieslavereda;

import es.ieslavereda.server.model.service.ImpFacturaService;

public class Test {
    public static void main(String[] args) {
        ImpFacturaService impFacturaService = new ImpFacturaService();

        System.out.println(impFacturaService.createFactura(1));
    }
}
