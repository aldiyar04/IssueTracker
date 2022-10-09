package kz.iitu.issuetracker.controller;

import kz.iitu.issuetracker.controller.compoundrequestparam.TicketFilterReq;
import kz.iitu.issuetracker.controller.compoundrequestparam.annotation.CompoundRequestParam;
import kz.iitu.issuetracker.dto.ticket.request.TicketCreationReq;
import kz.iitu.issuetracker.dto.ticket.request.TicketUpdateReq;
import kz.iitu.issuetracker.dto.ticket.response.TicketDto;
import kz.iitu.issuetracker.dto.ticket.response.TicketPaginatedResp;
import kz.iitu.issuetracker.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseStatus(HttpStatus.OK)
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/projects/{projectId}/tickets")
    public TicketPaginatedResp getTickets(Pageable pageable,
                                          @PathVariable("projectId") long projectId,
                                          @CompoundRequestParam TicketFilterReq filterReq) {
        return ticketService.getTickets(pageable, projectId);
    }

    @GetMapping("/projects/{projectId}/tickets/{id}")
    public TicketDto getTicketById(@PathVariable("projectId") long projectId,
                                   @PathVariable("id") long id) {
        return ticketService.getById(projectId, id);
    }

    @PostMapping("/projects/{projectId}/tickets")
    @ResponseStatus(HttpStatus.CREATED)
    public TicketDto createTicket(@PathVariable("projectId") long projectId,
                                                  @RequestBody TicketCreationReq creationReq) {
        return ticketService.create(projectId, creationReq);
    }

    @PutMapping("/projects/{projectId}/tickets/{id}")
    public TicketDto updateTicket(@PathVariable("projectId") long projectId,
                                                  @PathVariable("id") long id,
                                                  @RequestBody TicketUpdateReq updateReq) {
        return ticketService.update(projectId, id, updateReq);
    }

    @DeleteMapping("/projects/{projectId}/tickets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable("projectId") long projectId,
                                          @PathVariable("id") long id) {
        ticketService.delete(projectId, id);
    }
}
