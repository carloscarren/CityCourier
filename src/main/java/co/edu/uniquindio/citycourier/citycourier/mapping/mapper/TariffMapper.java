package co.edu.uniquindio.citycourier.citycourier.mapping.mapper;

import co.edu.uniquindio.citycourier.citycourier.domain.Tariff;
import co.edu.uniquindio.citycourier.citycourier.mapping.dto.TariffDTO;

public final class TariffMapper {
    private TariffMapper() {}

    public static TariffDTO toDTO(Tariff t) {
        if (t == null) return null;
        TariffDTO dto = new TariffDTO();
        dto.base = t.getBase();
        dto.recargoPeso = t.getRecargoPeso();
        dto.recargoVolumen = t.getRecargoVolumen();
        dto.recargoPrioridad = t.getRecargoPrioridad();
        return dto;
    }
}
