package com.udemy.learn;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Getter
@Component
public class GameImpl implements Game {

     // == fields ==
    @Getter(AccessLevel.NONE)
    private  final NumberGenerator numberGenerator;

    private final int guessCount;
    private final int minNumber;
    private int number;
    private int smallest;
    private int biggest;
    private int remainingGuesses;
    private boolean validNumberRange = true;

    @Setter
    private int guess;

    // == Constructors ==
    @Autowired
    public GameImpl(NumberGenerator numberGenerator,@GuessCount int guessCount,@MinNumber int minNumber) {
        this.numberGenerator = numberGenerator;
        this.guessCount = guessCount;
        this.minNumber = minNumber;
    }

    //init
    @PostConstruct
    @Override
    public void reset() {
        smallest = minNumber;
        guess = minNumber;
        remainingGuesses = guessCount;
        biggest = numberGenerator.getMaxNumber();
        number = numberGenerator.next();
        log.debug("the number is {}",number);
        System.out.println("reset() called");
    }

    @PreDestroy
    public void preDistroy() {
        log.info("in Game preDestroy()");
    }

    @Override
    public void check() {
        checkValidNumberRange();
        if (validNumberRange) {
            if (guess > number) {
                biggest = guess -1;
            }

            if (guess < number) {
                smallest = guess +1;
            }

            remainingGuesses--;
        }

    }

    @Override
    public boolean isGameWon() {
        return guess == number;
    }

    @Override
    public boolean isGameLost() {
        return !isGameWon() && remainingGuesses <= 0;
    }

    //private method
    private void checkValidNumberRange() {
        validNumberRange = (guess >= smallest) && (guess <= biggest);
    }
}
