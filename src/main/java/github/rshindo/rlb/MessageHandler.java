package github.rshindo.rlb;


import java.io.IOException;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

/**
 * 
 * @author Ryo Shindo
 *
 */
@LineMessageHandler
public class MessageHandler {

	private final ReplyMessageHandler replyMessageHandler;
	
	public MessageHandler(ReplyMessageHandler replyMessageHandler) {
		super();
		this.replyMessageHandler = replyMessageHandler;
	}

	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws IOException {
		System.out.println("event: " + event);
		BotApiResponse response = replyMessageHandler.reply(event);
		System.out.println("Sent messages: " + response);
	}
	
	@EventMapping
	public void defaultMessageEvent(Event event) {
		System.out.println("event: " + event);
	}
	
}
