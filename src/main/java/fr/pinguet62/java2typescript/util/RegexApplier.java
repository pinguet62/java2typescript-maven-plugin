package fr.pinguet62.java2typescript.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexApplier {

    private String value;

    private RegexApplier(String value) {
        this.value = value;
    }

    public static RegexApplier from(String input) {
        return new RegexApplier(input);
    }

    public RegexApplier extract(String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.find())
            throw new IllegalArgumentException("For input " + value + " the regex " + regex + " found 0 value");
        value = matcher.group();
        return this;
    }

    public RegexApplier remove(String regex) {
        value = value.replaceAll(regex, "");
        return this;
    }

    @Override
    public String toString() {
        return value;
    }

}