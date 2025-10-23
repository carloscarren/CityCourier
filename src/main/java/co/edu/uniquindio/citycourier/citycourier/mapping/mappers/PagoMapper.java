package co.edu.uniquindio.citycourier.citycourier.mapping.mappers;

import co.edu.uniquindio.citycourier.citycourier.mapping.dto.PagoDto;
import co.edu.uniquindio.citycourier.citycourier.model.Pago;

import java.util.ArrayList;
import java.util.List;

public class PagoMapper {

    public static PagoDto pagoToPagoDto(Pago pago) {
        if (pago == null) return null;

        return new PagoDto(
                pago.getIdPago(),
                pago.getIdEnvio(),
                pago.getMonto(),
                pago.getMetodoPago(),
                pago.getResultado().toString(),
                pago.getFecha().toString()
        );
    }

    public static Pago pagoDtoToPago(PagoDto pagoDto) {
        if (pagoDto == null) return null;

        Pago pago = new Pago();
        pago.setIdPago(pagoDto.idPago());
        pago.setIdEnvio(pagoDto.idEnvio());
        pago.setMonto(pagoDto.monto());
        pago.setMetodoPago(pagoDto.metodoPago());
        // El resultado y fecha pueden inicializarse por defecto dentro de Pago
        return pago;
    }

    public static List<PagoDto> getPagosDto(List<Pago> listaPagos) {
        if (listaPagos == null) return null;

        List<PagoDto> listaDto = new ArrayList<>(listaPagos.size());
        for (Pago pago : listaPagos) {
            listaDto.add(pagoToPagoDto(pago));
        }
        return listaDto;
    }
}
