package telran.net;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.function.BiFunction;

public class CalculatorProtocol implements ApplProtocol {
    private static Map<String, BiFunction<String, String, String>> ops = Map.of(
            "+", (o1, o2) -> Double.parseDouble(o1) + Double.parseDouble(o2) + "",
            "-", (o1, o2) -> Double.parseDouble(o1) - Double.parseDouble(o2) + "",
            "*", (o1, o2) -> Double.parseDouble(o1) * Double.parseDouble(o2) + "",
            "/", (o1, o2) -> Double.parseDouble(o1) / Double.parseDouble(o2) + "",
            "between", (o1, o2) -> ChronoUnit.DAYS.between(LocalDate.parse(o1), LocalDate.parse(o2)) + "",
            "plusDate", (o1, o2) -> LocalDate.parse(o1).plusDays(Integer.parseInt(o2)) + "",
            "minusDate", (o1, o2) -> LocalDate.parse(o1).minusDays(Integer.parseInt(o2)) + ""
    );


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
        BiFunction fn = ops.get(type);
        return new Response(fn == null ? ResponseCode.WRONG_TYPE: ResponseCode.OK, (String)fn.apply(tokens[0], tokens[1]));
    }
}
