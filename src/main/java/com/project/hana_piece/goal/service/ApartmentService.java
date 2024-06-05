package com.project.hana_piece.goal.service;

import com.project.hana_piece.ai.dto.GeminiCallResponse;
import com.project.hana_piece.ai.service.AiService;
import com.project.hana_piece.ai.vo.GeminiPrompt;
import com.project.hana_piece.goal.domain.Apartment;
import com.project.hana_piece.goal.dto.ApartmentGetResponse;
import com.project.hana_piece.goal.dto.ApartmentPricePredictRequest;
import com.project.hana_piece.goal.dto.ApartmentPricePredictResponse;
import com.project.hana_piece.goal.repository.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final AiService aiService;

    public List<ApartmentGetResponse> getAllApartments() {
        List<Apartment> apartments = apartmentRepository.findAll();
        return apartments.stream()
                .map(ApartmentGetResponse::fromEntity)
                .toList();
    }

    public ApartmentPricePredictResponse predictApartmentPrice(ApartmentPricePredictRequest request) {


        String prompt = String.format("%d개월 뒤 가장 최근 거래가가 %d억인 %s %s %d평의 가격을 선형 회귀 모델을 이용해 오차범위 50퍼센트 가격 이내에서 중간 시나리오로 최대한 정확히 예측해서 %d 형식으로 가격만 알려줘",
                request.duration(), request.price(), request.region(), request.apartmentNm(), request.area(), request.price());

        GeminiPrompt geminiPrompt = GeminiPrompt.builder().requests(prompt).build();
        GeminiCallResponse response = aiService.callGenerativeLanguageApi(geminiPrompt);

        long messageLong = Long.parseLong(response.message());
        return new ApartmentPricePredictResponse(messageLong);
    }
}
