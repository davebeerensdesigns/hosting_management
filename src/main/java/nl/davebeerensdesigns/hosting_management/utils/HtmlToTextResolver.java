package nl.davebeerensdesigns.hosting_management.utils;

import org.jsoup.Jsoup;

public class HtmlToTextResolver {
    public static String HtmlToText(String html) {
        return Jsoup.parse(html).text();
    }
}