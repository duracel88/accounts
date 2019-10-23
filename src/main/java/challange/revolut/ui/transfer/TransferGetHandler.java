package challange.revolut.ui.transfer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import challange.revolut.domain.TransferHistory;
import challange.revolut.domain.repository.TransferHistoryRepository;
import challange.revolut.ui.GetHandler;
import challange.revolut.ui.transfer.dto.TransferHistoryDTO;

import static java.util.stream.Collectors.toList;
import static javax.servlet.http.HttpServletResponse.SC_OK;

public class TransferGetHandler implements GetHandler {

    private final TransferHistoryRepository repository;
    private final ObjectMapper objectMapper;

    @Inject
    public TransferGetHandler(TransferHistoryRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<TransferHistory> history = repository.findAll();
        List<TransferHistoryDTO> dtos = history.stream()
                .map(h -> TransferHistoryDTO.builder()
                        .id(h.getTransferId().getId())
                        .to(h.getCommittedTransfer().getAccountTo().getId())
                        .from(h.getCommittedTransfer().getAccountFrom().getId())
                        .amount(h.getCommittedTransfer().getAmount().getValue().doubleValue())
                        .currency(h.getCommittedTransfer().getCurrency())
                        .timestamp(h.getCommittedAt().format(DateTimeFormatter.ISO_DATE_TIME))
                        .build())
                .collect(toList());

        resp.setStatus(SC_OK);
        objectMapper.writeValue(resp.getWriter(), dtos);

    }
}
