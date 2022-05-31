package com.esgi.algoBattle.game.infrastructure.dataprovider.dao;

import com.esgi.algoBattle.game.domain.dao.GameDAO;
import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.game.infrastructure.dataprovider.entity.GameEntity;
import com.esgi.algoBattle.game.infrastructure.dataprovider.mapper.GameMapper;
import com.esgi.algoBattle.game.infrastructure.dataprovider.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameMySqlDao implements GameDAO {

    private final GameRepository repository;
    private final GameMapper mapper;

    @Override
    public Game create(Game game) {
        GameEntity entity = mapper.toEntity(game);
        entity = repository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Game findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Game> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Game> findAllUnfinishedGames() {
        return repository.findByOver(false)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Game update(Game game) {
        GameEntity gameEntity = repository.getOne(game.getId());
        gameEntity.setOver(game.getOver());
        return mapper.toDomain(repository.save(gameEntity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
