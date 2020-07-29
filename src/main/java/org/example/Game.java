package org.example;

import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

public class Game {
    private int numberOfCorrectAnswer;
    private int numberOfWrongAnswer;
    private String status;
    private String quizName;
    private int count = 1;
    private int tmp = 0;
    List<User> userList = new ArrayList<>();
    private String login;
    Scanner scanner = new Scanner(System.in);
    Map<Quiz, List<Question>> quizListMap = new TreeMap<>();
    Map<User, List<QuizResults>> resultMap = new TreeMap<>();
    Map<Quiz, List<TopUserResult>> quizResultUser = new TreeMap<>();
    Quiz quiz;

    public void game() throws FileNotFoundException {
        String fileName3 = "TopUserResult.dat";
        File file3 = new File(fileName3);
        if (file3.exists()) {
            quizResultUser = readObjectTopUserResult(fileName3);
        }

        String fileName2 = "QuizListMap.dat";
        File file2 = new File(fileName2);
        if (file2.exists()) {
            quizListMap = readObjectQuizListMap(fileName2);
        }
        String fileName = "UserDataBase.dat";
        File file = new File(fileName);
        if (file.exists()) {
            userList = readObjectUser(fileName);
        }

        String fileName1 = "ResultMap.dat";
        File file1 = new File(fileName1);
        if (file1.exists()) {
            resultMap = readObjectResultMap(fileName1);
        }
        String password;
        String dateOfBirth;

        System.out.println("Здравствуйте !!!! ");
        System.out.println("Приветствуем Вас на викторине Хочу Все Знать !!!!");
        do {
            status = null;
            System.out.println("1 - войти");
            System.out.println("2 - зарегистрировать нового пользователя");
            System.out.println("exit - выйти");
            status = scanner.nextLine();
            if ("1".equals(status)) {
                do {
                    System.out.println("Ведите login");
                    System.out.println("exit - выход");
                    login = scanner.nextLine();
                    if ("exit".equals(login)) {
                        login = null;
                        break;
                    }
                    if (isPresent(login)) {
                        if (passwordTrue(login)) {
                            break;
                        }
                    } else {
                        System.out.println("Такого логина не существует !!!");
                    }
                } while (true);
                do {
                    System.out.println("1 - стартовать викторину");
                    System.out.println("2 - посмотреть результаты своих викторин");
                    System.out.println("3 - посмотреть топ - 20 по викторинам");
                    System.out.println("4 - изменить данные аккаунта");
                    System.out.println("exit - выход");
                    status = scanner.nextLine();
                    if ("1".equals(status)) {
                        count = 1;
                        System.out.println("Доступные викторины");
                        for (Map.Entry<Quiz, List<Question>> r : quizListMap.entrySet()) {
                            System.out.println(count + "- " + r.getKey().getName());
                            count++;
                        }
                        System.out.println("Введите номер викторины");
                        status = scanner.nextLine();
                        try {
                            tmp = Integer.parseInt(status);
                        } catch (Exception e) {
                            System.out.println("Введите правильно номер викторины");
                        }
                        count = 1;
                        for (Map.Entry<Quiz, List<Question>> r : quizListMap.entrySet()) {
                            List<Question> questionList;
                            if (count == tmp) {
                                System.out.println("Викторина : " + r.getKey().getName());
                                quizName = r.getKey().getName();
                                questionList = r.getValue();
                                for (Question q : questionList) {
                                    System.out.println("Вопрос : " + q.getQuestion());
                                    System.out.println("Варианты ответа :");
                                    System.out.printf("1- %-15s 2- %-15s %n3- %-15s 4- %-15s%n", q.getAnswer1(), q.getAnswer2(), q.getAnswer3(), q.getAnswer4());
                                    System.out.println("Введите номер правильного ответа");
                                    do {
                                        status = scanner.nextLine();
                                        try {
                                            tmp = Integer.parseInt(status);
                                            break;
                                        } catch (Exception e) {
                                            tmp = 0;
                                            System.out.println("Введите правильно номер ответа");
                                        }
                                    } while (true);
                                    do {
                                        if (tmp < 1 || tmp > 4) {
                                            System.out.println("Введите правильно номер ответа");
                                            status = scanner.nextLine();
                                            try {
                                                tmp = Integer.parseInt(status);
                                            } catch (Exception e) {
                                            }
                                        }

                                        if (tmp >= 1 && tmp <= 4) {
                                            if (tmp == q.getCorrectAnswer()) {
                                                numberOfCorrectAnswer++;
                                                System.out.println("Правильно");
                                                break;
                                            } else {
                                                numberOfWrongAnswer++;
                                                System.out.println("Вы ошиблись");
                                                break;
                                            }
                                        }
                                    } while (true);
                                }
                                System.out.println("Количество правильных ответов - " + numberOfCorrectAnswer);
                                System.out.println("Количество не правильных ответов - " + numberOfWrongAnswer);

                                saveResult(login, r.getKey().getName(), numberOfCorrectAnswer, numberOfWrongAnswer);
                                saveTopUserResult(quizName, login, numberOfCorrectAnswer, numberOfWrongAnswer);
                                status = null;

                            }
                            count++;
                        }
                        status = null;

                    }
                    if ("2".equals(status)) {
                        List<QuizResults> qr;
                        for (Map.Entry<User, List<QuizResults>> res : resultMap.entrySet()) {
                            if (res.getKey().getLogin().equals(login)) {
                                qr = res.getValue();
                                System.out.println("Ваши результаты :");
                                for (QuizResults q : qr) {
                                    System.out.println("Викторина : " + q.getName());
                                    System.out.println("Количество правильных ответов : " + q.getNumberOfCorrectAnswer());
                                    System.out.println("Количество не правильных ответов : " + q.getNumberOfWrongAnswer());
                                }
                            }
                        }
                    }
                    if ("3".equals(status)) {
                        List<TopUserResult> lqr;
                        count = 1;
                        System.out.println("Выберете номер викторины");
                        for (Map.Entry<Quiz, List<TopUserResult>> r : quizResultUser.entrySet()) {
                            System.out.println(count + "- " + r.getKey().getName());
                            count++;
                        }
                        status = scanner.nextLine();
                        try {
                            tmp = Integer.parseInt(status);
                        } catch (Exception e) {
                            System.out.println("Введите правильно номер викторины");
                        }
                        count = 1;
                        for (Map.Entry<Quiz, List<TopUserResult>> rr : quizResultUser.entrySet()) {
                            if (count == tmp) {
                                lqr = rr.getValue();
                                for (TopUserResult wr : lqr) {
                                    System.out.println("Логин : " + wr.getUserName());
                                    System.out.println("Количество правильных ответов : " + wr.getNumberOfCorrectAnswer());
                                    System.out.println("Количество не правильных ответов : " + wr.getNumberOfWrongAnswer());
                                }
                            }
                            count++;
                        }

                    }
                    if ("4".equals(status)) {
                        String tmpStatus;
                        String tmpPassword;
                        String tmpDateOfBirth;
                        for (User us : userList) {
                            if (us.getLogin().equals(login)) {
                                do {
                                    System.out.println("Введите 1 - чтобы изменить пароль");
                                    System.out.println("Введите 2 - чтобы изменить дату рождения");
                                    System.out.println("exit - выход");
                                    tmpStatus = scanner.nextLine();
                                    if ("1".equals(tmpStatus)) {
                                        System.out.println("Введите новый пароль");
                                        tmpPassword = scanner.nextLine();
                                        us.setPassword(tmpPassword);
                                        System.out.println("Пароль изменнен");
                                    }
                                    if ("2".equals(tmpStatus)) {
                                        System.out.println("Введите новую дату рождения");
                                        tmpDateOfBirth = scanner.nextLine();
                                        us.setDateOfBirth(tmpDateOfBirth);
                                        System.out.println("Дата рождения изменена");
                                    }
                                } while (!"exit".equals(tmpStatus));

                            }
                        }
                    }
                } while (!("exit".equals(status)));

            }
            if ("2".equals(status)) {
                System.out.println("Ведите login");
                login = scanner.nextLine();
                if (isPresent(login)) {
                    System.out.println("Такой логин уже существует !!!");
                } else {
                    System.out.println("Введите password");
                    password = scanner.nextLine();
                    System.out.println("Введите дату рождения");
                    dateOfBirth = scanner.nextLine();
                    userList.add(new User(login, password, dateOfBirth));
                    resultMap.put(new User(login, password, dateOfBirth), new ArrayList<>());
                    System.out.println("Пользователь успешно добавлен");
                }
            }


        } while (!("exit".equals(status)));
        System.out.println(quizResultUser);


        saveObjectUser(userList, "UserDataBase.dat");
        saveObjectResultMap(resultMap, "ResultMap.dat");
        saveObjectTopUserResult(quizResultUser, "TopUserResult.dat");

    }

