package kz.iitu.issuetracker.dto.ticket.response;

import kz.iitu.issuetracker.feature.mapper.TicketMapper;
import kz.iitu.issuetracker.entity.Ticket;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class TicketPaginatedResp {
    private final List<TicketDto> dtos;
    private final int totalPages;

    public static TicketPaginatedResp fromTicketPage(Page<Ticket> ticketPage) {
        List<TicketDto> ticketDtos = ticketPage.getContent().stream()
                .map(TicketMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList());
        return new TicketPaginatedResp(ticketDtos, ticketPage.getTotalPages());
    }
}
