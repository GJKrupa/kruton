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
import java.util.regex.Matcher;

@Component
public class KrutonBot extends Bot {

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

    @Controller(events = EventType.MESSAGE, pattern = "^.*?([a-zA-Z0-9_]+)\\?.*?$")
    public void onReceiveMessage(WebSocketSession session, Event event, Matcher matcher) throws IOException {
        Message reply = new Message("<@" + event.getUserId() + "> asked a question about " + matcher.group(1));
        reply.setChannel(event.getChannelId());
        reply.setType(EventType.MESSAGE.name().toLowerCase());
        session.sendMessage(new TextMessage(reply.toJSONString()));
    }
}
