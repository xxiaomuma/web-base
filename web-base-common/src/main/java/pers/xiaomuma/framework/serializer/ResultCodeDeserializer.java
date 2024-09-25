package pers.xiaomuma.framework.serializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import pers.xiaomuma.framework.response.ResponseCode;

import java.io.IOException;

public class ResultCodeDeserializer extends JsonDeserializer<ResponseCode> {

	private static final String RESULT_CODE_FIELD_CODE = "code";

	@Override
	public ResponseCode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		JsonNode treeNode = jsonParser.getCodec().readTree(jsonParser);
		JsonNode codeNode = treeNode.get(RESULT_CODE_FIELD_CODE);
		if (codeNode == null) {
			throw new JsonParseException(jsonParser, "field [code] is not exist");
		}
		if (!codeNode.isInt()) {
			throw new JsonParseException(jsonParser, "field [code] type is not int");
		}
		return ResponseCode.valueOf(codeNode.asInt());
	}
}
