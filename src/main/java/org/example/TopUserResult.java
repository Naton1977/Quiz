package org.example;

import java.io.Serializable;

public class TopUserResult implements Serializable, Comparable<TopUserResult> {
    private String userName;
    private int numberOfCorrectAnswer;
    private int numberOfWrongAnswer;

    public TopUserResult(String userName, int numberOfCorrectAnswer, int numberOfWrongAnswer) {
        this.userName = userName;
        this.numberOfCorrectAnswer = numberOfCorrectAnswer;
        this.numberOfWrongAnswer = numberOfWrongAnswer;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        return "TopUserResult{" +
                "userName='" + userName + '\'' +
                ", numberOfCorrectAnswer=" + numberOfCorrectAnswer +
                ", numberOfWrongAnswer=" + numberOfWrongAnswer +
                '}';
    }

    @Override
    public int compareTo(TopUserResult o) {
        return numberOfCorrectAnswer - o.numberOfCorrectAnswer;
    }
}
