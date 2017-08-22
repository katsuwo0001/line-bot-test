package github.rshindo.rlb.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.social.twitter.api.MediaEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.util.StringUtils;

public class TwitterApiUtils {
	
	public static final String IMAGE_FILTER = " filter:images";
	
	public static List<String> getImageUrlList(Tweet tweet) {
		List<MediaEntity> mediaList = tweet.getEntities().getMedia();
		return mediaList.stream().map(media -> {
				return StringUtils.hasText(media.getMediaSecureUrl()) ? media.getMediaSecureUrl()
						: StringUtils.hasText(media.getMediaUrl()) ? media.getMediaUrl()
						: "";
			})
			.filter(StringUtils::hasText)
			.collect(Collectors.toList());
	}

}
