package challange.revolut.ui.transfer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import challange.revolut.domain.TransferHistory;
import challange.revolut.domain.repository.TransferHistoryRepository;
import challange.revolut.domain.valueobject.TransferId;
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
        if(req.getPathInfo() == null){
            getAll(resp);
        } else {
            String id = req.getPathInfo().split("/")[1];
            UUID uuid = UUID.fromString(id);
            getById(uuid, resp);
        }
    }

    private void getAll(HttpServletResponse resp) throws IOException{
        List<TransferHistory> history = repository.findAll();
        List<TransferHistoryDTO> dtos = history.stream()
                .map(this::map)
                .collect(toList());

        resp.setStatus(SC_OK);
        objectMapper.writeValue(resp.getWriter(), dtos);
    }

    private void getById(UUID id, HttpServletResponse resp) throws IOException {
        TransferHistory h = repository.findById(new TransferId(id));
        TransferHistoryDTO dto = map(h);
        resp.setStatus(SC_OK);
        objectMapper.writeValue(resp.getWriter(), dto);
    }

    private TransferHistoryDTO map(TransferHistory h){
        return TransferHistoryDTO.builder()
                .id(h.getTransferId().getId())
                .to(h.getCommittedTransfer().getAccountTo().getId())
                .from(h.getCommittedTransfer().getAccountFrom().getId())
                .amount(h.getCommittedTransfer().getAmount().getValue().doubleValue())
                .currency(h.getCommittedTransfer().getCurrency())
                .timestamp(h.getCommittedAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }
}
