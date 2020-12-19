package com.udemy.learn.service;

import com.udemy.learn.Game;
import com.udemy.learn.MessageGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class GameServiceImpl implements GameService {

    // == Fields ==
    private final Game game;
    private final MessageGenerator messageGenerator;

    // == Constructor ==
    @Autowired
    public GameServiceImpl(Game game, MessageGenerator messageGenerator) {
        this.game = game;
        this.messageGenerator = messageGenerator;
    }

    // == Methods ==
    @PostConstruct
    public void init() {
        log.info("@Main message: " + messageGenerator.something());
        log.info("@Number: " + game.getNumber());
    }

    @Override
    public boolean isGameOver() {
        if (game.isGameWon() || game.isGameLost()) {
            return true;
        }
        return false;
    }

    @Override
    public String getMainMessage() {
        return messageGenerator.something();
    }

    @Override
    public String getResultMessage() {
        return messageGenerator.somethingElse();
    }

    @Override
    public void checkGuess(int guess) {
        game.setGuess(guess);
        game.check();
    }

    @Override
    public void reset() {
        game.reset();
    }
}
