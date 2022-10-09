package kz.iitu.issuetracker.entity.enumconverter;

import kz.iitu.issuetracker.entity.Ticket;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class TicketStatusConverter implements AttributeConverter<Ticket.Status, String> {
    @Override
    public String convertToDatabaseColumn(Ticket.Status status) {
        return status.toString();
    }

    @Override
    public Ticket.Status convertToEntityAttribute(String s) {
        for (Ticket.Status status: Ticket.Status.values()) {
            if (Objects.equals(s, status.toString())) {
                return status;
            }
        }
        throw new IllegalArgumentException(s + " is not a valid Ticket.Status enum constant");
    }
}
