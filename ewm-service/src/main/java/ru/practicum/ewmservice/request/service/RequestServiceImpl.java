package ru.practicum.ewmservice.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.request.dto.RequestDto;
import ru.practicum.ewmservice.request.enums.RequestStatus;
import ru.practicum.ewmservice.request.mapper.RequestMapper;
import ru.practicum.ewmservice.request.model.Request;
import ru.practicum.ewmservice.request.repository.RequestRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public List<RequestDto> getRequestsByUserId(Long userId) {
        return RequestMapper.toDtoList(requestRepository.findAllByRequester_Id(userId));
    }

    @Override
    public RequestDto addRequest(Long userId, Long eventId) {
        Request request = RequestMapper.toEntity(userId, eventId);
        return RequestMapper.toDto(requestRepository.saveAndFlush(request));
    }

    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {
        Request request = requestRepository.findByIdAndRequester_Id(requestId, userId).orElseThrow(() -> {
            //log.info(String.format("Пользователя №%d не существует!", userId));
            throw new RuntimeException();
        });
        request.setStatus(RequestStatus.REJECTED);
        return RequestMapper.toDto(requestRepository.saveAndFlush(request));
    }
}
