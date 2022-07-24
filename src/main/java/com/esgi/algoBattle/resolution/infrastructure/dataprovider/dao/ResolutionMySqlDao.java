package com.esgi.algoBattle.resolution.infrastructure.dataprovider.dao;

import com.esgi.algoBattle.compiler.domain.model.LinterError;
import com.esgi.algoBattle.resolution.domain.dao.ResolutionDAO;
import com.esgi.algoBattle.resolution.domain.model.Resolution;
import com.esgi.algoBattle.resolution.infrastructure.dataprovider.entity.ResolutionEntity;
import com.esgi.algoBattle.resolution.infrastructure.dataprovider.mapper.ResolutionMapper;
import com.esgi.algoBattle.resolution.infrastructure.dataprovider.repository.ResolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResolutionMySqlDao implements ResolutionDAO {

    private final ResolutionRepository resolutionRepository;
    private final ResolutionMapper resolutionMapper;

    @Override
    public Resolution create(Resolution resolution) {
        ResolutionEntity entity = resolutionMapper.toEntity(resolution);
        return resolutionMapper
                .toDomain(resolutionRepository.save(entity));
    }

    @Override
    public Resolution startResolution(Resolution resolution) {
        final var entity = resolutionMapper.toStartedResolutionEntity(resolution);
        return resolutionMapper.toDomain(resolutionRepository.save(entity));
    }

    @Override
    public Resolution update(Resolution resolution) {
        var resolutionEntity = resolutionRepository
                .findByUserIdAndAlgoId(resolution.getUser().getId(),
                        resolution.getAlgorithm().getId());

        if (resolutionEntity.isPresent()) {
            final var entity = resolutionEntity.get();
            entity.setResolutionTime(resolution.getResolutionTime());
            entity.setSolved(resolution.getSolved());
            entity.setLinterErrors(resolution.getLinterErrors().stream()
                    .mapToInt(LinterError::getErrorNumber)
                    .sum());
            return resolutionMapper
                    .toDomain(resolutionRepository.save(entity));
        }

        return null;
    }

    @Override
    public void delete(Long userId, Long algorithmId) {
        resolutionRepository.findByUserIdAndAlgoId(userId, algorithmId)
                .ifPresent(resolutionRepository::delete);
    }

    @Override
    public Resolution findByIds(Long userId, Long algorithmId) {
        return resolutionRepository.findByUserIdAndAlgoId(userId, algorithmId)
                .map(resolutionMapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Resolution> findResolutionsByUser(Long userId) {
        return resolutionRepository.findByUserId(userId)
                .stream()
                .map(resolutionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Resolution> findResolutionsByAlgo(Long algorithmId) {
        return resolutionRepository.findByAlgoId(algorithmId)
                .stream()
                .map(resolutionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Resolution> findResolutionsByGame(Long gameId) {
        return resolutionRepository.findByGameId(gameId)
                .stream()
                .map(resolutionMapper::toDomain)
                .collect(Collectors.toList());
    }
}
