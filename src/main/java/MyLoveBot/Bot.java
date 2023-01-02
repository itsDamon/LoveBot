package MyLoveBot;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

public class Bot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "OurLove_29_Bot";
    }

    @Override
    public String getBotToken() {
        return "5911765782:AAFZcAm_XJrdEG9sXX7MatH5FtLSAzM8sP4";
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();
        var id = user.getId();

        System.out.println(user.getFirstName() + ": "+ msg.getText());

        if (msg.isCommand()) {
            executeCommand(msg);
            return;
        }

        sendMessage(id, "Hey tu! Usa un comando ^_^");

    }

    private void executeCommand(Message msg) {
        var command = msg.getText().split("/")[1];
        var id = msg.getFrom().getId();

        switch (command) {
            default -> {
                sendMessage(id, "Heyy! Quel comando non esiste, se vuoi che io lo aggiunga dimmelo :) <3");
            }
            case "start" -> {
                sendMessage(id, "benvenuto <3");
            }
            case "love" -> {
                String[] loveStrings = {"Ti amo!", "Ti amo tantissimo!", "Sono fiero di te!", "Sei bellissima!"};
                sendMessage(id, randomStringFromArray(loveStrings));
            }
            case "ourdays" -> {
                LocalDate inizio = LocalDate.of(2022, 10, 29);
                Period tempo = Period.between(inizio, LocalDate.now());
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stiamo insieme da \n");
                if (tempo.getYears() != 0) {
                    stringBuilder.append(tempo.getYears());
                    stringBuilder.append((tempo.getYears() > 1) ? "anni" : "anno");
                }
                stringBuilder.append(tempo.getMonths()).append(" mesi e\n").append(tempo.getDays()).append(" giorni\n\nTi amo💙");
                if (LocalDate.now().getDayOfMonth() == 29) {
                    stringBuilder.append("\nAuguri a noii!💙");
                }
                sendMessage(id, stringBuilder.toString());
            }
            case "prossimavolta" -> {
                DateTime prossimaVolta = new DateTime(2023, 1, 7, 14, 0);
                Interval interval = new Interval(DateTime.now(), prossimaVolta);
                Duration duration = interval.toDuration();
                String stringBuilder = "Alla prossima volta che ci vedremo mancano\n" +
                        duration.getStandardDays() + " giorni\n" +
                        (duration.getStandardHours() - duration.getStandardDays() * 24) + " ore\n" +
                        (duration.getStandardMinutes() - duration.getStandardHours() * 60 + 1) + " minuti\n" +
                        "\n(non vedo l'oraa💙)";
                sendMessage(id, stringBuilder);
            }
        }
    }

    private String randomStringFromArray(String[] strings) {
        Random r = new Random();
        int len = strings.length;
        int index = r.nextInt(len);
        System.out.println(strings[index]);
        return strings[index];
    }

    public void sendMessage(Long who, String message) {
        SendMessage sm = SendMessage.builder().chatId(who.toString()).text(message).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
