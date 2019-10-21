package challange.revolut.ui.transfer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import challange.revolut.domain.TransferHistory;
import challange.revolut.domain.exception.InsufficientBalanceException;
import challange.revolut.domain.repository.TransferHistoryRepository;
import challange.revolut.service.transfer.TransferMoneyRequest;
import challange.revolut.service.transfer.TransferMoneyResponse;
import challange.revolut.service.transfer.TransferMoneyService;
import challange.revolut.ui.transfer.dto.TransferAcceptedDTO;
import challange.revolut.ui.transfer.dto.TransferDTO;
import challange.revolut.ui.transfer.dto.TransferErrorDTO;
import challange.revolut.ui.transfer.dto.TransferHistoryDTO;

import static challange.revolut.ui.transfer.dto.TransferErrorDTO.insufficientBalance;
import static challange.revolut.ui.transfer.dto.TransferErrorDTO.validationException;
import static java.util.stream.Collectors.toList;
import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_ACCEPTABLE;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@Singleton
public class TransferServlet extends HttpServlet {

    public static final String PATH = "/transfer";

    private final TransferMoneyService sendMoneyService;
    private final TransferRequestFactory requestFactory;
    private final TransferHistoryRepository repository;
    private final ObjectMapper objectMapper;

    @Inject
    public TransferServlet(TransferMoneyService sendMoneyService, TransferRequestFactory requestFactory, TransferHistoryRepository repository, ObjectMapper objectMapper) {
        this.sendMoneyService = sendMoneyService;
        this.requestFactory = requestFactory;
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TransferDTO dto = objectMapper.readValue(request.getInputStream(), TransferDTO.class);
        TransferMoneyRequest transferRequest = requestFactory.apply(dto);
        try {
            TransferMoneyResponse result = sendMoneyService.handle(transferRequest);
            response.setStatus(SC_ACCEPTED);
            objectMapper.writeValue(response.getWriter(), new TransferAcceptedDTO(result.getTransactionId()));
        } catch (ConstraintViolationException e){

            response.setStatus(SC_BAD_REQUEST);
            objectMapper.writeValue(response.getWriter(), validationException(e));
        } catch (ValidationException e) {

            response.setStatus(SC_BAD_REQUEST);
            objectMapper.writeValue(response.getWriter(), validationException(e));
        } catch (InsufficientBalanceException e){

            response.setStatus(SC_NOT_ACCEPTABLE);
            objectMapper.writeValue(response.getWriter(), insufficientBalance(e));
        } catch (Exception e){
            e.printStackTrace();
            response.setStatus(SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(response.getWriter(), new TransferErrorDTO(e.getMessage()));
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
