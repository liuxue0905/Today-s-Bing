package binggallery.chinacloudsites.cn;

/**
 * Created by liuxue on 2017/1/2 0002.
 */

public class BingGalleryImageUtil {

    public static Image bingGalleryImage2Image(BingGalleryImage bingGalleryImage) {
        return new Image(bingGalleryImage.getUid(), bingGalleryImage.getMinpix(), bingGalleryImage.getMaxpix(), bingGalleryImage.getCopyright(), bingGalleryImage.getDesc());
    }

    public static BingGalleryImage image2BingGalleryImage(Image image) {
        return new BingGalleryImage(null, image.getUid(), image.getMinpix(), image.getMaxpix(), image.getCopyright(), image.getDesc());
    }

    public static BingGalleryImage[] images2BingGalleryImages(Image[] images) {
        BingGalleryImage[] bingGalleryImages = new BingGalleryImage[images.length];

        for (int i = 0; i < images.length; i++) {
            Image image = images[i];
            bingGalleryImages[i] = BingGalleryImageUtil.image2BingGalleryImage(image);
        }

        return bingGalleryImages;
    }
}
