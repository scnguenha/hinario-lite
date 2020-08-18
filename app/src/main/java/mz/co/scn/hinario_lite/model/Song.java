package mz.co.scn.hinario_lite.model;

/**
 * Created by Sidónio Goenha on 14/08/2020.
 */
public class Song {

    private String title;
    private int number;
    private String key;
    private String mid;
    private String composer;
    private String lyric;
    private String book;
    private Category category;
    private boolean favorite;
    private String link;

    /**
     *
     */
    public Song() {
    }

    /**
     * @param title
     * @param number
     * @param book
     * @param lyric
     */
    public Song(String title, int number, String book, String lyric) {
        this.title = title;
        this.number = number;
        this.lyric = lyric;
        this.book = book;
    }

    /**
     * @param title
     * @param number
     * @param book
     * @param lyric
     */
    public Song(int number, String title,String composer, String book, String lyric) {
        this.title = title;
        this.number = number;
        this.lyric = lyric;
        this.book = book;
        this.composer = composer;
    }

    /**
     *
     * @param number
     * @param title
     * @param composer
     * @param category
     * @param key
     */
    public Song(int number, String title, String composer, Category category, String key) {
        this.number = number;
        this.title = title;
        this.composer = composer;
        this.category = category;
        this.key = key;
    }


    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return title
     */
    public String getTitleNumber() {
        return number + ". " + title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @return book
     */
    public String getBook() {
        return book;
    }

    /**
     * @param book
     */
    public void setBook(String book) {
        this.book = book;
    }

    /**
     * @return lyric
     */
    public String getLyric() {
        return lyric;
    }

    /**
     * @param lyric
     */
    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    /**
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return mid
     */
    public String getMid() {
        return mid;
    }

    /**
     * @param mid
     */
    public void setMid(String mid) {
        this.mid = mid;
    }

    /**
     * @return composer
     */
    public String getComposer() {
        return composer;
    }

    /**
     * @param composer
     */
    public void setComposer(String composer) {
        this.composer = composer;
    }

    /**
     * @return category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return favorite
     */
    public boolean isFavorite() {
        return favorite;
    }

    /**
     * @param favorite
     */
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    /**
     * @return link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link
     */
    public void setLink(String link) {
        this.link = link;
    }

}
