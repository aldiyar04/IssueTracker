package kz.iitu.issuetracker.controller.compoundrequestparam;

import kz.iitu.issuetracker.controller.compoundrequestparam.annotation.RequestParamName;
import kz.iitu.issuetracker.dto.ticket.response.TicketDto;
import kz.iitu.issuetracker.controller.api.TicketApi;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class TicketFilterReq {
    @RequestParamName(TicketApi.Filter.TYPE)
    private final TicketDto.Type type;

    @RequestParamName(TicketApi.Filter.STATUS)
    private final TicketDto.Status status;

    @RequestParamName(TicketApi.Filter.PRIORITY)
    private final TicketDto.Priority priority;

    @RequestParamName(TicketApi.Filter.IS_ASSIGNED)
    private final Boolean isAssigned;

    @RequestParamName(TicketApi.Filter.IS_OVERDUE)
    private final Boolean isOverdue;
}
