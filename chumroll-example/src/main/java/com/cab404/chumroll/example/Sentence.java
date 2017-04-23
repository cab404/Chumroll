package com.cab404.chumroll.example;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Well, sorry for no comments here!
 * Still you can send me your question to me@cab404.ru!
 * <p/>
 * Created at 13:13 on 09/03/16
 *
 * @author cab404
 */
public class Sentence {
    public String prefix, word;

    /**
     * Method to generate some words
     */
    public static Sentence generateSentence(Context ctx) {
        Sentence sentence = new Sentence();
        String[] prefixes = ctx.getResources().getStringArray(R.array.prefix);

        String prefix = prefixes[((int) (prefixes.length * Math.random()))];

        sentence.prefix = prefix;
        sentence.word = generateWord(ctx);

        return sentence;
    }

    /**
     * Another method to generate some words
     */
    public static String generateWord(Context ctx) {
        String[] parts = ctx.getResources().getStringArray(R.array.start_part);
        String[] words = ctx.getResources().getStringArray(R.array.end_part);
        String end = words[((int) (words.length * Math.random()))];
        ArrayList<String> nominatedParts = new ArrayList<>(Arrays.asList(parts));
        StringBuilder word = new StringBuilder();

        int partSize = (int) (Math.sqrt(Math.random()) * 4);
        for (int i = 0; i < partSize; i++)
            word.append(nominatedParts.remove((int) (Math.random() * nominatedParts.size())));

        word.append(end);
        return word.toString();
    }

}
