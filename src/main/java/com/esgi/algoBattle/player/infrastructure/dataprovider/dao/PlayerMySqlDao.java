package com.esgi.algoBattle.player.infrastructure.dataprovider.dao;

import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.game.infrastructure.dataprovider.mapper.GameMapper;
import com.esgi.algoBattle.game.infrastructure.dataprovider.repository.GameRepository;
import com.esgi.algoBattle.player.domain.dao.PlayerDAO;
import com.esgi.algoBattle.player.domain.model.Player;
import com.esgi.algoBattle.player.infrastructure.dataprovider.entity.PlayerEntity;
import com.esgi.algoBattle.player.infrastructure.dataprovider.mapper.PlayerMapper;
import com.esgi.algoBattle.player.infrastructure.dataprovider.repository.PlayerRepository;
import com.esgi.algoBattle.user.domain.model.User;
import com.esgi.algoBattle.user.infrastructure.dataprovider.mapper.UserMapper;
import com.esgi.algoBattle.user.infrastructure.dataprovider.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerMySqlDao implements PlayerDAO {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final PlayerMapper playerMapper;
    private final GameMapper gameMapper;
    private final UserMapper userMapper;

    @Override
    public void create(Player player) {
        PlayerEntity entity = playerMapper.toEntity(player);
        playerRepository.save(entity);
    }

    @Override
    public void update(Player player) {
        var playerEntity = playerRepository.findByGameIdAndUserId(player.getGame().getId(),
                player.getUser().getId());
        playerEntity.ifPresent(entity -> {
            entity.setRemainingHealthPoints(player.getRemainingHealthPoints());
            entity.setWon(player.getWon());
            playerRepository.save(entity);
        });
    }

    @Override
    public void delete(Long gameId, Long userId) {
        playerRepository.findByGameIdAndUserId(gameId, userId)
                .ifPresent(playerRepository::delete);
    }

    @Override
    public Player findByIds(Long gameId, Long userId) {
        return playerRepository.findByGameIdAndUserId(gameId, userId)
                .map(playerMapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Player> findAllByGame(Long gameId) {
        var game = findGameById(gameId);
        var entitiesByGame = playerRepository.findByGameId(gameId);

        List<Player> players = new ArrayList<>();

        entitiesByGame.forEach(entity -> {
            var user = findUserById(entity.getUserId());
            var player = playerMapper.toDomainWithModels(entity, game, user);
            players.add(player);
        });

        return players;
    }

    @Override
    public List<Player> findAllByUser(Long userId) {
        var user = findUserById(userId);
        var entitiesByUser = playerRepository.findByUserId(userId);

        List<Player> players = new ArrayList<>();

        entitiesByUser.forEach(entity -> {
            var game = findGameById(entity.getGameId());
            var player = playerMapper.toDomainWithModels(entity, game, user);
            players.add(player);
        });

        return players;
    }

    @Override
    public List<Player> findAllByAllGames(List<Long> gameIds) {
        final var entities = playerRepository.findByGameIdIn(gameIds);
        final var players = new ArrayList<Player>();

        entities.forEach(entity -> {
            final var game = findGameById(entity.getGameId());
            final var user = findUserById(entity.getUserId());
            final var player = playerMapper.toDomainWithModels(entity, game, user);
            players.add(player);
        });

        return players;
    }

    private Game findGameById(Long gameId) {
        return gameRepository.findById(gameId)
                .map(gameMapper::toDomain)
                .orElse(new Game());
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDomain)
                .orElse(new User());
    }
}
