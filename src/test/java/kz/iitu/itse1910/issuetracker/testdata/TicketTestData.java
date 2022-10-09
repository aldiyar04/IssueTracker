package kz.iitu.itse1910.issuetracker.testdata;

import kz.iitu.issuetracker.dto.ticket.response.TicketDto;
import kz.iitu.issuetracker.entity.Ticket;
import kz.iitu.issuetracker.feature.mapper.TicketMapper;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;

public class TicketTestData {
    @Getter
    public static class Entity {
        @Getter(AccessLevel.NONE)
        private final ProjectTestData.Entity projects = new ProjectTestData.Entity();

        private final Ticket ticket1 = Ticket.builder()
                .id(1L)
                .title("Issue 1")
                .project(projects.getProject1())
                .build();
        private final Ticket ticket2 = Ticket.builder()
                .id(2L)
                .title("Issue 2")
                .project(projects.getProject1())
                .build();
        private final Ticket ticket3 = Ticket.builder()
                .id(3L)
                .title("Issue 3")
                .project(projects.getProject2())
                .build();
        private final Ticket ticket4 = Ticket.builder()
                .id(4L)
                .title("Issue 4")
                .project(projects.getProject2())
                .build();
        private final Ticket ticket5 = Ticket.builder()
                .id(5L)
                .title("Issue 5")
                .project(projects.getProject3())
                .build();
        private final Ticket ticket6 = Ticket.builder()
                .id(6L)
                .title("Issue 6")
                .project(projects.getProject3())
                .build();

        public List<Ticket> getAllTickets() {
            return List.of(ticket1, ticket2, ticket3, ticket4, ticket5, ticket6);
        }

        public List<Ticket> getTicketsOfProject1() {
            return List.of(ticket1, ticket2);
        }

        public List<Ticket> getTicketsOfProject2() {
            return List.of(ticket3, ticket4);
        }

        public List<Ticket> getTicketsOfProject3() {
            return List.of(ticket5, ticket6);
        }
    }

    @Getter
    public static class Dto {
        @Getter(AccessLevel.NONE)
        private final Entity entities = new Entity();

        private final TicketDto ticket1 = toDto(entities.getTicket1());
        private final TicketDto ticket2 = toDto(entities.getTicket2());
        private final TicketDto ticket3 = toDto(entities.getTicket3());
        private final TicketDto ticket4 = toDto(entities.getTicket4());
        private final TicketDto ticket5 = toDto(entities.getTicket5());
        private final TicketDto ticket6 = toDto(entities.getTicket6());

        private TicketDto toDto(Ticket ticket) {
            return TicketMapper.INSTANCE.entityToDto(ticket);
        }

        public List<TicketDto> getAllTickets() {
            return List.of(ticket1, ticket2, ticket3, ticket4, ticket5, ticket6);
        }

        public List<TicketDto> getTicketsOfProject1() {
            return List.of(ticket1, ticket2);
        }

        public List<TicketDto> getTicketsOfProject2() {
            return List.of(ticket3, ticket4);
        }

        public List<TicketDto> getTicketsOfProject3() {
            return List.of(ticket5, ticket6);
        }
    }
}
