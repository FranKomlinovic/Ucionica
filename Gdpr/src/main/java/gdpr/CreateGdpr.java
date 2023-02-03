package gdpr;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import gdpr.dto.GdprTable;
import lombok.SneakyThrows;
import utils.dto.ResponseDto;

import java.io.InputStream;
import java.util.Map;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;
import static utils.entity.SdkObjects.GSON;

public class CreateGdpr implements RequestHandler<GdprTable, ResponseDto> {

    @SneakyThrows
    @Override
    public ResponseDto handleRequest(GdprTable gdprDto, Context context) {
        context.getLogger().log(gdprDto.toString());
        GdprTable gdprTable;

        if (!StringUtils.isNullOrEmpty(gdprDto.getId())) {
            gdprTable = DYNAMO_DB_MAPPER.load(GdprTable.class, gdprDto.getId());
            gdprTable.setCategories(gdprDto.getCategories());
            gdprTable.setGdprManagerName(gdprDto.getGdprManagerName());
            gdprTable.setResult(gdprDto.getResult());
            gdprTable.setCompanyId(gdprDto.getCompanyId());
        } else {
            gdprTable = gdprDto;
        }

        DYNAMO_DB_MAPPER.save(gdprTable);
        return new ResponseDto("Gdpr dodan");
    }
}
