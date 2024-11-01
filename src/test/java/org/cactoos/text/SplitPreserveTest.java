package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

import java.util.ArrayList;
import java.util.Iterator;

public class SplitPreserveTest {
    @Test
    void checkingSplit() throws Exception {
        String txt = "   ";
        ArrayList<Text> array = new ArrayList<>();
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        new Assertion<>(
                "Adjacent separators must create an empty element",
                getLength(new Split(new TextOf(txt), new TextOf(" ")).iterator()),
                IsNot.not(Matchers.equalTo(getLength(new IterableOf<Text>(array.iterator()).iterator())))
        ).affirm();

        txt = " how ";
        array = new ArrayList<>();
        array.add(new TextOf(""));
        array.add(new TextOf("how"));
        array.add(new TextOf(""));

        new Assertion<>(
                "Adjacent separators must create an empty element",
                getLength(new Split(new TextOf(txt), new TextOf(" ")).iterator()),
                IsNot.not(Matchers.equalTo(getLength(new IterableOf<Text>(array.iterator()).iterator())))
        ).affirm();
    }

    int getLength(Iterator<Text> iter) {
        int count = 0;
        while (iter.hasNext()) {
            iter.next();
            count++;
        }
        return count;
    }

    @Test
    void checkingSplitPreserveTokens() throws Exception {
        String txt = "   ";
        ArrayList<Text> array = new ArrayList<>();
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        array.add(new TextOf(""));

        new Assertion<>(
                "Adjacent separators must create an empty element",
                getLength(new SplitPreserveAllTokens(new TextOf(txt), new TextOf(" ")).iterator()),
                Matchers.equalTo(getLength(new IterableOf<Text>(array.iterator()).iterator()))
        ).affirm();

        txt = "lol\\  / dude";
        array = new ArrayList<>();
        array.add(new TextOf("lol\\"));
        array.add(new TextOf(""));
        array.add(new TextOf("/"));
        array.add(new TextOf("dude"));

        new Assertion<>(
                "Adjacent separators must create an empty element",
                getLength(new SplitPreserveAllTokens(new TextOf(txt), new TextOf(" ")).iterator()),
                Matchers.equalTo(getLength(new IterableOf<Text>(array.iterator()).iterator()))
        ).affirm();

        txt = " how ";
        array = new ArrayList<>();
        array.add(new TextOf(""));
        array.add(new TextOf("how"));
        array.add(new TextOf(""));

        new Assertion<>(
                "Adjacent separators must create an empty element",
                getLength(new SplitPreserveAllTokens(new TextOf(txt), new TextOf(" ")).iterator()),
                Matchers.equalTo(getLength(new IterableOf<Text>(array.iterator()).iterator()))
        ).affirm();
    }
}