package ru.practicum.ewmservice.compilation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.mapper.CompilationMapper;
import ru.practicum.ewmservice.compilation.model.Compilation;
import ru.practicum.ewmservice.compilation.repository.CompilationRepository;
import ru.practicum.ewmservice.exception.CompilationNotExists;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
@Slf4j
public class CompilationServiceImpl implements CompilationService{
    private final CompilationRepository compilationRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public CompilationServiceImpl(CompilationRepository compilationRepository) {
        this.compilationRepository = compilationRepository;
    }

    @Override
    public List<CompilationDto> getCompilations(String pinned, int size, int from) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Compilation> query = builder.createQuery(Compilation.class);
        Root<Compilation> compilation = query.from(Compilation.class);
        Predicate criteria = builder.conjunction();

        if (pinned != null) {
            boolean parsePinned = Boolean.parseBoolean(pinned);
            Predicate pinnedStat = compilation.get("pinned").in(parsePinned);
            criteria = builder.and(criteria, pinnedStat);
        }
        query.select(compilation).where(criteria);
        List<Compilation> result = entityManager.createQuery(query).setFirstResult(from).setMaxResults(size).getResultList();
        return CompilationMapper.toDtoList(result);
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(()->{
            log.info(String.format("Подборки %d не существует!", compId));
            throw new CompilationNotExists(String.format("Подборки %d не существует!", compId));
        });

        return CompilationMapper.toDto(compilation);
    }
}
