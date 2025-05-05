package com.example.plapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Top-level JSON response from /getQuiz.
 */
public class PlAppQuizResponse {
    @SerializedName("quiz")
    public List<PlAppQuizQuestion> quiz;
}
