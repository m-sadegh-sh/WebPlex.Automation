package ir.webplex.android.automation.models;

public class ProfileItem extends DrawerItem {
    private String mPostTitle;
    private String mEmail;
    private String mAvatar;

    public ProfileItem(long id, String postTitle, String email, String avatar) {
        super(id);
        mPostTitle = postTitle;
        mEmail = email;
        mAvatar = avatar;
    }

    public String getPostTitle() {
        return mPostTitle;
    }

    public void setPostTitle(String postTitle) {
        mPostTitle = postTitle;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }
}
