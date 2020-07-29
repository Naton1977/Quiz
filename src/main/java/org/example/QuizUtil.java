package org.example;

import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.*;

public class QuizUtil {
    private String status;
    private String login;
    private String password;
    private String quizName;
    private String quizQuestion;
    private String quizAnswer1;
    private String quizAnswer2;
    private String quizAnswer3;
    private String quizAnswer4;
    private String quizCorrectAnswerTmp;
    private String namberQuiz;
    private int quizCorrectAnswer;
    private int namberQuisNum;
    private int count;
    private int tmp;
    private Quiz quiz;
    Map<Quiz, List<Question>> quizListMap = new TreeMap<>();
    Map<Quiz, List<TopUserResult>> quizResultUser = new TreeMap<>();
    Scanner scanner = new Scanner(System.in);

    public void util() throws FileNotFoundException {
        Game game = new Game();
        String fileName3 = "TopUserResult.dat";
        File file3 = new File(fileName3);
        if (file3.exists()) {
            quizResultUser = readObjectTopUserResult(fileName3);
        }

        String fileName = "QuizListMap.dat";
        File file = new File(fileName);
        if (file.exists()) {
            quizListMap = readObjectQuizListMap(fileName);
        }
        do {
            System.out.println("1 - войти для редактирования викторин");
            System.out.println("exit -  выход");
            status = scanner.nextLine();
            if ("1".equals(status)) {
                System.out.println("Введите логин");
                login = scanner.nextLine();
                System.out.println("Введите пароль");
                password = scanner.nextLine();
                if ("Admin".equals(login) && "Admin".equals(password)) {
                    do {
                        System.out.println("1 - посмотреть список всех викторин");
                        System.out.println("2 - удалить викторину");
                        System.out.println("3 - редактировать викторину");
                        System.out.println("4 - создать новую викторину");
                        System.out.println("exit - выход");
                        status = scanner.nextLine();
                        if ("1".equals(status)) {
                            count = 1;
                            for (Map.Entry<Quiz, List<Question>> ql : quizListMap.entrySet()) {
                                System.out.println(count + " - " + ql.getKey().getName());
                                count++;
                            }
                        }
                        if ("2".equals(status)) {
                            count = 1;
                            for (Map.Entry<Quiz, List<Question>> ql : quizListMap.entrySet()) {
                                System.out.println(count + " - " + ql.getKey().getName());
                                count++;
                            }
                            do {
                                System.out.println("Введите номер викторины которую необходимо удалить");
                                System.out.println("exit - выход");
                                status = scanner.nextLine();
                                if ("exit".equals(status)) {
                                    break;
                                }
                                do {
                                    try {
                                        tmp = Integer.parseInt(status);
                                        break;
                                    } catch (Exception e) {
                                        System.out.println("Введите правильно номер викторины");
                                    }
                                } while (true);
                                count = 1;
                                for (Map.Entry<Quiz, List<Question>> ql : quizListMap.entrySet()) {
                                    if (count == tmp) {
                                        quiz = ql.getKey();

                                    }
                                    count++;
                                }
                                quizListMap.remove(quiz);
                                System.out.println("Викторина успешно удаленна !!!");
                            } while (true);


                        }
                        if ("3".equals(status)) {
                            count = 1;
                            for (Map.Entry<Quiz, List<Question>> ql : quizListMap.entrySet()) {
                                System.out.println(count + " - " + ql.getKey().getName());
                                count++;
                            }
                            System.out.println("Введите номер викторины для редактирования");
                            namberQuiz = scanner.nextLine();
                            do {
                                try {
                                    namberQuisNum = Integer.parseInt(namberQuiz);
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Введите правильно номер викторины");
                                }
                            } while (true);
                            count = 1;
                            for (Map.Entry<Quiz, List<Question>> ql : quizListMap.entrySet()) {
                                if (count == namberQuisNum) {
                                    quizName = ql.getKey().getName();
                                    do {
                                        System.out.println("1- корректировать название викторины");
                                        System.out.println("2- корректировать вопросы викторины");
                                        System.out.println("exit - выход");
                                        status = scanner.nextLine();
                                        if ("1".equals(status)) {
                                            System.out.println("Введите новое название викторины");
                                            quizName = scanner.nextLine();
                                            ql.getKey().setName(quizName);
                                            System.out.println("Название викторины изменено");
                                        }
                                        if ("2".equals(status)) {
                                            List<Question> questionList = ql.getValue();
                                            do {
                                                System.out.println("1- показать список вопросов");
                                                System.out.println("2- изменить вопрос и ответы");
                                                System.out.println("3- удалить вопрос");
                                                System.out.println("4- добавить вопрос");
                                                System.out.println("exit -  выход");
                                                status = scanner.nextLine();
                                                if ("1".equals(status)) {
                                                    for (int i = 0; i < questionList.size(); i++) {
                                                        System.out.println((i + 1) + " - " + questionList.get(i).getQuestion());
                                                    }
                                                }
                                                if ("2".equals(status)) {
                                                    System.out.println("Ведите номер вопроса который нужно изменить");
                                                    quizCorrectAnswerTmp = scanner.nextLine();
                                                    do {
                                                        try {
                                                            quizCorrectAnswer = Integer.parseInt(quizCorrectAnswerTmp);
                                                            break;
                                                        } catch (Exception e) {
                                                            System.out.println("Введите правильно номер вопроса");
                                                        }
                                                    } while (true);
                                                    for (int i = 0; i < questionList.size(); i++) {
                                                        if ((i + 1) == quizCorrectAnswer) {
                                                            System.out.println("Введите новый вопрос");
                                                            quizQuestion = scanner.nextLine();
                                                            questionList.get(i).setQuestion(quizQuestion);
                                                            System.out.println("Вопрос изменен");
                                                            System.out.println("Введите 1-й вариант ответа");
                                                            quizAnswer1 = scanner.nextLine();
                                                            questionList.get(i).setAnswer1(quizAnswer1);
                                                            System.out.println("Ответ изменен");
                                                            System.out.println("Ведите 2-й вариант ответа");
                                                            quizAnswer2 = scanner.nextLine();
                                                            questionList.get(i).setAnswer2(quizAnswer2);
                                                            System.out.println("Ответ изменен");
                                                            System.out.println("Введите 3-й вариант ответа");
                                                            quizAnswer3 = scanner.nextLine();
                                                            questionList.get(i).setAnswer3(quizAnswer3);
                                                            System.out.println("Ответ изменен");
                                                            System.out.println("Введите 4-й вариант ответа");
                                                            quizAnswer4 = scanner.nextLine();
                                                            questionList.get(i).setAnswer4(quizAnswer4);
                                                            System.out.println("Ответ изменен");
                                                            System.out.println("Введите номер правильного ответа");
                                                            quizCorrectAnswerTmp = scanner.nextLine();
                                                            do {
                                                                try {
                                                                    quizCorrectAnswer = Integer.parseInt(quizCorrectAnswerTmp);
                                                                    break;
                                                                } catch (Exception e) {
                                                                    System.out.println("Введите правильно номер ответа");
                                                                }
                                                            } while (true);
                                                            questionList.get(i).setCorrectAnswer(quizCorrectAnswer);
                                                            System.out.println("Номер изменен");
                                                            break;
                                                        }

                                                    }
                                                }
                                                if ("3".equals(status)) {
                                                    System.out.println("Ведите номер вопроса который нужно удалить");
                                                    quizCorrectAnswerTmp = scanner.nextLine();
                                                    do {
                                                        try {
                                                            quizCorrectAnswer = Integer.parseInt(quizCorrectAnswerTmp);
                                                            break;
                                                        } catch (Exception e) {
                                                            System.out.println("Введите правильно номер вопроса");
                                                        }
                                                    } while (true);
                                                    for (int i = 0; i < questionList.size(); i++) {
                                                        if ((i + 1) == quizCorrectAnswer) {
                                                            questionList.remove(i);
                                                            System.out.println("Вопрос удален");
                                                            break;
                                                        }
                                                    }

                                                }
                                                if ("4".equals(status)) {
                                                    addQuestion(quizName);
                                                    System.out.println("Вопрос добавлен");
                                                }
                                                if ("exit".equals(status)) {
                                                    status = null;
                                                    break;
                                                }
                                            } while (true);

                                        }
                                        if ("exit".equals(status)) {
                                            status = null;
                                            break;
                                        }
                                    } while (true);
                                }
                                count++;
                            }


                        }
                        if ("4".equals(status)) {
                            System.out.println("Введите название викторины");
                            quizName = scanner.nextLine();
                            quizListMap.put(new Quiz(quizName),new ArrayList<>());
                            quizResultUser.put(new Quiz(quizName), new ArrayList<>());
                            addQuestion(quizName);
                        }
                        if ("exit".equals(status)) {
                            status = null;
                            break;
                        }
                    } while (true);
                } else {
                    System.out.println("Вы ввели не правильный логин или пароль");
                }


            }
        } while (!"exit".equals(status));

        saveObjectQuizListMap(quizListMap, "QuizListMap.dat");
        saveObjectTopUserResult(quizResultUser, "TopUserResult.dat");
    }


