package kz.iitu.issuetracker.entity.enumconverter;

import kz.iitu.issuetracker.entity.Ticket;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class TicketPriorityConverter implements AttributeConverter<Ticket.Priority, String> {
    @Override
    public String convertToDatabaseColumn(Ticket.Priority priority) {
        return priority.toString();
    }

    @Override
    public Ticket.Priority convertToEntityAttribute(String s) {
        for (Ticket.Priority priority: Ticket.Priority.values()) {
            if (Objects.equals(s, priority.toString())) {
                return priority;
            }
        }
        throw new IllegalArgumentException(s + " is not a valid Ticket.Priority enum constant");
    }
}
