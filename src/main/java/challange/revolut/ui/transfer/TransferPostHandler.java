package challange.revolut.ui.transfer;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import challange.revolut.domain.exception.InsufficientBalanceException;
import challange.revolut.service.transfer.TransferMoneyRequest;
import challange.revolut.service.transfer.TransferMoneyResponse;
import challange.revolut.service.transfer.TransferMoneyService;
import challange.revolut.ui.PostHandler;
import challange.revolut.ui.transfer.dto.TransferAcceptedDTO;
import challange.revolut.ui.transfer.dto.TransferDTO;
import challange.revolut.ui.transfer.dto.TransferErrorDTO;

import static challange.revolut.ui.transfer.dto.TransferErrorDTO.insufficientBalance;
import static challange.revolut.ui.transfer.dto.TransferErrorDTO.validationException;
import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_ACCEPTABLE;

public class TransferPostHandler implements PostHandler {

    private final TransferMoneyService sendMoneyService;
    private final TransferRequestFactory requestFactory;
    private final ObjectMapper objectMapper;

    @Inject
    public TransferPostHandler(TransferMoneyService sendMoneyService, TransferRequestFactory requestFactory, ObjectMapper objectMapper) {
        this.sendMoneyService = sendMoneyService;
        this.requestFactory = requestFactory;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
}
