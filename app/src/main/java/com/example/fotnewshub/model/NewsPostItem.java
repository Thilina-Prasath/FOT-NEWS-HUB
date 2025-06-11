package fotnewshub;

public class NewsPostItem {

    private String profileImage;
    private String headerText;
    private String subHeaderText;
    private String imageUrl;
    private String titleText;
    private String subtitleText;
    private String descriptionText;

    public NewsPostItem() {
        // Default constructor required for Firebase
    }

    public NewsPostItem(String profileImage, String headerText, String subHeaderText, String imageUrl,
                        String titleText, String subtitleText, String descriptionText) {
        this.profileImage = profileImage;
        this.headerText = headerText;
        this.subHeaderText = subHeaderText;
        this.imageUrl = imageUrl;
        this.titleText = titleText;
        this.subtitleText = subtitleText;
        this.descriptionText = descriptionText;
    }

    // Getters and Setters
    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public String getSubHeaderText() {
        return subHeaderText;
    }

    public void setSubHeaderText(String subHeaderText) {
        this.subHeaderText = subHeaderText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public String getSubtitleText() {
        return subtitleText;
    }

    public void setSubtitleText(String subtitleText) {
        this.subtitleText = subtitleText;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }
}
