package fotnewshub;

public class NewsItem {
    private String headerText;
    private String subHeaderText;
    private String titleText;
    private String subtitleText;
    private String descriptionText;
    private String profileImage;
    private String imageUrl;

    public NewsItem(String headerText, String subHeaderText, String titleText, String subtitleText,
                    String descriptionText, String profileImage, String imageUrl) {
        this.headerText = headerText;
        this.subHeaderText = subHeaderText;
        this.titleText = titleText;
        this.subtitleText = subtitleText;
        this.descriptionText = descriptionText;
        this.profileImage = profileImage;
        this.imageUrl = imageUrl;
    }

    public String getHeaderText() {
        return headerText;
    }

    public String getSubHeaderText() {
        return subHeaderText;
    }

    public String getTitleText() {
        return titleText;
    }

    public String getSubtitleText() {
        return subtitleText;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public void setSubHeaderText(String subHeaderText) {
        this.subHeaderText = subHeaderText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public void setSubtitleText(String subtitleText) {
        this.subtitleText = subtitleText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
