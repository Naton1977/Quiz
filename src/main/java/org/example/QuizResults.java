package org.example;

import java.io.Serializable;

public class QuizResults implements Comparable<QuizResults>, Serializable {
    private String name;
    private int numberOfCorrectAnswer;
    private int numberOfWrongAnswer;

    public QuizResults(String name, int numberOfCorrectAnswer, int numberOfWrongAnswer) {
        this.name = name;
        this.numberOfCorrectAnswer = numberOfCorrectAnswer;
        this.numberOfWrongAnswer = numberOfWrongAnswer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfCorrectAnswer() {
        return numberOfCorrectAnswer;
    }

    public void setNumberOfCorrectAnswer(int numberOfCorrectAnswer) {
        this.numberOfCorrectAnswer = numberOfCorrectAnswer;
    }

    public int getNumberOfWrongAnswer() {
        return numberOfWrongAnswer;
    }

    public void setNumberOfWrongAnswer(int numberOfWrongAnswer) {
        this.numberOfWrongAnswer = numberOfWrongAnswer;
    }

    @Override
    public String toString() {
        return "QuizResults{" +
                "name='" + name + '\'' +
                ", numberOfCorrectAnswer=" + numberOfCorrectAnswer +
                ", numberOfWrongAnswer=" + numberOfWrongAnswer +
                '}';
    }


    @Override
    public int compareTo(QuizResults o) {
        return numberOfCorrectAnswer - o.numberOfCorrectAnswer;
    }
}
