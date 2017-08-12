package uk.krupa.me.kruton.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

@Component
public class KrutonBot extends Bot {

    private Map<String,String> definitions = new HashMap<>();

    @Value("${kruton.slack.token}")
    private String slackToken;

    @Override
    public String getSlackToken() {
        return slackToken;
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }

    @Controller(events = {EventType.DIRECT_MENTION}, pattern = "^<@[^>]+>\\s(.*?)\\sis\\s(.*)$")
    public void define(WebSocketSession session, Event event, Matcher matcher) throws IOException {
        definitions.put(matcher.group(1), matcher.group(2));
        reply(session, event, new Message("Definition of " + matcher.group(1) + " accepted"));
    }

    @Controller(events = {EventType.MESSAGE, EventType.DIRECT_MENTION}, pattern = "^.*?([a-zA-Z0-9_]+)\\?.*?$")
    public void requestDefinition(WebSocketSession session, Event event, Matcher matcher) throws IOException {
        String key = matcher.group(1);
        if (definitions.containsKey(key)) {
            String text = encode(key + " is " + definitions.get(key));
            Message reply = new Message("<@" + event.getUserId() + "> " + text);
            reply.setChannel(event.getChannelId());
            reply.setType(EventType.MESSAGE.name().toLowerCase());
            session.sendMessage(new TextMessage(reply.toJSONString()));
        } else if (event.getType().equals(EventType.DIRECT_MENTION.name())) {
            String text = encode("Sorry, I don't know about " + key);
            Message reply = new Message("<@" + event.getUserId() + "> " + text);
            reply.setChannel(event.getChannelId());
            reply.setType(EventType.MESSAGE.name().toLowerCase());
            session.sendMessage(new TextMessage(reply.toJSONString()));
        }
    }

    private String encode(String message) {
        return message == null ? null : message.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