    private static void saveObjectQuizListMap(Map<Quiz, List<Question>> quizListMap, String fileName) throws FileNotFoundException {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(quizListMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   private static Map<Quiz, List<Question>> readObjectQuizListMap(String fileName) throws FileNotFoundException {
        Map<Quiz, List<Question>> quizListMap = null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            quizListMap = (Map<Quiz, List<Question>>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return quizListMap;
    }

    private void addQuestion(String quizName) {

        do {
            System.out.println("Введите вопрос");
            System.out.println("exit - выход");
            quizQuestion = scanner.nextLine();
            if ("exit".equals(quizQuestion)) {
                quizQuestion = null;
                break;
            }
            System.out.println("Ведите 1-й вариант ответа");
            quizAnswer1 = scanner.nextLine();
            System.out.println("Введите 2-й вариант ответа");
            quizAnswer2 = scanner.nextLine();
            System.out.println("Введите 3-й вариант ответа");
            quizAnswer3 = scanner.nextLine();
            System.out.println("Введите 4-й вариант ответа");
            quizAnswer4 = scanner.nextLine();
            System.out.println("Введите номер правильного ответа");
            quizCorrectAnswerTmp = scanner.nextLine();
            do {
                try {
                    quizCorrectAnswer = Integer.parseInt(quizCorrectAnswerTmp);
                    break;
                } catch (Exception e) {
                    System.out.println("Введите правильно номер ответа");
                }
            } while (true);

            for (Map.Entry<Quiz, List<Question>> ql : quizListMap.entrySet()) {
                if (ql.getKey().getName().equals(quizName)) {
                    if(ql.getValue().size() <= 20) {
                        ql.getValue().add(new Question(quizQuestion, quizAnswer1, quizAnswer2, quizAnswer3, quizAnswer4, quizCorrectAnswer));
                        System.out.println("Вопрос № " + (ql.getValue().size()) + " добавлен");
                    } else {
                        System.out.println("Ввекторине может быть только 20 вопросов !!!");
                    }
                }

            }

        } while (true);

    }
    private static void saveObjectTopUserResult(Map<Quiz, List<TopUserResult>> quizResultUser, String fileName) throws FileNotFoundException {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(quizResultUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<Quiz, List<TopUserResult>> readObjectTopUserResult(String fileName) throws FileNotFoundException {
        Map<Quiz, List<TopUserResult>> quizResultUser = null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            quizResultUser = (Map<Quiz, List<TopUserResult>>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return quizResultUser;
    }
}
