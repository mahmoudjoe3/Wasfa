package com.mahmoudjoe3.wasfaty.pojo;

public class CommentPost {
    private int Id;
    private int UserId;
    private int RecipeId;
    private boolean IsCreator;
    private long CreatedDate;
    private String ReviewText;
    private double ReviewResult;

    public CommentPost(Comment comment, double commentPolarity) {
        this.Id = comment.getId();
        this.UserId = comment.getUserId();
        this.RecipeId = comment.getRecipeId();
        this.IsCreator = comment.isCreator();
        this.CreatedDate = comment.getCreatedTime();
        this.ReviewText = comment.getCommentText();
        this.ReviewResult =  commentPolarity;
    }



    @Override
    public String toString() {
        return "CommentPost{" +
                "id=" + Id +
                ", userId=" + UserId +
                ", recipeId=" + RecipeId +
                ", creator=" + IsCreator +
                ", createdTime=" + CreatedDate +
                ", commentText='" + ReviewText + '\'' +
                ", reviewResult=" + ReviewResult +
                '}';
    }


}
