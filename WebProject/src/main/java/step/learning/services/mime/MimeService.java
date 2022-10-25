package step.learning.services.mime;

import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class MimeService {
    private Map<String, String> imageTypes;

    public MimeService() {
        imageTypes = new HashMap<>();
        imageTypes.put(".png", "image/png");
        imageTypes.put(".jpg", "image/jpeg");
        imageTypes.put(".jpeg", "image/jpeg");
        imageTypes.put(".webp", "image/webp");
        imageTypes.put(".bmp", "image/bmp");
        imageTypes.put(".gif", "image/gif");
    }

    /**
     * Checks if the given extension is an image type
     *
     * @param extension with dot char like '.png'
     * @return true if extension is registered image MIME type
     */
    public boolean isImage(String extension) {
        return imageTypes.containsKey(extension);
    }

    /**
     * Get MIME type for given file extension
     *
     * @param extension with dot char like '.png'
     * @return MIME type or null
     */
    public String getMimeType(String extension) {
        if (imageTypes.containsKey(extension)) {
            return imageTypes.get(extension);
        }

        return null;
    }
}