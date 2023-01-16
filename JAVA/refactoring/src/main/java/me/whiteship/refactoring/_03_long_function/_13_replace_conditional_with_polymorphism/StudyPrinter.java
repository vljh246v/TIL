package me.whiteship.refactoring._03_long_function._13_replace_conditional_with_polymorphism;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

public class StudyPrinter {

    private int totalNumberOfEvents;

    private List<Participant> participants;

    private PrinterMode printerMode;

    public StudyPrinter(int totalNumberOfEvents, List<Participant> participants, PrinterMode printerMode) {
        this.totalNumberOfEvents = totalNumberOfEvents;
        this.participants = participants;
        this.participants.sort(Comparator.comparing(Participant::username));
        this.printerMode = printerMode;
    }

    public void execute() throws IOException {
        switch (printerMode) {
            case CVS -> {
                try (FileWriter fileWriter = new FileWriter("participants.cvs");
                     PrintWriter writer = new PrintWriter(fileWriter)) {
                    writer.println(cvsHeader(this.participants.size()));
                    this.participants.forEach(p -> {
                        writer.println(getCvsForParticipant(p));
                    });
                }
            }
            case CONSOLE -> {
                this.participants.forEach(p -> {
                    System.out.printf("%s %s:%s\n", p.username(), checkMark(p), p.getRate(this.totalNumberOfEvents));
                });
            }
            case MARKDOWN -> {
                try (FileWriter fileWriter = new FileWriter("participants.md");
                     PrintWriter writer = new PrintWriter(fileWriter)) {

                    writer.print(header(this.participants.size()));

                    this.participants.forEach(p -> {
                        String markdownForHomework = getMarkdownForParticipant(p);
                        writer.print(markdownForHomework);
                    });
                }
            }
        }
    }

    private String getCvsForParticipant(Participant participant) {
        StringBuilder line = new StringBuilder();
        line.append(participant.username());
        for (int i = 1 ; i <= this.totalNumberOfEvents ; i++) {
            if(participant.homework().containsKey(i) && participant.homework().get(i)) {
                line.append(",O");
            } else {
                line.append(",X");
            }
        }
        line.append(",").append(participant.getRate(this.totalNumberOfEvents));
        return line.toString();
    }

    private String cvsHeader(int totalNumberOfParticipants) {
        StringBuilder header = new StringBuilder(String.format("참여자 (%d),", totalNumberOfParticipants));
        for (int index = 1; index <= this.totalNumberOfEvents; index++) {
            header.append(String.format("%d주차,", index));
        }
        header.append("참석율");
        return header.toString();
    }

    private String getMarkdownForParticipant(Participant p) {
        return String.format("| %s %s | %.2f%% |\n", p.username(), checkMark(p),
                p.getRate(this.totalNumberOfEvents));
    }

    /**
     * | 참여자 (420) | 1주차 | 2주차 | 3주차 | 참석율 |
     * | --- | --- | --- | --- | --- |
     */
    private String header(int totalNumberOfParticipants) {
        StringBuilder header = new StringBuilder(String.format("| 참여자 (%d) |", totalNumberOfParticipants));

        for (int index = 1; index <= this.totalNumberOfEvents; index++) {
            header.append(String.format(" %d주차 |", index));
        }
        header.append(" 참석율 |\n");

        header.append("| --- ".repeat(Math.max(0, this.totalNumberOfEvents + 2)));
        header.append("|\n");

        return header.toString();
    }

    /**
     * |:white_check_mark:|:white_check_mark:|:white_check_mark:|:x:|
     */
    private String checkMark(Participant p) {
        StringBuilder line = new StringBuilder();
        for (int i = 1 ; i <= this.totalNumberOfEvents ; i++) {
            if(p.homework().containsKey(i) && p.homework().get(i)) {
                line.append("|:white_check_mark:");
            } else {
                line.append("|:x:");
            }
        }
        return line.toString();
    }
}
