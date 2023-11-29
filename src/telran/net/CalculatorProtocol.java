package telran.net;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

public class CalculatorProtocol implements ApplProtocol {
    private static Map<String, BiFunction<String, String, Serializable>> ops = Map.of(
            "+", (o1, o2) -> Double.parseDouble(o1) + Double.parseDouble(o2),
            "-", (o1, o2) -> Double.parseDouble(o1) - Double.parseDouble(o2),
            "*", (o1, o2) -> Double.parseDouble(o1) * Double.parseDouble(o2),
            "/", (o1, o2) -> Double.parseDouble(o1) / Double.parseDouble(o2),
            "between", (o1, o2) -> ChronoUnit.DAYS.between(LocalDate.parse(o1), LocalDate.parse(o2)),
            "plusDate", (o1, o2) -> LocalDate.parse(o1).plusDays(Integer.parseInt(o2)),
            "minusDate", (o1, o2) -> LocalDate.parse(o1).minusDays(Integer.parseInt(o2))
    );


    @Override
    public Response getResponse(Request request) {
        String[] operands = ((String) request.requestData()).split("#");
        var fn = ops.get(request.requestType());

        return operands.length != 2 ? new Response(ResponseCode.WRONG_DATA, "Wrong operands count") :
                Objects.isNull(fn) ? new Response(ResponseCode.WRONG_TYPE, "Wrong operation") :
                        new Response(ResponseCode.OK, fn.apply(operands[0], operands[1]));
    }
}
