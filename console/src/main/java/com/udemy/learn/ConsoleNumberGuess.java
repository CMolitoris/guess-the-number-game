package com.udemy.learn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Slf4j
@Component
public class ConsoleNumberGuess implements ApplicationListener<ContextRefreshedEvent> {

    // == Fields ==
    private final Game game;
    private final MessageGenerator messageGenerator;

    // == Constructors ==
    @Autowired
    public ConsoleNumberGuess(Game game, MessageGenerator messageGenerator) {
        this.game = game;
        this.messageGenerator = messageGenerator;
    }

    // == Events ==
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Container ready for use.");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(messageGenerator.something());
            System.out.println(messageGenerator.somethingElse());

            int guess = scanner.nextInt();
            scanner.nextLine();
            game.setGuess(guess);
            game.check();

            if (game.isGameLost() || game.isGameWon()) {
                System.out.println(messageGenerator.somethingElse());
                System.out.println("Play again? Y/N");

                String playAgain = scanner.nextLine().trim();
                if (!playAgain.equalsIgnoreCase("y")) {
                    break;
                }
                game.reset();
            }
        }
    }
}
