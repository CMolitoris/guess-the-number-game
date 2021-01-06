package com.udemy.learn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MessageGeneratorImpl implements MessageGenerator {

    private static final String MAIN_MESSAGE = "game.main.message";
    private static final String WIN = "game.win";
    public static final String LOSE = "game.lose";
    public static final String INVALID = "game.invalid.range";
    public static final String FIRST_GUESS = "game.first.guess";
    public static final String HIGHER = "game.higher";
    public static final String LOWER = "game.lower";
    public static final String REMAINING = "game.remaining";

    private final Game game;
    private final MessageSource messageSource;

    // == Constructors ==
    @Autowired
    public MessageGeneratorImpl(Game game, MessageSource messageSource) {
        this.game = game;
        this.messageSource = messageSource;
    }

    // == PostConstruct Statement ==
    @PostConstruct
    public void start() {
        log.info("Value: {}",game);
    }

    @Override
    public String something() {
        return getMessage(MAIN_MESSAGE,game.getSmallest(),game.getBiggest());
    }

    @Override
    public String somethingElse() {
        if (game.isGameWon()) {
            return getMessage(WIN,game.getNumber());
        } else if (game.isGameLost()) {
            return getMessage(LOSE,game.getNumber());
        } else if (!game.isValidNumberRange()) {
            return getMessage(INVALID);
        } else if (game.getRemainingGuesses()==game.getGuessCount()) {
            return getMessage(FIRST_GUESS);
        } else {
            String direction = getMessage(LOWER);
            if (game.getGuess()<game.getNumber()) {
                direction = getMessage(HIGHER);
            }
            return direction + getMessage(REMAINING, game.getRemainingGuesses());
        }
    }

    private String getMessage(String code, Object... args) {
        return messageSource.getMessage(code,args, LocaleContextHolder.getLocale());
    }

}
