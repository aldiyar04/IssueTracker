package kz.iitu.issuetracker.controller.stringtoenumconverter;

import kz.iitu.issuetracker.dto.ticket.response.TicketDto;
import kz.iitu.issuetracker.controller.api.TicketApi;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTicketDtoPriorityConverter implements Converter<String, TicketDto.Priority> {
    @Override
    public TicketDto.Priority convert(String source) {
        switch (source) {
            case TicketApi.Priority.CRITICAL:
                return TicketDto.Priority.CRITICAL;
            case TicketApi.Priority.HIGH:
                return TicketDto.Priority.HIGH;
            case TicketApi.Priority.MEDIUM:
                return TicketDto.Priority.MEDIUM;
            case TicketApi.Priority.LOW:
                return TicketDto.Priority.LOW;
            default:
                throw new IllegalArgumentException("Invalid ticket priority: " + source);
        }
    }
}