    public boolean isPresent(String login) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public boolean passwordTrue(String login) {
        String password;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getLogin().equals(login)) {
                System.out.println("Введите пароль");
                password = scanner.nextLine();
                if (userList.get(i).getPassword().equals(password)) {
                    return true;
                } else {
                    System.out.println("Вы ввели не правильный пароль");
                }
            }
        }
        return false;
    }

    public User user(String login) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getLogin().equals(login)) {
                return userList.get(i);
            }
        }
        return null;
    }

    public void saveResult(String login, String name, int numberOfCorrectAnswer, int numberOfWrongAnswer) {
        for (Map.Entry<User, List<QuizResults>> save : resultMap.entrySet()) {
            if (save.getKey().getLogin().equals(login)) {
                save.getValue().add(new QuizResults(name, numberOfCorrectAnswer, numberOfWrongAnswer));
            }
        }
    }


    private static void saveObjectUser(List<User> userList, String fileName) throws FileNotFoundException {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<User> readObjectUser(String fileName) throws FileNotFoundException {
        List<User> userList = null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            userList = (List<User>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userList;
    }

    private static void saveObjectResultMap(Map<User, List<QuizResults>> resultMap, String fileName) throws FileNotFoundException {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(resultMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<User, List<QuizResults>> readObjectResultMap(String fileName) throws FileNotFoundException {
        Map<User, List<QuizResults>> result = null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            result = (Map<User, List<QuizResults>>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
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

    private void saveTopUserResult(String quizName, String userName, int correctAnswer, int wrongAnswer) {
        for (Map.Entry<Quiz, List<TopUserResult>> rr : quizResultUser.entrySet()) {
            if (rr.getKey().getName().equals(quizName)) {
                rr.getValue().add(new TopUserResult(userName, correctAnswer, wrongAnswer));
            }
        }
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

