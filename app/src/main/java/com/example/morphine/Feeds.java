package com.example.morphine;

public class Feeds {
    private  String feed_Key;
    private  String feed_name;
    private  String feed_value;

    public Feeds() {

    }

    public String getFeed_Key() {
        return feed_Key;
    }

    public void setFeed_Key(String feed_Key) {
        this.feed_Key = feed_Key;
    }

    public String getFeed_name() {
        return feed_name;
    }

    public void setFeed_name(String feed_name) {
        this.feed_name = feed_name;
    }

    public String getFeed_value() {
        return feed_value;
    }

    public void setFeed_value(String feed_value) {
        this.feed_value = feed_value;
    }

    @Override
    public String toString() {
        return "Feeds{" +
                "feed_Key='" + feed_Key + '\'' +
                ", feed_name='" + feed_name + '\'' +
                ", feed_value='" + feed_value + '\'' +
                '}';
    }
}
