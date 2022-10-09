package kz.iitu.issuetracker.dto.ticket.request;

import kz.iitu.issuetracker.dto.ticket.response.TicketDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@Getter
@ToString
public class TicketUpdateReq {
    private final String title;
    private final String description;
    private final Long assigneeId;
    private final TicketDto.Type type;
    private final TicketDto.Status status;
    private final TicketDto.Priority priority;
    private final LocalDate targetResolutionDate;
}
