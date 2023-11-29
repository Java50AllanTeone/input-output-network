package telran;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CalculatorProtocol implements ApplProtocol {
    @Override
    public Response getResponse(Request request) {
        Response response;
        String[] operands = ((String)request.requestData()).split("#");

        if (operands.length != 2) {
            response = new Response(ResponseCode.WRONG_DATA, "Wrong operands count");
        } else {
            response = handleRequest(request.requestType(), operands);
        }
        return response;
    }

    private Response handleRequest(String type, String[] tokens) {
        return switch (type) {
            case "+" -> new Response(ResponseCode.OK, Double.parseDouble(tokens[0]) + Double.parseDouble(tokens[1]));
            case "-" -> new Response(ResponseCode.OK, Double.parseDouble(tokens[0]) - Double.parseDouble(tokens[1]));
            case "*" -> new Response(ResponseCode.OK, Double.parseDouble(tokens[0]) * Double.parseDouble(tokens[1]));
            case "/" -> new Response(ResponseCode.OK, Double.parseDouble(tokens[0]) / Double.parseDouble(tokens[1]));
            case "between" -> new Response(ResponseCode.OK, ChronoUnit.DAYS.between(LocalDate.parse(tokens[1]), LocalDate.parse(tokens[0])));
            case "plusDate" -> new Response(ResponseCode.OK, LocalDate.parse(tokens[0]).plusDays(Integer.parseInt(tokens[1])));
            case "minusDate" -> new Response(ResponseCode.OK, LocalDate.parse(tokens[0]).minusDays(Integer.parseInt(tokens[1])));
            default -> new Response(ResponseCode.WRONG_TYPE, "Wrong type");
        };
    }
}
