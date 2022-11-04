package payments;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import payments.dto.CreatePaymentDto;
import utils.dto.ResponseDto;
import utils.dto.UserDto;
import utils.entity.PaymentTable;
import utils.utils.EuroUtils;

import java.time.LocalDateTime;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;
import static utils.utils.CognitoUtils.getUserById;
import static utils.utils.LocalDateTimeUtils.getCurrentTime;

public class CreatePayment implements RequestHandler<CreatePaymentDto, ResponseDto> {

    @Override
    public ResponseDto handleRequest(CreatePaymentDto paymentDto, Context context) {
        UserDto user = getUserById(paymentDto.getUserId());
        if (user == null) {
            return new ResponseDto("Nije pronađen korisnik");
        }
        LocalDateTime time = getCurrentTime();
        if (paymentDto.getTime() != null) {
            time = paymentDto.getTime();
        }

        PaymentTable paymentTable = new PaymentTable();
        paymentTable.setUserId(paymentDto.getUserId());
        // Makni kada dođe EURO
        paymentTable.setAmount(EuroUtils.convertFromHrk(paymentDto.getAmount()));
        paymentTable.setTime(time);
        DYNAMO_DB_MAPPER.save(paymentTable);
        return new ResponseDto("Pobratime " + user.getUsername() + " hvala na uplati od: " + paymentDto.getAmount().toPlainString() + "kn");

    }
}
