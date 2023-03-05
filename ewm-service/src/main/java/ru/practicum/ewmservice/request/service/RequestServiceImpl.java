package ru.practicum.ewmservice.request.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.event.enums.EventState;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.repository.EventRepository;
import ru.practicum.ewmservice.exception.RequestException;
import ru.practicum.ewmservice.exception.EventNotExists;
import ru.practicum.ewmservice.request.dto.RequestDto;
import ru.practicum.ewmservice.request.enums.RequestStatus;
import ru.practicum.ewmservice.request.mapper.RequestMapper;
import ru.practicum.ewmservice.request.model.Request;
import ru.practicum.ewmservice.request.repository.RequestRepository;

import java.util.List;

@Service
@Slf4j
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    public RequestServiceImpl(RequestRepository requestRepository, EventRepository eventRepository) {
        this.requestRepository = requestRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<RequestDto> getRequestsByUserId(Long userId) {
        return RequestMapper.toDtoList(requestRepository.findAllByRequester_Id(userId));
    }

    @Override
    public RequestDto addRequest(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            log.info(String.format("События %d не существует!", eventId));
            throw new EventNotExists(String.format("События %d не существует!", eventId));
        });

        if (event.getInitiator().getId().equals(userId)) {
            log.info("Пользователь не может подать заявку на своё событие!");
            throw new RequestException("Пользователь не может подать заявку на своё событие!");
        }

        if (!event.getState().equals(EventState.PUBLISHED)) {
            log.info("Нельзя подавать заявку на неопубликованное событие!");
            throw new RequestException("Нельзя подавать заявку на неопубликованное событие!");
        }

        if (requestRepository.existsByEvent_IdAndRequester_Id(eventId, userId)) {
            log.info("Создавать повторный запрос запрещено!");
            throw new RequestException("Создавать повторный запрос запрещено!");
        }
        ;

        if (requestRepository.findAllByEvent_IdAndStatus(eventId, RequestStatus.CONFIRMED)
                .size() >= event.getParticipantLimit()) {
            log.info("На событие больше нет свободных мест!");
            throw new RequestException("На событие больше нет свободных мест!");
        }

        Request request = RequestMapper.toEntity(userId, eventId);

        if(!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(RequestStatus.CONFIRMED);
        } else {
            request.setStatus(RequestStatus.PENDING);
        }

        return RequestMapper.toDto(requestRepository.saveAndFlush(request));
    }

    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {
        Request request = requestRepository.findByIdAndRequester_Id(requestId, userId).orElseThrow(() -> {
            log.info("Заявки не существует!");
            throw new RequestException("Заявки не существует!");
        });
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.toDto(requestRepository.saveAndFlush(request));
    }
}
