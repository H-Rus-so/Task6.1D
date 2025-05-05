package com.example.plapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Represents one question in the quiz.
 */
public class PlAppQuizQuestion {
    @SerializedName("question")
    public String question;

    @SerializedName("options")
    public List<String> options;

    @SerializedName("correct_answer")
    public String correctAnswer; // e.g. "A", "B", "C"
}
