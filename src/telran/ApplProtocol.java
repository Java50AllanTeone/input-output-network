package telran;

import telran.Request;
import telran.Response;

public interface ApplProtocol {
    Response getResponse(Request request);
}
