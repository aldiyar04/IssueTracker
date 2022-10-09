package kz.iitu.issuetracker.entity.enumconverter;

import kz.iitu.issuetracker.entity.Ticket;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class TicketTypeConverter implements AttributeConverter<Ticket.Type, String> {
    @Override
    public String convertToDatabaseColumn(Ticket.Type type) {
        return type.toString();
    }

    @Override
    public Ticket.Type convertToEntityAttribute(String s) {
        for (Ticket.Type type: Ticket.Type.values()) {
            if (Objects.equals(s, type.toString())) {
                return type;
            }
        }
        throw new IllegalArgumentException(s + " is not a valid Ticket.Type enum constant");
    }
}
