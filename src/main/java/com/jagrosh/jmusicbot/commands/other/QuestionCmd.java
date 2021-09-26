package com.jagrosh.jmusicbot.commands.other;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда, которая отвечает на любой вопрос.
 */
public class QuestionCmd extends Command {

    private List<String> allAnswers = new ArrayList<>();

    public QuestionCmd() {
        this.name = "q";
        this.help = "получить ответ на любой вопрос";
        this.arguments = "<любой вопрос>";

        String[] answers = new String[]{ "да", "нет" };
        String[] prefixes = new String[]{ "наверное", "определенно", "скорее всего" };

        for (String answer : answers) {
            for (String prefix : prefixes) {
                allAnswers.add(prefix + " " + answer);
            }
        }

        allAnswers.add("не знаю");
        allAnswers.add("секрет");
    }

    @Override
    protected void execute(CommandEvent event) {
        String args = event.getArgs();

        args = args.trim().toLowerCase();

        if (args.length() == 0) {
            event.reply(event.getClient().getError()+" Укажите вопрос!");
            return;
        }

        int answerIndex = getAnswerIndex(args.hashCode(), allAnswers.size());
        String answer = allAnswers.get(answerIndex);
        event.replySuccess(answer);
    }

    public static int getAnswerIndex(int question, int maxAnswers) {
        if (question < 0 || question >= maxAnswers) {
            int origin = (question / maxAnswers) * maxAnswers;
            int relativePosition = question - origin;
            if (relativePosition < 0) {
                // переводим в положительную часть
                relativePosition += maxAnswers;
            }

            return relativePosition;
        }

        // если координата уже в нулевых координатах, тогда возвращаем ее без изменений
        return question;
    }
}
