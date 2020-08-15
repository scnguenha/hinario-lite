package mz.co.scn.hinario_lite.model;

import java.util.UUID;

/**
 * Created by Sidónio Goenha on 6/26/2019.
 */
public class Category {
    private String id;
    private String description;
    private int categoryLevel;
    private String superCategoryNumber;
    private String number;

    /**
     *
     */
    public Category() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     *
     * @param description
     * @param categoryLevel
     * @param superCategoryNumber
     * @param number
     */
    public Category(String description, int categoryLevel, String superCategoryNumber, String number) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.categoryLevel = categoryLevel;
        this.superCategoryNumber = superCategoryNumber;
        this.number = number;
    }

    /**
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return categoryLevel
     */
    public int getCategoryLevel() {
        return categoryLevel;
    }

    /**
     *
     * @param categoryLevel
     */
    public void setCategoryLevel(int categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    /**
     *
     * @return superCategoryNumber
     */
    public String getSuperCategoryNumber() {
        return superCategoryNumber;
    }

    /**
     *
     * @param superCategoryNumber
     */
    public void setSuperCategoryNumber(String superCategoryNumber) {
        this.superCategoryNumber = superCategoryNumber;
    }

    /**
     *
     * @return number
     */
    public String getNumber() {
        return number;
    }

    /**
     *
     * @param number
     */
    public void setNumber(String number) {
        this.number = number;
    }
}
