package github.rshindo.rlb;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import com.linecorp.bot.client.LineMessagingService;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import github.rshindo.rlb.util.TwitterApiUtils;

/**
 * 
 * @author Ryo Shindo
 *
 */
@Service
public class ReplyMessageHandler {
	
	private static final String RIETION = "りえしょん";
	private static final String MURAKAWA_RIE = "村川梨衣";
	private static final String ROBOT = "ロボ";
	
	@Value("${rietionbot.twitter.robot}")
	@NotNull
	private String robotAccountId; 
	
	@Value("${rietionbot.twitter.riemagic}")
	@NotNull
	private String riemagicAccountId;
	
	private final Twitter twitter;
	private final LineMessagingService lineMessagingService;
	
	public ReplyMessageHandler(Twitter twitter, LineMessagingService lineMessagingService) {
		super();
		this.twitter = twitter;
		this.lineMessagingService = lineMessagingService;
	}

	public BotApiResponse reply(MessageEvent<TextMessageContent> event) throws IOException {
		
		String receivedMessage = event.getMessage().getText();
		String replyToken = event.getReplyToken();
		List<Message> messages = null;
		switch (receivedMessage) {
			case RIETION:
				messages = searchRietionImages();
				break;
				
			case MURAKAWA_RIE:
				messages = searchRieMurakawaImages();
				break;
				
			case ROBOT:
				messages = searchRobotTexts();
				break;
	
			default:
				messages = searchRiemagicTexts();
				break;
		}
		return lineMessagingService
			.replyMessage(new ReplyMessage(replyToken, messages))
			.execute()
			.body();
		
	}
	
	protected List<Message> searchRietionImages() {
		return searchImages(RIETION + TwitterApiUtils.IMAGE_FILTER);
	}
	
	protected List<Message> searchRieMurakawaImages() {
		return searchImages(MURAKAWA_RIE + TwitterApiUtils.IMAGE_FILTER);
	}
	
	protected List<Message> searchImages(String searchKey) {
		
		SearchResults results = twitter.searchOperations().search(
				new SearchParameters(searchKey)
					.count(10));
		List<Tweet> tweets = results.getTweets();
		List<Message> messages = tweets.stream()
				.flatMap(tweet -> TwitterApiUtils.getImageUrlList(tweet).stream())
				.limit(3)
				.map(url -> new ImageMessage(url, url))
				.collect(Collectors.toList());
		if(messages.isEmpty()) {
			return Arrays.asList(new TextMessage("画像が見つかりませんでした"));
		} else {
			return messages;
		}
	}
	
	//@robo_shon
	protected List<Message> searchRobotTexts() {
		return searchTexts(robotAccountId);
	}
	
	protected List<Message> searchRiemagicTexts() {
		return searchTexts(riemagicAccountId);
	}
	
	protected List<Message> searchTexts(String searchKey) {
		SearchResults results = twitter.searchOperations().search(
				new SearchParameters(searchKey)
					.count(1));
		List<Message> messages = results.getTweets().stream()
				.map(tweet -> new TextMessage(tweet.getText()))
				.collect(Collectors.toList());
		if(messages.isEmpty()) {
			return Arrays.asList(new TextMessage("ツイートが見つかりません"));
		} else {
			return messages;
		}
	}
	
	
}
