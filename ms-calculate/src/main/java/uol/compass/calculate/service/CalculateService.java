package uol.compass.calculate.service;

import uol.compass.calculate.dto.request.CalculateRequest;
import uol.compass.calculate.dto.response.CalculateResponse;

public interface CalculateService {

    CalculateResponse calculate(CalculateRequest request);

}