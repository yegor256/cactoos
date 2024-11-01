package org.cactoos.func;

import org.cactoos.BiFunc;
import org.cactoos.list.ListOf;

import java.util.Collection;

public class BiFuncSplitPreserve implements BiFunc<String, String, Collection<String>> {
    @Override
    public Collection<String> apply(String str, String regex) throws Exception {
        ListOf<String> ret = new ListOf<>();
        int start = 0;
        int pos = str.indexOf(regex);
        while (pos >= start) {
            ret.add(str.substring(start, pos));
            start = pos + regex.length();
            pos = str.indexOf(regex, start);
        }
        if (start < str.length())
            ret.add(str.substring(start));
        else if (start == str.length())
            ret.add("");
        return ret;
    }
}
