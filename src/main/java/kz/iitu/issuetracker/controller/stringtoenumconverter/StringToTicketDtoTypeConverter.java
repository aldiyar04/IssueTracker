package kz.iitu.issuetracker.controller.stringtoenumconverter;

import kz.iitu.issuetracker.dto.ticket.response.TicketDto;
import kz.iitu.issuetracker.controller.api.TicketApi;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTicketDtoTypeConverter implements Converter<String, TicketDto.Type> {
    @Override
    public TicketDto.Type convert(String source) {
        switch (source) {
            case TicketApi.Type.BUG:
                return TicketDto.Type.BUG;
            case TicketApi.Type.VULNERABILITY:
                return TicketDto.Type.VULNERABILITY;
            case TicketApi.Type.FEATURE_REQUEST:
                return TicketDto.Type.FEATURE_REQUEST;
            case TicketApi.Type.REFACTORING:
                return TicketDto.Type.REFACTORING;
            case TicketApi.Type.OTHER:
                return TicketDto.Type.OTHER;
            default:
                throw new IllegalArgumentException("Invalid ticket type: " + source);
        }
    }
}
