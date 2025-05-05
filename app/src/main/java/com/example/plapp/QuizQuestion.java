package com.example.plapp;

import java.util.List;

// This class represents a single quiz question with its options and the correct answer.
public class QuizQuestion {
    public String question;         // The actual question text
    public List<String> options;    // A list of answer options (usually 3)
    public String correctAnswer;    // The correct answer from the options

    // Constructor to create a new quiz question
    public QuizQuestion(String question, List<String> options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}
